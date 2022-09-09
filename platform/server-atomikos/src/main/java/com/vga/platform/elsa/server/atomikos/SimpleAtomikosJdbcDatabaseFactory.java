/*
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vga.platform.elsa.server.atomikos;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.vga.platform.elsa.common.core.l10n.SupportedLocalesProvider;
import com.vga.platform.elsa.common.core.model.common.ClassMapper;
import com.vga.platform.elsa.common.core.model.common.EnumMapper;
import com.vga.platform.elsa.common.core.model.common.IdGenerator;
import com.vga.platform.elsa.common.core.model.common.Xeption;
import com.vga.platform.elsa.common.core.reflection.ReflectionFactory;
import com.vga.platform.elsa.common.meta.custom.CustomMetaRegistry;
import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistry;
import com.vga.platform.elsa.core.storage.database.Database;
import com.vga.platform.elsa.core.storage.database.DatabaseFactory;
import com.vga.platform.elsa.core.storage.database.jdbc.JdbcClassMapperImpl;
import com.vga.platform.elsa.core.storage.database.jdbc.JdbcDatabase;
import com.vga.platform.elsa.core.storage.database.jdbc.JdbcEnumMapperImpl;
import com.vga.platform.elsa.core.storage.database.jdbc.JdbcIdGeneratorImpl;
import com.vga.platform.elsa.core.storage.database.jdbc.adapter.JdbcDataSourceProvider;
import com.vga.platform.elsa.core.storage.database.jdbc.model.JdbcDatabaseMetadataProvider;
import com.vga.platform.elsa.core.storage.database.jdbc.structureUpdater.JdbcStructureUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SimpleAtomikosJdbcDatabaseFactory implements DatabaseFactory {

    private Database database;

    private UserTransactionManager userTransactionManager;

    @Value("${elsa.storage.adapter:hsqldb}")
    private String adapterId;

    @Value("${elsa.storage.url:}")
    private String url;

    @Value("${elsa.storage.poolSize:5}")
    private int poolSize;

    @Value("${elsa.storage.login:}")
    private String login;

    @Value("${elsa.storage.password:}")
    private String password;

    @Autowired
    private Environment env;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Autowired
    private List<JdbcDataSourceProvider> providers;

    @Autowired
    private JdbcDatabaseMetadataProvider jdbcDatabaseMetadataProvider;

    @Autowired
    private DomainMetaRegistry domainMetaRegistry;

    @Autowired
    private CustomMetaRegistry customMetaRegistry;

    @Autowired
    private ReflectionFactory reflectionFactory;

    @Autowired
    private SupportedLocalesProvider supportedLocalesProvider;

    private ClassMapper classMapper;

    private EnumMapper enumMapper;

    private IdGenerator idGenerator;

    private PlatformTransactionManager transactionManager;

    private DataSource ds;

    @PostConstruct
    public void init() throws Exception {
        var adapter = providers.stream().filter(it -> it.getId().equals(adapterId)).findFirst().orElse(null);
        if(adapter == null){
            throw Xeption.forDeveloper("unsupported adapter id: %s".formatted(adapterId));
        }
        var props = new HashMap<String,Object>();
        props.put("login", login);
        props.put("password", password);
        props.put("poolSize", poolSize);
        props.put("url", url);
        var autoCommitTemplate = new JdbcTemplate(adapter.createDataSource(props));
        var noAutoCommitDataSource = new AtomikosDataSourceProxy();
        noAutoCommitDataSource.setPoolSize(poolSize);
        noAutoCommitDataSource.setXaDataSource(adapter.createXADataSource(props));
        noAutoCommitDataSource.setUniqueResourceName("main");
        noAutoCommitDataSource.setLocalTransactionMode(true);
        ds = noAutoCommitDataSource;
        noAutoCommitDataSource.init();
        var dialect = adapter.createDialect(ds);
        var storageTemplate = new JdbcTemplate(noAutoCommitDataSource);
        JdbcStructureUpdater.updateStructure(autoCommitTemplate, dialect, jdbcDatabaseMetadataProvider);
        classMapper = new JdbcClassMapperImpl(domainMetaRegistry, customMetaRegistry, autoCommitTemplate ,dialect);
        enumMapper = new JdbcEnumMapperImpl(domainMetaRegistry, autoCommitTemplate ,supportedLocalesProvider, dialect);
        database = new JdbcDatabase(storageTemplate, jdbcDatabaseMetadataProvider, enumMapper, classMapper,
                dialect, domainMetaRegistry, reflectionFactory);
        idGenerator = new JdbcIdGeneratorImpl(autoCommitTemplate, dialect);
        userTransactionManager = new UserTransactionManager();
        userTransactionManager.setTransactionTimeout(300);
        userTransactionManager.setForceShutdown(true);
        userTransactionManager.init();
        var jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager);
        jtaTransactionManager.setUserTransaction(userTransactionManager);
        transactionManager = jtaTransactionManager;
    }

    private DataSource createAtomikosDS(XADataSource xaDataSource, boolean autocommit, int poolSize) {
        return new AtomikosDataSourceBean() {
            {
                setPoolSize(poolSize);
            }

            @Override
            public Connection getConnection() throws SQLException {
                var conn = super.getConnection();
                conn.setAutoCommit(autocommit);
                return conn;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                var conn = super.getConnection(username, password);
                conn.setAutoCommit(autocommit);
                return conn;
            }

        };
    }

    @Override
    public Database getPrimaryDatabase() {
        return database;
    }

    @Override
    public ClassMapper getClassMapper() {
        return classMapper;
    }

    @Override
    public EnumMapper getEnumMapper() {
        return enumMapper;
    }

    @Override
    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Override
    public DataSource getFakeDataSource() {
        return ds;
    }

    @PreDestroy
    public void preDestroy(){
        ((AtomikosDataSourceBean)ds).close();
        userTransactionManager.close();
    }


}


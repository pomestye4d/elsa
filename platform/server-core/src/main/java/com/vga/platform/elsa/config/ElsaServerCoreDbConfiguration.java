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

package com.vga.platform.elsa.config;

import com.vga.platform.elsa.common.core.model.common.ClassMapper;
import com.vga.platform.elsa.common.core.model.common.EnumMapper;
import com.vga.platform.elsa.common.core.model.common.IdGenerator;
import com.vga.platform.elsa.core.storage.database.Database;
import com.vga.platform.elsa.core.storage.database.DatabaseFactory;
import com.vga.platform.elsa.core.storage.transaction.ElsaTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ElsaServerCoreDbConfiguration {

    @Autowired
    private DatabaseFactory factory;

    @Bean
    public Database primaryDatabase(){
        return factory.getPrimaryDatabase();
    }

    @Bean
    public ClassMapper classMapper(){
        return factory.getClassMapper();
    }

    @Bean
    public EnumMapper enumMapper(){
        return factory.getEnumMapper();
    }

    @Bean
    public IdGenerator idGenerator(){
        return factory.getIdGenerator();
    }

    @Bean
    public DataSource fakeDataSource(){
        return factory.getFakeDataSource();
    }

    @Bean
    public  PlatformTransactionManager transactionManager(){
        return factory.getTransactionManager();
    }

    @Bean
    public ElsaTransactionManager elsaTransactionManager(){
        return new ElsaTransactionManager();
    }

}

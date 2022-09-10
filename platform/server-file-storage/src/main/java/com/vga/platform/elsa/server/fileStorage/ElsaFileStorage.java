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

package com.vga.platform.elsa.server.fileStorage;

import com.atomikos.datasource.ResourceException;
import com.atomikos.datasource.xa.XATransactionalResource;
import com.atomikos.icatch.config.Configuration;
import com.vga.platform.elsa.common.core.model.common.CallableWithExceptionAndArgument;
import com.vga.platform.elsa.common.core.utils.ExceptionUtils;
import com.vga.platform.elsa.core.storage.transaction.ElsaTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.xadisk.bridge.proxies.interfaces.XAFileOutputStream;
import org.xadisk.bridge.proxies.interfaces.XAFileSystem;
import org.xadisk.bridge.proxies.interfaces.XAFileSystemProxy;
import org.xadisk.bridge.proxies.interfaces.XASession;
import org.xadisk.filesystem.standalone.StandaloneFileSystemConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ElsaFileStorage {

    private XAFileSystem xafs;
    private InputStreamHandler<InputStream> standardInputStreamHanlder = new InputStreamHandler<>() {
        @Override
        public int read(InputStream is, byte[] buffer) throws Exception {
            return is.read(buffer);
        }

        @Override
        public void close(InputStream is) throws IOException {
            is.close();
        }
    };
    private OutputStreamHandler<XAFileOutputStream> xaFileOutputStreamHandler = new OutputStreamHandler<>() {
        @Override
        public void write(XAFileOutputStream os, byte[] buffer, int start, int length) throws Exception {
            os.write(buffer, start, length);
        }

        @Override
        public void close(XAFileOutputStream os) throws Exception {
            os.close();
        }

        @Override
        public void flush(XAFileOutputStream os) throws Exception {
            os.flush();
        }
    };

    @Autowired
    private ElsaTransactionManager transactionManager;

    @PostConstruct
    public void postConstruct() throws Exception {
        var configuration = new StandaloneFileSystemConfiguration(new File("temp/xa-disk").getAbsolutePath(), "1");
        xafs = XAFileSystemProxy.bootNativeXAFileSystem(configuration);
        xafs.waitForBootup(-1);
        Configuration.addResource(new XATransactionalResource("file-storage") {
            @Override
            protected XAResource refreshXAConnection() throws ResourceException {
                XAResource xarXADisk = (XAResource) transactionManager.getContext().getAttributes().get("xa-disk-resource");
                if(xarXADisk == null){
                    XASession xaSession = xafs.createSessionForXATransaction();
                    xarXADisk = xaSession.getXAResource();
                    transactionManager.getContext().getAttributes().put("xa-disk-resource", xarXADisk);
                    transactionManager.getContext().getAttributes().put("xa-disk-session", xaSession);
                }
                return xarXADisk;
            }
        });
    }

    public void write(File file, InputStream is) {
        withTransaction((session) -> {
//            var os = session.createXAFileOutputStream(file, true);
//            copyStream(is, os, standardInputStreamHanlder, xaFileOutputStreamHandler);
            session.createFile(file, false);
            return null;
        });
    }

    private <IS, OS> void copyStream(IS is, OS os, InputStreamHandler<IS> ish, OutputStreamHandler<OS> osh) throws Exception {

        try {
            final byte[] buffer = new byte[256];
            int n;
            long count = 0;
            while (-1 != (n = ish.read(is, buffer))) {
                osh.write(os, buffer, 0, n);
                count += n;
            }
            osh.flush(os);
        } finally {
            osh.close(os);
        }
    }

    private <P> P withTransaction(CallableWithExceptionAndArgument<P, XASession> func) {
        return ExceptionUtils.wrapException(() -> {
            XAResource xarXADisk = (XAResource) transactionManager.getContext().getAttributes().get("xa-disk-resource");
            XASession xaSession = (XASession) transactionManager.getContext().getAttributes().get("xa-disk-session");
            if(xarXADisk == null){
                xaSession = xafs.createSessionForXATransaction();
                xarXADisk = xaSession.getXAResource();
                transactionManager.getContext().getAttributes().put("xa-disk-resource", xarXADisk);
                transactionManager.getContext().getAttributes().put("xa-disk-session", xaSession);
            }
            Objects.requireNonNull(((JtaTransactionManager) transactionManager.getPlatformTransactionManager())
                    .getTransactionManager()).getTransaction().enlistResource(xarXADisk);
            return func.call(xaSession);
        });
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        xafs.shutdown();
    }

    interface InputStreamHandler<S> {

        int read(S is, byte[] buffer) throws Exception;

        void close(S is) throws Exception;
    }

    interface OutputStreamHandler<S> {

        void write(S os, byte[] buffer, int start, int length) throws Exception;

        void close(S os) throws Exception;

        void flush(S os) throws Exception;
    }
}


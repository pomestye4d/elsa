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

package com.vga.platform.elsa.core.storage.transaction;

import com.vga.platform.elsa.common.core.model.common.CallableWithExceptionAndArgument;
import com.vga.platform.elsa.common.core.model.common.RunnableWithExceptionAndArgument;
import com.vga.platform.elsa.common.core.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

public class ElsaTransactionManager {

    private final ThreadLocal<ElsaTransactionContext> contexts = new ThreadLocal<>();

    private TransactionTemplate readOnlyTemplate;

    private TransactionTemplate template;

    @Autowired
    public void setTransactionManager(PlatformTransactionManager dstm) {
        template = new TransactionTemplate(dstm);
        readOnlyTemplate = new TransactionTemplate(dstm);
        readOnlyTemplate.setReadOnly(true);
    }

    public Transaction getTransaction() throws SystemException {
        var tm = ((TransactionManager) template.getTransactionManager());
        return tm== null? null: tm.getTransaction();
    }

    public void withTransaction(RunnableWithExceptionAndArgument<ElsaTransactionContext> func) {
        withTransaction((context) ->{
            func.run(context);
            return (Void) null;
        }, false);
    }

    @SuppressWarnings("UnusedReturnValue")
    public<P> P withTransaction(CallableWithExceptionAndArgument<P, ElsaTransactionContext> func, boolean readonly) {
        return ExceptionUtils.wrapException(() -> {
            var owner = contexts.get() == null;
            try {
                if (owner) {
                    var context = new ElsaTransactionContext();
                    contexts.set(context);
                    return (readonly? readOnlyTemplate : template).execute((status) ->
                            ExceptionUtils.wrapException(() -> func.call(context)));
                } else {
                    return func.call(contexts.get());
                }
            } finally {
                if (owner) {
                    var callbacks = contexts.get().getPostCommitCallbacks();
                    contexts.remove();
                    callbacks.forEach(ExceptionUtils::wrapException);
                }
            }
        });
    }


}

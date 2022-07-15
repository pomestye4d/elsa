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

package com.vga.platform.elsa.core.storage.standard;

import com.vga.platform.elsa.common.core.model.common.IdGenerator;
import com.vga.platform.elsa.common.core.model.domain.BaseAsset;
import com.vga.platform.elsa.common.core.model.domain.BaseDocument;
import com.vga.platform.elsa.core.storage.OperationContext;
import com.vga.platform.elsa.core.storage.StorageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;


public class IdUpdaterInterceptor implements StorageInterceptor {
    @Autowired
    private IdGenerator idGenerator;

    @Override
    public double getPriority() {
        return 0;
    }

    @Override
    public <A extends BaseAsset> void onSave(A asset, OperationContext<A> context) {
        if(asset.getId() == -1){
            asset.setId(idGenerator.nextId());
        } else {
            idGenerator.ensureIdRegistered(asset.getId());
        }
    }

    @Override
    public <D extends BaseDocument> void onSave(D doc, OperationContext<D> context) {
        if(doc.getId() == -1){
            doc.setId(idGenerator.nextId());
        } else {
            idGenerator.ensureIdRegistered(doc.getId());
        }
    }
}

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

package com.vga.platform.elsa.demo.activator;

import com.vga.platform.elsa.common.core.boot.ElsaActivator;
import com.vga.platform.elsa.core.storage.Storage;
import com.vga.platform.elsa.demo.model.domain.DemoUserAccount;
import com.vga.platform.elsa.demo.model.domain.DemoUserAccountProjection;
import com.vga.platform.elsa.demo.model.domain.DemoUserAccountProjectionFields;
import org.springframework.beans.factory.annotation.Autowired;

public class ElsaDemoActivator implements ElsaActivator {

    @Autowired
    private Storage storage;
    @Override
    public void activate() {
        if(storage.findUniqueDocument(DemoUserAccountProjection.class,
                DemoUserAccountProjectionFields.name, "admin", false) == null){
            {
                var adminProfile = new DemoUserAccount();
                adminProfile.setName("admin");
                storage.saveDocument(adminProfile);
            }
            for(int n =0; n < 5; n++){
                var adminProfile = new DemoUserAccount();
                adminProfile.setName("user %s".formatted(n));
                storage.saveDocument(adminProfile);
            }
        }
    }

    @Override
    public double getPriority() {
        return 10;
    }
}

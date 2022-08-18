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

package com.vga.platform.elsa.demo.userAccount;

import com.vga.platform.elsa.common.core.utils.TextUtils;
import com.vga.platform.elsa.core.remoting.RemotingServerCallContext;
import com.vga.platform.elsa.core.remoting.RemotingServerCallHandler;
import com.vga.platform.elsa.core.storage.Storage;
import com.vga.platform.elsa.demo.DemoElsaServerL10nFactory;
import com.vga.platform.elsa.demo.model.domain.DemoUserAccount;
import com.vga.platform.elsa.demo.model.remoting.*;
import com.vga.platform.elsa.demo.server.DemoElsaRemotingConstants;
import com.vga.platform.elsa.demo.server.DemoElsaRestController;
import com.vga.platform.elsa.demo.ui.UserEditorVV;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoSiteUpdateUserHandler implements RemotingServerCallHandler<DemoSiteUpdateUserRequest, DemoSiteUpdateUserResponse> {
    @Autowired
    private Storage storage;
    @Override
    public String getId() {
        return DemoElsaRemotingConstants.DEMO_SITE_UPDATE_USER;
    }

    @Autowired
    DemoElsaRestController restController;

    @Autowired
    DemoElsaServerL10nFactory l10nFactory;

    @Override
    public DemoSiteUpdateUserResponse service(DemoSiteUpdateUserRequest request, RemotingServerCallContext context){
        var result = new DemoSiteUpdateUserResponse();
        if(TextUtils.isBlank(request.getData().getUserName())){
            var val = new UserEditorVV();
            val.setUserName(l10nFactory.Field_is_empty());
            result.setSuccess(false);
            result.setValidation(val);
            return result;
        }
        DemoUserAccount user;
        if(request.getUserId() == -1){
            user = new DemoUserAccount();
        } else {
            user = storage.loadDocument(DemoUserAccount.class, request.getUserId(), true);
        }
        user.setName(request.getData().getUserName());
        storage.saveDocument(user);
        result.setSuccess(true);
        restController.sendSubscriptionEvent(DemoElsaRemotingConstants.DEMO_SITE_SUBSCRIBE_USERS_MODIFICATION, new UserModificationSubscriptionEvent());
        return result;
    }
}

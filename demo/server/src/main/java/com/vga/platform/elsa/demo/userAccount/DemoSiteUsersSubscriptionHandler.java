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

import com.vga.platform.elsa.core.remoting.RemotingSubscriptionHandler;
import com.vga.platform.elsa.demo.model.remoting.UserModificationSubscriptionEvent;
import com.vga.platform.elsa.demo.model.remoting.UserModificationSubscriptionParameters;
import com.vga.platform.elsa.demo.server.DemoElsaRemotingConstants;

public class DemoSiteUsersSubscriptionHandler implements RemotingSubscriptionHandler<UserModificationSubscriptionParameters, UserModificationSubscriptionEvent> {


    @Override
    public String getId() {
        return DemoElsaRemotingConstants.DEMO_SITE_SUBSCRIBE_USERS_MODIFICATION;
    }

    @Override
    public boolean isApplicable(UserModificationSubscriptionEvent event, UserModificationSubscriptionParameters parameters){
        return true;
    }


}

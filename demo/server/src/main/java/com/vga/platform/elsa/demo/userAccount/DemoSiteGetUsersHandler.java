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

import com.vga.platform.elsa.common.core.search.SearchQueryBuilder;
import com.vga.platform.elsa.common.core.search.SortOrder;
import com.vga.platform.elsa.core.remoting.RemotingServerCallContext;
import com.vga.platform.elsa.core.remoting.RemotingServerCallHandler;
import com.vga.platform.elsa.core.storage.Storage;
import com.vga.platform.elsa.demo.model.domain.DemoUserAccountProjection;
import com.vga.platform.elsa.demo.model.domain.DemoUserAccountProjectionFields;
import com.vga.platform.elsa.demo.model.remoting.DemoSiteGetUsersRequest;
import com.vga.platform.elsa.demo.model.remoting.DemoSiteGetUsersResponse;
import com.vga.platform.elsa.demo.model.remoting.DemoSiteUser;
import com.vga.platform.elsa.demo.server.DemoElsaRemotingConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoSiteGetUsersHandler implements RemotingServerCallHandler<DemoSiteGetUsersRequest, DemoSiteGetUsersResponse> {
    @Autowired
    private Storage storage;
    @Override
    public String getId() {
        return DemoElsaRemotingConstants.DEMO_SITE_GET_USERS;
    }

    @Override
    public DemoSiteGetUsersResponse service(DemoSiteGetUsersRequest request, RemotingServerCallContext context){
        var users = storage.searchDocuments(DemoUserAccountProjection.class, new SearchQueryBuilder().orderBy(DemoUserAccountProjectionFields.name, SortOrder.ASC).build()).stream().map(it ->{
            var user = new DemoSiteUser();
            user.setUserId(it.getDocument().getId());
            user.setUserName(it.getName());
            return user;
        }).toList();
        var result = new DemoSiteGetUsersResponse();
        result.getUsers().addAll(users);
        return result;
    }
}

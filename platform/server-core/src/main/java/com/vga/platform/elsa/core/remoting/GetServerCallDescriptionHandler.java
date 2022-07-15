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

package com.vga.platform.elsa.core.remoting;

import com.vga.platform.elsa.common.meta.remoting.RemotingMetaRegistry;
import com.vga.platform.elsa.common.rest.core.GetServerCallDescriptionRequest;
import com.vga.platform.elsa.common.rest.core.GetServerCallDescriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class GetServerCallDescriptionHandler implements RemotingServerCallHandler<GetServerCallDescriptionRequest, GetServerCallDescriptionResponse> {

    @Autowired
    private RemotingMetaRegistry remotingMetaRegistry;

    @Override
    public String getId() {
        return "core:meta:get-server-call-description";
    }

    @Override
    public GetServerCallDescriptionResponse service(GetServerCallDescriptionRequest request, RemotingServerCallContext context){
        var serverCall = remotingMetaRegistry.getRemotings().get(request.getRemotingId()).getGroups().get(request.getGroupId()).getServerCalls().get(request.getMethodId());
        var result = new GetServerCallDescriptionResponse();
        result.setRequestClassName(serverCall.getRequestClassName());
        result.setResponseClassName(serverCall.getResponseClassName());
        return result;
    }
}

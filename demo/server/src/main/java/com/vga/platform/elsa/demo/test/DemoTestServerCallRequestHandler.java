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

package com.vga.platform.elsa.demo.test;

import com.vga.platform.elsa.core.remoting.RemotingServerCallContext;
import com.vga.platform.elsa.core.remoting.RemotingServerCallHandler;
import com.vga.platform.elsa.demo.model.remoting.DemoTestServerCallRequest;
import com.vga.platform.elsa.demo.model.remoting.DemoTestServerCallResponse;
import com.vga.platform.elsa.demo.server.DemoElsaRemotingConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DemoTestServerCallRequestHandler implements RemotingServerCallHandler<DemoTestServerCallRequest, DemoTestServerCallResponse> {

    @Override
    public String getId() {
        return DemoElsaRemotingConstants.DEMO_TEST_SERVER_CALL;
    }

    @Override
    public DemoTestServerCallResponse service(DemoTestServerCallRequest request, RemotingServerCallContext context){
        var param = request.getParam();
        var result = new DemoTestServerCallResponse();
        result.setDateProperty(LocalDate.now());
        result.setDateTimeProperty(LocalDateTime.now());
        return result;
    }

}

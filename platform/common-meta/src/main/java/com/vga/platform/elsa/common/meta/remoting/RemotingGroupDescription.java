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

package com.vga.platform.elsa.common.meta.remoting;

import com.vga.platform.elsa.common.meta.common.BaseElementWithId;

import java.util.LinkedHashMap;
import java.util.Map;

public class RemotingGroupDescription extends BaseElementWithId {
    private final Map<String, RemotingServerCallDescription> serverCalls = new LinkedHashMap<>();

    private final Map<String, RemotingClientCallDescription> clientCalls = new LinkedHashMap<>();

    private final Map<String, RemotingSubscriptionDescription> subscriptions = new LinkedHashMap<>();

    public RemotingGroupDescription() {
    }

    public RemotingGroupDescription(String id) {
        super(id);
    }

    public Map<String, RemotingServerCallDescription> getServerCalls() {
        return serverCalls;
    }

    public Map<String, RemotingClientCallDescription> getClientCalls() {
        return clientCalls;
    }

    public Map<String, RemotingSubscriptionDescription> getSubscriptions() {
        return subscriptions;
    }
}

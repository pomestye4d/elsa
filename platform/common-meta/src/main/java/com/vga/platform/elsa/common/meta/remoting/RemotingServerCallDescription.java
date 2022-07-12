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

public class RemotingServerCallDescription extends BaseElementWithId {
    private boolean validatable;

    private String requestClassName;

    private String responseClassName;

    public RemotingServerCallDescription() {
    }

    public RemotingServerCallDescription(String id) {
        super(id);
    }

    public boolean isValidatable() {
        return validatable;
    }

    public void setValidatable(boolean validatable) {
        this.validatable = validatable;
    }

    public String getRequestClassName() {
        return requestClassName;
    }

    public void setRequestClassName(String requestClassName) {
        this.requestClassName = requestClassName;
    }

    public String getResponseClassName() {
        return responseClassName;
    }

    public void setResponseClassName(String responseClassName) {
        this.responseClassName = responseClassName;
    }
}

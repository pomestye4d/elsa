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
 *
 *****************************************************************
 * This is generated code, don't modify it manually
 *****************************************************************/

package com.vga.platform.elsa.common.rest.core;

import com.vga.platform.elsa.common.core.model.common.BaseIntrospectableObject;

public class ClientResponse extends BaseIntrospectableObject{

	private String errorMessage;

	private String requestId;

	private String responseStr;

	public String getErrorMessage(){
		return errorMessage;
	}

	public void setErrorMessage(String value){
		this.errorMessage = value;
	}

	public String getRequestId(){
		return requestId;
	}

	public void setRequestId(String value){
		this.requestId = value;
	}

	public String getResponseStr(){
		return responseStr;
	}

	public void setResponseStr(String value){
		this.responseStr = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("errorMessage".equals(propertyName)){
			return errorMessage;
		}

		if("requestId".equals(propertyName)){
			return requestId;
		}

		if("responseStr".equals(propertyName)){
			return responseStr;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("errorMessage".equals(propertyName)){
			this.errorMessage = (String) value;
			return;
		}

		if("requestId".equals(propertyName)){
			this.requestId = (String) value;
			return;
		}

		if("responseStr".equals(propertyName)){
			this.responseStr = (String) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
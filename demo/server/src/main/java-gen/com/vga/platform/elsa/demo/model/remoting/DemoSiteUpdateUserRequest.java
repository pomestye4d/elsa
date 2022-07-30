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

package com.vga.platform.elsa.demo.model.remoting;

import com.vga.platform.elsa.common.core.model.common.BaseIntrospectableObject;
import com.vga.platform.elsa.demo.ui.UserEditorVM;

public class DemoSiteUpdateUserRequest extends BaseIntrospectableObject{

	private long userId;

	private UserEditorVM data;

	public long getUserId(){
		return userId;
	}

	public void setUserId(long value){
		this.userId = value;
	}

	public UserEditorVM getData(){
		return data;
	}

	public void setData(UserEditorVM value){
		this.data = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("userId".equals(propertyName)){
			return userId;
		}

		if("data".equals(propertyName)){
			return data;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("userId".equals(propertyName)){
			this.userId = (long) value;
			return;
		}

		if("data".equals(propertyName)){
			this.data = (UserEditorVM) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
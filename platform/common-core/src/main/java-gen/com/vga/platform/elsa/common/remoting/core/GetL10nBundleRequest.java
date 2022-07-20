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

package com.vga.platform.elsa.common.remoting.core;

import com.vga.platform.elsa.common.core.model.common.BaseIntrospectableObject;

public class GetL10nBundleRequest extends BaseIntrospectableObject{

	private String language;

	private String bundleId;

	public String getLanguage(){
		return language;
	}

	public void setLanguage(String value){
		this.language = value;
	}

	public String getBundleId(){
		return bundleId;
	}

	public void setBundleId(String value){
		this.bundleId = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("language".equals(propertyName)){
			return language;
		}

		if("bundleId".equals(propertyName)){
			return bundleId;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("language".equals(propertyName)){
			this.language = (String) value;
			return;
		}

		if("bundleId".equals(propertyName)){
			this.bundleId = (String) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
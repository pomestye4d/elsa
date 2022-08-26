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

package com.vga.platform.elsa.common.domain.core;

import com.vga.platform.elsa.common.core.model.common.BaseIntrospectableObject;
import java.lang.Object;

public class AclAction extends BaseIntrospectableObject{

	private String action;

	private String variant;

	private Object parameter;

	public String getAction(){
		return action;
	}

	public void setAction(String value){
		this.action = value;
	}

	public String getVariant(){
		return variant;
	}

	public void setVariant(String value){
		this.variant = value;
	}

	public Object getParameter(){
		return parameter;
	}

	public void setParameter(Object value){
		this.parameter = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("action".equals(propertyName)){
			return action;
		}

		if("variant".equals(propertyName)){
			return variant;
		}

		if("parameter".equals(propertyName)){
			return parameter;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("action".equals(propertyName)){
			this.action = (String) value;
			return;
		}

		if("variant".equals(propertyName)){
			this.variant = (String) value;
			return;
		}

		if("parameter".equals(propertyName)){
			this.parameter = (Object) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
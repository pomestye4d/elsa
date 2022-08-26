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

public class AclRestriction extends BaseIntrospectableObject{

	private String property;

	private AclCondition condition;

	private Object value;

	public String getProperty(){
		return property;
	}

	public void setProperty(String value){
		this.property = value;
	}

	public AclCondition getCondition(){
		return condition;
	}

	public void setCondition(AclCondition value){
		this.condition = value;
	}

	public Object getValue(){
		return value;
	}

	public void setValue(Object value){
		this.value = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("property".equals(propertyName)){
			return property;
		}

		if("condition".equals(propertyName)){
			return condition;
		}

		if("value".equals(propertyName)){
			return value;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("property".equals(propertyName)){
			this.property = (String) value;
			return;
		}

		if("condition".equals(propertyName)){
			this.condition = (AclCondition) value;
			return;
		}

		if("value".equals(propertyName)){
			this.value = (Object) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
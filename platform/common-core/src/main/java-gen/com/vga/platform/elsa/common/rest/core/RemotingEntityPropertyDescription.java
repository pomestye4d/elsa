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

public class RemotingEntityPropertyDescription extends BaseIntrospectableObject{

	private RemotingEntityValueType type;

	private String id;

	private String className;

	private boolean isAbstract;

	public RemotingEntityValueType getType(){
		return type;
	}

	public void setType(RemotingEntityValueType value){
		this.type = value;
	}

	public String getId(){
		return id;
	}

	public void setId(String value){
		this.id = value;
	}

	public String getClassName(){
		return className;
	}

	public void setClassName(String value){
		this.className = value;
	}

	public boolean getIsAbstract(){
		return isAbstract;
	}

	public void setIsAbstract(boolean value){
		this.isAbstract = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("type".equals(propertyName)){
			return type;
		}

		if("id".equals(propertyName)){
			return id;
		}

		if("className".equals(propertyName)){
			return className;
		}

		if("isAbstract".equals(propertyName)){
			return isAbstract;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("type".equals(propertyName)){
			this.type = (RemotingEntityValueType) value;
			return;
		}

		if("id".equals(propertyName)){
			this.id = (String) value;
			return;
		}

		if("className".equals(propertyName)){
			this.className = (String) value;
			return;
		}

		if("isAbstract".equals(propertyName)){
			this.isAbstract = (boolean) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
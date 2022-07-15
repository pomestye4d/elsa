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

public class RemotingEntityCollectionDescription extends BaseIntrospectableObject{

	private RemotingEntityValueType elementType;

	private String id;

	private String elementClassName;

	private boolean elementIsAbstract;

	public RemotingEntityValueType getElementType(){
		return elementType;
	}

	public void setElementType(RemotingEntityValueType value){
		this.elementType = value;
	}

	public String getId(){
		return id;
	}

	public void setId(String value){
		this.id = value;
	}

	public String getElementClassName(){
		return elementClassName;
	}

	public void setElementClassName(String value){
		this.elementClassName = value;
	}

	public boolean getElementIsAbstract(){
		return elementIsAbstract;
	}

	public void setElementIsAbstract(boolean value){
		this.elementIsAbstract = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("elementType".equals(propertyName)){
			return elementType;
		}

		if("id".equals(propertyName)){
			return id;
		}

		if("elementClassName".equals(propertyName)){
			return elementClassName;
		}

		if("elementIsAbstract".equals(propertyName)){
			return elementIsAbstract;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("elementType".equals(propertyName)){
			this.elementType = (RemotingEntityValueType) value;
			return;
		}

		if("id".equals(propertyName)){
			this.id = (String) value;
			return;
		}

		if("elementClassName".equals(propertyName)){
			this.elementClassName = (String) value;
			return;
		}

		if("elementIsAbstract".equals(propertyName)){
			this.elementIsAbstract = (boolean) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
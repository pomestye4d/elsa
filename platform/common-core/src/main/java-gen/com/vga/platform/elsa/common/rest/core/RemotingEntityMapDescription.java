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

public class RemotingEntityMapDescription extends BaseIntrospectableObject{

	private String id;

	private RemotingEntityValueType keyType;

	private boolean keyIsAbstract;

	private String keyClassName;

	private RemotingEntityValueType valueType;

	private String valueClassName;

	private boolean valueIsAbstract;

	public String getId(){
		return id;
	}

	public void setId(String value){
		this.id = value;
	}

	public RemotingEntityValueType getKeyType(){
		return keyType;
	}

	public void setKeyType(RemotingEntityValueType value){
		this.keyType = value;
	}

	public boolean getKeyIsAbstract(){
		return keyIsAbstract;
	}

	public void setKeyIsAbstract(boolean value){
		this.keyIsAbstract = value;
	}

	public String getKeyClassName(){
		return keyClassName;
	}

	public void setKeyClassName(String value){
		this.keyClassName = value;
	}

	public RemotingEntityValueType getValueType(){
		return valueType;
	}

	public void setValueType(RemotingEntityValueType value){
		this.valueType = value;
	}

	public String getValueClassName(){
		return valueClassName;
	}

	public void setValueClassName(String value){
		this.valueClassName = value;
	}

	public boolean getValueIsAbstract(){
		return valueIsAbstract;
	}

	public void setValueIsAbstract(boolean value){
		this.valueIsAbstract = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("id".equals(propertyName)){
			return id;
		}

		if("keyType".equals(propertyName)){
			return keyType;
		}

		if("keyIsAbstract".equals(propertyName)){
			return keyIsAbstract;
		}

		if("keyClassName".equals(propertyName)){
			return keyClassName;
		}

		if("valueType".equals(propertyName)){
			return valueType;
		}

		if("valueClassName".equals(propertyName)){
			return valueClassName;
		}

		if("valueIsAbstract".equals(propertyName)){
			return valueIsAbstract;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("id".equals(propertyName)){
			this.id = (String) value;
			return;
		}

		if("keyType".equals(propertyName)){
			this.keyType = (RemotingEntityValueType) value;
			return;
		}

		if("keyIsAbstract".equals(propertyName)){
			this.keyIsAbstract = (boolean) value;
			return;
		}

		if("keyClassName".equals(propertyName)){
			this.keyClassName = (String) value;
			return;
		}

		if("valueType".equals(propertyName)){
			this.valueType = (RemotingEntityValueType) value;
			return;
		}

		if("valueClassName".equals(propertyName)){
			this.valueClassName = (String) value;
			return;
		}

		if("valueIsAbstract".equals(propertyName)){
			this.valueIsAbstract = (boolean) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
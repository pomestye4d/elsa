/*****************************************************************
 * This is generated code, don't modify it manually
 *****************************************************************/

package com.vga.platform.elsa.common.rest.core;

import com.vga.platform.elsa.common.core.model.common.BaseIntrospectableObject;

public class GetClientCallDescriptionResponse extends BaseIntrospectableObject{

	private String requestClassName;

	private String responseClassName;

	public String getRequestClassName(){
		return requestClassName;
	}

	public void setRequestClassName(String value){
		this.requestClassName = value;
	}

	public String getResponseClassName(){
		return responseClassName;
	}

	public void setResponseClassName(String value){
		this.responseClassName = value;
	}

	@Override
	public Object getValue(String propertyName){

		if("requestClassName".equals(propertyName)){
			return requestClassName;
		}

		if("responseClassName".equals(propertyName)){
			return responseClassName;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("requestClassName".equals(propertyName)){
			this.requestClassName = (String) value;
			return;
		}

		if("responseClassName".equals(propertyName)){
			this.responseClassName = (String) value;
			return;
		}

		super.setValue(propertyName, value);
	}
}
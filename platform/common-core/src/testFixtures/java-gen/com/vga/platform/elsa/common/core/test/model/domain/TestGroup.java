/*****************************************************************
 * This is generated code, don't modify it manually
 *****************************************************************/

package com.vga.platform.elsa.common.core.test.model.domain;

import com.vga.platform.elsa.common.core.model.common.BaseIntrospectableObject;
import java.util.*;

public class TestGroup extends BaseIntrospectableObject{

	private String name;

	private final List<TestItem> items = new ArrayList<>();

	public String getName(){
		return name;
	}

	public void setName(String value){
		this.name = value;
	}

	public List<TestItem> getItems(){
		return items;
	}

	@Override
	public Object getValue(String propertyName){

		if("name".equals(propertyName)){
			return name;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("name".equals(propertyName)){
			this.name = (String) value;
			return;
		}

		super.setValue(propertyName, value);
	}

	@Override
	public Collection<?> getCollection(String collectionName){

		if("items".equals(collectionName)){
			return items;
		}

		return super.getCollection(collectionName);
	}
}
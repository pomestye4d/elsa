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

package com.vga.platform.elsa.common.core.test.model.domain;

import com.vga.platform.elsa.common.core.model.domain.BaseDocument;
import com.vga.platform.elsa.common.core.model.domain.EntityReference;
import java.util.*;

public class TestDomainDocument extends BaseDocument{

	private String stringProperty;

	private String getAllProperty;

	private BaseTestDomainNestedDocument entityProperty;

	private TestEnum enumProperty;

	private EntityReference<TestDomainDocument> entityReference;

	private final List<String> stringCollection = new ArrayList<>();

	private final List<BaseTestDomainNestedDocument> entityCollection = new ArrayList<>();

	private final List<TestGroup> groups = new ArrayList<>();

	private final List<TestEnum> enumCollection = new ArrayList<>();

	private final List<EntityReference<TestDomainDocument>> entityRefCollection = new ArrayList<>();

	public String getStringProperty(){
		return stringProperty;
	}

	public void setStringProperty(String value){
		this.stringProperty = value;
	}

	public String getGetAllProperty(){
		return getAllProperty;
	}

	public void setGetAllProperty(String value){
		this.getAllProperty = value;
	}

	public BaseTestDomainNestedDocument getEntityProperty(){
		return entityProperty;
	}

	public void setEntityProperty(BaseTestDomainNestedDocument value){
		this.entityProperty = value;
	}

	public TestEnum getEnumProperty(){
		return enumProperty;
	}

	public void setEnumProperty(TestEnum value){
		this.enumProperty = value;
	}

	public EntityReference<TestDomainDocument> getEntityReference(){
		return entityReference;
	}

	public void setEntityReference(EntityReference<TestDomainDocument> value){
		this.entityReference = value;
	}

	public List<String> getStringCollection(){
		return stringCollection;
	}

	public List<BaseTestDomainNestedDocument> getEntityCollection(){
		return entityCollection;
	}

	public List<TestGroup> getGroups(){
		return groups;
	}

	public List<TestEnum> getEnumCollection(){
		return enumCollection;
	}

	public List<EntityReference<TestDomainDocument>> getEntityRefCollection(){
		return entityRefCollection;
	}

	@Override
	public Object getValue(String propertyName){

		if("stringProperty".equals(propertyName)){
			return stringProperty;
		}

		if("getAllProperty".equals(propertyName)){
			return getAllProperty;
		}

		if("entityProperty".equals(propertyName)){
			return entityProperty;
		}

		if("enumProperty".equals(propertyName)){
			return enumProperty;
		}

		if("entityReference".equals(propertyName)){
			return entityReference;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("stringProperty".equals(propertyName)){
			this.stringProperty = (String) value;
			return;
		}

		if("getAllProperty".equals(propertyName)){
			this.getAllProperty = (String) value;
			return;
		}

		if("entityProperty".equals(propertyName)){
			this.entityProperty = (BaseTestDomainNestedDocument) value;
			return;
		}

		if("enumProperty".equals(propertyName)){
			this.enumProperty = (TestEnum) value;
			return;
		}

		if("entityReference".equals(propertyName)){
			//noinspection unchecked
			this.entityReference = (EntityReference<TestDomainDocument>) value;
			return;
		}

		super.setValue(propertyName, value);
	}

	@Override
	public Collection<?> getCollection(String collectionName){

		if("stringCollection".equals(collectionName)){
			return stringCollection;
		}

		if("entityCollection".equals(collectionName)){
			return entityCollection;
		}

		if("groups".equals(collectionName)){
			return groups;
		}

		if("enumCollection".equals(collectionName)){
			return enumCollection;
		}

		if("entityRefCollection".equals(collectionName)){
			return entityRefCollection;
		}

		return super.getCollection(collectionName);
	}

	@Override
	public String toString(){
		return stringProperty;
	}
}
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DemoTestServerCallResponse extends BaseIntrospectableObject{

	private String stringProperty;

	private LocalDate dateProperty;

	private LocalDateTime dateTimeProperty;

	private DemoTestEnum enumProperty;

	private final List<String> stringCollection = new ArrayList<>();

	private final List<LocalDate> dateCollection = new ArrayList<>();

	private final List<DemoTestEntity> entityCollection = new ArrayList<>();

	private final Map<String,String> stringMap = new HashMap<>();

	private final Map<LocalDate,LocalDate> dateMap = new HashMap<>();

	private final Map<DemoTestEntity,DemoTestEntity> entityMap = new HashMap<>();

	public String getStringProperty(){
		return stringProperty;
	}

	public void setStringProperty(String value){
		this.stringProperty = value;
	}

	public LocalDate getDateProperty(){
		return dateProperty;
	}

	public void setDateProperty(LocalDate value){
		this.dateProperty = value;
	}

	public LocalDateTime getDateTimeProperty(){
		return dateTimeProperty;
	}

	public void setDateTimeProperty(LocalDateTime value){
		this.dateTimeProperty = value;
	}

	public DemoTestEnum getEnumProperty(){
		return enumProperty;
	}

	public void setEnumProperty(DemoTestEnum value){
		this.enumProperty = value;
	}

	public List<String> getStringCollection(){
		return stringCollection;
	}

	public List<LocalDate> getDateCollection(){
		return dateCollection;
	}

	public List<DemoTestEntity> getEntityCollection(){
		return entityCollection;
	}

	public Map<String,String> getStringMap(){
		return stringMap;
	}

	public Map<LocalDate,LocalDate> getDateMap(){
		return dateMap;
	}

	public Map<DemoTestEntity,DemoTestEntity> getEntityMap(){
		return entityMap;
	}

	@Override
	public Object getValue(String propertyName){

		if("stringProperty".equals(propertyName)){
			return stringProperty;
		}

		if("dateProperty".equals(propertyName)){
			return dateProperty;
		}

		if("dateTimeProperty".equals(propertyName)){
			return dateTimeProperty;
		}

		if("enumProperty".equals(propertyName)){
			return enumProperty;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("stringProperty".equals(propertyName)){
			this.stringProperty = (String) value;
			return;
		}

		if("dateProperty".equals(propertyName)){
			this.dateProperty = (LocalDate) value;
			return;
		}

		if("dateTimeProperty".equals(propertyName)){
			this.dateTimeProperty = (LocalDateTime) value;
			return;
		}

		if("enumProperty".equals(propertyName)){
			this.enumProperty = (DemoTestEnum) value;
			return;
		}

		super.setValue(propertyName, value);
	}

	@Override
	public Collection<?> getCollection(String collectionName){

		if("stringCollection".equals(collectionName)){
			return stringCollection;
		}

		if("dateCollection".equals(collectionName)){
			return dateCollection;
		}

		if("entityCollection".equals(collectionName)){
			return entityCollection;
		}

		return super.getCollection(collectionName);
	}

	@Override
	public Map<?,?> getMap(String mapName){

		if("stringMap".equals(mapName)){
			return stringMap;
		}

		if("dateMap".equals(mapName)){
			return dateMap;
		}

		if("entityMap".equals(mapName)){
			return entityMap;
		}

		return super.getMap(mapName);
	}
}
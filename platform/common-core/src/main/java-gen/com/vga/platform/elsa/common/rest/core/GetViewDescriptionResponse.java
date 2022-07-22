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
import java.util.*;

public class GetViewDescriptionResponse extends BaseIntrospectableObject{

	private XmlNodeDT view;

	private final Map<String,String> localizations = new HashMap<>();

	public XmlNodeDT getView(){
		return view;
	}

	public void setView(XmlNodeDT value){
		this.view = value;
	}

	public Map<String,String> getLocalizations(){
		return localizations;
	}

	@Override
	public Object getValue(String propertyName){

		if("view".equals(propertyName)){
			return view;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("view".equals(propertyName)){
			this.view = (XmlNodeDT) value;
			return;
		}

		super.setValue(propertyName, value);
	}

	@Override
	public Map<?,?> getMap(String mapName){

		if("localizations".equals(mapName)){
			return localizations;
		}

		return super.getMap(mapName);
	}
}
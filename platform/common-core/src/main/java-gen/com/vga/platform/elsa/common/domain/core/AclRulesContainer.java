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
import java.util.*;

public class AclRulesContainer extends BaseIntrospectableObject{

	private String resource;

	private final List<AclRule> rules = new ArrayList<>();

	private final List<AclRulesContainer> children = new ArrayList<>();

	public String getResource(){
		return resource;
	}

	public void setResource(String value){
		this.resource = value;
	}

	public List<AclRule> getRules(){
		return rules;
	}

	public List<AclRulesContainer> getChildren(){
		return children;
	}

	@Override
	public Object getValue(String propertyName){

		if("resource".equals(propertyName)){
			return resource;
		}

		return super.getValue(propertyName);
	}

	@Override
	public void setValue(String propertyName, Object value){

		if("resource".equals(propertyName)){
			this.resource = (String) value;
			return;
		}

		super.setValue(propertyName, value);
	}

	@Override
	public Collection<?> getCollection(String collectionName){

		if("rules".equals(collectionName)){
			return rules;
		}

		if("children".equals(collectionName)){
			return children;
		}

		return super.getCollection(collectionName);
	}
}
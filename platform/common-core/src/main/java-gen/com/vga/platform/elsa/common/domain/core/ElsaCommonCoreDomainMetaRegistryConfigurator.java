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

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.EnumDescription;
import com.vga.platform.elsa.common.meta.common.EnumItemDescription;
import com.vga.platform.elsa.common.meta.common.StandardCollectionDescription;
import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistry;
import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistryConfigurator;

public class ElsaCommonCoreDomainMetaRegistryConfigurator implements DomainMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(DomainMetaRegistry registry){
		{
			var enumDescription = new EnumDescription("com.vga.platform.elsa.common.domain.core.AclCondition");
			registry.getEnums().put(enumDescription.getId(), enumDescription);
			{
				var enumItemDescription = new EnumItemDescription("EQUALS");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("NOT_EQUALS");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.domain.core.AclRestriction");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("property");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("condition");
				propertyDescription.setType(StandardValueType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.common.domain.core.AclCondition");
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("value");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("java.lang.Object");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.domain.core.AclAction");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("action");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("variant");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("parameter");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("java.lang.Object");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.domain.core.AclRule");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var collectionDescription = new StandardCollectionDescription("restrictions");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.domain.core.AclRestriction");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("actions");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.domain.core.AclAction");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.domain.core.AclRulesContainer");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("resource");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("rules");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.domain.core.AclRule");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("children");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.domain.core.AclRulesContainer");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
	}
}
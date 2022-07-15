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

package com.vga.platform.elsa.config;

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.StandardCollectionDescription;
import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.custom.CustomMetaRegistry;
import com.vga.platform.elsa.common.meta.custom.CustomMetaRegistryConfigurator;

public class ElsaCommonCoreCustomMetaRegistryConfigurator implements CustomMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(CustomMetaRegistry registry){
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.model.common.BaseIdentity");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			entityDescription.setAbstract(true);
			{
				var propertyDescription = new StandardPropertyDescription("id");
				propertyDescription.setType(StandardValueType.LONG);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.model.domain.BaseAsset");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			entityDescription.setAbstract(true);
			entityDescription.setExtendsId("com.vga.platform.elsa.common.core.model.common.BaseIdentity");
			{
				var propertyDescription = new StandardPropertyDescription("versionInfo");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.common.core.model.domain.VersionInfo");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.model.domain.BaseSearchableProjection");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			entityDescription.setAbstract(true);
			{
				var propertyDescription = new StandardPropertyDescription("document");
				propertyDescription.setType(StandardValueType.ENTITY_REFERENCE);
				propertyDescription.setClassName("Object");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("navigationKey");
				propertyDescription.setType(StandardValueType.INT);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.model.common.L10nMessage");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("key");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("bundle");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("parameters");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("Object");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.model.domain.VersionInfo");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("versionNumber");
				propertyDescription.setType(StandardValueType.INT);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("revision");
				propertyDescription.setType(StandardValueType.INT);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("modifiedBy");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("modified");
				propertyDescription.setType(StandardValueType.LOCAL_DATE_TIME);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("comment");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.model.domain.BaseDocument");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			entityDescription.setAbstract(true);
			entityDescription.setExtendsId("com.vga.platform.elsa.common.core.model.common.BaseIdentity");
			{
				var propertyDescription = new StandardPropertyDescription("versionInfo");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.common.core.model.domain.VersionInfo");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
	}
}
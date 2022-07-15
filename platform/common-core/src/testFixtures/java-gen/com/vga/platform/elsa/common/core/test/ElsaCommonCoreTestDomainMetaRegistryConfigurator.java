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

package com.vga.platform.elsa.common.core.test;

import com.vga.platform.elsa.common.core.utils.LocaleUtils;
import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.EnumDescription;
import com.vga.platform.elsa.common.meta.common.EnumItemDescription;
import com.vga.platform.elsa.common.meta.common.StandardCollectionDescription;
import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.domain.AssetDescription;
import com.vga.platform.elsa.common.meta.domain.DatabaseCollectionDescription;
import com.vga.platform.elsa.common.meta.domain.DatabaseCollectionType;
import com.vga.platform.elsa.common.meta.domain.DatabasePropertyDescription;
import com.vga.platform.elsa.common.meta.domain.DatabasePropertyType;
import com.vga.platform.elsa.common.meta.domain.DocumentDescription;
import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistry;
import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistryConfigurator;
import com.vga.platform.elsa.common.meta.domain.SearchableProjectionDescription;

public class ElsaCommonCoreTestDomainMetaRegistryConfigurator implements DomainMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(DomainMetaRegistry registry){
		{
			var enumDescription = new EnumDescription("com.vga.platform.elsa.common.core.test.model.domain.TestEnum");
			registry.getEnums().put(enumDescription.getId(), enumDescription);
			{
				var enumItemDescription = new EnumItemDescription("ITEM1");
				enumItemDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Item 1");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("ITEM2");
				enumItemDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Item 2");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("ITEM3");
				enumItemDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Item 3");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.test.model.domain.BaseTestDomainNestedDocument");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			entityDescription.setAbstract(true);
			{
				var propertyDescription = new StandardPropertyDescription("name");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.test.model.domain.TestDomainNestedDocumentImpl");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			entityDescription.setExtendsId("com.vga.platform.elsa.common.core.test.model.domain.BaseTestDomainNestedDocument");
			{
				var propertyDescription = new StandardPropertyDescription("value");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.test.model.domain.TestGroup");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("name");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("items");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.core.test.model.domain.TestItem");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.core.test.model.domain.TestItem");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("name");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var documentDescription = new DocumentDescription("com.vga.platform.elsa.common.core.test.model.domain.TestDomainDocument");
			registry.getDocuments().put(documentDescription.getId(), documentDescription);
			documentDescription.setCacheResolve(true);
			{
				var propertyDescription = new StandardPropertyDescription("stringProperty");
				propertyDescription.setType(StandardValueType.STRING);
				documentDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("getAllProperty");
				propertyDescription.setType(StandardValueType.STRING);
				documentDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("entityProperty");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.common.core.test.model.domain.BaseTestDomainNestedDocument");
				documentDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("enumProperty");
				propertyDescription.setType(StandardValueType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.common.core.test.model.domain.TestEnum");
				documentDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("entityReference");
				propertyDescription.setType(StandardValueType.ENTITY_REFERENCE);
				propertyDescription.setClassName("com.vga.platform.elsa.common.core.test.model.domain.TestDomainDocument");
				documentDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("stringCollection");
				collectionDescription.setElementType(StandardValueType.STRING);
				documentDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("entityCollection");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.core.test.model.domain.BaseTestDomainNestedDocument");
				documentDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("groups");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.core.test.model.domain.TestGroup");
				documentDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("enumCollection");
				collectionDescription.setElementType(StandardValueType.ENUM);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.core.test.model.domain.TestEnum");
				documentDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("entityRefCollection");
				collectionDescription.setElementType(StandardValueType.ENTITY_REFERENCE);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.core.test.model.domain.TestDomainDocument");
				documentDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var projectionDescription = new SearchableProjectionDescription("com.vga.platform.elsa.common.core.test.model.domain.TestDomainDocumentProjection");
			registry.getSearchableProjections().put(projectionDescription.getId(), projectionDescription);
			projectionDescription.setDocument("com.vga.platform.elsa.common.core.test.model.domain.TestDomainDocument");
			projectionDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Test documents");
			{
				var propertyDescription = new DatabasePropertyDescription("stringProperty");
				propertyDescription.setType(DatabasePropertyType.STRING);
				propertyDescription.setCacheFind(true);
				propertyDescription.setUseInTextSearch(true);
				propertyDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "String  property");
				projectionDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new DatabasePropertyDescription("getAllProperty");
				propertyDescription.setType(DatabasePropertyType.STRING);
				propertyDescription.setCacheGetAll(true);
				projectionDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new DatabasePropertyDescription("enumProperty");
				propertyDescription.setType(DatabasePropertyType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.common.core.test.model.domain.TestEnum");
				propertyDescription.setUseInTextSearch(true);
				propertyDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Enum  property");
				projectionDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new DatabasePropertyDescription("entityReference");
				propertyDescription.setType(DatabasePropertyType.ENTITY_REFERENCE);
				propertyDescription.setClassName("com.vga.platform.elsa.common.core.test.model.domain.TestDomainDocument");
				propertyDescription.setUseInTextSearch(true);
				propertyDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Entity Reference");
				projectionDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var collectionDescription = new DatabaseCollectionDescription("stringCollection");
				collectionDescription.setElementType(DatabaseCollectionType.STRING);
				collectionDescription.setUseInTextSearch(true);
				collectionDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "String  collection");
				projectionDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new DatabaseCollectionDescription("enumCollection");
				collectionDescription.setElementType(DatabaseCollectionType.ENUM);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.core.test.model.domain.TestEnum");
				collectionDescription.setUseInTextSearch(true);
				collectionDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Enum collection");
				projectionDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new DatabaseCollectionDescription("entityRefCollection");
				collectionDescription.setElementType(DatabaseCollectionType.ENTITY_REFERENCE);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.core.test.model.domain.TestDomainDocument");
				collectionDescription.setUseInTextSearch(true);
				collectionDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Entity ref collection");
				projectionDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var assetDescription = new AssetDescription("com.vga.platform.elsa.common.core.test.model.domain.TestDomainAsset");
			registry.getAssets().put(assetDescription.getId(), assetDescription);
			assetDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Test Domain Assets");
			{
				var propertyDescription = new DatabasePropertyDescription("stringProperty");
				propertyDescription.setType(DatabasePropertyType.STRING);
				propertyDescription.setCacheFind(true);
				propertyDescription.setUseInTextSearch(true);
				propertyDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "String property");
				assetDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new DatabasePropertyDescription("dateProperty");
				propertyDescription.setType(DatabasePropertyType.LOCAL_DATE_TIME);
				propertyDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Date property");
				assetDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
	}
}
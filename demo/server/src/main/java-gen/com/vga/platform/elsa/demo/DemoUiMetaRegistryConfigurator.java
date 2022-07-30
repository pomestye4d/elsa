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

package com.vga.platform.elsa.demo;

import com.vga.platform.elsa.common.core.utils.LocaleUtils;
import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.StandardCollectionDescription;
import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistryConfigurator;
import com.vga.platform.elsa.common.meta.ui.UiViewDescription;
import java.util.*;

public class DemoUiMetaRegistryConfigurator implements UiMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(UiMetaRegistry registry){
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersListVM");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("content");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.demo.ui.UsersTableVM");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersListVC");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("content");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.demo.ui.UsersTableVC");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersListVV");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("content");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.demo.ui.UsersTableVV");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersTableVM");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var collectionDescription = new StandardCollectionDescription("items");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.demo.ui.UsersTableRowVM");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersTableVC");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var collectionDescription = new StandardCollectionDescription("items");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.demo.ui.UsersTableRowVC");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersTableVV");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var collectionDescription = new StandardCollectionDescription("items");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.demo.ui.UsersTableRowVV");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersTableRowVM");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("userId");
				propertyDescription.setType(StandardValueType.LONG);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("userName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersTableRowVC");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UsersTableRowVV");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UserEditorVM");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("userName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UserEditorVC");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("userName");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.demo.server.ui.template.TextBoxWidgetConfiguration");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.ui.UserEditorVV");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("userName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var viewDescription = new UiViewDescription("com.vga.platform.elsa.demo.ui.UsersTable");
			registry.getViews().put("com.vga.platform.elsa.demo.ui.UsersTable", viewDescription);
			var xmlNode0 = new XmlNode();
			xmlNode0.setName("table");
			xmlNode0.getAttributes().put("id", "com.vga.platform.elsa.demo.ui.UsersTable");
			{
				var xmlNode1 = new XmlNode();
				xmlNode1.setName("column");
				xmlNode1.getAttributes().put("dataType", "LONG");
				xmlNode1.getAttributes().put("hidden", "true");
				xmlNode1.getAttributes().put("id", "userId");
				xmlNode0.getChildren().add(xmlNode1);
			}
			{
				var xmlNode1 = new XmlNode();
				xmlNode1.setName("column");
				xmlNode1.getAttributes().put("dataType", "TEXT");
				xmlNode1.getAttributes().put("id", "userName");
				xmlNode0.getChildren().add(xmlNode1);
			}
			viewDescription.setView(xmlNode0);
			{
				var l10n = new LinkedHashMap<Locale, String>();
				l10n.put(LocaleUtils.getLocale("ru",""), "Имя");
				viewDescription.getLocalizations().put("userName", l10n);
			}
		}
		{
			var viewDescription = new UiViewDescription("com.vga.platform.elsa.demo.ui.UsersList");
			registry.getViews().put("com.vga.platform.elsa.demo.ui.UsersList", viewDescription);
			var xmlNode0 = new XmlNode();
			xmlNode0.setName("list-with-tools");
			xmlNode0.getAttributes().put("id", "com.vga.platform.elsa.demo.ui.UsersList");
			{
				var xmlNode1 = new XmlNode();
				xmlNode1.setName("content");
				xmlNode1.getAttributes().put("container-ref", "com.vga.platform.elsa.demo.ui.UsersTable");
				xmlNode0.getChildren().add(xmlNode1);
			}
			viewDescription.setView(xmlNode0);
		}
		{
			var viewDescription = new UiViewDescription("com.vga.platform.elsa.demo.ui.UserEditor");
			registry.getViews().put("com.vga.platform.elsa.demo.ui.UserEditor", viewDescription);
			var xmlNode0 = new XmlNode();
			xmlNode0.setName("simple-editor");
			xmlNode0.getAttributes().put("id", "com.vga.platform.elsa.demo.ui.UserEditor");
			{
				var xmlNode1 = new XmlNode();
				xmlNode1.setName("row");
				xmlNode1.getAttributes().put("id", "userName");
				{
					var xmlNode2 = new XmlNode();
					xmlNode2.setName("text-box-widget");
					xmlNode1.getChildren().add(xmlNode2);
				}
				xmlNode0.getChildren().add(xmlNode1);
			}
			viewDescription.setView(xmlNode0);
			{
				var l10n = new LinkedHashMap<Locale, String>();
				l10n.put(LocaleUtils.getLocale("ru",""), "Имя");
				viewDescription.getLocalizations().put("userName", l10n);
			}
		}
	}
}
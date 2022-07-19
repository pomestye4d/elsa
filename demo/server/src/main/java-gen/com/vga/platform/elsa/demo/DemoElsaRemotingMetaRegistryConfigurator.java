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

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.EnumDescription;
import com.vga.platform.elsa.common.meta.common.EnumItemDescription;
import com.vga.platform.elsa.common.meta.common.StandardCollectionDescription;
import com.vga.platform.elsa.common.meta.common.StandardMapDescription;
import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.remoting.RemotingDescription;
import com.vga.platform.elsa.common.meta.remoting.RemotingGroupDescription;
import com.vga.platform.elsa.common.meta.remoting.RemotingMetaRegistry;
import com.vga.platform.elsa.common.meta.remoting.RemotingMetaRegistryConfigurator;
import com.vga.platform.elsa.common.meta.remoting.RemotingServerCallDescription;

public class DemoElsaRemotingMetaRegistryConfigurator implements RemotingMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(RemotingMetaRegistry registry){
		{
			var enumDescription = new EnumDescription("com.vga.platform.elsa.demo.model.remoting.DemoTestEnum");
			registry.getEnums().put(enumDescription.getId(), enumDescription);
			{
				var enumItemDescription = new EnumItemDescription("ITEM1");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("ITEM2");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.model.remoting.DemoTestEntity");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("stringProperty");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.model.remoting.DemoTestServerCallRequest");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("param");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.demo.model.remoting.DemoTestServerCallResponse");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("stringProperty");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("dateProperty");
				propertyDescription.setType(StandardValueType.LOCAL_DATE);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("dateTimeProperty");
				propertyDescription.setType(StandardValueType.LOCAL_DATE_TIME);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("enumProperty");
				propertyDescription.setType(StandardValueType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.demo.model.remoting.DemoTestEnum");
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("stringCollection");
				collectionDescription.setElementType(StandardValueType.STRING);
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("dateCollection");
				collectionDescription.setElementType(StandardValueType.LOCAL_DATE);
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("entityCollection");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.demo.model.remoting.DemoTestEntity");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var mapDescription = new StandardMapDescription("stringMap");
				mapDescription.setKeyType(StandardValueType.STRING);
				mapDescription.setValueType(StandardValueType.STRING);
				entityDescription.getMaps().put(mapDescription.getId(), mapDescription);
			}
			{
				var mapDescription = new StandardMapDescription("dateMap");
				mapDescription.setKeyType(StandardValueType.LOCAL_DATE);
				mapDescription.setValueType(StandardValueType.LOCAL_DATE);
				entityDescription.getMaps().put(mapDescription.getId(), mapDescription);
			}
			{
				var mapDescription = new StandardMapDescription("entityMap");
				mapDescription.setKeyType(StandardValueType.ENTITY);
				mapDescription.setValueType(StandardValueType.ENTITY);
				mapDescription.setKeyClassName("com.vga.platform.elsa.demo.model.remoting.DemoTestEntity");
				mapDescription.setValueClassName("com.vga.platform.elsa.demo.model.remoting.DemoTestEntity");
				entityDescription.getMaps().put(mapDescription.getId(), mapDescription);
			}
		}
		{
			var remotingDescription = new RemotingDescription("demo");
			registry.getRemotings().put(remotingDescription.getId(), remotingDescription);
			{
				var groupDescription = new RemotingGroupDescription("test");
				remotingDescription.getGroups().put(groupDescription.getId(), groupDescription);
				{
					var serverCallDescription = new RemotingServerCallDescription("test");
					serverCallDescription.setValidatable(false);
					serverCallDescription.setRequestClassName("com.vga.platform.elsa.demo.model.remoting.DemoTestServerCallRequest");
					serverCallDescription.setResponseClassName("com.vga.platform.elsa.demo.model.remoting.DemoTestServerCallResponse");
					groupDescription.getServerCalls().put("server-call", serverCallDescription);
				}
			}
		}
	}
}
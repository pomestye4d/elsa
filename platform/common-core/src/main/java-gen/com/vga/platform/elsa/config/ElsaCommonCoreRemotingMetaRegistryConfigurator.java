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

public class ElsaCommonCoreRemotingMetaRegistryConfigurator implements RemotingMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(RemotingMetaRegistry registry){
		{
			var enumDescription = new EnumDescription("com.vga.platform.elsa.common.rest.core.RemotingEntityValueType");
			registry.getEnums().put(enumDescription.getId(), enumDescription);
			{
				var enumItemDescription = new EnumItemDescription("STRING");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("LOCAL_DATE");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("LOCAL_DATE_TIME");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("ENUM");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("CLASS");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("BOOLEAN");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("BYTE_ARRAY");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("ENTITY");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("ENTITY_REFERENCE");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("LONG");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("INT");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("BIG_DECIMAL");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
		}
		{
			var enumDescription = new EnumDescription("com.vga.platform.elsa.common.rest.core.RemotingMessageType");
			registry.getEnums().put(enumDescription.getId(), enumDescription);
			{
				var enumItemDescription = new EnumItemDescription("PING");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
			{
				var enumItemDescription = new EnumItemDescription("SUBSCRIPTION");
				enumDescription.getItems().put(enumItemDescription.getId(), enumItemDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.ClientResponse");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("errorMessage");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("requestId");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("responseStr");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.RemotingMessage");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("type");
				propertyDescription.setType(StandardValueType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.common.rest.core.RemotingMessageType");
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("subscriptionId");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("callId");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("groupId");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("methodId");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("data");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.RemotingEntityPropertyDescription");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("type");
				propertyDescription.setType(StandardValueType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.common.rest.core.RemotingEntityValueType");
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("id");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("className");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("isAbstract");
				propertyDescription.setType(StandardValueType.BOOLEAN);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.RemotingEntityCollectionDescription");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("elementType");
				propertyDescription.setType(StandardValueType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.common.rest.core.RemotingEntityValueType");
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("id");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("elementClassName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("elementIsAbstract");
				propertyDescription.setType(StandardValueType.BOOLEAN);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.RemotingEntityMapDescription");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("id");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("keyType");
				propertyDescription.setType(StandardValueType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.common.rest.core.RemotingEntityValueType");
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("keyIsAbstract");
				propertyDescription.setType(StandardValueType.BOOLEAN);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("keyClassName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("valueType");
				propertyDescription.setType(StandardValueType.ENUM);
				propertyDescription.setClassName("com.vga.platform.elsa.common.rest.core.RemotingEntityValueType");
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("valueClassName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("valueIsAbstract");
				propertyDescription.setType(StandardValueType.BOOLEAN);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.RemotingEntityDescription");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var collectionDescription = new StandardCollectionDescription("properties");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.rest.core.RemotingEntityPropertyDescription");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("collections");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.rest.core.RemotingEntityCollectionDescription");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("maps");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.rest.core.RemotingEntityMapDescription");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.XmlNodeDT");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("name");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("value");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var collectionDescription = new StandardCollectionDescription("children");
				collectionDescription.setElementType(StandardValueType.ENTITY);
				collectionDescription.setElementClassName("com.vga.platform.elsa.common.rest.core.XmlNodeDT");
				entityDescription.getCollections().put(collectionDescription.getId(), collectionDescription);
			}
			{
				var mapDescription = new StandardMapDescription("attributes");
				mapDescription.setKeyType(StandardValueType.STRING);
				mapDescription.setValueType(StandardValueType.STRING);
				entityDescription.getMaps().put(mapDescription.getId(), mapDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.remoting.core.GetL10nBundleRequest");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("language");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("bundleId");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.remoting.core.GetL10nBundleResponse");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var mapDescription = new StandardMapDescription("messages");
				mapDescription.setKeyType(StandardValueType.STRING);
				mapDescription.setValueType(StandardValueType.STRING);
				entityDescription.getMaps().put(mapDescription.getId(), mapDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.GetServerCallDescriptionRequest");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("remotingId");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("groupId");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("methodId");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.GetServerCallDescriptionResponse");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("requestClassName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("responseClassName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.GetSubscriptionDescriptionRequest");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("remotingId");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("groupId");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("subscriptionId");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.GetSubscriptionDescriptionResponse");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("parameterClassName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var propertyDescription = new StandardPropertyDescription("eventClassName");
				propertyDescription.setType(StandardValueType.STRING);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.GetRemotingEntityDescriptionRequest");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("entityId");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.GetRemotingEntityDescriptionResponse");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("description");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.common.rest.core.RemotingEntityDescription");
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.GetViewDescriptionRequest");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("viewId");
				propertyDescription.setType(StandardValueType.STRING);
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var entityDescription = new EntityDescription("com.vga.platform.elsa.common.rest.core.GetViewDescriptionResponse");
			registry.getEntities().put(entityDescription.getId(), entityDescription);
			{
				var propertyDescription = new StandardPropertyDescription("view");
				propertyDescription.setType(StandardValueType.ENTITY);
				propertyDescription.setClassName("com.vga.platform.elsa.common.rest.core.XmlNodeDT");
				propertyDescription.setNonNullable(true);
				entityDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
			{
				var mapDescription = new StandardMapDescription("localizations");
				mapDescription.setKeyType(StandardValueType.STRING);
				mapDescription.setValueType(StandardValueType.STRING);
				entityDescription.getMaps().put(mapDescription.getId(), mapDescription);
			}
		}
		{
			var remotingDescription = new RemotingDescription("core");
			registry.getRemotings().put(remotingDescription.getId(), remotingDescription);
			{
				var groupDescription = new RemotingGroupDescription("l10n");
				remotingDescription.getGroups().put(groupDescription.getId(), groupDescription);
				{
					var serverCallDescription = new RemotingServerCallDescription("l10n");
					serverCallDescription.setValidatable(false);
					serverCallDescription.setRequestClassName("com.vga.platform.elsa.common.remoting.core.GetL10nBundleRequest");
					serverCallDescription.setResponseClassName("com.vga.platform.elsa.common.remoting.core.GetL10nBundleResponse");
					groupDescription.getServerCalls().put("get-bundle", serverCallDescription);
				}
			}
			{
				var groupDescription = new RemotingGroupDescription("meta");
				remotingDescription.getGroups().put(groupDescription.getId(), groupDescription);
				{
					var serverCallDescription = new RemotingServerCallDescription("meta");
					serverCallDescription.setValidatable(false);
					serverCallDescription.setRequestClassName("com.vga.platform.elsa.common.rest.core.GetServerCallDescriptionRequest");
					serverCallDescription.setResponseClassName("com.vga.platform.elsa.common.rest.core.GetServerCallDescriptionResponse");
					groupDescription.getServerCalls().put("get-server-call-description", serverCallDescription);
				}
				{
					var serverCallDescription = new RemotingServerCallDescription("meta");
					serverCallDescription.setValidatable(false);
					serverCallDescription.setRequestClassName("com.vga.platform.elsa.common.rest.core.GetSubscriptionDescriptionRequest");
					serverCallDescription.setResponseClassName("com.vga.platform.elsa.common.rest.core.GetSubscriptionDescriptionResponse");
					groupDescription.getServerCalls().put("get-subscription-description", serverCallDescription);
				}
				{
					var serverCallDescription = new RemotingServerCallDescription("meta");
					serverCallDescription.setValidatable(false);
					serverCallDescription.setRequestClassName("com.vga.platform.elsa.common.rest.core.GetRemotingEntityDescriptionRequest");
					serverCallDescription.setResponseClassName("com.vga.platform.elsa.common.rest.core.GetRemotingEntityDescriptionResponse");
					groupDescription.getServerCalls().put("get-entity-description", serverCallDescription);
				}
				{
					var serverCallDescription = new RemotingServerCallDescription("meta");
					serverCallDescription.setValidatable(false);
					serverCallDescription.setRequestClassName("com.vga.platform.elsa.common.rest.core.GetViewDescriptionRequest");
					serverCallDescription.setResponseClassName("com.vga.platform.elsa.common.rest.core.GetViewDescriptionResponse");
					groupDescription.getServerCalls().put("get-view-description", serverCallDescription);
				}
			}
		}
	}
}
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
import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.domain.DatabasePropertyDescription;
import com.vga.platform.elsa.common.meta.domain.DatabasePropertyType;
import com.vga.platform.elsa.common.meta.domain.DocumentDescription;
import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistry;
import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistryConfigurator;
import com.vga.platform.elsa.common.meta.domain.SearchableProjectionDescription;

public class DemoElsaDomainMetaRegistryConfigurator implements DomainMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(DomainMetaRegistry registry){
		{
			var documentDescription = new DocumentDescription("com.vga.platform.elsa.demo.model.domain.DemoUserAccount");
			registry.getDocuments().put(documentDescription.getId(), documentDescription);
			documentDescription.setCacheResolve(true);
			{
				var propertyDescription = new StandardPropertyDescription("name");
				propertyDescription.setType(StandardValueType.STRING);
				documentDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
		{
			var projectionDescription = new SearchableProjectionDescription("com.vga.platform.elsa.demo.model.domain.DemoUserAccountProjection");
			registry.getSearchableProjections().put(projectionDescription.getId(), projectionDescription);
			projectionDescription.setDocument("com.vga.platform.elsa.demo.model.domain.DemoUserAccount");
			projectionDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "User account");
			{
				var propertyDescription = new DatabasePropertyDescription("name");
				propertyDescription.setType(DatabasePropertyType.STRING);
				propertyDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Name");
				projectionDescription.getProperties().put(propertyDescription.getId(), propertyDescription);
			}
		}
	}
}
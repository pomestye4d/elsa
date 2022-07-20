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
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.l10n.L10nMessageDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMessageParameterDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMessagesBundleDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMetaRegistry;
import com.vga.platform.elsa.common.meta.l10n.L10nMetaRegistryConfigurator;

public class DemoElsaL10nMetaRegistryConfigurator implements L10nMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(L10nMetaRegistry registry){
		{
			var bundleDescription = new L10nMessagesBundleDescription("demo-site");
			registry.getBundles().put(bundleDescription.getId(), bundleDescription);
			{
				var messageDescription = new L10nMessageDescription("Are_you_sure_to_delete");
				{
					var paramDescription = new L10nMessageParameterDescription("object");
					paramDescription.setType(StandardValueType.ENTITY_REFERENCE);
					messageDescription.getParameters().put(paramDescription.getId(), paramDescription);
				}
				messageDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Вы уверены что хотите удалить {0}?");
				bundleDescription.getMessages().put(messageDescription.getId(), messageDescription);
			}
			{
				var messageDescription = new L10nMessageDescription("Yes");
				messageDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Да");
				bundleDescription.getMessages().put(messageDescription.getId(), messageDescription);
			}
		}
	}
}
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

package com.vga.platform.elsa.server.core;

import com.vga.platform.elsa.common.core.utils.LocaleUtils;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.l10n.L10nMessageDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMessageParameterDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMessagesBundleDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMetaRegistry;
import com.vga.platform.elsa.common.meta.l10n.L10nMetaRegistryConfigurator;
import org.springframework.stereotype.Component;

public class CoreL10nMessagesRegistryConfigurator implements L10nMetaRegistryConfigurator{

	@Override
	public void updateMetaRegistry(L10nMetaRegistry registry){
		{
			var bundleDescription = new L10nMessagesBundleDescription("core");
			registry.getBundles().put(bundleDescription.getId(), bundleDescription);
			{
				var messageDescription = new L10nMessageDescription("Found_several_records");
				{
					var paramDescription = new L10nMessageParameterDescription("objectType");
					paramDescription.setType(StandardValueType.STRING);
					messageDescription.getParameters().put(paramDescription.getId(), paramDescription);
				}
				{
					var paramDescription = new L10nMessageParameterDescription("propertyName");
					paramDescription.setType(StandardValueType.STRING);
					messageDescription.getParameters().put(paramDescription.getId(), paramDescription);
				}
				{
					var paramDescription = new L10nMessageParameterDescription("propertyValue");
					paramDescription.setType(StandardValueType.STRING);
					messageDescription.getParameters().put(paramDescription.getId(), paramDescription);
				}
				messageDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "найдено несколько записей {0}, где {1} = {2}");
				bundleDescription.getMessages().put(messageDescription.getId(), messageDescription);
			}
			{
				var messageDescription = new L10nMessageDescription("Yes");
				messageDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Да");
				bundleDescription.getMessages().put(messageDescription.getId(), messageDescription);
			}
			{
				var messageDescription = new L10nMessageDescription("No");
				messageDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Нет");
				bundleDescription.getMessages().put(messageDescription.getId(), messageDescription);
			}
			{
				var messageDescription = new L10nMessageDescription("Choose_variant");
				messageDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Выберите вариант");
				bundleDescription.getMessages().put(messageDescription.getId(), messageDescription);
			}
			{
				var messageDescription = new L10nMessageDescription("Question");
				messageDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Вопрос");
				bundleDescription.getMessages().put(messageDescription.getId(), messageDescription);
			}
			{
				var messageDescription = new L10nMessageDescription("Object_not_found");
				{
					var paramDescription = new L10nMessageParameterDescription("objectId");
					paramDescription.setType(StandardValueType.STRING);
					messageDescription.getParameters().put(paramDescription.getId(), paramDescription);
				}
				{
					var paramDescription = new L10nMessageParameterDescription("objectUid");
					paramDescription.setType(StandardValueType.STRING);
					messageDescription.getParameters().put(paramDescription.getId(), paramDescription);
				}
				messageDescription.getDisplayNames().put(LocaleUtils.getLocale("ru",""), "Объект {0} с идентификатором {1} не найден");
				bundleDescription.getMessages().put(messageDescription.getId(), messageDescription);
			}
		}
	}
}
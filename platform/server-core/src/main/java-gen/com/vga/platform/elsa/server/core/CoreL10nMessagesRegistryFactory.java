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

import com.vga.platform.elsa.common.core.l10n.Localizer;
import com.vga.platform.elsa.common.core.model.common.L10nMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class CoreL10nMessagesRegistryFactory{

	@Autowired
	private Localizer localizer;

	public String Found_several_records(String objectType, String propertyName, String propertyValue){
		return localizer.toString("core", "Found_several_records", null, objectType, propertyName, propertyValue);
	}

	public static L10nMessage Found_several_recordsMessage(String objectType, String propertyName, String propertyValue){
		return new L10nMessage("core", "Found_several_records", objectType, propertyName, propertyValue);
	}

	public String Object_not_found(String objectId, String objectUid){
		return localizer.toString("core", "Object_not_found", null, objectId, objectUid);
	}

	public static L10nMessage Object_not_foundMessage(String objectId, String objectUid){
		return new L10nMessage("core", "Object_not_found", objectId, objectUid);
	}
}
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
 */

package com.vga.platform.elsa.common.meta.ui;

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.EnumDescription;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class UiMetaRegistry {
    private final Map<String, EnumDescription> enums = new LinkedHashMap<>();

    private final Map<String, EntityDescription> entities = new LinkedHashMap<>();

    private final Map<String, UiWidgetDescription> widgets = new LinkedHashMap<>();

    private final Map<String, UiViewTemplateDescription> viewTemplates = new LinkedHashMap<>();

    private final Map<String, UiTemplateGroupDescription> groups = new LinkedHashMap<>();

    private final Map<String, UiViewDescription> views = new LinkedHashMap<>();


    @Autowired(required = false)
    public void setConfigurators(List<UiMetaRegistryConfigurator> configurators){
        configurators.forEach(it -> it.updateMetaRegistry(this));
    }

    public Map<String, EnumDescription> getEnums() {
        return enums;
    }

    public Map<String, UiWidgetDescription> getWidgets() {
        return widgets;
    }

    public Map<String, UiViewTemplateDescription> getViewTemplates() {
        return viewTemplates;
    }

    public Map<String, UiTemplateGroupDescription> getGroups() {
        return groups;
    }

    public Map<String, EntityDescription> getEntities() {
        return entities;
    }

    public Map<String, UiViewDescription> getViews() {
        return views;
    }
}

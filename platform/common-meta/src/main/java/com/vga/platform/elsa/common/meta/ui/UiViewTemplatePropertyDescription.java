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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UiViewTemplatePropertyDescription {
    private String tagName;
    private UiModelPropertyType type;
    private boolean nonNullable;
    private String className;
    private final Map<String, UiAttributeDescription> attributes = new LinkedHashMap<>();
    private final Map<String, UiViewTemplatePropertyDescription> properties = new LinkedHashMap<>();
    private final Map<String, UiViewTemplateCollectionDescription> collections = new LinkedHashMap<>();
    private final List<UiGroupDescription> groups = new ArrayList<>();

    public UiViewTemplatePropertyDescription() {
    }

    public UiViewTemplatePropertyDescription(String tagName) {
        this.tagName = tagName;
    }


    public UiModelPropertyType getType() {
        return type;
    }

    public void setType(UiModelPropertyType type) {
        this.type = type;
    }

    public boolean isNonNullable() {
        return nonNullable;
    }

    public void setNonNullable(boolean nonNullable) {
        this.nonNullable = nonNullable;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, UiViewTemplatePropertyDescription> getProperties() {
        return properties;
    }

    public Map<String, UiViewTemplateCollectionDescription> getCollections() {
        return collections;
    }

    public Map<String, UiAttributeDescription> getAttributes() {
        return attributes;
    }

    public List<UiGroupDescription> getGroups() {
        return groups;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}


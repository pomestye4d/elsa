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

public class UiViewTemplateCollectionDescription {
    private String elementTagName;
    private UiModelPropertyType elementType;
    private String elementClassName;
    private String wrapperTagName;
    private final Map<String, UiAttributeDescription> attributes = new LinkedHashMap<>();
    private final Map<String, UiViewTemplatePropertyDescription> properties = new LinkedHashMap<>();
    private final Map<String, UiViewTemplateCollectionDescription> collections = new LinkedHashMap<>();
    private final List<UiGroupDescription> groups = new ArrayList<>();

    public UiViewTemplateCollectionDescription() {
    }

    public UiViewTemplateCollectionDescription(String elementTagName) {
        this.elementTagName = elementTagName;
    }

    public String getElementTagName() {
        return elementTagName;
    }

    public void setElementTagName(String elementTagName) {
        this.elementTagName = elementTagName;
    }

    public UiModelPropertyType getElementType() {
        return elementType;
    }

    public void setElementType(UiModelPropertyType elementType) {
        this.elementType = elementType;
    }

    public String getElementClassName() {
        return elementClassName;
    }

    public void setElementClassName(String elementClassName) {
        this.elementClassName = elementClassName;
    }

    public List<UiGroupDescription> getGroups() {
        return groups;
    }


    public Map<String, UiAttributeDescription> getAttributes() {
        return attributes;
    }

    public Map<String, UiViewTemplatePropertyDescription> getProperties() {
        return properties;
    }

    public Map<String, UiViewTemplateCollectionDescription> getCollections() {
        return collections;
    }

    public String getWrapperTagName() {
        return wrapperTagName;
    }

    public void setWrapperTagName(String wrapperTagName) {
        this.wrapperTagName = wrapperTagName;
    }
}

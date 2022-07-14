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

package com.vga.platform.elsa.gradle.parser.ui;

import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiTemplateMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiViewMemberDescription;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public interface ViewTemplateParserHandler {

    static boolean isViewTemplate(XmlNode node, ViewTemplateParserHandlerCallback callback) {
        return callback.getHandler(node.getName()) != null;
    }

    static void updateModelWidgetProperty(XmlNode widgetNode, StandardPropertyDescription descr, ViewTemplateParserHandlerCallback callback) {
        var widget = callback.getFullRegistry().getWidgets().get(widgetNode.getName());
        descr.setType(UiTemplateMetaRegistryParser.toStandardType(widget.getModel().getType()));
        descr.setClassName(getClassName(descr.getType(), widget.getModel().getClassName()));
    }

    static void updateConfigurationWidgetProperty(XmlNode widgetNode, StandardPropertyDescription descr, ViewTemplateParserHandlerCallback callback) {
        var widget = callback.getFullRegistry().getWidgets().get(widgetNode.getName());
        descr.setType(UiTemplateMetaRegistryParser.toStandardType(widget.getConfiguration().getType()));
        descr.setClassName(getClassName(descr.getType(), widget.getConfiguration().getClassName()));
    }

    static void updateValidationWidgetProperty(XmlNode widgetNode, StandardPropertyDescription descr, ViewTemplateParserHandlerCallback callback) {
        var widget = callback.getFullRegistry().getWidgets().get(widgetNode.getName());
        descr.setType(UiTemplateMetaRegistryParser.toStandardType(widget.getValidation().getType()));
        descr.setClassName(getClassName(descr.getType(), widget.getModel().getClassName()));
    }

    private static String getClassName(StandardValueType type, String className){
        return switch (type){
            case ENUM, CLASS, ENTITY -> className;
            default -> null;
        };
    }

    String getTagName();
    String getModelClassName(XmlNode node);
    String getConfigurationClassName(XmlNode node);
    String getValidationClassName(XmlNode node);
    void addEntities(XmlNode node, Map<String, Map<Locale, String>> localizations, ViewTemplateParserHandlerCallback callback);
    List<UiViewMemberDescription> getViewMembers(XmlNode node, Map<String, ViewTemplateParserHandler> handlers, UiTemplateMetaRegistry fullTemplateRegistry, UiMetaRegistry registry);
    String getId(XmlNode viewNode);
    String getWidgetClassName(XmlNode node);
    void updateImports(Set<String> additionalEntities, XmlNode name, UiMetaRegistry ftr, Map<String, ViewTemplateParserHandler> handler);

    List<XmlNode> getAllViewNodes(XmlNode view, Map<String, ViewTemplateParserHandler> handlers);
}

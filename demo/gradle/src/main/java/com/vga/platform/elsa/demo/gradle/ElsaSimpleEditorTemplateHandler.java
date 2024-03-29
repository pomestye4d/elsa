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

package com.vga.platform.elsa.demo.gradle;

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiTemplateMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiViewMemberDescription;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.parser.ui.ViewTemplateParserHandler;
import com.vga.platform.elsa.gradle.parser.ui.ViewTemplateParserHandlerCallback;

import java.util.*;

public class ElsaSimpleEditorTemplateHandler implements ViewTemplateParserHandler {
    @Override
    public String getTagName() {
        return "simple-editor";
    }

    @Override
    public String getModelClassName(XmlNode node) {
        return "%sVM".formatted(node.getAttribute("id"));
    }

    @Override
    public String getConfigurationClassName(XmlNode node) {
        return "%sVC".formatted(node.getAttribute("id"));
    }

    @Override
    public String getValidationClassName(XmlNode node) {
        return "%sVV".formatted(node.getAttribute("id"));
    }

    @Override
    public void addEntities(XmlNode originalNode, Map<String, Map<Locale, String>> localizations, ViewTemplateParserHandlerCallback callback) {
        var node = originalNode.cloneNode();
        String id = node.getAttribute("id");
        var modelEtt = new EntityDescription("%sVM".formatted(id));
        var configEtt = new EntityDescription("%sVC".formatted(id));
        var validationEtt = new EntityDescription("%sVV".formatted(id));
        Map<String, Map<Locale, String>> l10ns = new LinkedHashMap<>();
        callback.addEntity(modelEtt);
        callback.addEntity(configEtt);
        callback.addEntity(validationEtt);
        for (XmlNode columnElm : node.getChildren("row")) {
            var rowId = columnElm.getAttribute("id");
            if(localizations.containsKey(rowId)){
                l10ns.put(rowId, localizations.get(rowId));
            }
            var widgetElm = columnElm.getChildren().get(0);
            var vmProp = new StandardPropertyDescription(rowId);
            modelEtt.getProperties().put(vmProp.getId(), vmProp);
            var vcProp = new StandardPropertyDescription(rowId);
            configEtt.getProperties().put(vcProp.getId(), vcProp);
            var vvProp = new StandardPropertyDescription(rowId);
            validationEtt.getProperties().put(vvProp.getId(), vvProp);
            ViewTemplateParserHandler.updateModelWidgetProperty(widgetElm, vmProp, callback);
            ViewTemplateParserHandler.updateConfigurationWidgetProperty(widgetElm, vcProp, callback);
            ViewTemplateParserHandler.updateValidationWidgetProperty(widgetElm, vvProp, callback);
        }
        callback.addViewDescription(id, node, l10ns);
    }

    @Override
    public List<UiViewMemberDescription> getViewMembers(XmlNode node, Map<String, ViewTemplateParserHandler> handlers, UiTemplateMetaRegistry fullTemplateRegistry, UiMetaRegistry registry) {
        return Collections.emptyList();
    }

    private XmlNode getContentElm(XmlNode node, UiMetaRegistry registry) {
        var contentNode = node.getFirstChild("content");
        if(contentNode.getAttribute("container-ref") != null){
            return registry.getViews().get(contentNode.getAttribute("container-ref")).getView();
        }
        return contentNode.getChildren().get(0);
    }

    @Override
    public String getId(XmlNode viewNode) {
        return viewNode.getAttribute("id");
    }


    @Override
    public String getWidgetClassName(XmlNode node) {
        var id = JavaCodeGeneratorUtils.getSimpleName(getId(node));
        return "SimpleEditorTemplate<%sVM, %sVC, %sVV>".formatted(id, id, id);
    }

    @Override
    public void updateImports(Set<String> additionalEntities, XmlNode node, UiTemplateMetaRegistry ftr, Map<String, ViewTemplateParserHandler> handlers) {
        additionalEntities.add("SimpleEditorTemplate");
    }

    @Override
    public List<XmlNode> getAllViewNodes(XmlNode node, Map<String, ViewTemplateParserHandler> handlers) {
        var result = new ArrayList<XmlNode>();
        result.add(node);
        return result;
    }

}

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

import com.vga.platform.elsa.common.meta.common.*;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiTemplateMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiViewMemberDescription;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.parser.ui.ViewTemplateParserHandler;
import com.vga.platform.elsa.gradle.parser.ui.ViewTemplateParserHandlerCallback;

import java.util.*;

public class ElsaTableTemplateHandler implements ViewTemplateParserHandler {

    @Override
    public String getTagName() {
        return "table";
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
    public void addEntities(XmlNode node, Map<String, Map<Locale, String>> localizations, ViewTemplateParserHandlerCallback callback){
        String id = node.getAttribute("id");
        var modelEtt = new EntityDescription("%sVM".formatted(id));
        var configEtt = new EntityDescription("%sVC".formatted(id));
        var validationEtt = new EntityDescription("%sVV".formatted(id));
        callback.addEntity(modelEtt);
        callback.addEntity(configEtt);
        callback.addEntity(validationEtt);
        Map<String, Map<Locale, String>> l10ns = new LinkedHashMap<>();
        var modelRowEtt = new EntityDescription("%sRowVM".formatted(id));
        var rowVM = new StandardCollectionDescription();
        rowVM.setId("items");
        rowVM.setElementType(StandardValueType.ENTITY);
        rowVM.setElementClassName(modelRowEtt.getId());
        modelEtt.getCollections().put(rowVM.getId(), rowVM);
        var configRowEtt = new EntityDescription("%sRowVC".formatted(id));
        var rowVC = new StandardCollectionDescription();
        rowVC.setId("items");
        rowVC.setElementType(StandardValueType.ENTITY);
        rowVC.setElementClassName(configRowEtt.getId());
        configEtt.getCollections().put(rowVC.getId(), rowVC);
        var validationRowEtt = new EntityDescription("%sRowVV".formatted(id));
        var rowVV = new StandardCollectionDescription();
        rowVV.setId("items");
        rowVV.setElementType(StandardValueType.ENTITY);
        rowVV.setElementClassName(validationRowEtt.getId());
        validationEtt.getCollections().put(rowVV.getId(), rowVV);
        callback.addEntity(modelRowEtt);
        callback.addEntity(configRowEtt);
        callback.addEntity(validationRowEtt);
        for (XmlNode columnElm : node.getChildren("column")) {
            var columnId = columnElm.getAttribute("id");
            if(localizations.containsKey(columnId)){
                l10ns.put(columnId, localizations.get(columnId));
            }
            var vmProp = new StandardPropertyDescription(columnId);
            vmProp.setType(getType(columnElm.getAttribute("dataType")));
            modelRowEtt.getProperties().put(vmProp.getId(), vmProp);
        }
        callback.addViewDescription(id, node, l10ns);
    }

    private StandardValueType getType(String dataType) {
        return switch (dataType){
            case "TEXT" -> StandardValueType.STRING;
            case "LONG" -> StandardValueType.LONG;
            default -> throw new IllegalArgumentException("unsupported data type %s".formatted(dataType));
        };
    }

    @Override
    public List<UiViewMemberDescription> getViewMembers(XmlNode node, Map<String, ViewTemplateParserHandler> handlers, UiTemplateMetaRegistry fullTemplateRegistry, UiMetaRegistry registry) {
        return Collections.emptyList();
    }

    @Override
    public String getId(XmlNode viewNode) {
        return viewNode.getAttribute("id");
    }

    @Override
    public String getWidgetClassName(XmlNode node) {
        var id = JavaCodeGeneratorUtils.getSimpleName(getId(node));
        return "TableTemplate<%sVM, %sVC, %sVV>".formatted(id, id, id);
    }

    @Override
    public void updateImports(Set<String> additionalEntities, XmlNode node, UiTemplateMetaRegistry ftr, Map<String, ViewTemplateParserHandler> handlers) {
        additionalEntities.add("TableTemplate");
    }

    @Override
    public List<XmlNode> getAllViewNodes(XmlNode node, Map<String, ViewTemplateParserHandler> handlers) {
        var result = new ArrayList<XmlNode>();
        result.add(node);
        return result;
    }
}

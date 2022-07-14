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

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiTemplateMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiViewDescription;
import com.vga.platform.elsa.gradle.parser.common.CommonParserUtils;
import com.vga.platform.elsa.gradle.parser.common.MetaDataParsingResult;
import com.vga.platform.elsa.gradle.plugin.ElsaJavaExtension;
import com.vga.platform.elsa.gradle.plugin.ElsaWebExtension;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;
import org.gradle.api.Project;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UiMetaRegistryParser {

    public void updateMetaRegistry(UiMetaRegistry registry, List<File> sources, Map<Object,Object> context) {
        var fullTemplateRegistry = (UiTemplateMetaRegistry) context.get("full-template-ui-meta-registry");
        if(fullTemplateRegistry == null){
            fullTemplateRegistry = new UiTemplateMetaRegistry();
            context.put("full-template-ui-meta-registry", fullTemplateRegistry);
        }
        var ftr = fullTemplateRegistry;
        var fullRegistry = (UiMetaRegistry) context.get("full-ui-meta-registry");
        if(fullRegistry == null){
            fullRegistry = new UiMetaRegistry();
        }
        var fr = fullRegistry;
        Project project = (Project) context.get("project");
        var javaExt = project.getExtensions().findByType(ElsaJavaExtension.class);
        Map<String, ViewTemplateParserHandler> handlers;
        if(javaExt != null){
            handlers = javaExt.getTemplatesHandlers();
        } else {
            handlers = project.getExtensions().getByType(ElsaWebExtension.class).getTemplatesHandlers();
        }
        var callback = new ViewTemplateParserHandlerCallback() {
            @Override
            public void addEntity(EntityDescription ed) {
                registry.getEntities().put(ed.getId(), ed);
            }
            @Override
            public void addViewDescription(String id, XmlNode view, Map<String, Map<Locale, String>> localizations) {
                var vd = new UiViewDescription(id);
                vd.setView(view);
                vd.getLocalizations().putAll(localizations);
                registry.getViews().put(id, vd);
                fr.getViews().put(id, vd);
            }

            @Override
            public ViewTemplateParserHandler getHandler(String tagName) {
                return handlers.get(tagName);
            }

            @Override
            public UiTemplateMetaRegistry getFullRegistry() {
                return ftr;
            }
        };
        sources.forEach(it -> BuildExceptionUtils.wrapException(() -> {
            MetaDataParsingResult pr = CommonParserUtils.parse(it);
            XmlNode node = pr.node();
            for(XmlNode child: node.getChildren()){
                if("enum".equals(child.getName())){
                    CommonParserUtils.updateEnum(registry.getEnums(), child, pr.localizations());
                } else {
                    var handler = handlers.get(child.getName());
                    if(handler != null){
                        handler.addEntities(child, pr.localizations(), callback);
                    }
                }
            }
        }));
    }

}

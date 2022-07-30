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

package com.vga.platform.elsa.gradle.codegen.ui;

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiTemplateMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiViewDescription;
import com.vga.platform.elsa.gradle.codegen.common.CodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.codegen.common.TypeScriptCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.WebCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.parser.ui.UiMetaRegistryParser;
import com.vga.platform.elsa.gradle.parser.ui.UiTemplateMetaRegistryParser;
import com.vga.platform.elsa.gradle.parser.ui.ViewTemplateParserHandler;
import com.vga.platform.elsa.gradle.plugin.ElsaWebExtension;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;
import com.vga.platform.elsa.gradle.utils.BuildTextUtils;
import org.gradle.api.Project;

import java.io.File;
import java.util.*;

public class WebUiCodeGenerator implements CodeGenerator<WebUiCodeGenRecord> {
    @Override
    public void generate(List<WebUiCodeGenRecord> records, File destDir, Set<File> generatedFiles, Map<Object,Object> context){
        @SuppressWarnings("unchecked") var tsAssociations = (Map<String, File>) context.get("ts-associations");
        if(tsAssociations == null){
            tsAssociations = new LinkedHashMap<>();
            context.put("ts-associations", tsAssociations);
        }
        var tsa = tsAssociations;
        var fullTemplateRegistry = (UiTemplateMetaRegistry) context.get("full-ui-template-meta-registry");
        if(fullTemplateRegistry == null){
            fullTemplateRegistry = new UiTemplateMetaRegistry();
        }
        var ftr = fullTemplateRegistry;
        var fullRegistry = (UiMetaRegistry) context.get("full-ui-meta-registry");
        if(fullRegistry == null){
            fullRegistry = new UiMetaRegistry();
        }
        var fr = fullRegistry;
        Project project = (Project) context.get("project");
        var ext = project.getExtensions().getByType(ElsaWebExtension.class);
        tsa.putAll(ext.getImports());
        var handlers = ext.getTemplatesHandlers();
        var parser = new UiMetaRegistryParser();
        var templateParser = new UiTemplateMetaRegistryParser();
        records.forEach(it -> BuildExceptionUtils.wrapException(() ->{
            var metaRegistry = new UiMetaRegistry();
            parser.updateMetaRegistry(metaRegistry, it.getSources(),context);
            parser.updateMetaRegistry(fr, it.getSources(), context);
            templateParser.updateMetaRegistry(ftr, it.getSources());
            var additionalEntities = new LinkedHashSet<String>();
            for(var view: metaRegistry.getViews().values()){
                updateImportEntities(view, additionalEntities, handlers, ftr, fr);
            }
            var gen = new TypeScriptCodeGenerator();
            gen.printLine("/* eslint-disable max-classes-per-file,no-unused-vars,max-len,lines-between-class-members,no-use-before-define  */");
            if(!metaRegistry.getViews().isEmpty()){
                gen.printLine("import { ViewReference } from 'elsa-web-core';");
            }
            WebCodeGeneratorUtils.generateImportCode(metaRegistry.getEntities().values(), additionalEntities, tsa, gen, new File(destDir, it.getTsFileName()));
            for(var en: metaRegistry.getEnums().values()){
                tsa.put(JavaCodeGeneratorUtils.getSimpleName(en.getId()), new File(destDir, it.getTsFileName()));
                WebCodeGeneratorUtils.generateWebEnumCode(en, gen);
            }
            for(var ett: metaRegistry.getEntities().values()){
                tsa.put(JavaCodeGeneratorUtils.getSimpleName(ett.getId()), new File(destDir, it.getTsFileName()));
                WebCodeGeneratorUtils.generateWebEntityCode(ett,  gen);
            }
            for(var view: metaRegistry.getViews().values()){
                generateViewCode(view, gen, handlers, ftr, fr, tsa, new File(destDir, it.getTsFileName()));
            }
            if(!metaRegistry.getViews().isEmpty()){
                generateConstantsCode(metaRegistry.getViews().values(), gen);
            }
            var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), it.getTsFileName(), destDir);
            generatedFiles.add(file);
        }));
    }

    private void generateConstantsCode(Collection<UiViewDescription> values, TypeScriptCodeGenerator gen) throws Exception {
        gen.wrapWithBlock("export const Constants = ", ()->{
            for(UiViewDescription value: values){
                gen.printLine("%s: new ViewReference<%s>('%s'),".formatted(JavaCodeGeneratorUtils.getSimpleName(value.getId()), JavaCodeGeneratorUtils.getSimpleName(value.getId()), value.getId()));
            }
        });
        gen.print(";");
        gen.blankLine();
    }

    private boolean depends(EntityDescription a, EntityDescription b) {
        if(a.getProperties().values().stream().anyMatch((it) -> b.getId().equals(it.getClassName()))){
            return true;
        }
        return a.getCollections().values().stream().anyMatch((it) -> b.getId().equals(it.getElementClassName()));
    }

    private void updateImportEntities(UiViewDescription view, Set<String> additionalEntities, Map<String, ViewTemplateParserHandler> handlers, UiTemplateMetaRegistry uiTemplateMetaRegistry, UiMetaRegistry ftr) {
        var handler = handlers.get(view.getView().getName());
        if(handler == null){
            return;
        }
        handler.updateImports(additionalEntities, view.getView(), uiTemplateMetaRegistry, handlers);
    }

    private void generateViewCode(UiViewDescription view, TypeScriptCodeGenerator gen, Map<String, ViewTemplateParserHandler> handlers, UiTemplateMetaRegistry fullTemplateRegistry, UiMetaRegistry fr, Map<String, File> tsa, File file ) throws Exception {
        var viewNode = view.getView();
        var handler = handlers.get(viewNode.getName());
        if(handler == null){
            return;
        }
        tsa.put(JavaCodeGeneratorUtils.getSimpleName(handler.getId(viewNode)), file);
        gen.wrapWithBlock("export class %s extends %s ".formatted(JavaCodeGeneratorUtils.getSimpleName(handler.getId(viewNode)), handler.getWidgetClassName(viewNode)), () ->{
            var members = handler.getViewMembers(viewNode, handlers, fullTemplateRegistry, fr);
            for(var member: members){
                gen.printLine("// @ts-ignore");
                gen.printLine("%s: %s;".formatted(member.getId(), member.getWidgetClass()));
            }
        });
        gen.blankLine();
    }
}

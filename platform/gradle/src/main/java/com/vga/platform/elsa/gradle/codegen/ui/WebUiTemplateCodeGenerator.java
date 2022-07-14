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

import com.vga.platform.elsa.common.meta.ui.UiTemplateMetaRegistry;
import com.vga.platform.elsa.gradle.codegen.common.CodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.codegen.common.TypeScriptCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.WebCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.parser.ui.UiTemplateMetaRegistryParser;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;

import java.io.File;
import java.util.*;

public class WebUiTemplateCodeGenerator implements CodeGenerator<WebUiTemplateCodeGenRecord> {
    @Override
    public void generate(List<WebUiTemplateCodeGenRecord> records, File destDir, Set<File> generatedFiles, Map<Object,Object> context){
        var parser = new UiTemplateMetaRegistryParser();
        var fullTemplateRegistry = (UiTemplateMetaRegistry) context.get("full-ui-template-meta-registry");
        if(fullTemplateRegistry == null){
            fullTemplateRegistry = new UiTemplateMetaRegistry();
            context.put("full-ui-template-meta-registry", fullTemplateRegistry);
        }
        var ftr = fullTemplateRegistry;
        @SuppressWarnings("unchecked") var tsAssociations = (Map<String, File>) context.get("ts-associations");
        if(tsAssociations == null){
            tsAssociations = new LinkedHashMap<>();
            context.put("ts-associations", tsAssociations);
        }
        var tsa = tsAssociations;
        records.forEach(it -> BuildExceptionUtils.wrapException(() ->{
            var metaRegistry = new UiTemplateMetaRegistry();
            parser.updateMetaRegistry(metaRegistry, it.getSources());
            parser.updateMetaRegistry(ftr, it.getSources());
            var gen = new TypeScriptCodeGenerator();
            WebCodeGeneratorUtils.generateImportCode(metaRegistry.getEntities().values(), new HashSet<>(), tsa, gen, new File(destDir, it.getTsFileName()));
            for(var en: metaRegistry.getEnums().values()){
                WebCodeGeneratorUtils.generateWebEnumCode(en, gen);
            }
            for(var ett: metaRegistry.getEntities().values()){
                WebCodeGeneratorUtils.generateWebEntityCode(ett, gen);
            }
            var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), it.getTsFileName(), destDir);
            metaRegistry.getEnums().keySet().forEach(it2 -> tsa.put(it2, file));
            metaRegistry.getEntities().keySet().forEach(it2 -> tsa.put(it2, file));
            generatedFiles.add(file);
        }));
    }
}

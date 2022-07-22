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
import com.vga.platform.elsa.gradle.parser.ui.UiTemplateMetaRegistryParser;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaUiTemplateCodeGenerator implements CodeGenerator<JavaUiTemplateCodeGenRecord> {
    @Override
    public void generate(List<JavaUiTemplateCodeGenRecord> records, File destDir, Set<File> generatedFiles, Map<Object,Object> context) {
        var fullTemplateRegistry = (UiTemplateMetaRegistry) context.get("full-ui-template-meta-registry");
        if(fullTemplateRegistry == null){
            fullTemplateRegistry = new UiTemplateMetaRegistry();
            context.put("full-ui-template-meta-registry", fullTemplateRegistry);
        }
        var configurators = new LinkedHashMap<String, List<File>>();
        var parser = new UiTemplateMetaRegistryParser();
        var ftr = fullTemplateRegistry;
        records.forEach(it -> BuildExceptionUtils.wrapException(() ->{
            var metaRegistry = new UiTemplateMetaRegistry();
            parser.updateMetaRegistry(metaRegistry, it.getSources());
            parser.updateMetaRegistry(ftr, it.getSources());
            BuildExceptionUtils.wrapException(() -> JavaUiTemplateXsdGenerator.generate(metaRegistry, destDir, it.getXsdFileName(), it.getTargetNameSpace(), generatedFiles));
            BuildExceptionUtils.wrapException(() -> JavaUiTemplateEntitiesGenerator.generate(metaRegistry, destDir,  generatedFiles));
            if(it.getConfigurator() != null){
                BuildExceptionUtils.wrapException(() -> JavaUiTemplateConfiguratorGenerator.generate(metaRegistry, it.getConfigurator(), destDir,  generatedFiles));
            }
        }));
    }
}

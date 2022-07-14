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

package com.vga.platform.elsa.gradle.codegen.domain;

import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistry;
import com.vga.platform.elsa.gradle.codegen.common.CodeGenerator;
import com.vga.platform.elsa.gradle.parser.domain.DomainMetaRegistryParser;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaDomainCodeGenerator implements CodeGenerator<JavaDomainCodeGenRecord> {
    @Override
    public void generate(List<JavaDomainCodeGenRecord> records, File destDir, Set<File> generatedFiles, Map<Object,Object> context){
        records.forEach(it -> BuildExceptionUtils.wrapException(() ->{
            var parser = new DomainMetaRegistryParser();
            var metaRegistry = new DomainMetaRegistry();
            parser.updateMetaRegistry(metaRegistry, it.getSources());
            BuildExceptionUtils.wrapException(() ->JavaDomainConfiguratorCodeGenerator.generate(metaRegistry, it.getRegistryConfigurator(), destDir, generatedFiles));
            BuildExceptionUtils.wrapException(() ->JavaDomainEntitiesCodeGenerator.generate(metaRegistry, destDir, generatedFiles));
            BuildExceptionUtils.wrapException(() ->JavaDomainCachedObjectsCodeGenerator.generate(metaRegistry, (DomainMetaRegistry) context.get("domain-meta-registry"), destDir, generatedFiles));
            BuildExceptionUtils.wrapException(() ->JavaDomainFieldsClassCodeGenerator.generate(metaRegistry, destDir, generatedFiles));
        }));
    }
}

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

package com.vga.platform.elsa.gradle.codegen.remoting;

import com.vga.platform.elsa.common.meta.remoting.RemotingMetaRegistry;
import com.vga.platform.elsa.gradle.codegen.common.CodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.codegen.common.TypeScriptCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.WebCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.parser.remoting.RemotingMetaRegistryParser;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebRemotingCodeGenerator implements CodeGenerator<WebRemotingCodeGenRecord> {
    @Override
    public void generate(List<WebRemotingCodeGenRecord> records, File destDir, Set<File> generatedFiles, Map<Object,Object> context){
        records.forEach(it -> {
            var parser = new RemotingMetaRegistryParser();
            var metaRegistry = new RemotingMetaRegistry();
            parser.updateMetaRegistry(metaRegistry, it.getSources());
            BuildExceptionUtils.wrapException(() -> {
                var gen = new TypeScriptCodeGenerator();
                gen.printLine("import { serverCall, PreloaderHandler } from 'elsa-web-core';");
                gen.blankLine();
                for(var en: metaRegistry.getEnums().values()){
                    WebCodeGeneratorUtils.generateWebEnumCode(en, gen);
                }
                for(var ett: metaRegistry.getEntities().values()){
                    WebCodeGeneratorUtils.generateWebEntityCode(ett, gen);
                }

                for(var remoting: metaRegistry.getRemotings().values()){
                    for(var group: remoting.getGroups().values()){
                        for(var serverCall: group.getServerCalls().values()){
                            gen.printLine("// eslint-disable-next-line max-len");
                            gen.wrapWithBlock("export const %s = async (request: %s, preloaderHandler: PreloaderHandler| boolean = true, operationId: string|null = null) => "
                                            .formatted(JavaCodeGeneratorUtils.toCamelCased("%s_%s".formatted(group.getId(), serverCall.getId())),
                                                    JavaCodeGeneratorUtils.getSimpleName(serverCall.getRequestClassName()))
                                    , ()->{
                                gen.printLine("// noinspection UnnecessaryLocalVariableJS");
                                gen.printLine("const result = await serverCall<%s, %s>('%s', '%s', '%s', request, preloaderHandler, operationId);"
                                        .formatted(JavaCodeGeneratorUtils.getSimpleName(serverCall.getRequestClassName()),
                                                JavaCodeGeneratorUtils.getSimpleName(serverCall.getResponseClassName()), remoting.getId(), group.getId(), serverCall.getId()));
                                gen.printLine("return result;");
                            });
                            gen.print(";\n");
                        }
                    }
                }
                var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), it.getRemotingFacade(), destDir);
                generatedFiles.add(file);
            });
        });
    }
}

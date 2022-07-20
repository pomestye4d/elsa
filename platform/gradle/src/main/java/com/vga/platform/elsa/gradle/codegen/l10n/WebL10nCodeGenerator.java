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

package com.vga.platform.elsa.gradle.codegen.l10n;

import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.l10n.L10nMessageDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMessageParameterDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMessagesBundleDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMetaRegistry;
import com.vga.platform.elsa.gradle.codegen.common.CodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.codegen.common.TypeScriptCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.WebCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.parser.l10n.L10nMetaRegistryParser;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;
import com.vga.platform.elsa.gradle.utils.BuildTextUtils;

import java.io.File;
import java.util.*;

public class WebL10nCodeGenerator implements CodeGenerator<WebL10nCodeGenRecord> {
    @Override
    public void generate(List<WebL10nCodeGenRecord> records, File destDir, Set<File> generatedFiles, Map<Object,Object> context) throws Exception{
        var parser = new L10nMetaRegistryParser();
        for(WebL10nCodeGenRecord record: records){
            var reg = new L10nMetaRegistry();
            record.getSources().forEach(it -> BuildExceptionUtils.wrapException(() -> parser.updateMetaRegistry(reg, it)));
            if(!reg.getBundles().isEmpty()){
                var bundle = reg.getBundles().values().iterator().next();
                generateL10n(record.getL10nFileName(), record.getTsClassName(), bundle, destDir, generatedFiles);
            }
        }
    }

    private void generateL10n(String fileName, String tsClassName, L10nMessagesBundleDescription bundle, File destDir, Set<File> generatedFiles) throws Exception {
        var gen = new TypeScriptCodeGenerator();
        var commonImports = new ArrayList<String>();
        for(L10nMessageDescription message : bundle.getMessages().values()){
            for(L10nMessageParameterDescription param: message.getParameters().values()){
                if(param.getType() == StandardValueType.ENTITY_REFERENCE && !commonImports.contains("EntityReference")){
                    commonImports.add("EntityReference");
                }
            }
        }
        gen.printLine("import { BaseL10nBundle%s } from 'elsa-web-core';".formatted(commonImports.isEmpty()? "": ", " + BuildTextUtils.joinToString(commonImports,", ")));
        gen.blankLine();
        gen.wrapWithBlock("class %s extends BaseL10nBundle ".formatted(tsClassName), () ->{
            for(L10nMessageDescription message : bundle.getMessages().values()){
                if(message.getParameters().isEmpty()){
                    gen.printLine("%s = '???';".formatted(message.getId()));
                    gen.blankLine();
                }
            }
            gen.wrapWithBlock("constructor() ", ()-> gen.printLine("super('%s');".formatted(bundle.getId())));
            for(L10nMessageDescription message : bundle.getMessages().values()){
                if(!message.getParameters().isEmpty()){
                    gen.blankLine();
                    var sb1 = new StringBuilder();
                    var sb2 = new StringBuilder();
                    for(L10nMessageParameterDescription param: message.getParameters().values()){
                        if(sb2.length() > 0){
                            sb1.append(", ");
                            sb2.append(", ");
                        }
                        sb1.append("%s: %s".formatted(param.getId(), WebCodeGeneratorUtils.getType(param.getType(), param.getClassName())));
                        sb2.append(param.getId());
                    }
                    gen.wrapWithBlock("%s(%s) ".formatted(message.getId(), sb1), ()-> gen.printLine("return this.getMessage('%s', %s);".formatted(message.getId(), sb2)));
                }
            }
        });
        gen.blankLine();
        gen.printLine("// eslint-disable-next-line import/prefer-default-export");
        gen.printLine("export const %s = new %s();".formatted(tsClassName.substring(0,1).toLowerCase(Locale.ROOT)+ tsClassName.substring(1), tsClassName));
        var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), fileName, destDir);
        generatedFiles.add(file);
    }
}

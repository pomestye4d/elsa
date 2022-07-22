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
import com.vga.platform.elsa.common.meta.common.EnumDescription;
import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistry;
import com.vga.platform.elsa.common.meta.ui.UiViewDescription;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.utils.BuildTextUtils;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class JavaUiConfiguratorGenerator {
    public static void generate(UiMetaRegistry registry, String configurator, File destDir, Set<File> generatedFiles) throws Exception {
        var gen = new JavaCodeGenerator();
        gen.setPackageName(JavaCodeGeneratorUtils.getPackage(configurator));
        gen.addImport("com.vga.platform.elsa.common.meta.ui.UiMetaRegistryConfigurator");
        gen.addImport("com.vga.platform.elsa.common.meta.ui.UiMetaRegistry");
        gen.addImport("java.util.*");
        gen.wrapWithBlock("public class %s implements UiMetaRegistryConfigurator".formatted(JavaCodeGeneratorUtils.getSimpleName(configurator)), () -> {
            gen.blankLine();
            gen.printLine("@Override");
            gen.wrapWithBlock("public void updateMetaRegistry(UiMetaRegistry registry)", () -> {
                for (EnumDescription ed : registry.getEnums().values()) {
                    JavaCodeGeneratorUtils.generateJavaEnumConfiguratorCode(ed, gen);
                }
                for (EntityDescription ed : registry.getEntities().values()) {
                    JavaCodeGeneratorUtils.generateJavaEntityConfiguratorCode(ed, gen);
                }
                for(UiViewDescription view: registry.getViews().values()){
                    gen.wrapWithBlock("", () ->{
                        gen.addImport("com.vga.platform.elsa.common.meta.ui.UiViewDescription");
                        gen.printLine("var viewDescription = new UiViewDescription(\"%s\");".formatted(view.getId()));
                        gen.printLine("registry.getViews().put(\"%s\", viewDescription);".formatted(view.getId()));
                        processXmlNode(gen, view.getView(), 0);
                        for(Map.Entry<String, Map<Locale, String>> locEntry :  view.getLocalizations().entrySet()) {
                            gen.addImport("java.util.*");
                            gen.wrapWithBlock("", () -> {
                                gen.printLine("var l10n = new LinkedHashMap<Locale, String>();");
                                for (Map.Entry<Locale, String> entry : locEntry.getValue().entrySet()) {
                                    gen.addImport("com.vga.platform.elsa.common.core.utils.LocaleUtils");
                                    gen.printLine("l10n.put(LocaleUtils.getLocale(\"%s\",\"%s\"), \"%s\");".formatted(entry.getKey().getLanguage(), entry.getKey().getCountry(), entry.getValue()));
                                }
                                gen.printLine("viewDescription.getLocalizations().put(\"%s\", l10n);".formatted(locEntry.getKey()));
                            });
                        }
                    });
                }
            });
        });
        var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), configurator + ".java", destDir);
        generatedFiles.add(file);
    }

    private static void processXmlNode(JavaCodeGenerator gen, XmlNode node, int index) throws Exception {
        gen.addImport("com.vga.platform.elsa.common.meta.common.XmlNode");
        gen.printLine("var xmlNode%s = new XmlNode();".formatted(index));
        gen.printLine("xmlNode%s.setName(\"%s\");".formatted(index, node.getName()));
        if(!BuildTextUtils.isBlank(node.getValue())){
            gen.printLine("xmlNode%s.setValue(\"%s\");".formatted(index, node.getValue()));
        }
        for(Map.Entry<String, String> entry: node.getAttributes().entrySet()){
            gen.printLine("xmlNode%s.getAttributes().put(\"%s\", \"%s\");".formatted(index, entry.getKey(), entry.getValue()));
        }
        for(XmlNode child: node.getChildren()){
            gen.wrapWithBlock("", () ->{
                processXmlNode(gen, child, index+1);
                gen.printLine("xmlNode%s.getChildren().add(xmlNode%s);".formatted(index, index+1));
            });
        }
    }
}

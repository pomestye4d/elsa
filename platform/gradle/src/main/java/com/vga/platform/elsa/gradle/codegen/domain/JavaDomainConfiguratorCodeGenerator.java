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

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.EnumDescription;
import com.vga.platform.elsa.common.meta.domain.*;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class JavaDomainConfiguratorCodeGenerator {
    public static void generate(DomainMetaRegistry registry, String configurator, File destDir, Set<File> generatedFiles) throws Exception {
        var gen = new JavaCodeGenerator();
        gen.setPackageName(JavaCodeGeneratorUtils.getPackage(configurator));
        gen.addImport("com.vga.platform.elsa.common.meta.domain.DomainMetaRegistryConfigurator");
        gen.addImport("com.vga.platform.elsa.common.meta.domain.DomainMetaRegistry");
        gen.wrapWithBlock("public class %s implements DomainMetaRegistryConfigurator".formatted(JavaCodeGeneratorUtils.getSimpleName(configurator)), () -> {
            gen.blankLine();
            gen.printLine("@Override");
            gen.wrapWithBlock("public void updateMetaRegistry(DomainMetaRegistry registry)", () ->{
                for(EnumDescription ed : registry.getEnums().values()){
                    JavaCodeGeneratorUtils.generateJavaEnumConfiguratorCode(ed, gen);
                }
                for(EntityDescription ed : registry.getEntities().values()){
                    JavaCodeGeneratorUtils.generateJavaEntityConfiguratorCode(ed, gen);
                }
                for(DocumentDescription dd : registry.getDocuments().values()){
                    gen.addImport("com.vga.platform.elsa.common.meta.domain.DocumentDescription");
                    gen.wrapWithBlock(null, () ->{
                        gen.printLine("var documentDescription = new DocumentDescription(\"%s\");".formatted(dd.getId()));
                        gen.printLine("registry.getDocuments().put(documentDescription.getId(), documentDescription);");
                        if(dd.isCacheCaption()){
                            gen.printLine("documentDescription.setCacheCaption(true);");
                        }
                        if(dd.isCacheResolve()){
                            gen.printLine("documentDescription.setCacheResolve(true);");
                        }
                        JavaCodeGeneratorUtils.generateJavaEntityConfiguratorCode("documentDescription", dd, gen);
                    });
                }
                for(SearchableProjectionDescription pd: registry.getSearchableProjections().values()){
                    gen.addImport("com.vga.platform.elsa.common.meta.domain.SearchableProjectionDescription");
                    gen.wrapWithBlock(null, () ->{
                        gen.printLine("var projectionDescription = new SearchableProjectionDescription(\"%s\");".formatted(pd.getId()));
                        gen.printLine("registry.getSearchableProjections().put(projectionDescription.getId(), projectionDescription);");
                        gen.printLine("projectionDescription.setDocument(\"%s\");".formatted(pd.getDocument()));
                        generateJavaSearchableCode("projectionDescription", pd, gen);
                    });
                }
                for(AssetDescription ad: registry.getAssets().values()){
                    gen.addImport("com.vga.platform.elsa.common.meta.domain.AssetDescription");
                    gen.wrapWithBlock(null, () ->{
                        gen.printLine("var assetDescription = new AssetDescription(\"%s\");".formatted(ad.getId()));
                        gen.printLine("registry.getAssets().put(assetDescription.getId(), assetDescription);");
                        generateJavaSearchableCode("assetDescription", ad, gen);
                    });
                }
            });

        });
        var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), configurator+".java", destDir);
        generatedFiles.add(file);
    }

    private static void generateJavaSearchableCode(String descriptionName, BaseSearchableDescription sd, JavaCodeGenerator gen) throws Exception {
        if(sd.isHidden()){
            gen.printLine("%s.setHidden(true);".formatted(descriptionName));
        }
        for(Map.Entry<Locale, String> entry: sd.getDisplayNames().entrySet()){
            gen.addImport("com.vga.platform.elsa.common.core.utils.LocaleUtils");
            gen.printLine("%s.getDisplayNames().put(LocaleUtils.getLocale(\"%s\",\"%s\"), \"%s\");".formatted(descriptionName, entry.getKey().getLanguage(), entry.getKey().getCountry(), entry.getValue()));
        }
        for (DatabasePropertyDescription pd : sd.getProperties().values()) {
            gen.addImport("com.vga.platform.elsa.common.meta.domain.DatabasePropertyDescription");
            gen.addImport("com.vga.platform.elsa.common.meta.domain.DatabasePropertyType");
            gen.wrapWithBlock(null, () -> {
                gen.printLine("var propertyDescription = new DatabasePropertyDescription(\"%s\");".formatted(pd.getId()));
                gen.printLine("propertyDescription.setType(DatabasePropertyType.%s);".formatted(pd.getType().name()));
                if (pd.getClassName() != null) {
                    gen.printLine("propertyDescription.setClassName(\"%s\");".formatted(pd.getClassName()));
                }
                if(pd.isCacheFind()){
                    gen.printLine("propertyDescription.setCacheFind(true);");
                }
                if(pd.isCacheGetAll()){
                    gen.printLine("propertyDescription.setCacheGetAll(true);");
                }
                if(pd.isUseInTextSearch()){
                    gen.printLine("propertyDescription.setUseInTextSearch(true);");
                }
                for(Map.Entry<Locale, String> entry: pd.getDisplayNames().entrySet()){
                    gen.addImport("com.vga.platform.elsa.common.core.utils.LocaleUtils");
                    gen.printLine("propertyDescription.getDisplayNames().put(LocaleUtils.getLocale(\"%s\",\"%s\"), \"%s\");".formatted(entry.getKey().getLanguage(), entry.getKey().getCountry(), entry.getValue()));
                }
                gen.printLine("%s.getProperties().put(propertyDescription.getId(), propertyDescription);".formatted(descriptionName));
            });
        }
        for (DatabaseCollectionDescription cd : sd.getCollections().values()) {
            gen.addImport("com.vga.platform.elsa.common.meta.domain.DatabaseCollectionDescription");
            gen.addImport("com.vga.platform.elsa.common.meta.domain.DatabaseCollectionType");
            gen.wrapWithBlock(null, () -> {
                gen.printLine("var collectionDescription = new DatabaseCollectionDescription(\"%s\");".formatted(cd.getId()));
                gen.printLine("collectionDescription.setElementType(DatabaseCollectionType.%s);".formatted(cd.getElementType().name()));
                if (cd.getElementClassName() != null) {
                    gen.printLine("collectionDescription.setElementClassName(\"%s\");".formatted(cd.getElementClassName()));
                }
                if(cd.isUnique()){
                    gen.printLine("collectionDescription.setUnique(true);");
                }
                if(cd.isUseInTextSearch()){
                    gen.printLine("collectionDescription.setUseInTextSearch(true);");
                }
                for(Map.Entry<Locale, String> entry: cd.getDisplayNames().entrySet()){
                    gen.addImport("com.vga.platform.elsa.common.core.utils.LocaleUtils");
                    gen.printLine("collectionDescription.getDisplayNames().put(LocaleUtils.getLocale(\"%s\",\"%s\"), \"%s\");".formatted(entry.getKey().getLanguage(), entry.getKey().getCountry(), entry.getValue()));
                }
                gen.printLine("%s.getCollections().put(collectionDescription.getId(), collectionDescription);".formatted(descriptionName));
            });
        }
    }
}

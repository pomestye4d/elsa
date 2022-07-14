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

import com.vga.platform.elsa.common.meta.common.*;
import com.vga.platform.elsa.common.meta.domain.*;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;
import com.vga.platform.elsa.gradle.utils.BuildTextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class JavaDomainCachedObjectsCodeGenerator {
    public static void generate(DomainMetaRegistry registry, DomainMetaRegistry totalRegistry, File destDir, Set<File> generatedFiles){
        Set<String> cachedObjects = new HashSet<>();
        registry.getDocuments().values().forEach(doc -> {
            if (doc.isCacheResolve()) {
                collectObjects(doc.getId(), totalRegistry, cachedObjects);
            }
        });
        registry.getAssets().values().forEach(asset -> {
            if (asset.isCacheResolve()) {
                cachedObjects.add(asset.getId());
            }
        });
        registry.getDocuments().values().forEach(doc -> {
            if (isCached(doc.getId(), cachedObjects, totalRegistry) && !doc.isAbstract()) {
                BuildExceptionUtils.wrapException(() -> generateCachedEntity(doc, totalRegistry, destDir, generatedFiles));
            }
        });
        registry.getEntities().values().forEach(ett -> {
            if (isCached(ett.getId(), cachedObjects, totalRegistry) && !ett.isAbstract()) {
                BuildExceptionUtils.wrapException(() -> generateCachedEntity(ett, totalRegistry, destDir, generatedFiles));
            }
        });
        registry.getAssets().values().forEach(asset -> {
            if (isCached(asset.getId(), cachedObjects, totalRegistry) && !asset.isAbstract()) {
                BuildExceptionUtils.wrapException(() -> generateCachedAsset(asset, totalRegistry, destDir, generatedFiles));
            }
        });
    }

    private static void generateCachedEntity(EntityDescription ed, DomainMetaRegistry totalRegistry, File destDir, Set<File> generatedFiles) throws Exception {
        var gen = new JavaCodeGenerator();
        var packageName = JavaCodeGeneratorUtils.getPackage(ed.getId());
        gen.addImport("com.vga.platform.elsa.common.core.model.common.Xeption");
        gen.addImport("com.vga.platform.elsa.common.core.model.domain.CachedObject");
         gen.setPackageName(packageName);
        String cnsb = "public class _Cached%s extends %s implements CachedObject".formatted(JavaCodeGeneratorUtils.getSimpleName(ed.getId()),
                JavaCodeGeneratorUtils.getSimpleName(ed.getId()));
        var allProperties = new ArrayList<StandardPropertyDescription>();
        var allCollections = new ArrayList<StandardCollectionDescription>();
        var allMaps = new ArrayList<StandardMapDescription>();
        if (ed instanceof DocumentDescription) {
            {
                var pd = new StandardPropertyDescription();
                pd.setId("id");
                pd.setType(StandardValueType.LONG);
                pd.setNonNullable(true);
                allProperties.add(pd);
            }
            {
                var pd = new StandardPropertyDescription();
                pd.setId("versionInfo");
                pd.setType(StandardValueType.ENTITY);
                pd.setClassName("com.vga.platform.elsa.common.core.model.domain.VersionInfo");
                allProperties.add(pd);
            }
            gen.addImport("com.vga.platform.elsa.common.core.model.domain.VersionInfo");
        }
        var id = ed.getId();
        while (id != null) {
            var doc = totalRegistry.getEntities().get(id);
            if (doc == null) {
                doc = totalRegistry.getDocuments().get(id);
            }
            allProperties.addAll(doc.getProperties().values());
            allCollections.addAll(doc.getCollections().values());
            allMaps.addAll(doc.getMaps().values());
            id = doc.getExtendsId();
        }

        gen.wrapWithBlock(cnsb, () -> {
            gen.blankLine();
            gen.printLine("private boolean allowChanges = false;");
            for (StandardCollectionDescription cd : allCollections) {
                gen.blankLine();
                String className = JavaCodeGeneratorUtils.getPropertyType(cd.getElementType(), cd.getElementClassName(), false, gen);
                gen.addImport(cd.isUnique() ? "com.vga.platform.elsa.common.core.model.common.ReadOnlyHashSet" : "com.vga.platform.elsa.common.core.model.common.ReadOnlyArrayList");
                gen.printLine("private final %s<%s> %s = new %s<>();".formatted(cd.isUnique() ? "Set" : "List", className, cd.getId(), cd.isUnique() ? "ReadOnlyHashSet" : "ReadOnlyArrayList"));
            }
            for (StandardMapDescription md : allMaps) {
                gen.blankLine();
                gen.addImport("com.vga.platform.elsa.common.core.model.common.ReadOnlyHashMap");
                String keyClassName = JavaCodeGeneratorUtils.getPropertyType(md.getKeyType(), md.getKeyClassName(), false, gen);
                String valueClassName = JavaCodeGeneratorUtils.getPropertyType(md.getValueType(), md.getValueClassName(), false, gen);
                gen.printLine("private final Map<%s,%s> %s = new ReadOnlyHashMap<>();".formatted(keyClassName, valueClassName, md.getId()));
            }
            gen.blankLine();
            gen.printLine("@Override");
            gen.wrapWithBlock("public void setAllowChanges(boolean allowChanges)", () -> gen.printLine("this.allowChanges = allowChanges;"));
            for (StandardPropertyDescription pd : allProperties) {
                gen.blankLine();
                String className = JavaCodeGeneratorUtils.getPropertyType(pd.getType(), pd.getClassName(), pd.isNonNullable(), gen);
                gen.printLine("@Override");
                gen.wrapWithBlock("public void set%s(%s value)".formatted(BuildTextUtils.capitalize(pd.getId()), className), () -> {
                    gen.wrapWithBlock("if(!allowChanges)", () -> gen.printLine("throw Xeption.forDeveloper(\"changes are not allowed\");"));
                    gen.printLine("super.set%s(value);".formatted(BuildTextUtils.capitalize(pd.getId())));
                });
            }
            for (StandardCollectionDescription cd : allCollections) {
                gen.blankLine();
                gen.addImport("java.util.*");
                String className = JavaCodeGeneratorUtils.getPropertyType(cd.getElementType(), cd.getElementClassName(), false, gen);
                gen.printLine("@Override");
                gen.wrapWithBlock("public %s<%s> get%s()".formatted(cd.isUnique() ? "Set" : "List", className, BuildTextUtils.capitalize(cd.getId())), () -> gen.printLine("return %s;".formatted(cd.getId())));
            }
            for (StandardMapDescription md : allMaps) {
                gen.blankLine();
                gen.addImport("java.util.*");
                String keyClassName = JavaCodeGeneratorUtils.getPropertyType(md.getKeyType(), md.getKeyClassName(), false, gen);
                String valueClassName = JavaCodeGeneratorUtils.getPropertyType(md.getValueType(), md.getValueClassName(), false, gen);
                gen.wrapWithBlock("public Map<%s,%s> get%s()".formatted(keyClassName, valueClassName, BuildTextUtils.capitalize(md.getId())), () -> gen.printLine("return %s;".formatted(md.getId())));
            }
            gen.blankLine();
            gen.printLine("@Override");
            gen.wrapWithBlock("public void setValue(String propertyName, Object value)", () -> {
                gen.wrapWithBlock("if(!allowChanges)", () -> gen.printLine("throw Xeption.forDeveloper(\"changes are not allowed\");"));
                gen.printLine("super.setValue(propertyName, value);");
            });
            if (!allCollections.isEmpty()) {
                gen.blankLine();
                gen.printLine("@Override");
                gen.wrapWithBlock("public Collection<?> getCollection(String collectionName)", () -> {
                    for (StandardCollectionDescription cd : ed.getCollections().values()) {
                        gen.blankLine();
                        gen.wrapWithBlock("if(\"%s\".equals(collectionName))".formatted(cd.getId()), () -> gen.printLine("return %s;".formatted(cd.getId())));
                    }
                    gen.blankLine();
                    gen.printLine("return super.getCollection(collectionName);");
                });
            }
            if (!allMaps.isEmpty()) {
                gen.blankLine();
                gen.printLine("@Override");
                gen.wrapWithBlock("public Map<?,?> getMap(String mapName)", () -> {
                    for (StandardMapDescription md : ed.getMaps().values()) {
                        gen.blankLine();
                        gen.wrapWithBlock("if(\"%s\".equals(mapName))".formatted(md.getId()), () -> gen.printLine("return %s;".formatted(md.getId())));
                    }
                    gen.blankLine();
                    gen.printLine("return super.getMap(mapName);");
                });
            }
        });

        var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), "%s.java"
                .formatted(ed.getId().substring(0, ed.getId().lastIndexOf(".")) + "._Cached" +
                        ed.getId().substring(ed.getId().lastIndexOf(".") + 1)), destDir);
        generatedFiles.add(file);
    }

    private static void generateCachedAsset(AssetDescription ed, DomainMetaRegistry totalRegistry, File destDir, Set<File> generatedFiles) throws Exception {
        var gen = new JavaCodeGenerator();
        var packageName = JavaCodeGeneratorUtils.getPackage(ed.getId());
        gen.addImport("com.vga.platform.elsa.common.core.model.common.Xeption");
        gen.addImport("com.vga.platform.elsa.common.core.model.domain.CachedObject");
        gen.addImport("com.vga.platform.elsa.common.core.model.domain.VersionInfo");
        gen.setPackageName(packageName);
        String cnsb = "public class _Cached%s extends %s implements CachedObject".formatted(JavaCodeGeneratorUtils.getSimpleName(ed.getId()),
                JavaCodeGeneratorUtils.getSimpleName(ed.getId()));
        var allProperties = new ArrayList<DatabasePropertyDescription>();
        var allCollections = new ArrayList<DatabaseCollectionDescription>();

        {
            var pd = new DatabasePropertyDescription();
            pd.setId("id");
            pd.setType(DatabasePropertyType.LONG);
            allProperties.add(pd);
        }

        var id = ed.getId();
        while (id != null) {
            var doc = totalRegistry.getAssets().get(id);
            allProperties.addAll(doc.getProperties().values());
            allCollections.addAll(doc.getCollections().values());
            id = doc.getExtendsId();
        }

        gen.wrapWithBlock(cnsb, () -> {
            gen.blankLine();
            gen.printLine("private boolean allowChanges = false;");
            for (DatabaseCollectionDescription cd : allCollections) {
                gen.blankLine();
                String className = JavaCodeGeneratorUtils.getPropertyType(JavaDomainEntitiesCodeGenerator.getStandardValueType(cd.getElementType()), cd.getElementClassName(), false, gen);
                gen.addImport(cd.isUnique() ? "com.vga.platform.elsa.common.core.model.common.ReadOnlyHashSet" : "com.vga.platform.elsa.common.core.model.common.ReadOnlyArrayList");
                gen.printLine("private final %s<%s> %s = new %s<>();".formatted(cd.isUnique() ? "Set" : "List", className, cd.getId(), cd.isUnique() ? "ReadOnlyHashSet" : "ReadOnlyArrayList"));
            }
            gen.blankLine();
            gen.wrapWithBlock("public void setAllowChanges(boolean allowChanges)", () -> gen.printLine("this.allowChanges = allowChanges;"));
            gen.printLine("@Override");
            gen.wrapWithBlock("public void setVersionInfo(VersionInfo value)", () -> {
                gen.wrapWithBlock("if(!allowChanges)", () -> gen.printLine("throw Xeption.forDeveloper(\"changes are not allowed\");"));
                gen.printLine("super.setVersionInfo(value);");
            });
            for (DatabasePropertyDescription pd : allProperties) {
                gen.blankLine();
                String className = JavaCodeGeneratorUtils.getPropertyType(JavaDomainEntitiesCodeGenerator.getStandardValueType(pd.getType()),
                        pd.getClassName(), "id".equals(pd.getId()) || JavaDomainEntitiesCodeGenerator.isNonNullable(pd.getType()), gen);
                gen.printLine("@Override");
                gen.wrapWithBlock("public void set%s(%s value)".formatted(BuildTextUtils.capitalize(pd.getId()), className), () -> {
                    gen.wrapWithBlock("if(!allowChanges)", () -> gen.printLine("throw Xeption.forDeveloper(\"changes are not allowed\");"));
                    gen.printLine("super.set%s(value);".formatted(BuildTextUtils.capitalize(pd.getId())));
                });
            }
            for (DatabaseCollectionDescription cd : allCollections) {
                gen.blankLine();
                gen.addImport("java.util.*");
                String className = JavaCodeGeneratorUtils.getPropertyType(JavaDomainEntitiesCodeGenerator.getStandardValueType(cd.getElementType()),
                        cd.getElementClassName(), false, gen);
                gen.printLine("@Override");
                gen.wrapWithBlock("public %s<%s> get%s()".formatted(cd.isUnique() ? "Set" : "List", className, BuildTextUtils.capitalize(cd.getId())), () -> gen.printLine("return %s;".formatted(cd.getId())));
            }
            gen.blankLine();
            gen.printLine("@Override");
            gen.wrapWithBlock("public void setValue(String propertyName, Object value)", () -> {
                gen.wrapWithBlock("if(!allowChanges)", () -> gen.printLine("throw Xeption.forDeveloper(\"changes are not allowed\");"));
                gen.printLine("super.setValue(propertyName, value);");
            });
            if (!allCollections.isEmpty()) {
                gen.blankLine();
                gen.printLine("@Override");
                gen.wrapWithBlock("public Collection<?> getCollection(String collectionName)", () -> {
                    for (DatabaseCollectionDescription cd : allCollections) {
                        gen.blankLine();
                        gen.wrapWithBlock("if(\"%s\".equals(collectionName))".formatted(cd.getId()), () -> gen.printLine("return %s;".formatted(cd.getId())));
                    }
                    gen.blankLine();
                    gen.printLine("return super.getCollection(collectionName);");
                });
            }
        });

        var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), "%s.java"
                .formatted(ed.getId().substring(0, ed.getId().lastIndexOf(".")) + "._Cached" +
                        ed.getId().substring(ed.getId().lastIndexOf(".") + 1)), destDir);
        generatedFiles.add(file);
    }

    private static boolean isCached(String id, Set<String> cachedObjects, DomainMetaRegistry registry) {
        var iid = id;
        while (iid != null) {
            var doc = registry.getDocuments().get(iid);
            if (doc == null) {
                break;
            }
            if (cachedObjects.contains(iid)) {
                return true;
            }
            iid = doc.getExtendsId();
        }
        iid = id;
        while (iid != null) {
            var ett = registry.getEntities().get(iid);
            if (ett == null) {
                break;
            }
            if (cachedObjects.contains(iid)) {
                return true;
            }
            iid = ett.getExtendsId();
        }
        iid = id;
        while (iid != null) {
            var asset = registry.getAssets().get(iid);
            if (asset == null) {
                break;
            }
            if (cachedObjects.contains(iid)) {
                return true;
            }
            iid = asset.getExtendsId();
        }
        return false;
    }

    private static void collectObjects(String docId, DomainMetaRegistry totalRegistry, Set<String> cachedObjects) {
        if (!cachedObjects.add(docId)) {
            return;
        }
        EntityDescription ett = totalRegistry.getDocuments().get(docId);
        if (ett == null) {
            ett = totalRegistry.getEntities().get(docId);
        }
        if (ett != null) {
            ett.getProperties().values().forEach(prop -> {
                if (prop.getType() == StandardValueType.ENTITY) {
                    collectObjects(prop.getClassName(), totalRegistry, cachedObjects);
                }
            });
            ett.getCollections().values().forEach(prop -> {
                if (prop.getElementType() == StandardValueType.ENTITY) {
                    collectObjects(prop.getElementClassName(), totalRegistry, cachedObjects);
                }
            });
            ett.getMaps().values().forEach(prop -> {
                if (prop.getKeyType() == StandardValueType.ENTITY) {
                    collectObjects(prop.getKeyClassName(), totalRegistry, cachedObjects);
                }
                if (prop.getValueType() == StandardValueType.ENTITY) {
                    collectObjects(prop.getValueClassName(), totalRegistry, cachedObjects);
                }
            });
        }

    }


}

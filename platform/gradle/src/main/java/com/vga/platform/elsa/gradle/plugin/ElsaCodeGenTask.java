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

package com.vga.platform.elsa.gradle.plugin;

import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistry;
import com.vga.platform.elsa.gradle.codegen.common.BaseCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.common.CodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.GeneratorType;
import com.vga.platform.elsa.gradle.codegen.custom.JavaCustomCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.domain.JavaDomainCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.l10n.JavaL10nCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.l10n.WebL10nCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.remoting.JavaRemotingCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.remoting.WebRemotingCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.ui.JavaUiCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.ui.JavaUiTemplateCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.ui.WebUiCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.ui.WebUiTemplateCodeGenerator;
import com.vga.platform.elsa.gradle.parser.domain.DomainMetaRegistryParser;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ElsaCodeGenTask extends DefaultTask {
    public ElsaCodeGenTask() {
        setGroup("elsa");
    }

    @TaskAction
    public void generate() {
        var records = new ArrayList<BaseCodeGenRecord>();
        var jext = getProject().getExtensions().findByType(ElsaJavaExtension.class);
        var javaProject = false;
        if (jext != null) {
            records.addAll(jext.getCodegenRecords());
            javaProject = true;
        } else {
            var wext = getProject().getExtensions().getByType(ElsaWebExtension.class);
            records.addAll(wext.getCodegenRecords());
        }
        Set<File> generatedFiles = new HashSet<>();
        var generators = new LinkedHashMap<GeneratorType, Map<File, List<BaseCodeGenRecord>>>();
        records.forEach(it -> {
            var dests = generators
                    .computeIfAbsent(it.getGeneratorType(), t -> new LinkedHashMap<>());
            dests.computeIfAbsent(it.getDestinationDir(), t -> new ArrayList<>()).add(it);
        });
        Map<Object, Object> context = new HashMap<>();
        context.put("project", getProject());
        if (javaProject) {
            var domainMetaRegistry = new DomainMetaRegistry();
            context.put("domain-meta-registry", domainMetaRegistry);
            var parser = new DomainMetaRegistryParser();
            var projects = new ArrayList<Project>();
            getProject().getConfigurations().getAt("implementation").getAllDependencies().withType(ProjectDependency.class).forEach((proj) -> projects.add(proj.getDependencyProject()));
            projects.add(getProject());
            projects.forEach(project -> {
                try {
                    var extension = project.getExtensions().findByType(ElsaJavaExtension.class);
                    if(extension != null) {
                        var records2 = (List<?>) extension.getClass().getMethod("getCodegenRecords").invoke(extension);
                        for (Object record : records2) {
                            var type = (Enum<?>) record.getClass().getMethod("getGeneratorType").invoke(record);
                            if ("JAVA_DOMAIN".equals(type.name())) {
                                @SuppressWarnings("unchecked") var sources = (List<File>) record.getClass().getMethod("getSources").invoke(record);
                                parser.updateMetaRegistry(domainMetaRegistry, sources);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        var gens = new ArrayList<>(generators.entrySet());
        gens.sort(Comparator.comparing(it -> switch (it.getKey()){
            case JAVA_UI -> 1;
            case JAVA_REMOTING -> 2;
            case WEB_UI -> 3;
            case WEB_REMOTING -> 4;
            default -> 0;
        }));
        gens.forEach(entry -> {
            @SuppressWarnings("unchecked") var codeGen = (CodeGenerator<BaseCodeGenRecord>) switch (entry.getKey()) {
                case JAVA_DOMAIN -> new JavaDomainCodeGenerator();
                case JAVA_CUSTOM -> new JavaCustomCodeGenerator();
                case JAVA_REMOTING -> new JavaRemotingCodeGenerator();
                case JAVA_L10N -> new JavaL10nCodeGenerator();
                case JAVA_UI_TEMPLATE -> new JavaUiTemplateCodeGenerator();
                case JAVA_UI -> new JavaUiCodeGenerator();
                case WEB_REMOTING -> new WebRemotingCodeGenerator();
                case WEB_UI_TEMPLATE -> new WebUiTemplateCodeGenerator();
                case WEB_UI -> new WebUiCodeGenerator();
                case WEB_L10N -> new WebL10nCodeGenerator();
            };
            entry.getValue().forEach((key1, value1) -> BuildExceptionUtils.wrapException(() -> codeGen.generate(value1, key1, generatedFiles, context)));
        });
        Set<File> destDirs = new HashSet<>();
        records.forEach(it -> destDirs.add(it.getDestinationDir()));
        cleanupDirs(destDirs, generatedFiles);
    }

    private boolean cleanupDirs(Collection<File> destDirs, Set<File> generatedFiles) {
        var result = new AtomicBoolean(false);
        destDirs.forEach(fileOrDir -> {
            if (!fileOrDir.exists()) {
                return;
            }
            if (fileOrDir.isFile()) {
                if (!generatedFiles.contains(fileOrDir)) {
                    assert fileOrDir.delete();
                } else {
                    result.set(true);
                }
                return;
            }
            var subRes = cleanupDirs(Arrays.asList(Objects.requireNonNull(fileOrDir.listFiles())), generatedFiles);
            if (subRes) {
                result.set(true);
                return;
            }
            assert fileOrDir.delete();
        });
        return result.get();
    }
}

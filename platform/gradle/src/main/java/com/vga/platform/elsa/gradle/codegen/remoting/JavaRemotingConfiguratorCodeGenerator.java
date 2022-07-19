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

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.EnumDescription;
import com.vga.platform.elsa.common.meta.remoting.RemotingDescription;
import com.vga.platform.elsa.common.meta.remoting.RemotingGroupDescription;
import com.vga.platform.elsa.common.meta.remoting.RemotingMetaRegistry;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGenerator;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;

import java.io.File;
import java.util.Set;

public class JavaRemotingConfiguratorCodeGenerator {
    public static void generate(RemotingMetaRegistry registry, String configurator, File destDir, Set<File> generatedFiles) throws Exception {
        var gen = new JavaCodeGenerator();
        gen.setPackageName(JavaCodeGeneratorUtils.getPackage(configurator));
        gen.addImport("com.vga.platform.elsa.common.meta.remoting.RemotingMetaRegistryConfigurator");
        gen.addImport("com.vga.platform.elsa.common.meta.remoting.RemotingMetaRegistry");
        gen.wrapWithBlock("public class %s implements RemotingMetaRegistryConfigurator".formatted(JavaCodeGeneratorUtils.getSimpleName(configurator)), () -> {
            gen.blankLine();
            gen.printLine("@Override");
            gen.wrapWithBlock("public void updateMetaRegistry(RemotingMetaRegistry registry)", () -> {
                for (EnumDescription ed : registry.getEnums().values()) {
                    JavaCodeGeneratorUtils.generateJavaEnumConfiguratorCode(ed, gen);
                }
                for (EntityDescription ed : registry.getEntities().values()) {
                    JavaCodeGeneratorUtils.generateJavaEntityConfiguratorCode(ed, gen);
                }
                for(RemotingDescription rd: registry.getRemotings().values()){
                    gen.addImport("com.vga.platform.elsa.common.meta.remoting.RemotingDescription");
                    gen.wrapWithBlock(null, () -> {
                        gen.printLine("var remotingDescription = new RemotingDescription(\"%s\");".formatted(rd.getId()));
                        gen.printLine("registry.getRemotings().put(remotingDescription.getId(), remotingDescription);");
                        for (RemotingGroupDescription gd : rd.getGroups().values()) {
                            gen.addImport("com.vga.platform.elsa.common.meta.remoting.RemotingGroupDescription");
                            gen.wrapWithBlock(null, () -> {
                                gen.printLine("var groupDescription = new RemotingGroupDescription(\"%s\");".formatted(gd.getId()));
                                gen.printLine("remotingDescription.getGroups().put(groupDescription.getId(), groupDescription);");
                                gd.getServerCalls().values().forEach(sc -> {
                                    gen.addImport("com.vga.platform.elsa.common.meta.remoting.RemotingServerCallDescription");
                                    BuildExceptionUtils.wrapException(() -> gen.wrapWithBlock(null, () -> {
                                        gen.printLine("var serverCallDescription = new RemotingServerCallDescription(\"%s\");".formatted(gd.getId()));
                                        gen.printLine("serverCallDescription.setValidatable(%s);".formatted(sc.isValidatable()));
                                        if(sc.getRequestClassName() != null) {
                                            gen.printLine("serverCallDescription.setRequestClassName(\"%s\");".formatted(sc.getRequestClassName()));
                                        }
                                        if(sc.getResponseClassName() != null) {
                                            gen.printLine("serverCallDescription.setResponseClassName(\"%s\");".formatted(sc.getResponseClassName()));
                                        }
                                        gen.printLine("groupDescription.getServerCalls().put(\"%s\", serverCallDescription);".formatted(sc.getId()));
                                    }));

                                });
                                gd.getSubscriptions().values().forEach(sub -> {
                                    gen.addImport("com.vga.platform.elsa.common.meta.remoting.RemotingSubscriptionDescription");
                                    BuildExceptionUtils.wrapException(() -> gen.wrapWithBlock(null, () -> {
                                        gen.printLine("var subscriptionDescription = new RemotingSubscriptionDescription(\"%s\");".formatted(sub.getId()));
                                        gen.printLine("subscriptionDescription.setParameterClassName(\"%s\");".formatted(sub.getParameterClassName()));
                                        gen.printLine("subscriptionDescription.setEventClassName(\"%s\");".formatted(sub.getEventClassName()));
                                        gen.printLine("groupDescription.getSubscriptions().put(\"%s\", subscriptionDescription);".formatted(sub.getId()));
                                    }));
                                });
                            });
                        }
                    });
                }


            });

        });
        var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), configurator + ".java", destDir);
        generatedFiles.add(file);
    }
}

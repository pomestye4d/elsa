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
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGenerator;
import com.vga.platform.elsa.gradle.codegen.common.JavaCodeGeneratorUtils;

import java.io.File;
import java.util.Set;

public class JavaRemotingRestControllerCodeGenerator {
    public static void generate(RemotingMetaRegistry registry, File destDir, String controllerClassName, Set<File> generatedFiles) throws Exception {
        String remotingId = registry.getRemotings().values().iterator().next().getId();
        var gen = new JavaCodeGenerator();
        gen.setPackageName(JavaCodeGeneratorUtils.getPackage(controllerClassName));
        gen.addImport("com.vga.platform.elsa.core.remoting.BaseRemotingController");
        gen.addImport("org.springframework.web.bind.annotation.RestController");
        gen.addImport("org.springframework.web.bind.annotation.RequestMapping");
        gen.wrapWithBlock("""
                @RestController
                @RequestMapping("/remoting/%s")
                public class %s extends BaseRemotingController""".formatted(remotingId, JavaCodeGeneratorUtils.getSimpleName(controllerClassName)), () -> gen.wrapWithBlock("public %s()".formatted(JavaCodeGeneratorUtils.getSimpleName(controllerClassName)), ()-> gen.printLine("super(\"%s\");".formatted(remotingId))));
        var file = JavaCodeGeneratorUtils.saveIfDiffers(gen.toString(), controllerClassName + ".java", destDir);
        generatedFiles.add(file);
    }
}

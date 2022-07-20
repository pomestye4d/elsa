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

import com.vga.platform.elsa.gradle.codegen.common.BaseCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.l10n.WebL10nCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.remoting.WebRemotingCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.ui.WebUiCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.ui.WebUiTemplateCodeGenRecord;

import java.io.File;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
public class ElsaWebCodeGenExtension {

    private final List<BaseCodeGenRecord> codegenRecords;

    private final Map<String, File> imports;

    private final File projectDir;

    public ElsaWebCodeGenExtension(List<BaseCodeGenRecord> codegenRecords, File projectDir, Map<String,File> imports) {
        this.codegenRecords = codegenRecords;
        this.projectDir = projectDir;
        this.imports = imports;
    }

    public void remoting(String destDir, String facade, List<String> sourcesFileNames){
        var record = new WebRemotingCodeGenRecord();
        record.setRemotingFacade(facade);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

    public void uiTemplate(String destDir, String tsFileName, List<String> sourcesFileNames){
        var record = new WebUiTemplateCodeGenRecord();
        record.setTsFileName(tsFileName);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

    public void ui(String destDir, String tsFileName, List<String> sourcesFileNames){
        var record = new WebUiCodeGenRecord();
        record.setTsFileName(tsFileName);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

    public void declareImport(String path, String... widgets){
        for(String widget: widgets){
            imports.put(widget, new File(projectDir, path));
        }
    }

    public void l10n(String destDir, String l10nFileName, String tsClassName, List<String> sourcesFileNames){
        var record = new WebL10nCodeGenRecord();
        record.setTsClassName(tsClassName);
        record.setL10nFileName(l10nFileName);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }
}

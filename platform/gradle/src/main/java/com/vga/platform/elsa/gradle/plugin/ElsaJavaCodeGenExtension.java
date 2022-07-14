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
import com.vga.platform.elsa.gradle.codegen.custom.JavaCustomCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.domain.JavaDomainCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.l10n.JavaL10nCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.remoting.JavaRemotingCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.ui.JavaUiCodeGenRecord;
import com.vga.platform.elsa.gradle.codegen.ui.JavaUiTemplateCodeGenRecord;

import java.io.File;
import java.util.List;

public class ElsaJavaCodeGenExtension {

    private String artefactId;

    public String getArtefactId() {
        return artefactId;
    }

    public void setArtefactId(String artefactId) {
        this.artefactId = artefactId;
    }

    private final List<BaseCodeGenRecord> codegenRecords;

    private final File projectDir;

    public ElsaJavaCodeGenExtension(List<BaseCodeGenRecord> codegenRecords, File projectDir){
        this.codegenRecords = codegenRecords;
        this.projectDir = projectDir;
    }

    public void domain(String destDir, String configurator, List<String> sourcesFileNames){
        var record = new JavaDomainCodeGenRecord();
        record.setRegistryConfigurator(configurator);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

    public void custom(String destDir, String configurator, List<String> sourcesFileNames){
        var record = new JavaCustomCodeGenRecord();
        record.setRegistryConfigurator(configurator);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

    public void remoting(String destDir, String configurator, List<String> sourcesFileNames){
        var record = new JavaRemotingCodeGenRecord();
        record.setRegistryConfigurator(configurator);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

    public void l10n( String destDir, String configurator, String factory, List<String> sourcesFileNames){
        var record = new JavaL10nCodeGenRecord();
        record.setRegistryConfigurator(configurator);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setFactory(factory);
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

    public void uiTemplate( String destDir, String xsdFileName, String targetNameSpace, List<String> sourcesFileNames){
        var record = new JavaUiTemplateCodeGenRecord();
        record.setDestinationDir(new File(projectDir, destDir));
        record.setTargetNameSpace(targetNameSpace);
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setXsdFileName(xsdFileName);
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

    public void ui( String destDir, List<String> sourcesFileNames){
        var record = new JavaUiCodeGenRecord();
        record.setDestinationDir(new File(projectDir, destDir));
        sourcesFileNames.forEach(it -> record.getSources().add(new File(projectDir, it)));
        record.setDestinationDir(new File(projectDir, destDir));
        codegenRecords.add(record);
    }

}

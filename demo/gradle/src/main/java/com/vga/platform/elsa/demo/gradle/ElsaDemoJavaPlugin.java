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

package com.vga.platform.elsa.demo.gradle;

import com.vga.platform.elsa.gradle.plugin.ElsaJavaExtension;
import com.vga.platform.elsa.gradle.plugin.ElsaWebExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ElsaDemoJavaPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        var ext = target.getExtensions().findByType( ElsaJavaExtension.class);
        var tableHanlder = new ElsaTableTemplateHandler();
        var listHandler = new ElsaListTemplateHandler();
        if(ext != null){
            ext.setProjectDir(target.getProjectDir());
            ext.getTemplatesHandlers().put(tableHanlder.getTagName(), tableHanlder);
            ext.getTemplatesHandlers().put(listHandler.getTagName(), listHandler);
            return;
        }
        var ext2 = target.getExtensions().getByType(ElsaWebExtension.class);
        ext2.setProjectDir(target.getProjectDir());
        ext2.getTemplatesHandlers().put(tableHanlder.getTagName(), tableHanlder);
        ext2.getTemplatesHandlers().put(listHandler.getTagName(), listHandler);
    }
}

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

package com.vga.platform.elsa.gradle.parser.domain;

import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.domain.*;
import com.vga.platform.elsa.gradle.parser.common.CommonParserUtils;
import com.vga.platform.elsa.gradle.parser.common.MetaDataParsingResult;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DomainMetaRegistryParser {

    public void updateMetaRegistry(DomainMetaRegistry registry, List<File> sources) {
        sources.forEach(it -> BuildExceptionUtils.wrapException(() -> {
            MetaDataParsingResult pr = CommonParserUtils.parse(it);
            XmlNode node = pr.node();
            node.getChildren("enum").forEach(child ->
                    CommonParserUtils.updateEnum(registry.getEnums(), child, pr.localizations()));
            node.getChildren("entity").forEach(child ->
                    CommonParserUtils.updateEntity(registry.getEntities(), child));
            node.getChildren("document").forEach(child -> {
                        var dd = registry.getDocuments().computeIfAbsent(CommonParserUtils.getIdAttribute(child),
                                DocumentDescription::new);
                        dd.setCacheResolve("true".equals(child.getAttribute("cache-resolve")));
                        dd.setCacheCaption("true".equals(child.getAttribute("cache-caption")));
                        var ce = child.getChildren("caption-expression");
                        dd.setCaptionExpression(ce.isEmpty() ? null : ce.get(0).getValue());
                        var lce = child.getChildren("localizable-caption-expression");
                        dd.setLocalizableCaptionExpression(lce.isEmpty() ? null : lce.get(0).getValue());
                        CommonParserUtils.fillEntityDescription(child, dd);
                    }
            );
            node.getChildren("searchable-projection").forEach(child -> {
                var proj = registry.getSearchableProjections().computeIfAbsent(CommonParserUtils.getIdAttribute(child),
                        SearchableProjectionDescription::new);
                proj.setDocument(child.getAttribute("document"));
                fillSearchable(child, proj, pr.localizations());
            });
            node.getChildren("asset").forEach(child -> {
                var proj = registry.getAssets().computeIfAbsent(CommonParserUtils.getIdAttribute(child),
                        AssetDescription::new);
                proj.setCacheCaption("true".equals(child.getAttribute("cache-caption")));
                proj.setCacheResolve("true".equals(child.getAttribute("cache-resolve")));
                proj.setAbstract("true".equals(child.getAttribute("abstract")));
                proj.setExtendsId(child.getAttribute("extends"));
                var ce = child.getChildren("caption-expression");
                proj.setCaptionExpression(ce.isEmpty() ? null : ce.get(0).getValue());
                var lce = child.getChildren("localizable-caption-expression");
                proj.setLocalizableCaptionExpression(lce.isEmpty() ? null : lce.get(0).getValue());
                fillSearchable(child, proj, pr.localizations());
            });
        }));
    }

    private <T extends BaseSearchableDescription> void fillSearchable(XmlNode elm, T description, Map<String, Map<Locale, String>> localizations) {
        CommonParserUtils.updateLocalizationsOfChild(description, localizations, null);
        CommonParserUtils.updateParameters(elm, description);
        elm.getChildren("property").forEach(child -> {
            var pd = description.getProperties().computeIfAbsent(CommonParserUtils.getIdAttribute(child), DatabasePropertyDescription::new);
            CommonParserUtils.updateParameters(child, pd);
            CommonParserUtils.updateLocalizationsOfChild(pd, localizations, description.getId());
            pd.setUseInTextSearch("true".equals(child.getAttribute("use-in-text-search")));
            pd.setCacheFind("true".equals(child.getAttribute("cache-find")));
            pd.setCacheGetAll("true".equals(child.getAttribute("cache-get-all")));
            pd.setClassName(child.getAttribute("class-name"));
            pd.setType(DatabasePropertyType.valueOf(child.getAttribute("type")));
        });
        elm.getChildren("collection").forEach(child -> {
            var cd = description.getCollections().computeIfAbsent(CommonParserUtils.getIdAttribute(child), DatabaseCollectionDescription::new);
            CommonParserUtils.updateParameters(child, cd);
            CommonParserUtils.updateLocalizationsOfChild(cd, localizations, description.getId());
            cd.setUseInTextSearch("true".equals(child.getAttribute("use-in-text-search")));
            cd.setUnique("true".equals(child.getAttribute("unique")));
            cd.setElementClassName(child.getAttribute("element-class-name"));
            cd.setElementType(DatabaseCollectionType.valueOf(child.getAttribute("element-type")));
        });
    }
}

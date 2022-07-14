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

package com.vga.platform.elsa.gradle.parser.ui;

import com.vga.platform.elsa.common.meta.common.EntityDescription;
import com.vga.platform.elsa.common.meta.common.StandardPropertyDescription;
import com.vga.platform.elsa.common.meta.common.StandardValueType;
import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.ui.*;
import com.vga.platform.elsa.gradle.parser.common.CommonParserUtils;
import com.vga.platform.elsa.gradle.parser.common.MetaDataParsingResult;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class UiTemplateMetaRegistryParser {

    public void updateMetaRegistry(UiTemplateMetaRegistry registry, List<File> sources) {
        sources.forEach(it -> BuildExceptionUtils.wrapException(() -> {
            MetaDataParsingResult pr = CommonParserUtils.parse(it);
            XmlNode node = pr.node();
            node.getChildren("enum").forEach(child ->
                    CommonParserUtils.updateEnum(registry.getEnums(), child, pr.localizations()));
            node.getChildren("widget").forEach(widgetElm -> {
                var widgetDescr = registry.getWidgets().computeIfAbsent(CommonParserUtils.getIdAttribute(widgetElm), UiWidgetDescription::new);
                widgetDescr.setTsClassName(widgetElm.getAttribute("ts-class-name"));
                var propsElm = widgetElm.getFirstChild("properties");
                if (propsElm != null) {
                    updateAttributes(widgetDescr.getProperties().getAttributes(), propsElm);
                }
                {
                    var modelElm = widgetElm.getFirstChild("model");
                    var model = widgetDescr.getModel();
                    model.setType(UiModelPropertyType.valueOf(modelElm.getAttribute("type")));
                    model.setClassName(modelElm.getAttribute("class-name"));
                    modelElm.getChildren("property").forEach(propElm -> {
                        var prop = model.getProperties().computeIfAbsent(CommonParserUtils.getIdAttribute(propElm), UiWidgetModelPropertyDescription::new);
                        prop.setClassName(propElm.getAttribute("class-name"));
                        prop.setType(UiModelPropertyType.valueOf(propElm.getAttribute("type")));
                        prop.setNonNullable("true".equals(propElm.getAttribute("non-nullable")));
                    });
                    if(model.getType() == UiModelPropertyType.ENTITY){
                            var ed = new EntityDescription();
                            ed.setId(model.getClassName());
                            for(UiWidgetModelPropertyDescription propDescr: model.getProperties().values()){
                                var prop = new StandardPropertyDescription();
                                prop.setId(propDescr.getId());
                                prop.setType(toStandardType(propDescr.getType()));
                                prop.setClassName(propDescr.getClassName());
                                prop.setNonNullable(propDescr.isNonNullable());
                                ed.getProperties().put(prop.getId(), prop);
                            }
                            registry.getEntities().put(ed.getId(), ed);
                    }
                }
                {
                    var configElm = widgetElm.getFirstChild("configuration");
                    var config = widgetDescr.getConfiguration();
                    config.setType(UiConfigurationPropertyType.valueOf(configElm.getAttribute("type")));
                    config.setClassName(configElm.getAttribute("class-name"));
                    configElm.getChildren("property").forEach(propElm -> {
                        var prop = config.getProperties().computeIfAbsent(CommonParserUtils.getIdAttribute(propElm), UiWidgetConfigurationPropertyDescription::new);
                        prop.setClassName(propElm.getAttribute("class-name"));
                        prop.setType(UiConfigurationPropertyType.valueOf(propElm.getAttribute("type")));
                        prop.setNonNullable("true".equals(propElm.getAttribute("non-nullable")));
                    });
                    if(config.getType() == UiConfigurationPropertyType.ENTITY){
                        var ed = new EntityDescription();
                        ed.setId(config.getClassName());
                        for(UiWidgetConfigurationPropertyDescription propDescr: config.getProperties().values()){
                            var prop = new StandardPropertyDescription();
                            prop.setId(propDescr.getId());
                            prop.setType(toStandardType(propDescr.getType()));
                            prop.setClassName(propDescr.getClassName());
                            prop.setNonNullable(propDescr.isNonNullable());
                            ed.getProperties().put(prop.getId(), prop);
                        }
                        registry.getEntities().put(ed.getId(), ed);
                    }
                }
                {
                    var validationElm = widgetElm.getFirstChild("validation");
                    var validation = widgetDescr.getValidation();
                    validation.setType(UiValidationPropertyType.valueOf(validationElm.getAttribute("type")));
                    validation.setClassName(validationElm.getAttribute("class-name"));
                    validationElm.getChildren("property").forEach(propElm -> {
                        var prop = validation.getProperties().computeIfAbsent(CommonParserUtils.getIdAttribute(propElm), UiWidgetValidationPropertyDescription::new);
                        prop.setClassName(propElm.getAttribute("class-name"));
                        prop.setType(UiValidationPropertyType.valueOf(propElm.getAttribute("type")));
                    });
                }
            });
            node.getChildren("view-template").forEach(templateElm -> {
                var viewTemplateDescr = registry.getViewTemplates().computeIfAbsent(CommonParserUtils.getIdAttribute(templateElm), UiViewTemplateDescription::new);
                viewTemplateDescr.setTsClassName(templateElm.getAttribute("ts-class-name"));
                var propsElm = templateElm.getFirstChild("properties");
                if (propsElm != null) {
                    updateAttributes(viewTemplateDescr.getProperties().getAttributes(), propsElm);
                }
                var contentElm = templateElm.getFirstChild("content");
                contentElm.getChildren("property").forEach(propElm ->{
                    var tagName = propElm.getAttribute("tag-name");
                    var prop = viewTemplateDescr.getContent().getProperties().computeIfAbsent(tagName, UiViewTemplatePropertyDescription::new );
                    updateProperty(prop, propElm);
                });
                contentElm.getChildren("collection").forEach(collElm ->{
                    var tagName = collElm.getAttribute("element-tag-name");
                    var coll = viewTemplateDescr.getContent().getCollections().computeIfAbsent(tagName, UiViewTemplateCollectionDescription::new );
                    updateCollection(coll, collElm);
                });

            });
            node.getChildren("group").forEach(groupElm -> {
                var groupDescr = registry.getGroups().computeIfAbsent(CommonParserUtils.getIdAttribute(groupElm), UiTemplateGroupDescription::new);
                groupElm.getChildren("element-ref").forEach(wElm -> groupDescr.getElements().add(wElm.getAttribute("ref")));
            });
        }));
    }

    private void updateCollection(UiViewTemplateCollectionDescription coll, XmlNode collElm) {
        coll.setElementClassName(collElm.getAttribute("element-class-name"));
        coll.setElementType(UiModelPropertyType.valueOf(collElm.getAttribute("element-type")));
        coll.setWrapperTagName(collElm.getAttribute("wrapper-tag-name"));
        updateAttributes(coll.getAttributes(), collElm);
        collElm.getChildren("property").forEach(propElm2 ->{
            var tagName = propElm2.getAttribute("tag-name");
            var prop2 = coll.getProperties().computeIfAbsent(tagName, UiViewTemplatePropertyDescription::new );
            updateProperty(prop2, propElm2);
        });
        collElm.getChildren("collection").forEach(collElm2 ->{
            var tagName = collElm2.getAttribute("element-tag-name");
            var coll2 = coll.getCollections().computeIfAbsent(tagName, UiViewTemplateCollectionDescription::new );
            updateCollection(coll2, collElm2);
        });
        collElm.getChildren("group-ref").forEach(groupElm -> coll.getGroups().add(new UiGroupDescription(groupElm.getAttribute("ref"))));
    }

    private void updateProperty(UiViewTemplatePropertyDescription prop, XmlNode propElm) {
        prop.setClassName(propElm.getAttribute("class-name"));
        prop.setNonNullable("true".equals(propElm.getAttribute("non-nullable")));
        prop.setType(UiModelPropertyType.valueOf(propElm.getAttribute("type")));
        updateAttributes(prop.getAttributes(), propElm);
        propElm.getChildren("property").forEach(propElm2 ->{
            var tagName = propElm2.getAttribute("tag-name");
            var prop2 = prop.getProperties().computeIfAbsent(tagName, UiViewTemplatePropertyDescription::new );
            updateProperty(prop2, propElm2);
        });
        propElm.getChildren("collection").forEach(collElm2 ->{
            var tagName = collElm2.getAttribute("element-tag-name");
            var coll2 = prop.getCollections().computeIfAbsent(tagName, UiViewTemplateCollectionDescription::new );
            updateCollection(coll2, collElm2);
        });
        propElm.getChildren("group-ref").forEach(groupElm -> prop.getGroups().add(new UiGroupDescription(groupElm.getAttribute("ref"))));
    }

    private void updateAttributes(Map<String, UiAttributeDescription> attributes, XmlNode propsElm) {
        propsElm.getChildren("attribute").forEach(attrElm -> {
            var name = attrElm.getAttribute("name");
            var attr = attributes.computeIfAbsent(name, UiAttributeDescription::new);
            attr.setClassName(attrElm.getAttribute("class-name"));
            attr.setType(UiAttributeType.valueOf(attrElm.getAttribute("type")));
            attr.setDefaultValue(attrElm.getAttribute("default"));
            attr.setNonNullable("true".equals(attrElm.getAttribute("non-nullable")));
        });
    }

    public static StandardValueType toStandardType(UiConfigurationPropertyType type) {
        return switch (type){
            case STRING -> StandardValueType.STRING;
            case BOOLEAN -> StandardValueType.BOOLEAN;
            case ENTITY -> StandardValueType.ENTITY;
            case INT -> StandardValueType.INT;
        };
    }

    public static StandardValueType toStandardType(UiValidationPropertyType type) {
        return switch (type){
            case STRING -> StandardValueType.STRING;
            case ENTITY -> StandardValueType.ENTITY;
        };
    }

    public static StandardValueType toStandardType(UiModelPropertyType type) {
        return switch (type){
            case STRING -> StandardValueType.STRING;
            case LOCAL_DATE -> StandardValueType.LOCAL_DATE;
            case LOCAL_DATE_TIME -> StandardValueType.LOCAL_DATE_TIME;
            case ENUM -> StandardValueType.ENUM;
            case BOOLEAN -> StandardValueType.BOOLEAN;
            case BYTE_ARRAY -> StandardValueType.BYTE_ARRAY;
            case ENTITY -> StandardValueType.ENTITY;
            case ENTITY_REFERENCE -> StandardValueType.ENTITY_REFERENCE;
            case LONG -> StandardValueType.LONG;
            case INT -> StandardValueType.INT;
            case BIG_DECIMAL -> StandardValueType.BIG_DECIMAL;
        };
    }
}

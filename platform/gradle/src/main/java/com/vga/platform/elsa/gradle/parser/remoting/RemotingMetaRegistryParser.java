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

package com.vga.platform.elsa.gradle.parser.remoting;

import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.remoting.*;
import com.vga.platform.elsa.gradle.parser.common.CommonParserUtils;
import com.vga.platform.elsa.gradle.parser.common.MetaDataParsingResult;
import com.vga.platform.elsa.gradle.utils.BuildExceptionUtils;

import java.io.File;
import java.util.List;

public class RemotingMetaRegistryParser {

    public void updateMetaRegistry(RemotingMetaRegistry registry, List<File> sources) {
        sources.forEach(it -> BuildExceptionUtils.wrapException(() -> {
            MetaDataParsingResult pr = CommonParserUtils.parse(it);
            XmlNode node = pr.node();

            node.getChildren("enum").forEach(child ->
                    CommonParserUtils.updateEnum(registry.getEnums(), child, pr.localizations()));
            node.getChildren("entity").forEach(child ->
                    CommonParserUtils.updateEntity(registry.getEntities(), child));
            var id = CommonParserUtils.getIdAttribute(node);
            var remotingDescription = registry.getRemotings().computeIfAbsent(CommonParserUtils.getIdAttribute(node), RemotingDescription::new);
            node.getChildren("group").forEach(groupChild ->{
              var groupDescr = remotingDescription.getGroups().computeIfAbsent(CommonParserUtils.getIdAttribute(groupChild), RemotingGroupDescription::new);
              groupChild.getChildren("server-call").forEach(item -> {
                  var sc = groupDescr.getServerCalls().computeIfAbsent(CommonParserUtils.getIdAttribute(item),
                          RemotingServerCallDescription::new);
                  sc.setValidatable("true".equals(item.getAttribute("validatable")));
                  var requestChildren = item.getChildren("request");
                  if(requestChildren.size() == 1) {
                      sc.setRequestClassName(parseEntity(registry, requestChildren.get(0)));
                  }
                  var responseChildren = item.getChildren("response");
                  if(responseChildren.size() == 1) {
                      sc.setResponseClassName(parseEntity(registry, responseChildren.get(0)));
                  }
              });
                groupChild.getChildren("client-call").forEach(item -> {
                    var sc = groupDescr.getClientCalls().computeIfAbsent(CommonParserUtils.getIdAttribute(item),
                            RemotingClientCallDescription::new);
                    sc.setRequestClassName(parseEntity(registry, item.getChildren("request").get(0)));
                    sc.setResponseClassName(parseEntity(registry, item.getChildren("response").get(0)));
                });
                groupChild.getChildren("subscription").forEach(item -> {
                    var sc = groupDescr.getSubscriptions().computeIfAbsent(CommonParserUtils.getIdAttribute(item),
                            RemotingSubscriptionDescription::new);
                    sc.setParameterClassName(parseEntity(registry, item.getChildren("parameter").get(0)));
                    sc.setEventClassName(parseEntity(registry, item.getChildren("event").get(0)));
                });
            });
        }));
    }

    private String parseEntity(RemotingMetaRegistry registry, XmlNode elm) {
        var ett = CommonParserUtils.updateEntity(registry.getEntities(), elm);
        return ett.getId();
    }
}

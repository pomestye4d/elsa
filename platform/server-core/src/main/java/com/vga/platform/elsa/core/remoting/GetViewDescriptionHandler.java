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

package com.vga.platform.elsa.core.remoting;

import com.vga.platform.elsa.common.core.utils.LocaleUtils;
import com.vga.platform.elsa.common.meta.common.XmlNode;
import com.vga.platform.elsa.common.meta.ui.UiMetaRegistry;
import com.vga.platform.elsa.common.rest.core.*;
import com.vga.platform.elsa.server.core.CoreRemotingConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class GetViewDescriptionHandler implements RemotingServerCallHandler<GetViewDescriptionRequest, GetViewDescriptionResponse> {


    @Autowired
    private UiMetaRegistry uiMetaRegistry;

    @Override
    public String getId() {
        return CoreRemotingConstants.CORE_META_GET_VIEW_DESCRIPTION;
    }

    @Override
    public GetViewDescriptionResponse service(GetViewDescriptionRequest request, RemotingServerCallContext context){
        var viewDescription = uiMetaRegistry.getViews().get(request.getViewId());
        var result = new GetViewDescriptionResponse();
        viewDescription.getLocalizations().forEach((key, value) -> result.getLocalizations().put(key, LocaleUtils.getLocalizedName(value, null, null)));
        result.setView(createNode(viewDescription.getView()));
        return result;
    }

    private XmlNodeDT createNode(XmlNode view) {
        var result = new  XmlNodeDT();
        result.setName(view.getName());
        result.setValue(view.getValue());
        result.getAttributes().putAll(view.getAttributes());
        for(XmlNode child: view.getChildren()){
            result.getChildren().add(createNode(child));
        }
        return result;
    }
}

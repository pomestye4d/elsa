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
import com.vga.platform.elsa.common.meta.l10n.L10nMessageDescription;
import com.vga.platform.elsa.common.meta.l10n.L10nMetaRegistry;
import com.vga.platform.elsa.common.remoting.core.GetL10nBundleRequest;
import com.vga.platform.elsa.common.remoting.core.GetL10nBundleResponse;
import com.vga.platform.elsa.server.core.CoreRemotingConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class GetL10nBundleHandler implements RemotingServerCallHandler<GetL10nBundleRequest, GetL10nBundleResponse> {
    @Autowired
    private L10nMetaRegistry registry;

    @Override
    public String getId() {
        return CoreRemotingConstants.CORE_L10N_GET_BUNDLE;
    }

    @Override
    public GetL10nBundleResponse service(GetL10nBundleRequest request, RemotingServerCallContext context) {
        var bundle = registry.getBundles().get(request.getBundleId());
        var result = new GetL10nBundleResponse();
        if(bundle != null){
            var locale = LocaleUtils.getLocale(request.getLanguage() == null? "en": request.getLanguage(), null);
            for(L10nMessageDescription message: bundle.getMessages().values()){
                result.getMessages().put(message.getId(), LocaleUtils.getLocalizedName(message.getDisplayNames(), locale, message.getId()));
            }
        }
        return result;
    }
}

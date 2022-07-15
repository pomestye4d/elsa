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

package com.vga.platform.elsa.core.storage.standard;

import com.vga.platform.elsa.common.core.l10n.SupportedLocalesProvider;
import com.vga.platform.elsa.common.core.model.common.BaseIdentity;
import com.vga.platform.elsa.common.core.model.domain.CaptionProvider;
import com.vga.platform.elsa.common.core.model.domain.EntityReference;
import com.vga.platform.elsa.common.core.utils.LocaleUtils;
import com.vga.platform.elsa.common.core.utils.TextUtils;
import com.vga.platform.elsa.core.cache.CacheManager;
import com.vga.platform.elsa.core.cache.CacheMetadataProvider;
import com.vga.platform.elsa.core.cache.CachedValue;
import com.vga.platform.elsa.core.cache.KeyValueCache;
import com.vga.platform.elsa.core.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class StorageCaptionProviderImpl implements CaptionProvider {

    private final Map<String, KeyValueCache<Long, String>> captionsCache = new ConcurrentHashMap<>();

    private final Map<String, KeyValueCache<Long, Map<Locale, String>>> localizedCaptionsCache = new ConcurrentHashMap<>();

    @Autowired
    private Environment env;

    @Autowired
    private CacheMetadataProvider cacheMetadataProvider;

    @Autowired
    private Storage storage;

    @Autowired
    private CacheManager cacheManager;

    private final String nullString = TextUtils.generateUUID();

    @Autowired
    private SupportedLocalesProvider supportedLocalesProvider;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public String getCaption(EntityReference<?> ref) {
        if(cacheMetadataProvider.isCacheCaption(ref.getType())){
            var cache = getOrCreateCaptionCache(captionsCache, String.class);
            var oldValue = cache.get(ref.getId());
            if (oldValue != null && oldValue.value() != null) {
                return oldValue.value().equals(nullString) ? null : oldValue.value();
            }
            var ar = storage.getCaption(ref.getType(), ref.getId(), LocaleUtils.getCurrentLocale());
            var newValue = new CachedValue<>(System.nanoTime(), ar == null ? nullString : ar);
            cache.replace(ref.getId(), oldValue, newValue);
            return Objects.equals(newValue.value(), nullString) ? null : newValue.value();
        }
        if(cacheMetadataProvider.isCacheLocalizedCaption(ref.getType())){
            var cache = getOrCreateCaptionCache((Map) localizedCaptionsCache, Map.class);
            var oldValue = (CachedValue<Map<Locale, String>>)cache.get(ref.getId());
            if (oldValue != null && oldValue.value() != null) {
                return oldValue.value().get(LocaleUtils.getCurrentLocale());
            }
            var res = new HashMap<Locale, String>();
            supportedLocalesProvider.getSupportedLocales().forEach(loc ->{
                var ar = storage.getCaption(ref.getType(), ref.getId(), loc);
                res.put(loc, ar);
            });
            var newValue = new CachedValue<>(System.nanoTime(), res);
            cache.replace(ref.getId(), oldValue, newValue);
            return res.get(LocaleUtils.getCurrentLocale());
        }
        return ref.getCaption();
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    <I extends BaseIdentity> void invalidateCaptionsCache(Class<I> cls, long id) {
        if(cacheMetadataProvider.isCacheCaption(cls)){
            getOrCreateCaptionCache(captionsCache, String.class).put(id, new CachedValue<>(System.nanoTime(), null));
            return;
        }
        if(cacheMetadataProvider.isCacheLocalizedCaption(cls)){
            getOrCreateCaptionCache((Map)localizedCaptionsCache, Map.class).put(id, new CachedValue<>(System.nanoTime(), null));
        }
    }
    private <D> KeyValueCache<Long, D> getOrCreateCaptionCache(Map<String, KeyValueCache<Long, D>> caches, Class<D> cls) {
        var cache = caches.get(cls.getName());
        if (cache == null) {
            var className = cls.getName();
            var capacityStr = env.getProperty("cache.caption.capacity.%s".formatted(className));
            if (capacityStr == null) {
                capacityStr = env.getProperty("cache.caption.capacity.default", "10000");
            }
            var capacity = Integer.parseInt(capacityStr);
            var expirationInSecondsStr = env.getProperty("cache.caption.expiration.%s".formatted(className));
            if (expirationInSecondsStr == null) {
                expirationInSecondsStr = env.getProperty("cache.caption.expiration.default", "3600");
            }
            var expirationInSeconds = Integer.parseInt(expirationInSecondsStr);
            cache = cacheManager.createKeyValueCache(Long.class, cls, "caption_%s".formatted(className), capacity, expirationInSeconds);
            caches.put(className, cache);
        }
        return cache;
    }
}

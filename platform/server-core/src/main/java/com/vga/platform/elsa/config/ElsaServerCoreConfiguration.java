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

package com.vga.platform.elsa.config;

import com.vga.platform.elsa.common.core.model.domain.CaptionProvider;
import com.vga.platform.elsa.core.cache.CacheManager;
import com.vga.platform.elsa.core.cache.CacheMetadataProvider;
import com.vga.platform.elsa.core.cache.ehCache.EhCacheManager;
import com.vga.platform.elsa.core.codec.DesCodec;
import com.vga.platform.elsa.core.storage.Storage;
import com.vga.platform.elsa.core.storage.database.jdbc.model.JdbcDatabaseMetadataProvider;
import com.vga.platform.elsa.core.storage.standard.CacheStorageAdvice;
import com.vga.platform.elsa.core.storage.standard.IdUpdaterInterceptor;
import com.vga.platform.elsa.core.storage.standard.InvalidateCacheStorageInterceptor;
import com.vga.platform.elsa.core.storage.standard.StorageCaptionProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

@Configuration
public class ElsaServerCoreConfiguration {

    @Bean
    public DesCodec desCodec(){
        return new DesCodec();
    }

    @Bean
    public Storage storage(){
        return new Storage();
    }

    @Bean
    public JdbcDatabaseMetadataProvider databaseMetadataProvider(){
        return new JdbcDatabaseMetadataProvider();
    }

    @Bean
    public CacheManager standardCacheManager(){
        return new EhCacheManager();
    }

    @Bean
    public CacheStorageAdvice cacheStorageAdvice(){
        return new CacheStorageAdvice();
    }

    @Bean
    public IdUpdaterInterceptor idUpdaterInterceptor(){
        return new IdUpdaterInterceptor();
    }

    @Bean
    public InvalidateCacheStorageInterceptor invalidateCacheStorageInterceptor(){
        return new InvalidateCacheStorageInterceptor();
    }

    @Bean
    @Primary
    public CaptionProvider captionProvider(){
        return new StorageCaptionProviderImpl();
    }

    @Bean
    public CacheMetadataProvider cacheMetadataProvider(){
        return new CacheMetadataProvider();
    }
}

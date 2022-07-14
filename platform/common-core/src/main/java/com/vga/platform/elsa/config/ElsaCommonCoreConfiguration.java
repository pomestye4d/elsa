/*****************************************************************
 * Gridnine http://www.gridnine.com
 * Project: Elsa
 *****************************************************************/

package com.vga.platform.elsa.config;

import com.vga.platform.elsa.common.core.l10n.Localizer;
import com.vga.platform.elsa.common.core.l10n.SupportedLocalesProvider;
import com.vga.platform.elsa.common.core.lock.LockManager;
import com.vga.platform.elsa.common.core.lock.standard.StandardLockManager;
import com.vga.platform.elsa.common.core.model.domain.CaptionProvider;
import com.vga.platform.elsa.common.core.model.domain.EntityReference;
import com.vga.platform.elsa.common.core.reflection.ReflectionFactory;
import com.vga.platform.elsa.common.core.serialization.CachedObjectConverter;
import com.vga.platform.elsa.common.core.serialization.Cloner;
import com.vga.platform.elsa.common.core.serialization.JsonMarshaller;
import com.vga.platform.elsa.common.core.serialization.JsonUnmarshaller;
import com.vga.platform.elsa.common.core.serialization.meta.ObjectMetadataProvidersFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class ElsaCommonCoreConfiguration {

    @Bean
    @Order(0)
    public CaptionProvider standardCaptionProvider(){
        return EntityReference::getCaption;
    }

    @Bean
    public Localizer localizer(){
        return new Localizer();
    }

    @Bean
    public SupportedLocalesProvider supportedLocalesProvider(){
        return new SupportedLocalesProvider();
    }

    @Bean
    public LockManager standardLockManager(){
        return new StandardLockManager();
    }

    @Bean
    public ReflectionFactory reflectionFactory(){
        return new ReflectionFactory();
    }

    @Bean
    public CachedObjectConverter cachedObjectConverter(){
        return new CachedObjectConverter();
    }

    @Bean
    public Cloner cloner(){
        return new Cloner();
    }

    @Bean
    public JsonMarshaller jsonMarshaller(){
        return new JsonMarshaller();
    }

    @Bean
    public JsonUnmarshaller jsonUnmarshaller(){
        return new JsonUnmarshaller();
    }

    @Bean
    public ObjectMetadataProvidersFactory objectMetadataProvidersFactory(){
        return new ObjectMetadataProvidersFactory();
    }

    @Bean
    public ElsaCommonCoreCustomMetaRegistryConfigurator elsaCommonCoreCustomMetaRegistryConfigurator(){
        return new ElsaCommonCoreCustomMetaRegistryConfigurator();
    }

    @Bean
    public ElsaCommonCoreRemotingMetaRegistryConfigurator elsaCommonCoreRemotingMetaRegistryConfigurator(){
        return new ElsaCommonCoreRemotingMetaRegistryConfigurator();
    }
}

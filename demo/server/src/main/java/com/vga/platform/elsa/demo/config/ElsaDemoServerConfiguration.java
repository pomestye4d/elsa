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

package com.vga.platform.elsa.demo.config;

import com.vga.platform.elsa.core.storage.database.jdbc.adapter.JdbcDataSourceProvider;
import com.vga.platform.elsa.demo.DemoElsaDomainMetaRegistryConfigurator;
import com.vga.platform.elsa.demo.DemoElsaL10nMetaRegistryConfigurator;
import com.vga.platform.elsa.demo.DemoElsaRemotingMetaRegistryConfigurator;
import com.vga.platform.elsa.demo.activator.ElsaDemoActivator;
import com.vga.platform.elsa.demo.server.DemoElsaRestController;
import com.vga.platform.elsa.demo.test.DemoTestServerCallRequestHandler;
import com.vga.platform.elsa.demo.userAccount.DemoUserAccountProjectionHandler;
import com.vga.platform.elsa.server.core.common.HsqldbDataSourceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElsaDemoServerConfiguration {
    @Bean
    public ElsaDemoActivator demoActivator(){
        return new ElsaDemoActivator();
    }

    @Bean
    public DemoElsaDomainMetaRegistryConfigurator demoElsaDomainMetaRegistryConfigurator(){
        return new DemoElsaDomainMetaRegistryConfigurator();
    }

    @Bean
    public DemoUserAccountProjectionHandler demoUserAccountProjectionHandler(){
        return new DemoUserAccountProjectionHandler();
    }

    @Bean
    public JdbcDataSourceProvider hsqldbDataSourceProvider(){
        return new HsqldbDataSourceProvider();
    }

    @Bean
    public DemoElsaRestController demoElsaRestController(){
        return new DemoElsaRestController();
    }

    @Bean
    public DemoElsaRemotingMetaRegistryConfigurator demoElsaRemotingMetaRegistryConfigurator(){
        return new DemoElsaRemotingMetaRegistryConfigurator();
    }

    @Bean
    public DemoTestServerCallRequestHandler demoTestServerCallRequestHandler(){
        return new DemoTestServerCallRequestHandler();
    }

    @Bean
    public DemoElsaL10nMetaRegistryConfigurator demoElsaL10nMetaRegistryConfigurator(){
        return new DemoElsaL10nMetaRegistryConfigurator();
    }
}

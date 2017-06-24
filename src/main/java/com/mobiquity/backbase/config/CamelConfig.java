package com.mobiquity.backbase.config;

import com.mobiquity.backbase.data.AtmDataStore;
import com.mobiquity.backbase.service.AtmService;
import com.mobiquity.backbase.service.impl.AtmServiceImpl;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by sjoshi on 6/22/17.
 */
@Configuration
@ComponentScan("com.mobiquity.backbase.route")
@ImportResource("classpath:applicationContext-security.xml")
public class CamelConfig extends CamelConfiguration {

    @Bean
    public AtmService atmService() {
        return new AtmServiceImpl();
    }

    @Bean
    public AtmDataStore atmDataStore() {
        return new AtmDataStore();
    }

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
        //Setting up Camel Context
        super.setupCamelContext(camelContext);
    }
}

package com.sneaksapp.knowledgebuilder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

//Uygulamanın ayarlarını içeriyor 
@Configuration
public class RestClientConfig {

    @Bean //Bu metodun döndürdüğü nesneyi Spring hafızasında sakla
    public RestClient.Builder restClientBuilder() {

        return RestClient.builder();

    }

}
package com.tokkyokun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class JavaConfig {

    @Bean
    public WebClient webClient() {
        final var webClientBuilder = WebClient.builder();
        return webClientBuilder.build();
    }

}

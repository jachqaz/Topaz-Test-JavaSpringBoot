/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configura el {@link WebClient} que apunta a DummyJSON.
 * La baseURL es configurable vía {@code dummyjson.base-url}.
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient dummyJsonWebClient(
            @Value("${dummyjson.base-url:https://dummyjson.com}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}

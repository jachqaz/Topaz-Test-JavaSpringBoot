/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadatos de la documentación OpenAPI/Swagger.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI topazOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Topaz Products API")
                .description("BFF hexagonal que expone productos de DummyJSON al frontend React Native.")
                .version("v1")
                .license(new License().name("Proprietary").url("https://github.com/jachqaz/Topaz-Test-JavaSpringBoot")));
    }
}

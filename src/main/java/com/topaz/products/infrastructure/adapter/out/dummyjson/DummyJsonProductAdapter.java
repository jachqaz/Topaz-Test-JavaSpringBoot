/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.out.dummyjson;

import com.topaz.products.domain.model.Product;
import com.topaz.products.domain.port.out.ProductRepositoryPort;
import com.topaz.products.infrastructure.adapter.out.dummyjson.dto.DummyJsonProductDto;
import com.topaz.products.infrastructure.adapter.out.dummyjson.dto.DummyJsonProductListResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de SALIDA: implementa {@link ProductRepositoryPort} consumiendo la API
 * pública DummyJSON mediante {@link WebClient} de forma bloqueante (la API expuesta es MVC).
 */
@Component
public class DummyJsonProductAdapter implements ProductRepositoryPort {

    private final WebClient webClient;

    public DummyJsonProductAdapter(WebClient dummyJsonWebClient) {
        this.webClient = dummyJsonWebClient;
    }

    @Override
    public List<Product> findAll() {
        DummyJsonProductListResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/products").build())
                .retrieve()
                .bodyToMono(DummyJsonProductListResponse.class)
                .block();

        if (response == null || response.products() == null) {
            return List.of();
        }
        return response.products().stream()
                .map(DummyJsonProductMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(long id) {
        try {
            DummyJsonProductDto dto = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/products/{id}").build(id))
                    .retrieve()
                    .bodyToMono(DummyJsonProductDto.class)
                    .block();

            return Optional.ofNullable(dto).map(DummyJsonProductMapper::toDomain);
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatusCode.valueOf(404)) {
                return Optional.empty();
            }
            throw ex;
        }
    }
}

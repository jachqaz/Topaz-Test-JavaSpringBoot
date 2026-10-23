/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.out.dummyjson.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * DTO de la respuesta de lista de DummyJSON: {@code { products, total, skip, limit }}.
 * Vive en el adaptador de salida; no se filtra al dominio.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DummyJsonProductListResponse(
        List<DummyJsonProductDto> products,
        int total,
        int skip,
        int limit
) {
}

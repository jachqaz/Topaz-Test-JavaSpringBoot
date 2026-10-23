/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.out.dummyjson.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * DTO del producto tal como lo devuelve DummyJSON. Solo mapea los campos usados;
 * el resto (tags, sku, dimensions, reviews, meta...) se ignora.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DummyJsonProductDto(
        long id,
        String title,
        String description,
        String category,
        double price,
        double discountPercentage,
        double rating,
        int stock,
        String brand,
        String thumbnail,
        List<String> images
) {
}

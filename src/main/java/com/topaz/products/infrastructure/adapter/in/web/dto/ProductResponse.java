/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.in.web.dto;

import com.topaz.products.domain.model.Product;

import java.util.List;

/**
 * Representación de salida de la API propia. Desacopla el contrato REST del modelo de dominio.
 */
public record ProductResponse(
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
    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(
                product.id(),
                product.title(),
                product.description(),
                product.category(),
                product.price(),
                product.discountPercentage(),
                product.rating(),
                product.stock(),
                product.brand(),
                product.thumbnail(),
                product.images()
        );
    }
}

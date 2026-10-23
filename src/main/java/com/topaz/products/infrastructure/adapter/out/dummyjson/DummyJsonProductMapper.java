/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.out.dummyjson;

import com.topaz.products.domain.model.Product;
import com.topaz.products.infrastructure.adapter.out.dummyjson.dto.DummyJsonProductDto;

import java.util.List;

/**
 * Traduce el DTO de DummyJSON al modelo de dominio {@link Product}.
 * Mantiene el dominio aislado del contrato externo.
 */
final class DummyJsonProductMapper {

    private DummyJsonProductMapper() {
    }

    static Product toDomain(DummyJsonProductDto dto) {
        return new Product(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.category(),
                dto.price(),
                dto.discountPercentage(),
                dto.rating(),
                dto.stock(),
                dto.brand(),
                dto.thumbnail(),
                dto.images() == null ? List.of() : dto.images()
        );
    }
}

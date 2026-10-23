/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.in.web;

import com.topaz.products.domain.port.in.GetProductByIdUseCase;
import com.topaz.products.domain.port.in.GetProductsUseCase;
import com.topaz.products.infrastructure.adapter.in.web.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Adaptador de ENTRADA (REST). Expone los productos al frontend consumiendo
 * los puertos de entrada del dominio.
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Catálogo de productos (BFF sobre DummyJSON)")
public class ProductController {

    private final GetProductsUseCase getProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;

    public ProductController(GetProductsUseCase getProductsUseCase,
                             GetProductByIdUseCase getProductByIdUseCase) {
        this.getProductsUseCase = getProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
    }

    @GetMapping
    @Operation(summary = "Lista todos los productos")
    public List<ProductResponse> getProducts() {
        return getProductsUseCase.getProducts().stream()
                .map(ProductResponse::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene el detalle de un producto por id")
    public ProductResponse getProductById(@PathVariable long id) {
        return ProductResponse.fromDomain(getProductByIdUseCase.getProductById(id));
    }
}

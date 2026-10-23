/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.application;

import com.topaz.products.domain.exception.ProductNotFoundException;
import com.topaz.products.domain.model.Product;
import com.topaz.products.domain.port.in.GetProductByIdUseCase;
import com.topaz.products.domain.port.in.GetProductsUseCase;
import com.topaz.products.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de aplicación: orquesta los casos de uso del dominio apoyándose
 * en el puerto de salida {@link ProductRepositoryPort}. No conoce la tecnología
 * concreta del origen de datos.
 */
@Service
public class ProductService implements GetProductsUseCase, GetProductByIdUseCase {

    private final ProductRepositoryPort productRepository;

    public ProductService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}

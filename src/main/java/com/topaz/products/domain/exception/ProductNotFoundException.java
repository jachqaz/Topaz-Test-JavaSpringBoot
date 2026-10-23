/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.domain.exception;

/**
 * Se lanza cuando un producto solicitado no existe en el origen de datos.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(long id) {
        super("Product not found with id: " + id);
    }
}

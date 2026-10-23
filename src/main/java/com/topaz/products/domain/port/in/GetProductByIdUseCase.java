/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.domain.port.in;

import com.topaz.products.domain.model.Product;

/**
 * Puerto de ENTRADA del dominio: caso de uso para obtener un producto por id.
 */
public interface GetProductByIdUseCase {

    Product getProductById(long id);
}

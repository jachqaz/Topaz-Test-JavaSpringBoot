/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.domain.port.in;

import com.topaz.products.domain.model.Product;

import java.util.List;

/**
 * Puerto de ENTRADA del dominio: caso de uso para listar productos.
 * Lo invoca un adaptador de entrada (controller REST).
 */
public interface GetProductsUseCase {

    List<Product> getProducts();
}

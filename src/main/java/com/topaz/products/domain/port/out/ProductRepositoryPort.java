/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.domain.port.out;

import com.topaz.products.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de SALIDA del dominio. Define cómo el núcleo obtiene productos,
 * sin conocer la tecnología concreta (DummyJSON, BD, etc.).
 * Lo implementa un adaptador en la capa de infraestructura.
 */
public interface ProductRepositoryPort {

    /**
     * @return la lista completa de productos disponibles.
     */
    List<Product> findAll();

    /**
     * @param id identificador del producto.
     * @return el producto si existe.
     */
    Optional<Product> findById(long id);
}

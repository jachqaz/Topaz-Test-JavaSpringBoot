/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.domain.model;

import java.util.List;

/**
 * Modelo de dominio de un producto. Inmutable y libre de dependencias de framework.
 * Representa el núcleo de negocio, independiente del origen de datos (DummyJSON u otro).
 *
 * @param id                 identificador único
 * @param title              título del producto
 * @param description        descripción
 * @param category           categoría
 * @param price              precio
 * @param discountPercentage porcentaje de descuento
 * @param rating             valoración media
 * @param stock              unidades en stock
 * @param brand              marca (puede ser nula en algunos productos)
 * @param thumbnail          miniatura
 * @param images             galería de imágenes
 */
public record Product(
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
    public Product {
        images = images == null ? List.of() : List.copyOf(images);
    }
}

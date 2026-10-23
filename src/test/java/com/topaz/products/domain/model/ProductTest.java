/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.domain.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    void nullImages_becomeEmptyList() {
        Product p = new Product(1, "t", "d", "c", 1.0, 0.0, 4.0, 10, "b", "thumb", null);
        assertThat(p.images()).isEmpty();
    }

    @Test
    void images_areDefensivelyCopiedAndImmutable() {
        List<String> source = new ArrayList<>(List.of("a.webp", "b.webp"));
        Product p = new Product(1, "t", "d", "c", 1.0, 0.0, 4.0, 10, "b", "thumb", source);

        source.add("c.webp"); // no debe afectar al producto
        assertThat(p.images()).containsExactly("a.webp", "b.webp");

        assertThatThrownBy(() -> p.images().add("x.webp"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}

/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.application;

import com.topaz.products.domain.exception.ProductNotFoundException;
import com.topaz.products.domain.model.Product;
import com.topaz.products.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sample(long id) {
        return new Product(id, "Title " + id, "Desc", "beauty", 9.99, 10.0,
                4.5, 50, "BrandX", "thumb.webp", List.of("1.webp"));
    }

    @Test
    void getProducts_returnsAllFromRepository() {
        when(productRepository.findAll()).thenReturn(List.of(sample(1), sample(2)));

        List<Product> result = productService.getProducts();

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().title()).isEqualTo("Title 1");
    }

    @Test
    void getProducts_returnsEmptyWhenNone() {
        when(productRepository.findAll()).thenReturn(List.of());

        assertThat(productService.getProducts()).isEmpty();
    }

    @Test
    void getProductById_returnsProductWhenFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sample(1)));

        Product result = productService.getProductById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.images()).containsExactly("1.webp");
    }

    @Test
    void getProductById_throwsWhenMissing() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(999L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("999");
    }
}

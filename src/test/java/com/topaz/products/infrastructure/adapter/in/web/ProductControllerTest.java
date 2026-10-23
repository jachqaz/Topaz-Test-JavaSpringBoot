/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.in.web;

import com.topaz.products.domain.exception.ProductNotFoundException;
import com.topaz.products.domain.model.Product;
import com.topaz.products.domain.port.in.GetProductByIdUseCase;
import com.topaz.products.domain.port.in.GetProductsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProductsUseCase getProductsUseCase;

    @MockBean
    private GetProductByIdUseCase getProductByIdUseCase;

    private Product sample(long id) {
        return new Product(id, "Title " + id, "Desc", "beauty", 9.99, 10.0,
                4.5, 50, "BrandX", "thumb.webp", List.of("1.webp"));
    }

    @Test
    void getProducts_returns200WithList() throws Exception {
        when(getProductsUseCase.getProducts()).thenReturn(List.of(sample(1), sample(2)));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Title 1"));
    }

    @Test
    void getProductById_returns200WithProduct() throws Exception {
        when(getProductByIdUseCase.getProductById(1L)).thenReturn(sample(1));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.images[0]").value("1.webp"));
    }

    @Test
    void getProductById_returns404WhenMissing() throws Exception {
        when(getProductByIdUseCase.getProductById(999L))
                .thenThrow(new ProductNotFoundException(999L));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}

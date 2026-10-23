/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.out.dummyjson;

import com.topaz.products.domain.model.Product;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DummyJsonProductAdapterTest {

    private MockWebServer server;
    private DummyJsonProductAdapter adapter;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        WebClient client = WebClient.builder().baseUrl(server.url("/").toString()).build();
        adapter = new DummyJsonProductAdapter(client);
    }

    @AfterEach
    void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    void findAll_mapsListResponseToDomain() {
        String body = """
                {"products":[
                  {"id":1,"title":"Essence Mascara","description":"desc","category":"beauty",
                   "price":9.99,"discountPercentage":10.48,"rating":2.56,"stock":99,
                   "brand":"Essence","thumbnail":"t.webp","images":["1.webp"]}
                ],"total":1,"skip":0,"limit":30}
                """;
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(body));

        List<Product> result = adapter.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().title()).isEqualTo("Essence Mascara");
        assertThat(result.getFirst().images()).containsExactly("1.webp");
    }

    @Test
    void findAll_returnsEmptyWhenNoProducts() {
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("{\"products\":[],\"total\":0,\"skip\":0,\"limit\":30}"));

        assertThat(adapter.findAll()).isEmpty();
    }

    @Test
    void findById_mapsProductToDomain() {
        String body = """
                {"id":1,"title":"Essence Mascara","description":"desc","category":"beauty",
                 "price":9.99,"discountPercentage":10.48,"rating":2.56,"stock":99,
                 "brand":"Essence","thumbnail":"t.webp","images":["1.webp","2.webp"]}
                """;
        server.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(body));

        Optional<Product> result = adapter.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(1L);
        assertThat(result.get().images()).hasSize(2);
    }

    @Test
    void findById_returnsEmptyOn404() {
        server.enqueue(new MockResponse().setResponseCode(404)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"message\":\"not found\"}"));

        assertThat(adapter.findById(999L)).isEmpty();
    }
}

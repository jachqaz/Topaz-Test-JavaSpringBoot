/*
 * Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
 * All rights reserved.
 */
package com.topaz.products.infrastructure.adapter.in.web.dto;

import java.time.Instant;

/**
 * Cuerpo de error uniforme para las respuestas de la API.
 */
public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message
) {
    public static ApiErrorResponse of(int status, String error, String message) {
        return new ApiErrorResponse(Instant.now(), status, error, message);
    }
}

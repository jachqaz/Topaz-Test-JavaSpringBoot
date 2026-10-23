<!--
  Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
  All rights reserved.
-->

# TRACKING.md — Topaz Products API (Backend)

**Director de orquesta:** jachqaz (`jach_1997@hotmail.com`) · **Agente:** Kiro (Auto), guiado por el director.
**Repo:** https://github.com/jachqaz/Topaz-Test-JavaSpringBoot.git · **Rama de trabajo:** `develop`
**Stack:** Java 21 LTS · Spring Boot 3.4.13 · Maven · Arquitectura Hexagonal (Ports & Adapters).

> Estados: `TODO` · `IN_PROGRESS` · `FINISH`. Política: **solo commits locales, nunca push** (lo hace el director).

## Contexto
Microservicio BFF que consume la API pública **DummyJSON** (`/products`, `/products/{id}`) y la expone
al frontend React Native. Mini-tienda de productos (prueba técnica fullstack).

## Tareas

| # | Tarea | Estado |
|---|-------|--------|
| B0 | Scaffold en blanco (pom 3.4.13, main, test contexto) en `master` | FINISH |
| B1 | Crear `.md` del agente (.kiro steering, prompt, este tracking) en `develop` | FINISH |
| B2 | pom: deps hexagonal (webflux/RestClient, validation, actuator, lombok, springdoc, jacoco) | FINISH |
| B3 | DOMAIN: modelo `Product`, puerto `ProductRepositoryPort` | FINISH |
| B4 | APPLICATION: casos de uso `GetProducts`, `GetProductById` | FINISH |
| B5 | INFRA out: adaptador DummyJSON (RestClient/WebClient) + mapeo DTO→dominio | FINISH |
| B6 | INFRA in/web: controllers REST `/api/products`, `/api/products/{id}` | FINISH |
| B7 | INFRA config: OpenAPI/Swagger, CORS, manejo global de errores | FINISH |
| B8 | TESTING: unit (casos de uso, mapeo) + integración (web) + JaCoCo ≥80% | FINISH |
| B9 | DOCS: README (rol director de orquesta) | FINISH |
| B10 | CI/CD: `.github/workflows/ci.yml` build+test+coverage, artefacto `.jar` | FINISH |
| B11 | Preprocesado commit (Spotless, headers, audit) | IN_PROGRESS (headers de copyright aplicados; Spotless opcional pendiente) |
| B12 | Verificación final: build → test → build confirmación | FINISH |

## Verificación (evidencia real 2026-10-22)
- `./mvnw clean verify` → **BUILD SUCCESS**. 13 tests, 0 fallos, 0 errores.
- JaCoCo: "All coverage checks have been met" (gate ≥80%).
- Artefacto: `target/topaz-products-api-0.0.1-SNAPSHOT.jar` generado.
- WebClient a DummyJSON con baseURL configurable; 404 upstream → Optional.empty → 404 API.

## Decisiones
- Spring Boot 3.4.13 (última patch 3.4; Initializr ya no la ofrece → scaffold a mano).
- Cliente HTTP a DummyJSON: `RestClient` (síncrono, Spring 6.1+) o `WebClient`; se decide en B5 según simplicidad.
- Hexagonal estricta: dominio sin anotaciones de framework; adaptadores en infraestructura.

## Contrato DummyJSON (evidencia real capturada 2026-10-22)
- `GET /products` → `{ products: [...], total: 194, skip: 0, limit: 30 }`
- `GET /products/{id}` → objeto `Product`.
- `Product`: `id, title, description, category, price, discountPercentage, rating, stock, brand, thumbnail, images[]` (+tags, sku, dimensions, reviews, meta).

## Problemas y soluciones
| # | Problema | Solución | Estado |
|---|----------|----------|--------|
| — | — | — | — |

## Dónde retomar
Tras B1 → B2 (ajustar pom con dependencias hexagonal + jacoco) y B3 (dominio).

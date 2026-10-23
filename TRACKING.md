<!--
  Copyright (c) 2026 Johan Castano (jachqaz). Topaz Products API.
  All rights reserved.
-->

# TRACKING.md — Topaz Products API (Backend)

**Director de orquesta:** Johan Castano (jachqaz) (`jach_1997@hotmail.com`) · **Agente:** Kiro (Auto), guiado por el director.
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
| C1 | Integración: `server.port=8080` fijo + README (adb reverse, flujo dummyjson→back→front) | FINISH |

### Correcciones 2026-10-22 (integración front/back)
- `server.port=8080` fijo en `application.properties` (el `adb reverse` del front lo asume).
- Verificado en vivo: `GET /api/products` → 200 con 30 productos; cadena DummyJSON→back confirmada.
- README: tabla de configuración con el puerto y sección de integración con el front.

### Diagnóstico de conexión 2026-10-23
- Para diagnosticar "no llegan consultas al back" se habilitó temporalmente logging DEBUG del
  `DispatcherServlet` + `spring.mvc.log-request-details`. Evidencia: la cadena nunca estuvo rota
  (back vivo en 8080, CORS `*`, 30 productos, device con `adb reverse` activo). Era falta de
  observabilidad en el front (Axios sin logs), no de conectividad.
- El logging temporal se **retiró** de `application.properties` tras validar. No se tocó logging funcional.
- README actualizado con clonado desde cero (JDK 21, Maven Wrapper, verificación con `curl`).

### READMEs y CI 2026-10-23
- README: nueva sección "Modelo de trabajo: orquestación humano + agente" (valor del perfil del
  director: arquitectura hexagonal, decisiones, calidad, validación por evidencia).
- CI `ci.yml` revisado: **se deja como está** — refleja el build real (`mvnw clean verify -B`, Java 21
  Temurin, caché Maven, gate JaCoCo ≥80%, publica `.jar` + jacoco + surefire). No requiere cambios.

### Documentación 2026-10-23 — nombre del director
- Reemplazo en la documentación: `Johan A. Hernández (jachqaz)` → `Johan Castano (jachqaz)`;
  menciones de `jachqaz` como autoría → `Johan Castano (jachqaz)`. **No** se tocaron las URLs de
  GitHub ni la config `user.name = jachqaz` (identidad real de git).
  Archivos del repo: `README.md` (3), `TRACKING.md` (2), `.kiro/steering/project-rules.md` (1).
- **Rutina adoptada:** tras cada cambio → commit firmado (`-s`, sin push) con preproceso +
  actualización de este `.md` de seguimiento.
- Verificación pre-commit: `mvnw verify` BUILD SUCCESS, 13 tests, 0 fallos.
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

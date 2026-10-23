<!--
  Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
  All rights reserved.
-->

# Topaz Products API — Backend (Spring Boot · Arquitectura Hexagonal)

Microservicio **BFF** (Backend For Frontend) que consume la API pública
[DummyJSON](https://dummyjson.com/) y expone los productos al frontend React Native **Topaz**.
Mini-tienda de productos (prueba técnica fullstack).

## Roles del proyecto

- **Director de orquesta:** Johan A. Hernández (`jachqaz`) — define objetivos, arquitectura y prioridades.
- **Agente de desarrollo:** Kiro (modelo Auto) — ejecuta guiado por el director, con evidencia y trazabilidad.

## Stack

- Java 21 (LTS) · Spring Boot 3.4.13 · Maven (con Wrapper)
- Arquitectura **Hexagonal** (Ports & Adapters)
- WebClient (consumo de DummyJSON) · Bean Validation · Actuator
- JUnit 5 + Mockito + AssertJ + MockWebServer · JaCoCo (gate ≥ 80%)
- SpringDoc OpenAPI (Swagger UI)
- GitHub Actions (CI → artefacto `.jar`)

## Arquitectura

```
com.topaz.products
├─ domain/                         # NÚCLEO puro (sin Spring)
│  ├─ model/Product               # modelo de dominio (record inmutable)
│  ├─ exception/                  # ProductNotFoundException
│  └─ port/
│     ├─ in/                      # GetProductsUseCase, GetProductByIdUseCase
│     └─ out/ProductRepositoryPort
├─ application/ProductService      # implementa los casos de uso
└─ infrastructure/
   ├─ adapter/in/web/             # ProductController + DTOs + GlobalExceptionHandler
   ├─ adapter/out/dummyjson/      # DummyJsonProductAdapter + DTOs + Mapper
   └─ config/                     # WebClient, CORS, OpenAPI
```

Las dependencias apuntan hacia el dominio. Los adaptadores implementan los puertos;
el dominio no conoce Spring ni DummyJSON.

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/products` | Lista de productos |
| GET | `/api/products/{id}` | Detalle de un producto (404 si no existe) |
| GET | `/actuator/health` | Health check |
| GET | `/swagger-ui.html` | Documentación interactiva |

## Prerrequisitos

- Java 21 (JDK)
- (Opcional) `vfox use java@21`

## Ejecución

```bash
./mvnw spring-boot:run        # http://localhost:8080
```

Ejemplos:

```bash
curl http://localhost:8080/api/products
curl http://localhost:8080/api/products/1
```

Documentación OpenAPI: `http://localhost:8080/swagger-ui.html`

## Configuración

| Propiedad | Valor por defecto | Descripción |
|-----------|-------------------|-------------|
| `server.port` | `8080` | Puerto fijo del backend (lo asume el `adb reverse` del front) |
| `dummyjson.base-url` | `https://dummyjson.com` | Origen de datos externo |

## Integración con el frontend

El frontend React Native consume este backend en `http://localhost:8080/api`.
Para un device/emulador Android, el front mapea el puerto con:

```bash
adb reverse tcp:8080 tcp:8080    # el localhost:8080 del teléfono → este backend
```

Flujo de datos: **DummyJSON → este backend (BFF) → frontend**. Ver el README del
front para el detalle. El backend puede exponerse también por túnel (p. ej.
Cloudflare) para pruebas sin USB; es un camino opcional, no el principal.

## Testing

```bash
./mvnw clean verify           # build + tests + cobertura (gate >= 80%)
```

Reporte de cobertura: `target/site/jacoco/index.html`.

| Tipo | Ubicación | Herramienta |
|------|-----------|-------------|
| Unit (casos de uso) | `application/` | JUnit5 + Mockito |
| Dominio | `domain/model/` | JUnit5 |
| Adaptador salida | `infrastructure/adapter/out/` | MockWebServer |
| Integración web | `infrastructure/adapter/in/web/` | @WebMvcTest + MockMvc |

## CI/CD

`.github/workflows/ci.yml` en cada `push`/`pull_request` a `develop`/`master`:
build + test + gate de cobertura, y publica como artefactos el `.jar`, el reporte JaCoCo y los resultados de tests.

## Estado

Ver `TRACKING.md` para el detalle por fases.

---
_Desarrollado con asistencia de Kiro AI (modelo Auto) bajo la dirección de jachqaz._

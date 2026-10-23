<!--
  Copyright (c) 2026 Johan Castano (jachqaz). Topaz Products API.
  All rights reserved.
-->

# Topaz Products API — Backend (Spring Boot · Arquitectura Hexagonal)

Microservicio **BFF** (Backend For Frontend) que consume la API pública
[DummyJSON](https://dummyjson.com/) y expone los productos al frontend React Native **Topaz**.
Mini-tienda de productos (prueba técnica fullstack).

## Modelo de trabajo: orquestación humano + agente

Este backend se construyó bajo un modelo de **orquestación dirigida de IA**, donde un
profesional define la ingeniería y un agente de IA la ejecuta con evidencia y trazabilidad.
No se trata de generación automática sin control, sino de un proceso de desarrollo
supervisado en el que cada decisión técnica es dirigida, verificada y documentada:

- **Director de orquesta — Johan Castano (`jachqaz`).** Dueño de la arquitectura
  y las decisiones técnicas: impone la **arquitectura hexagonal** (dominio sin framework,
  puertos y adaptadores), fija el stack (Java 21 LTS, Spring Boot 3.4.x, WebClient a DummyJSON),
  los límites de calidad (JUnit 5 + Mockito, JaCoCo ≥ 80%, OpenAPI) y la política de commits
  (firmados, con preprocesado, sin push automático). Valida cada entrega contra evidencia real
  (`mvnw verify`, cobertura, respuestas HTTP).
- **Agente ejecutor — Kiro (modelo Auto).** Traduce la intención del director en trabajo
  accionable: implementa las capas, escribe los tests, mide la cobertura y verifica el contrato
  real contra DummyJSON, reportando con datos. Trabaja guiado, dentro de las decisiones del director.

El valor reside en la **dirección técnica especializada**: el diseño de la arquitectura,
la delimitación del alcance, la exigencia de cobertura y análisis de causa raíz, y la
validación del resultado. El agente acelera la ejecución; el criterio de ingeniería y la
responsabilidad del resultado corresponden al director. La trazabilidad de cada decisión
se documenta en `TRACKING.md`.

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

- **JDK 21** (Temurin/Zulu) con `JAVA_HOME` apuntando a él. Verifícalo con `java -version`.
- No necesitas instalar Maven: el proyecto incluye el **Maven Wrapper** (`mvnw` / `mvnw.cmd`).

## Clonado y ejecución desde cero

```bash
git clone https://github.com/jachqaz/Topaz-Test-JavaSpringBoot.git
cd Topaz-Test-JavaSpringBoot

# Unix / macOS:
./mvnw spring-boot:run          # arranca en http://localhost:8080

# Windows (PowerShell / CMD):
mvnw.cmd spring-boot:run
```

El backend queda escuchando en el **puerto 8080** (fijo). Compruébalo:

```bash
curl http://localhost:8080/api/products       # lista de 30 productos (JSON)
curl http://localhost:8080/api/products/1      # detalle del producto 1
curl http://localhost:8080/actuator/health     # {"status":"UP"}
```

Documentación OpenAPI interactiva: `http://localhost:8080/swagger-ui.html`

> Déjalo corriendo mientras usas el frontend: el front espera el back en `localhost:8080`.

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
_Desarrollado con asistencia de Kiro AI (modelo Auto) bajo la dirección de Johan Castano (jachqaz)._

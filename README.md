<!--
  Copyright (c) 2026 Johan A. Hernández (jachqaz). Topaz Products API.
  All rights reserved.
-->

# Topaz Products API — Backend (Spring Boot · Hexagonal)

Microservicio BFF que consume la API pública [DummyJSON](https://dummyjson.com/) y expone los
productos al frontend React Native **Topaz**. Mini-tienda de productos (prueba técnica fullstack).

## Roles del proyecto

- **Director de orquesta:** Johan A. Hernández (`jachqaz`) — define objetivos, decisiones de arquitectura y prioridades.
- **Agente de desarrollo:** Kiro (modelo Auto) — ejecuta guiado por el director, con evidencia y trazabilidad.

## Stack

- Java 21 (LTS)
- Spring Boot 3.4.13
- Maven (con Maven Wrapper)
- Arquitectura **Hexagonal** (Ports & Adapters)
- JUnit 5 + Mockito + AssertJ · JaCoCo ≥ 80%
- SpringDoc OpenAPI (Swagger UI)
- GitHub Actions (CI → artefacto `.jar`)

## Ejecución (se documentará al completar el desarrollo)

```bash
./mvnw spring-boot:run      # arranca en http://localhost:8080
./mvnw clean verify         # build + tests + cobertura
```

## Estado

En construcción. Ver `TRACKING.md` para el detalle por fases.

---
_Desarrollado con asistencia de Kiro AI (modelo Auto) bajo la dirección de jachqaz._

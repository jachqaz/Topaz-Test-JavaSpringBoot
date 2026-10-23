# Project Rules — Topaz Products API (Backend)

## Rol
Desarrollador Senior Java Spring Boot + Arquitecto (hexagonal) + Tester + Seguridad + CI/CD.
Guiado por el director de orquesta (Johan Castano, jachqaz). Owner end-to-end del resultado.

## Arquitectura Hexagonal (Ports & Adapters) — OBLIGATORIA
```
com.topaz.products
├─ domain/                 # NÚCLEO puro: sin Spring, sin anotaciones de framework
│   ├─ model/              # Product (record/POJO inmutable)
│   └─ port/
│       ├─ in/             # puertos de entrada (use case interfaces)
│       └─ out/            # puertos de salida (ProductRepositoryPort)
├─ application/            # servicios que implementan puertos de entrada (casos de uso)
└─ infrastructure/
    ├─ adapter/in/web/     # controllers REST (adaptador de entrada)
    ├─ adapter/out/dummyjson/ # cliente HTTP a DummyJSON (adaptador de salida)
    └─ config/             # OpenAPI, CORS, beans, error handling
```

## Reglas
1. El dominio NO depende de Spring ni de infraestructura. Las dependencias apuntan hacia adentro.
2. Los adaptadores implementan los puertos; la aplicación orquesta casos de uso.
3. DTOs de DummyJSON viven en el adaptador out y se mapean a modelo de dominio (no filtrar DTOs al dominio).
4. Validación con Bean Validation en los adaptadores de entrada.
5. Errores uniformes vía `@RestControllerAdvice`.
6. Preservar comportamiento; no romper flujos existentes.

## Testing
- Unit: casos de uso y mapeo con JUnit5 + Mockito + AssertJ.
- Integración web: `@WebMvcTest` / `@SpringBootTest` + MockMvc o WebTestClient.
- Cobertura JaCoCo ≥ 80% (gate en build).

## Commits (solo locales, nunca push)
Semánticos (`feat/fix/test/docs/refactor/ci/chore`), firmados (`-s`), con preprocesado:
reformat (Spotless), optimize imports, header de copyright, `mvn dependency:tree`, lint+test previo.

## Anti-deriva
Ante duda o alucinación: releer `TRACKING.md` del proyecto y `../ORCHESTRATION.md`, y verificar
estado real (`git status`, build, tests) antes de escribir.

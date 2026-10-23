# Testing Standards — Topaz Products API

## Objetivo
Cobertura JaCoCo ≥ 80% (gate en build). Probar comportamiento, no implementación.

## Tipos
| Tipo | Herramienta | Alcance |
|------|-------------|---------|
| Unit | JUnit5 + Mockito + AssertJ | casos de uso, mapeo DTO→dominio |
| Integración web | `@SpringBootTest`/`@WebMvcTest` + MockMvc o WebTestClient | controllers, serialización, errores |
| Adaptador out | MockWebServer / stub | cliente DummyJSON sin red real |

## Reglas
- Patrón AAA (Arrange-Act-Assert).
- Nada de llamadas de red reales en tests: mockear el puerto out o usar MockWebServer.
- Nombres descriptivos: `metodo_condicion_resultadoEsperado`.
- Un assert lógico por caso siempre que sea posible.

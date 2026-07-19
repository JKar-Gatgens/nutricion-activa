# Critique baseline — Sprint 4 (rediseño identidad visual)

> **Fecha:** 19 de julio de 2026 · **Método:** dual-agent (A: revisión de diseño · B: detector determinístico + evidencia de navegador en vivo)
> **Target:** `/programa` (`src/main/resources/templates/programa.html`, HU-12) + banner `na-programa-banner` en `index.html`
> **Propósito:** línea base ANTES del rediseño completo del Sprint 4 (HU-09a, temática gym — D-18). La dirección visual actual se reemplaza entera: los hallazgos de diseño de este reporte NO se corrigen sobre la estética saliente (sería trabajo doble), se resuelven de raíz en el MASTER v2 y su rollout. Los hallazgos de contenido/confianza trascienden la estética y se atienden dentro del rollout o cuando llegue el material del PO.
> El snapshot íntegro del critique vive en `.impeccable/critique/2026-07-19T14-16-20Z__src-main-resources-templates-programa-html.md`; re-correr `/impeccable critique` tras el rediseño y comparar contra este score es la medida del resultado.

## Score general (línea base a batir)

**Design Health Score: 25/40 (Aceptable)** — página honesta y bien construida sobre un sistema sólido, que vende el envoltorio y esconde sus dos únicas pruebas disponibles: la persona (Andrés no aparece en `/programa`) y la firma de marca (el aro no se usa ni una vez en la página del producto estrella).

| # | Heurística | Puntaje | Issue clave |
|---|-----------|:---:|-----------|
| 1 | Visibilidad del estado | 2 | El navbar nunca marca la página actual (`.nav-link.active::after` existe en CSS, ningún template lo aplica) |
| 2 | Sistema ↔ mundo real | 3 | "Módulo 1/2/3" sin nombre real es jerga estructural abstracta |
| 3 | Control y libertad | 3 | Sólida (anclas con scroll-padding, noopener, nav completo) |
| 4 | Consistencia y estándares | 2 | H1 tipográficamente menor que los H2; dos botones verdes primarios con acciones distintas |
| 5 | Prevención de errores | 3 | WhatsApp pre-llenado evita el "hola" vacío |
| 6 | Reconocimiento vs. recuerdo | 3 | El precio llega con su contexto restatado |
| 7 | Flexibilidad y eficiencia | 3 | FAB + CTA por sección + ancla; falta contexto de moneda para el extranjero |
| 8 | Estética y minimalismo | 2 | Una sección entera (módulos) gasta una pantalla en información casi nula; "90 días · 3 módulos" se repite ×4 |
| 9 | Recuperación de errores | 3 | Sin formularios; degradación sin JS bien resuelta |
| 10 | Ayuda y documentación | 1 | Cero respuesta a preguntas obvias de un producto de ₡105.000; la FAQ del MASTER §7.8 nunca se construyó |

**Veredicto anti-patrones:** no es slop flagrante — las defensas del sistema funcionan (Barra de Impulso en vez de eyebrows, iconos Lucide por contenido, copy de datos reales del PO, la cifra 90 es Cifra Protagonista legítima). Pero el tercio medio es calidad plantilla: tríada interrogativa intercambiable, grid de 6 ítems icono-círculo (la anti-referencia "grid de features" de PRODUCT.md), y 3 cards de módulo idénticas que le dan a la no-información el tratamiento visual de información. Aprueba raspando **por sistema, no por composición** — exactamente el diagnóstico que justifica el rediseño.

**Detector determinístico:** cero hallazgos en los 3 templates; 1 advisory en `styles.css:585` = falso positivo verificado (el `calc(var(--na-radius-md) - 4px)` del radio concéntrico del marco de foto).

**Lo que funciona y el MASTER v2 debe conservar:** (1) la Cifra Protagonista bien entendida — el 90 como hecho central del producto, no métrica inflada; (2) "sin prueba, sin promesa" llevado al markup (th:if sin placeholders ni márgenes fantasma); (3) el banner del index como única sección Verde Tinte que presenta sin canibalizar, delegando la venta a la página dedicada.

---

## 1. Hallazgos de diseño (se resuelven de raíz en el rediseño)

Insumo directo para los briefs de dirección y el MASTER v2. No corregir sobre la estética saliente.

- **[P1] Inversión de jerarquía tipográfica: el H1 es más chico que los H2.** `programa.html` — el H1 "Fuerte y Definido" no hereda `.na-hero h1` (su clase `na-programa-hero` es un hook vacío) y queda en ~2.5rem Bootstrap contra 3rem de los H2 de sección: "¿Cómo funciona?" se compone más grande que el nombre del programa. El MASTER v2 debe definir el rol Display del hero de página interior, no solo del index.
- **[P1] Los módulos como cards clónicas — el tratamiento, no el contenido.** Tres cards completas (sombra, círculo degradado, grid, stagger) para comunicar un solo hecho que el hero ya dijo. Direcciones candidatas ya identificadas: banda-timeline horizontal (1→2→3 conectados — la secuencia temporal dibujada como secuencia) o cuenta regresiva ("Día 1–30 / 31–60 / 61–90" sobre una barra de progreso con el degradado del aro, estrenando la Barra de Impulso como metáfora del producto). El detalle real de los módulos es asunto de contenido (ver sección 2), pero el tratamiento de la estructura es decisión de diseño del rediseño.
- **El aro (firma del sistema) está ausente en /programa** — candidato natural: el 90 compuesto con el aro como momento wow del rediseño. La única firma decorativa del sistema (El Aro, DESIGN.md) no aparece ni una vez en la página del producto estrella; el patrón de composición ya existe (`na-hero-visual` del index).
- **[P2] Barra de Impulso huérfana en los cierres (bug visual real, confirmado en navegador).** En la franja negra "Empezá tus 90 días" la barra queda pegada al margen izquierdo con el título centrado: `.na-cierre` centra con `text-align` pero el `::after` (block, 48px) solo se centra bajo `.text-center`. **Afecta también al cierre del index** — verificar que el sistema de kickers del MASTER v2 no herede el bug.
- **[P2] Conflicto de CTA primario en páginas interiores.** El navbar sticky mantiene "Agendar cita" (verde → `/agendar`) sobre una página cuyo CTA primario es WhatsApp y cuyo producto NO existe en `/agendar`: dos botones verdes idénticos, destinos distintos. El sistema de navegación del rediseño debe definir el comportamiento del CTA del navbar por contexto de página.
- **Navbar sin estado activo.** El estilo `.nav-link.active::after` existe huérfano en el CSS; ningún template lo aplica. Definir en el rollout.
- **Chunking de "¿Qué incluye?": 6 ítems planos** superan el límite de ≤4 sin agrupación ni orden aparente. Agrupar 3+3 (p. ej. "Tu plan" / "Tu acompañamiento") resuelve sin tocar contenido — decisión de layout para el rediseño.
- **[P3] El CTA de la card de precio es `btn-primary` sin `btn-lg`**: el botón más chico de la página justo en el momento del precio.
- **[P3] "90 días · 3 módulos" se repite cuatro veces** (hero, intro de módulos, card de precio, cierre). El rediseño debe decidir dónde vive la spec una sola vez con peso, y dónde se habla al usuario.
- **Evidencia móvil pendiente:** a 375px (análisis de código; la captura móvil no fue posible) la cifra 90 cae bajo el fold después de los CTAs y el precio queda a ~4 pantallas sin señal previa. El rediseño mobile-first debe decidir qué gancho abre en móvil.

## 2. Hallazgos de contenido/confianza (trascienden la estética)

Se atienden dentro del rollout del Sprint 4 o cuando llegue el material del PO — sobreviven a cualquier dirección visual.

- **[P1] Andrés no existe en `/programa`.** Un producto de ₡105.000 de acompañamiento personal de 90 días se vende sin ninguna persona: ni foto, ni nombre (salvo escondido en el prefill de WhatsApp), ni enlace a `/#sobre-mi`. Sin testimonios (D-11, restricción legítima), la cara de Andrés es **la única prueba autorizada disponible** — y es material 100% existente (`andres.webp`). La escalera de creencias de PRODUCT.md tiene "confío en Andrés" como peldaño 3; la página lo omite entero. Personas: para "Diego" (24, futbolista, llega de Instagram) la señal de "serio" en fitness es un humano, no tipografía — sin persona ni comparación de precio, su decisión de segundos termina en "lo dejo para después".
- **[P1→contenido] El valle de los módulos vacíos depende del material del PO (D-19).** Mientras el PO no produzca el detalle de los módulos (~30% desarrollado, ~3h/semana), cualquier tratamiento visual administra un vacío. El tratamiento se decide en el rediseño (sección 1); el vacío solo lo llena el contenido real. Cuando lleguen las descripciones (HU-13), activarlas es solo datos en `ProgramaService`. Mientras tanto, un "cómo arranca" de 3 pasos con hechos ya publicables (escribís por WhatsApp → evaluación y plan personalizado → seguimiento con ajustes) es contenido legítimo hoy que responde la promesa del título "¿Cómo funciona?".
- **[P2] Reaseguro del camino a WhatsApp.** El momento de mayor fricción (escribirle a un desconocido) no tiene reaseguro alrededor de ningún CTA: quién responde, cuándo, si compromete. Los datos existen y están publicados ("Lun–Sáb 8am–6pm hora Costa Rica", trustbar) — microcopy tipo "Te responde Andrés · Lun–Sáb 8am–6pm · Sin compromiso". También: nadie setea la expectativa de qué pasa después de enviar el mensaje.
- **[P2] Contexto de precio.** ₡105 000 sin la nota "colones costarricenses (CRC)" que el index sí tiene, para una población que la propia página define como "de Costa Rica o el extranjero"; y sin ancla de valor: **₡35.000 por módulo de 30 días** (aritmética del dato real del PO, ₡105.000 ÷ 3) hace la cifra legible sin inventar nada. Referencia cruzada posible con el precio de consulta suelta (₡30.000, `/#servicios`) como comparación de valor.
- **[P3] El cierre restata en vez de culminar.** El lead del cierre es `programa.descripcion` (ficha de catálogo, cuarta repetición de la spec). `mensajeVenta` ("Cambio físico en 90 días sin dietas restrictivas, con planes ajustados a tu rutina diaria") es promesa en segunda persona y cierra mejor — el titular "Empezá tus 90 días" ya es correcto.
- **Sin respuestas a las preguntas de compra** (heurística 10, score 1): cómo se paga, cómo funciona online vs. presencial, qué pasa tras escribir. La FAQ planificada en MASTER §7.8 nunca se construyó; requiere respuestas reales del PO.
- **Emoji 💪 del prefill de WhatsApp**: inconsistencia de voz ya anotada en `docs/pendientes.md` — validar con el PO en la demo.

## Anexo — insumos de dirección para los briefs del Sprint 4

**Preguntas provocadoras del critique** (para las variaciones de dirección que verá el PO):
1. ¿Y si los módulos se vendieran como cuenta regresiva en vez de como catálogo — la misma no-información convertida en promesa temporal visual?
2. ¿Por qué el 90 no vive dentro del aro? (ver hallazgo del aro, sección 1)
3. ¿Qué compra alguien que paga ₡105.000: un programa o a Andrés? Si la respuesta es Andrés, ¿cómo se vería `/programa` con su foto como hero y el 90 como acento — en vez de al revés?

**Red flags por persona** (resumen; detalle en el snapshot):
- **Jordan (primerizo):** dos botones verdes sin camino claro; "Ver cómo funciona" no agrega nada; cero expectativa post-WhatsApp.
- **Riley (stress tester):** cards clónicas = "placeholder olvidado" en 2 segundos; H1 menor que H2 visible a ojo; navbar sin ubicación. Resiste: anclas, sin-JS, reduced-motion, contraste.
- **Casey (móvil, una mano):** el 90 bajo el fold en 375px; precio a ~4 pantallas. A favor: FAB al pulgar, targets ≥44px.
- **Diego (proyecto):** sin humano ni olor a deporte, módulos vacíos = patrón del programa-humo de Instagram; abandona en segundos.

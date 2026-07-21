---
target: /programa (HU-12) + banner del index
total_score: 25
p0_count: 0
p1_count: 3
timestamp: 2026-07-19T14-16-20Z
slug: src-main-resources-templates-programa-html
---
Method: dual-agent (A: revisión de diseño · B: detector + evidencia de navegador)
Target: /programa (src/main/resources/templates/programa.html) + banner na-programa-banner en index.html

# Design Health Score — 25/40 (Aceptable)

| # | Heurística | Puntaje | Issue clave |
|---|-----------|-------|-----------|
| 1 | Visibilidad del estado | 2 | El navbar nunca marca la página actual: `.nav-link.active::after` existe en CSS (styles.css:246) pero ningún template lo aplica |
| 2 | Sistema ↔ mundo real | 3 | "Módulo 1/2/3" sin nombre real es jerga estructural abstracta |
| 3 | Control y libertad | 3 | n/a — anclas, noopener, nav completo; sólida |
| 4 | Consistencia y estándares | 2 | H1 tipográficamente menor que los H2; dos botones verdes primarios con acciones distintas (navbar vs. página) |
| 5 | Prevención de errores | 3 | n/a — WhatsApp pre-llenado evita el "hola" vacío |
| 6 | Reconocimiento vs. recuerdo | 3 | n/a — el precio llega con contexto restatado |
| 7 | Flexibilidad y eficiencia | 3 | FAB + CTA por sección + ancla; falta contexto de moneda para el extranjero |
| 8 | Estética y minimalismo | 2 | Una sección entera (módulos) gasta una pantalla en información casi nula; "90 días · 3 módulos" se repite ×4 |
| 9 | Recuperación de errores | 3 | n/a — sin formularios; degradación sin JS bien resuelta |
| 10 | Ayuda y documentación | 1 | Cero respuesta a preguntas obvias de un producto de ₡105.000 (cómo pago, qué pasa tras escribir); la FAQ del MASTER §7.8 no existe |
| **Total** | | **25/40** | **Aceptable — bien construida, con huecos de contenido y jerarquía** |

# Veredicto de anti-patrones

**LLM (Assessment A):** no es slop flagrante — el sistema tiene defensas (Barra de Impulso en vez de eyebrows, iconos Lucide elegidos por contenido, copy de datos reales del PO) y la página las respeta. Pero el tercio medio es calidad plantilla: la tríada interrogativa "¿Cómo funciona?/¿Qué incluye?/¿Para quién es?" es intercambiable, el grid de 6 ítems icono-círculo es el "grid de features" que PRODUCT.md declara anti-referencia, y las 3 cards de módulo idénticas son el tell más fuerte — no por la restricción de datos (D-19, legítima) sino por darle a la no-información el tratamiento visual de información. La cifra 90 se salva: es Cifra Protagonista legítima (duración real del producto, no métrica inflada). Test de segunda orden: tapando el logo, el hero y el grid podrían ser de cualquier nutricionista con plantilla decente; salvan el voseo, los colones, el botón negro-sobre-verde. Aprueba raspando por sistema, no por composición.

**Detector determinístico (Assessment B):** cero hallazgos en los 3 templates (exit 0). Complementario sobre styles.css: 1 advisory (`design-system-radius`, línea 585) — **falso positivo verificado**: es `calc(var(--na-radius-md) - 4px)`, el radio interior concéntrico correcto del marco de foto, derivado del token.

**Evidencia visual (navegador, desktop 1920px):** server Spring ya corriendo; capturas estables verificadas. Confirmado: cards de módulos efectivamente vacías y redundantes con su propio texto intro; sin overflow horizontal; sin errores de consola. **Hallazgo visual nuevo (real, consistente en 2 capturas): en el cierre negro "Empezá tus 90 días", la Barra de Impulso queda pegada al margen izquierdo mientras el título está centrado** — huérfana. Causa: `.na-cierre` centra con text-align pero la barra ::after (display:block, 48px) solo se centra bajo `.text-center`; el bug afecta también al cierre del index. Overlay detect.js no viable (Thymeleaf server-rendered, no live-server). Vista móvil 375px sin evidencia: la ventana de Chrome no tomó el resize; solo consta que a 1920px no hay overflow.

# Impresión general

Página honesta y bien construida sobre un sistema sólido, que vende un envoltorio y esconde sus dos únicas pruebas disponibles: la persona (Andrés no aparece) y la firma de marca (el aro no se usa ni una vez en la página del producto estrella). El mayor issue no es estético sino de confianza: el valle de los módulos vacíos en el punto de máxima atención.

# Qué funciona

1. **La Cifra Protagonista bien entendida**: el 90 es el hecho central del producto compuesto exactamente según la regla del sistema (condensada 700, tabular-nums, label con tracking correcto). Convierte una spec en identidad sin prometer nada indebido.
2. **"Sin prueba, sin promesa" llevado al markup**: th:if sin placeholders ni márgenes fantasma; iconos con default seguro para ítems futuros. La honestidad implementada, no solo declarada.
3. **El banner del index hace su trabajo**: única sección Verde Tinte (jerarquía de producto estrella con tokens existentes), compacta, un CTA, delega la venta. El ritmo de bloques de /programa es fiel al MASTER §6.

# Issues prioritarios

- **[P1] Inversión de jerarquía: el H1 es más chico que los H2.** `programa.html:27` — el H1 no hereda `.na-hero h1` (la clase es `na-programa-hero`, hook vacío) y queda en ~2.5rem Bootstrap vs. 3rem de los H2. "¿Cómo funciona?" se compone más grande que "Fuerte y Definido". **Fix**: regla `.na-programa-hero h1` con `--na-fs-display` + uppercase + tracking 0.01em. → /impeccable typeset
- **[P1] "¿Cómo funciona?" no responde cómo funciona.** Tres cards idénticas que repiten lo que el hero ya dijo; en un producto de ₡105.000 comunican "esto no existe" (Riley: "placeholder que se les olvidó llenar"; Diego: patrón del programa-humo de Instagram). **Fix**: comprimir los módulos a una banda-timeline (la secuencia dibujada como secuencia, estrenando la Barra de Impulso como metáfora) + 3 pasos de "cómo arranca" con hechos ya publicables (escribís → evaluación y plan → seguimiento con ajustes, patrón MASTER §7.5). No choca con D-19: es tratamiento, no contenido inventado. → /impeccable layout
- **[P1] Andrés no existe en /programa.** Sin testimonios (restricción legítima), la cara de Andrés es la única prueba autorizada disponible — y la página del producto de acompañamiento personal no muestra ninguna persona. **Fix**: franja compacta antes del precio con `na-photo-frame` + `andres.webp` (ya autorizados) + 1-2 líneas + enlace a /#sobre-mi. → /impeccable layout
- **[P2] Conflicto de CTA primario + fricción sin reaseguro.** Navbar "Agendar cita" (verde → /agendar, donde el programa NO está) compite con "Quiero más información" (verde → WhatsApp); y el momento de mayor fricción (escribirle a un desconocido) no tiene reaseguro. **Fix**: degradar el CTA del navbar a outline en /programa + microcopy "Te responde Andrés · Lun–Sáb 8am–6pm" (datos ya publicados). → /impeccable clarify
- **[P2] Precio sin contexto + Barra de Impulso huérfana en el cierre.** ₡105 000 sin nota CRC (el index sí la tiene) ni ancla de valor (₡35 000/módulo); y el bug visual del cierre (barra a la izquierda, título centrado — también en el index). **Fix**: nota de moneda + desglose por módulo; `margin-inline: auto` para la barra en `.na-cierre`. → /impeccable polish

# Red flags por persona

**Jordan (primerizo):** dos botones verdes sin saber cuál es "el" camino; si toca el del navbar aterriza donde el programa no está; "Ver cómo funciona" lo deja sabiendo lo mismo; cero expectativa de qué pasa tras el WhatsApp.
**Riley (stress tester):** detecta las cards clónicas en 2s y concluye "placeholder olvidado"; nota el H1 menor que los H2 a ojo; el navbar nunca indica dónde está. Resiste: anclas con scroll-padding, sin JS todo visible, reduced-motion cubierto, contraste del círculo degradado pasa.
**Casey (móvil, una mano):** a 375px la cifra 90 cae bajo el fold después de los CTAs; el precio queda a ~4 pantallas de scroll sin señal previa. A favor: FAB al pulgar, targets ≥44px.
**Diego (24, futbolista, Instagram, ¿vale ₡105.000?):** ninguna persona ni nada que huela a deporte — su señal de "serio" es un humano, no tipografía; los módulos vacíos son su "¿esto existe de verdad?"; sin comparación de precio ni saber quién atiende, su decisión de segundos termina en "lo dejo para después".

# Observaciones menores

- El CTA de la card de precio es `btn-primary` sin `btn-lg`: más chico que los otros dos CTAs justo en el momento del precio.
- "¿Qué incluye?" con 6 ítems planos falla el chunking ≤4: agrupar 3+3 ("Tu plan" / "Tu acompañamiento") resolvería sin tocar contenido.
- El cierre usa `programa.descripcion` (ficha de catálogo) como lead — cuarta repetición de la spec; `mensajeVenta` es promesa en segunda persona y cerraría mejor.
- `aria-label` del banner duplica el h2 interno (redundante, no dañino).
- El emoji 💪 del prefill fija el tono de la conversación — ya anotado en pendientes.md para validar con el PO.
- Falso positivo del detector en styles.css:585 (calc de radio concéntrico) — ya conocido.

# Preguntas provocadoras

1. ¿Y si los módulos vacíos se vendieran como cuenta regresiva ("Día 1–30 / 31–60 / 61–90" sobre una barra de progreso con el degradado del aro) en vez de como catálogo — la misma no-información convertida en promesa temporal?
2. ¿Por qué el 90 no vive dentro del aro? El sistema tiene UNA firma decorativa y la página del producto estrella no la usa ni una vez. ¿Se guarda para el sprint gym o se olvidó?
3. ¿Qué compra alguien que paga ₡105.000: un programa o a Andrés? Si la respuesta es Andrés, ¿cómo se vería /programa con su foto como hero y el 90 como acento — en vez de al revés?

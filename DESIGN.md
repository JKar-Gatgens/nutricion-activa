---
name: Nutrición Activa
description: Landing de nutrición deportiva online — energía atlética por bloques alrededor del aro de la marca
colors:
  verde-arranque: "#00C050"
  verde-teal: "#00A870"
  verde-profundo: "#007D3C"
  verde-tinte: "#E6F9EE"
  negro-marca: "#0A0A0A"
  gris-cuerpo: "#3D4440"
  gris-secundario: "#6B736E"
  gris-borde: "#DEE4E0"
  gris-superficie: "#F4F7F5"
  blanco: "#FFFFFF"
  verde-whatsapp: "#25D366"
  rojo-error: "#D93A3A"
  ambar-aviso: "#B45309"
  ambar-tinte: "#FFF3CD"
  ambar-tinta: "#7A4A08"
typography:
  display:
    fontFamily: "Barlow Condensed, Arial Narrow, sans-serif"
    fontSize: "clamp(2.5rem, 6vw, 4.5rem)"
    fontWeight: 700
    lineHeight: 1.15
  headline:
    fontFamily: "Barlow Condensed, Arial Narrow, sans-serif"
    fontSize: "clamp(2rem, 4vw, 3rem)"
    fontWeight: 700
    lineHeight: 1.15
  title:
    fontFamily: "Barlow Condensed, Arial Narrow, sans-serif"
    fontSize: "1.5rem"
    fontWeight: 700
    lineHeight: 1.2
  price:
    fontFamily: "Barlow Condensed, Arial Narrow, sans-serif"
    fontSize: "clamp(2rem, 3.5vw, 2.75rem)"
    fontWeight: 700
    lineHeight: 1.1
  lead:
    fontFamily: "Barlow, Helvetica Neue, Arial, sans-serif"
    fontSize: "1.25rem"
    fontWeight: 400
    lineHeight: 1.6
  body:
    fontFamily: "Barlow, Helvetica Neue, Arial, sans-serif"
    fontSize: "1rem"
    fontWeight: 400
    lineHeight: 1.6
  label:
    fontFamily: "Barlow, Helvetica Neue, Arial, sans-serif"
    fontSize: "0.875rem"
    fontWeight: 600
    lineHeight: 1.6
rounded:
  sm: "8px"
  md: "16px"
  pill: "999px"
spacing:
  "1": "4px"
  "2": "8px"
  "3": "16px"
  "4": "24px"
  "5": "32px"
  "6": "48px"
  "7": "64px"
  "8": "96px"
components:
  button-primary:
    backgroundColor: "{colors.verde-arranque}"
    textColor: "{colors.negro-marca}"
    rounded: "{rounded.pill}"
    padding: "0.75rem 2rem"
  button-primary-hover:
    backgroundColor: "{colors.verde-profundo}"
    textColor: "{colors.blanco}"
  button-outline:
    backgroundColor: "transparent"
    textColor: "{colors.negro-marca}"
    rounded: "{rounded.pill}"
    padding: "0.75rem 2rem"
  card:
    backgroundColor: "{colors.blanco}"
    rounded: "{rounded.md}"
    padding: "{spacing.4}"
  chip-eyebrow:
    backgroundColor: "{colors.verde-tinte}"
    textColor: "{colors.verde-profundo}"
    rounded: "{rounded.pill}"
    padding: "4px 16px"
---

# Design System: Nutrición Activa

## 1. Overview

**Creative North Star: "El Aro de Impulso"**

Todo el sistema orbita el aro degradado del logo: momentum circular que rodea contenido clínicamente limpio. La energía es física — verde de arranque, negro de figura atlética, tipografía condensada que empuja — pero el rigor es de consulta profesional: contraste impecable, aire generoso, datos concretos en grande. La página se estructura en bloques de entrenamiento que alternan blanco → gris superficie → negro, y el aro reaparece como acento medido (hero, marco de la foto, borde de la card destacada): máximo 1–2 formas por sección, nunca decoración gratuita.

El sistema rechaza explícitamente lo que PRODUCT.md prohíbe: el fitness agresivo de suplementos (nada de antes/después gritones ni tipografía de gimnasio), la clínica fría (nada de tono hospitalario), el coach genérico de Instagram (nada de frases vacías ni stock sonriente) y la plantilla SaaS (nada de hero de métricas infladas). La promesa visual es la misma que la de negocio: hábitos sostenibles, no dietas de moda — energía que se puede sostener.

**Key Characteristics:**
- Bloques de alto contraste (blanco/gris/negro) con 64–96px de aire vertical
- Verde brillante reservado para acción y para brillar sobre negro
- Cifras protagonistas en Barlow Condensed gigante (precios, resultados)
- Movimiento corto y con propósito (≤300ms, solo transform/opacity)
- El aro degradado como única firma decorativa

## 2. Colors: la paleta del arranque

Paleta muestreada del logo real: un verde de salida sobre blanco y negro absolutos — comprometida, no tímida.

### Primary
- **Verde Arranque** (#00C050): el verde del disparo de salida. CTAs, acentos, iconos sobre fondos oscuros. Brilla al máximo sobre negro (8.6:1). **Prohibido como texto sobre blanco** (2.4:1).
- **Verde Teal** (#00A870): extremo frío del degradado del aro. Hovers de enlace, degradados, acentos secundarios.
- **Verde Profundo** (#007D3C): el único verde legible sobre blanco (5.2:1). Enlaces, texto verde, iconos sobre claro, hover del botón primario.
- **Verde Tinte** (#E6F9EE): verde al ~8%. Fondos de badge/chip, iconos con fondo suave.

### Neutral
- **Negro Marca** (#0A0A0A): titulares, secciones oscuras (trustbar, footer), texto sobre el botón primario.
- **Gris Cuerpo** (#3D4440): texto de cuerpo sobre blanco (matiz verdoso, 9.9:1).
- **Gris Secundario** (#6B736E): texto secundario y captions **solo sobre blanco** (4.6:1); sobre negro usar Gris Borde.
- **Gris Borde** (#DEE4E0): bordes, divisores, texto secundario sobre negro.
- **Gris Superficie** (#F4F7F5): fondo de secciones alternas y superficies muted.
- **Blanco** (#FFFFFF): fondo principal — el logo vive sobre blanco.

### Tertiary
- **Verde WhatsApp** (#25D366): exclusivo del botón de WhatsApp, sin recolorear jamás (es marca de terceros).
- **Rojo Error** (#D93A3A) y **Ámbar Aviso** (#B45309): estados de formulario y borde del marcador de pendiente.
- **Ámbar Tinte** (#FFF3CD) y **Ámbar Tinta** (#7A4A08): exclusivos del chip `.na-pending` (dato pendiente del PO, 6.8:1 entre sí). Utilidad de desarrollo — no aparece en producción con el catálogo confirmado.

### Named Rules
**La Regla del Verde Legible.** #00C050 nunca es texto sobre blanco. Texto verde = Verde Profundo, siempre. Sobre negro, el brillante sí pasa — ahí es donde debe lucirse.

**La Regla del Botón Negro.** El botón primario es verde con texto NEGRO (8.6:1). Texto blanco sobre Verde Arranque está prohibido; el blanco llega solo con el hover a Verde Profundo.

**La Regla del Aro.** El degradado de marca (135°, #00A870 → #00C050) vive en formas y acentos: aros, bordes de 4px, marcos. Jamás como fondo de texto corrido.

## 3. Typography

**Display Font:** Barlow Condensed (con Arial Narrow de respaldo)
**Body Font:** Barlow (con Helvetica Neue / Arial de respaldo)

**Character:** una sola superfamilia en dos anchos — la condensada empuja como dorsal de competencia, la regular acompaña con claridad clínica. El contraste viene del ancho y el peso, no de mezclar familias.

### Hierarchy
- **Display** (700, clamp(2.5rem, 6vw, 4.5rem), 1.15): H1 del hero, mayúsculas opcionales. Uno por página.
- **Headline** (700, clamp(2rem, 4vw, 3rem), 1.15): títulos de sección (h2).
- **Title** (700, 1.5rem, 1.2): títulos de card y plan (h3), con reserva de 2 líneas en grillas para mantener los rieles.
- **Price** (700, clamp(2rem, 3.5vw, 2.75rem), 1.1, `tabular-nums`, `nowrap`): cifras de precio y resultado. La cifra es el héroe del patrón before–after.
- **Lead** (400, 1.25rem, 1.6): subtítulo del hero y entradas de sección, en Gris Secundario sobre blanco.
- **Body** (400, 1rem, 1.6): cuerpo, nunca menor de 16px en móvil, máximo 65ch de línea.
- **Label** (600, 0.875rem): captions, metadatos de card, chips.

### Named Rules
**La Regla de la Cifra Protagonista.** Todo número que importa (precio, kg, semanas) se compone en Barlow Condensed 700 con `tabular-nums`, en tamaño Price o mayor. Los números no se esconden en el párrafo.

**La Regla del Tracking en Caps.** Todo texto en MAYÚSCULAS lleva tracking por token: `--na-tracking-caps` (0.06em) a tamaño title y mayores, `--na-tracking-caps-sm` (0.08em) a tamaño label. Única excepción, declarada: el display del hero (condensado gigante) usa 0.01em para no romper la condensación.

**La Barra de Impulso.** El kicker de sección del sistema es una barra píldora de 48×4px con el degradado del aro bajo el h2 (`.na-section-title::after`) — nunca un chip rotulador. El chip `.na-eyebrow` está reservado a dos usos: el eslogan del hero y el badge "Recomendado".

## 4. Elevation

**Elevación que responde.** Las superficies descansan casi planas — la profundidad estructural la da la alternancia de bloques (blanco/gris/negro), no las sombras. La sombra en reposo es un susurro ambiental; la elevación fuerte es exclusivamente una **respuesta a la interacción**, y al elevarse se tiñe de verde: la marca responde al tacto. Nada de sombras apiladas ni efectos 3D (anti-patrón declarado en MASTER §6).

### Shadow Vocabulary
- **Reposo** (`box-shadow: 0 4px 16px rgba(10, 10, 10, 0.08)`): cards y marcos en estado base.
- **Respuesta** (`box-shadow: 0 10px 28px rgba(0, 192, 80, 0.18)` + `translateY(-4px)`): hover de card, 250ms, solo en dispositivos con puntero real.
- **Flotante** (`box-shadow: 0 6px 20px rgba(37, 211, 102, 0.35)`): exclusiva del FAB de WhatsApp.

### Named Rules
**La Regla del Tinte al Tacto.** La sombra de interacción siempre lleva el verde de marca en su color. Una sombra gris que crece es genérica; una que verdea es Nutrición Activa.

## 5. Components

Táctiles y con impulso: todo lo interactivo se siente físico, como equipamiento deportivo — responde al press, empuja en el hover, nunca ornamenta.

### Buttons
- **Shape:** píldora completa (999px), peso 600.
- **Primary:** Verde Arranque + texto Negro Marca, padding 0.75rem 2rem (btn-lg: 0.875rem 2.5rem vía `--bs-btn-padding-*`).
- **Hover / Focus:** fondo a Verde Profundo + texto blanco + `translateY(-2px)` (150ms, gated con `hover:hover`); press `scale(0.97)`; focus ring de 3px.
- **Secondary:** outline Negro Marca, misma píldora; en hover invierte a negro sólido.

### Chips
- **Style:** Verde Tinte de fondo, texto Verde Profundo, píldora, 0.875rem 600 uppercase con tracking.
- **Uso:** eslogan del hero y badge "Recomendado". No es gramática de sección — ver Don'ts.

### Cards / Containers
- **Corner Style:** 16px.
- **Background:** blanco sobre secciones Gris Superficie.
- **Shadow Strategy:** Reposo → Respuesta (ver Elevation).
- **Border:** ninguno; la destacada lleva barra superior de 4px con el degradado del aro (nunca lateral).
- **Internal Padding:** 24px; contenido en rieles (badge-row fija, título a 2 líneas, precio anclado abajo con `margin-top: auto`, metadatos con reserva de 2 filas).

### Inputs / Fields (previstos para /agendar)
- **Style:** borde Gris Borde, radio 8px, labels siempre visibles (nunca solo placeholder).
- **Focus:** ring verde de 3px, sin eliminar outline.
- **Error:** Rojo Error bajo el campo con `role="alert"`.

### Navigation
- Navbar sticky blanca con borde inferior Gris Borde; enlaces Barlow 500 negro con subrayado animado izquierda→derecha (150ms, 2px Verde Arranque); CTA píldora siempre visible fuera del collapse; targets ≥44px.

### FAB de WhatsApp (componente firma)
- Fijo inferior derecha, 56×56, Verde WhatsApp intocable, icono oficial blanco, safe-area respetada, entrada discreta (fade + scale 0.9, nunca desde 0) con excepción de reduced-motion. El contenido cercano recibe respiro (~88px) para que nunca tape un CTA.

## 6. Do's and Don'ts

### Do:
- **Do** componer texto verde sobre blanco únicamente con Verde Profundo (#007D3C) — La Regla del Verde Legible.
- **Do** usar el degradado del aro como firma: una forma o borde por sección, máximo dos.
- **Do** animar solo `transform` y `opacity`, 150–300ms, con el ease-out fuerte (`cubic-bezier(0.16, 1, 0.3, 1)`), y respetar `prefers-reduced-motion` en CSS **y** JS.
- **Do** mostrar datos reales del PO (precios en colones, horario GMT-6, voseo); los datos que faltan llevan marcador `.na-pending`, jamás valores inventados.
- **Do** mantener los rieles de las grillas: alturas derivadas de tokens y líneas de texto (`em`), nunca píxeles mágicos.

### Don't:
- **Don't** — texto blanco sobre Verde Arranque, ni #00C050 como texto sobre blanco. Prohibido sin excepción.
- **Don't** — "fitness agresivo de suplementos": antes/después exagerados, promesas milagro, tipografía de gimnasio a gritos (anti-referencia de PRODUCT.md).
- **Don't** — "clínica fría" ni "coach genérico de Instagram": tono hospitalario, frases motivacionales vacías, stock sonriente (anti-referencias de PRODUCT.md).
- **Don't** — "plantilla SaaS genérica": hero de métricas infladas, grid de features intercambiable (anti-referencia de PRODUCT.md).
- **Don't** — chip eyebrow encima de **cada** sección: es andamiaje de IA, no voz. El chip pertenece al eslogan del hero y al badge "Recomendado"; los h2 de sección jerarquizan solos con la Barra de Impulso. (Deuda del audit resuelta el 2026-07-12 en /impeccable typeset.)
- **Don't** — recolorear el verde de WhatsApp, usar emojis como iconos (solo SVG stroke de un set), franjas laterales de color en cards, gradient text, glassmorphism, sombras apiladas o efectos 3D.
- **Don't** — inventar prueba social: sin material real del PO no hay testimonios, ni transformaciones, ni credenciales — "Sin prueba, sin promesa" (PRODUCT.md).

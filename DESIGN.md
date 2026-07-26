---
name: Nutrición Activa
description: Landing + agendamiento en territorio claro, "El programa Fuerte y Definido" en territorio oscuro — dos superficies, un mismo aro degradado como firma
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
  hairline-oscuro: "rgba(255, 255, 255, 0.08)"
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
  display-dark:
    fontFamily: "Barlow Condensed, Arial Narrow, sans-serif"
    fontSize: "clamp(3.5rem, 8vw, 6rem)"
    fontWeight: 700
    lineHeight: 1.15
  stat-hero:
    fontFamily: "Barlow Condensed, Arial Narrow, sans-serif"
    fontSize: "clamp(6rem, 16vw, 11rem)"
    fontWeight: 700
    lineHeight: 1
  stat:
    fontFamily: "Barlow Condensed, Arial Narrow, sans-serif"
    fontSize: "clamp(3rem, 8vw, 5rem)"
    fontWeight: 700
    lineHeight: 1
  stat-price:
    fontFamily: "Barlow Condensed, Arial Narrow, sans-serif"
    fontSize: "clamp(2.25rem, 6vw, 3.75rem)"
    fontWeight: 700
    lineHeight: 1
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
  button-outline-dark:
    backgroundColor: "transparent"
    textColor: "{colors.blanco}"
    rounded: "{rounded.pill}"
    padding: "0.75rem 2rem"
  card:
    backgroundColor: "{colors.blanco}"
    rounded: "{rounded.md}"
    padding: "{spacing.4}"
  card-oscura:
    backgroundColor: "{colors.negro-marca}"
    textColor: "{colors.gris-borde}"
    rounded: "{rounded.md}"
    padding: "{spacing.5} {spacing.4}"
  chip-eyebrow:
    backgroundColor: "{colors.verde-tinte}"
    textColor: "{colors.verde-profundo}"
    rounded: "{rounded.pill}"
    padding: "4px 16px"
---

# Design System: Nutrición Activa

## 1. Overview

**Creative North Star: "El Aro de Impulso"**

Todo el sistema orbita el aro degradado del logo: momentum circular que rodea contenido clínicamente limpio. La energía es física — verde de arranque, negro de figura atlética, tipografía condensada que empuja — pero el rigor es de consulta profesional: contraste impecable, aire generoso, datos concretos en grande. El sistema rechaza explícitamente lo que PRODUCT.md prohíbe: el fitness agresivo de suplementos (nada de antes/después gritones ni tipografía de gimnasio), la clínica fría (nada de tono hospitalario), el coach genérico de Instagram (nada de frases vacías ni stock sonriente) y la plantilla SaaS (nada de hero de métricas infladas). La promesa visual es la misma que la de negocio: hábitos sostenibles, no dietas de moda — energía que se puede sostener.

**Dos territorios, un mismo aro (Sprint 4).** El sistema vivió una migración completa a fondo oscuro ("Neón de Madrugada") y el PO, ya viendo el sitio entero así, la acotó a mitad de camino: **la landing y `/agendar` son territorio claro** — el "Energía atlética por bloques" original, sin polaridad invertida — y **`/programa` es territorio oscuro** — el gimnasio a las 5 de la mañana, negro dominante, el verde como única luz encendida. No son dos sistemas distintos: son la misma paleta primitiva, la misma tipografía, el mismo aro, resueltos con polaridad opuesta. El aro mismo cambia de contenido y de tratamiento según el territorio en el que aparece — es la pieza que más se nota entre ambos, y se documenta como Signature Component en la sección 7.

**Key Characteristics:**
- Territorio claro (landing, `/agendar`): bloques de alto contraste (blanco/gris/negro) con 64–96px de aire vertical; sombra ambiental que se tiñe de verde al responder al tacto.
- Territorio oscuro (`/programa`): negro como escenario por defecto, verde brillante como única fuente de luz (rim-light, glow contenido), superficies planas con hairline en vez de sombra.
- Cifras protagonistas en Barlow Condensed gigante en ambos territorios — más grandes aún en el oscuro, donde compiten con una foto de gimnasio a pantalla completa, no con una card.
- Movimiento corto y con propósito: 150–300ms para toda interacción cotidiana; hasta 1100ms solo en las secuencias cinematográficas declaradas del hero (Ascenso Escalonado → Encendido/Revelado del Aro).
- El aro degradado como única firma decorativa — nunca más de 1–2 formas por sección, nunca decoración gratuita.
- En un formulario, la claridad manda: `/agendar` y su confirmación son territorio claro sin excepción, aunque `/programa` sea oscuro (Salas Iluminadas).

## 2. Colors

Paleta muestreada del logo real: un verde de salida sobre blanco y negro absolutos — comprometida, no tímida. **Ningún valor hex cambia entre territorios** — lo que cambia es cuál color es fondo y cuál es acento (ver Elevation & Depth).

### Primary
- **Verde Arranque** (#00C050): el verde del disparo de salida. CTAs, acentos, iconos. Brilla al máximo sobre negro (8.6:1). **Prohibido como texto sobre blanco** (2.4:1) — ver Regla del Verde Legible.
- **Verde Teal** (#00A870): extremo frío del degradado del aro. Hovers de enlace, degradados, acentos secundarios.
- **Verde Profundo** (#007D3C): el único verde legible sobre blanco (5.2:1). Enlaces, texto verde, iconos sobre claro, hover del botón primario. Se hunde a ~2:1 sobre negro — ahí es ilegible.

### Neutral
- **Negro Marca** (#0A0A0A): titulares y footer en territorio claro; **fondo dominante de toda sección de `/programa`** en territorio oscuro.
- **Gris Cuerpo** (#3D4440): texto de cuerpo sobre blanco (matiz verdoso, 9.9:1).
- **Gris Secundario** (#6B736E): texto secundario y captions **solo sobre blanco** (4.6:1) — sobre negro se hunde, usar Gris Borde.
- **Gris Borde** (#DEE4E0): bordes y divisores en territorio claro; **cuerpo de texto sobre negro** en territorio oscuro (nunca Gris Secundario ahí).
- **Gris Superficie** (#F4F7F5): fondo de secciones alternas y superficies muted, territorio claro.
- **Blanco** (#FFFFFF): fondo principal en territorio claro; titulares y foco en territorio oscuro.
- **Verde Tinte** (#E6F9EE): verde al ~8%. Fondos de badge/chip, iconos con fondo suave — territorio claro únicamente.
- **Hairline Oscuro** (`rgba(255, 255, 255, 0.08)`): el sustituto de la sombra sobre negro — una sombra oscura no se ve sobre negro, un hairline blanco translúcido sí. Bordes y divisores de toda superficie plana de `/programa`.

### Tertiary
- **Verde WhatsApp** (#25D366): exclusivo del botón de WhatsApp, sin recolorear jamás (es marca de terceros).
- **Rojo Error** (#D93A3A) y **Ámbar Aviso** (#B45309): estados de formulario y borde del marcador de pendiente.
- **Ámbar Tinte** (#FFF3CD) y **Ámbar Tinta** (#7A4A08): exclusivos del chip `.na-pending` (dato pendiente del PO, 6.8:1 entre sí). Utilidad de desarrollo — no aparece en producción con el catálogo confirmado.

### Named Rules
**La Regla del Verde Legible (extendida a los dos territorios).** Sobre fondo claro, texto verde = Verde Profundo, siempre — el brillante nunca pasa AA sobre blanco (2.4:1). Sobre fondo oscuro, texto verde = Verde Arranque brillante, siempre — el profundo se hunde a ~2:1 sobre negro. Nunca al revés en ningún territorio.

**La Regla del Botón Negro.** El botón primario es verde con texto NEGRO (8.6:1), en cualquier territorio. Texto blanco sobre Verde Arranque está prohibido; el blanco llega solo con el hover a Verde Profundo.

**La Regla del Aro.** El degradado de marca (135°, #00A870 → #00C050) vive en formas y acentos: aros, bordes de 4px, marcos, glow. Jamás como fondo de texto corrido. El aro es polimórfico por diseño — enmarca el logo, una foto real o una cifra según la sección — pero sigue siendo un único degradado: no se crean variantes de color del gradiente.

**El Presupuesto de Glow.** Máximo **una** sombra verde (glow) por elemento, nunca dos apiladas. Cero `text-shadow` en cuerpo de texto. Cero efectos cyberpunk: nada de scanlines, glitch, ni parpadeo. **El glow es exclusivo de `/programa`** — territorio claro no usa glow verde en ningún punto; ahí, la elevación que responde al tacto se tiñe de verde con sombra normal, nunca con luz (ver Elevation & Depth).

## 3. Typography

**Display Font:** Barlow Condensed (con Arial Narrow de respaldo)
**Body Font:** Barlow (con Helvetica Neue / Arial de respaldo)

**Character:** una sola superfamilia en dos anchos — la condensada empuja como dorsal de competencia, la regular acompaña con claridad clínica. El contraste viene del ancho y el peso, no de mezclar familias.

### Hierarchy — territorio claro (landing, `/agendar`)
- **Display** (700, clamp(2.5rem, 6vw, 4.5rem), 1.15): H1 del hero de la landing, mayúsculas opcionales. Uno por página.
- **Headline** (700, clamp(2rem, 4vw, 3rem), 1.15): títulos de sección (h2), incluidos los de `/agendar`.
- **Title** (700, 1.5rem, 1.2): títulos de card y plan (h3), con reserva de 2 líneas en grillas para mantener los rieles.
- **Price** (700, clamp(2rem, 3.5vw, 2.75rem), 1.1, `tabular-nums`, `nowrap`): cifras de precio y resultado en la landing. La cifra es el héroe del patrón before–after.
- **Lead** (400, 1.25rem, 1.6): subtítulo del hero y entradas de sección, en Gris Secundario sobre blanco.
- **Body** (400, 1rem, 1.6): cuerpo, nunca menor de 16px en móvil, máximo 65ch de línea.
- **Label** (600, 0.875rem): captions, metadatos de card, chips.

### Hierarchy — territorio oscuro (`/programa`), escala póster
Mismas dos familias, un peldaño más grande: la Cifra Protagonista aquí no compite con precios en una card, compite con una foto de gimnasio a toda pantalla.
- **Display-dark** (700, clamp(3.5rem, 8vw, 6rem), 1.15, mayúsculas + tracking 0.01em): H1 del hero de `/programa`.
- **Stat-hero** (700, clamp(6rem, 16vw, 11rem), 1, `tabular-nums`): el "90" dentro del aro del hero de `/programa` — la cifra más grande del sitio.
- **Stat** (700, clamp(3rem, 8vw, 5rem), 1, `tabular-nums`): cifras del tablero "El programa, en números" (90 / 3 / 6).
- **Stat-price** (700, clamp(2.25rem, 6vw, 3.75rem), 1, `tabular-nums`, `nowrap`): variante del tablero para el precio completo (₡105 000) — más caracteres que 90/3/6, mismo peso visual, clamp más contenido.

### Named Rules
**La Regla de la Cifra Protagonista.** Todo número que importa (precio, kg, semanas, días) se compone en Barlow Condensed 700 con `tabular-nums`, en tamaño Price o mayor en territorio claro — en tamaño Stat o mayor en territorio oscuro. Los números no se esconden en el párrafo.

**La Regla del Tracking en Caps.** Todo texto en MAYÚSCULAS lleva tracking por token: 0.06em a tamaño title y mayores, 0.08em a tamaño label. Única excepción, declarada: el display del hero (condensado gigante, en cualquier territorio) usa 0.01em para no romper la condensación.

**La Barra de Impulso.** El kicker de sección del sistema es una barra píldora de 48×4px con el degradado del aro bajo el h2 (`.na-section-title::after`) — nunca un chip rotulador. El chip `.na-eyebrow` está reservado a dos usos: el eslogan del hero y el badge "Recomendado". *(No confundir con la Barra de Impulso Luminosa, un patrón de movimiento distinto — ver Components.)*

## 4. Layout

Sistema de 8px (compatible con los `$spacers` de Bootstrap), mobile-first desde 375px. Landing de marketing → respirada, extremos amplios: 64px de padding vertical de sección en móvil, 96px en desktop. `/agendar` y sus pasos usan la misma escala pero con bloques más contenidos (`max-width: 760px` por paso) — un formulario no necesita el mismo aire que una landing de marketing.

**Grid:** `.container` de Bootstrap (max-width estándar, 1320px en XXL); grillas responsivas propias con `repeat(auto-fit, minmax(...))` en vez de breakpoints manuales donde alcanza (selector de servicios de `/agendar`, tablero de números de `/programa`). Breakpoints de Bootstrap (576/768/992/1200/1400); el sitio usa muy pocos `@media` propios porque `clamp()` resuelve la mayoría de la escala fluida.

**El indicador de pasos (`/agendar`).** Layout de 3 columnas iguales (`flex: 1` cada una) conectadas por una línea continua — el único lugar del sitio donde el layout en sí mismo comunica progreso, no solo jerarquía.

## 5. Elevation & Depth

**Dos filosofías, una por territorio — nunca mezcladas en la misma sección.**

**Territorio claro (landing, `/agendar`): elevación que responde.** Las superficies descansan casi planas — la profundidad estructural la da la alternancia de bloques (blanco/gris/negro), no las sombras. La sombra en reposo es un susurro ambiental; la elevación fuerte es exclusivamente una **respuesta a la interacción**, y al elevarse se tiñe de verde: la marca responde al tacto. Nada de sombras apiladas ni efectos 3D.

**Territorio oscuro (`/programa`): planas y profundas, con hairline en vez de sombra.** Una sombra oscura no se ve sobre negro — así que la profundidad no viene de `box-shadow` gris, viene de un borde hairline (`rgba(255,255,255,0.08)`) que separa superficies planas. El verde dejó de ser "color de botón": es fuente de luz — glow contenido, rim-light, el degradado como único brillo. Nada de glass, texturas ni efectos 3D acá tampoco.

### Shadow Vocabulary — territorio claro
- **Reposo** (`box-shadow: 0 4px 16px rgba(10, 10, 10, 0.08)`): cards y marcos en estado base.
- **Respuesta** (`box-shadow: 0 10px 28px rgba(0, 192, 80, 0.18)` + `translateY(-4px)`): hover de card con tinte verde, 250ms, solo en dispositivos con puntero real.
- **Flotante** (`box-shadow: 0 6px 20px rgba(37, 211, 102, 0.35)`): exclusiva del FAB de WhatsApp.

### Glow Vocabulary — territorio oscuro (Presupuesto de Glow: máximo uno por elemento)
- **Glow CTA** (`0 0 32px rgba(0, 192, 80, 0.35)`): el botón primario de `/programa` — "el botón emite luz".
- **Glow Foto** (`0 0 48px rgba(0, 192, 80, 0.45)`): marcos de fotografía sobre fondo oscuro, si `/programa` los necesita.
- **Glow Aro** (`0 0 44px rgba(0, 192, 80, 0.45)`): el aro que enciende el "90" del hero de `/programa`.

### Named Rules
**La Regla del Tinte al Tacto.** La elevación de interacción siempre lleva el verde de marca. En territorio claro es una sombra que crece y se tiñe de verde (`Reposo → Respuesta`); en territorio oscuro es un glow que se enciende (nunca los dos a la vez). Una sombra gris que crece sin teñirse es genérica; una que verdea, en cualquiera de sus dos formas, es Nutrición Activa.

## 6. Shapes

**El radio como firma discreta:** `sm` (8px, inputs y badges), `md` (16px, cards y marcos de foto), `pill` (999px, botones, chips y el aro). El sitio no usa esquinas rectas en ningún componente interactivo.

**El aro degradado** es la única forma geométrica de acento del sistema — un anillo de 4px de grosor (`padding` sobre un círculo con `background: var(--na-gradient-brand)`) que enmarca contenido real: el logo por defecto, una foto en la landing, una cifra en `/programa`. Máximo 1–2 apariciones por sección.

**Marcos concéntricos:** cuando una foto vive dentro de un marco con padding (el aro del hero, el marco de "Sobre mí"), el radio interior siempre se calcula como `radio del marco − su propio padding` para que las dos curvas queden concéntricas — nunca un valor de radio suelto sin relación con el marco que lo contiene.

**Territorio oscuro: hairline, no borde grueso.** Las superficies planas de `/programa` (tablero, módulos, cierre) se delimitan con `border: 1px solid var(--na-hairline)` o `border-top` — 1px, translúcido, nunca un borde de color sólido ni una sombra.

**El indicador de pasos:** geometría de dots conectados (36px de diámetro, línea de 2px entre ellos) — la única forma "de proceso" del sistema, reservada a `/agendar` porque ahí sí hay una secuencia real de 3 pasos que el usuario necesita ubicar.

## 7. Components

Táctiles y con impulso: todo lo interactivo se siente físico, como equipamiento deportivo — responde al press, empuja en el hover, nunca ornamenta. Esto es igual de cierto en los dos territorios; lo que cambia es si esa respuesta se ve como sombra o como luz (ver Elevation & Depth).

### Buttons
- **Shape:** píldora completa (999px), peso 600.
- **Primary:** Verde Arranque + texto Negro Marca en cualquier territorio, padding 0.75rem 2rem (btn-lg: 0.875rem 2.5rem). En `/programa` suma el Glow CTA; en territorio claro, nunca.
- **Hover / Focus:** fondo a Verde Profundo + texto blanco + `translateY(-2px)` (150ms, gated con `hover:hover`); press `scale(0.97)`; focus ring de 3px sólido (Verde Profundo sobre claro, blanco sobre oscuro — el profundo rinde ~3.7:1 sobre negro, insuficiente).
- **Secondary:** outline Negro Marca sobre superficie clara, invierte a negro sólido en hover; **outline blanco** (`button-outline-dark`) sobre superficie oscura o sobre cualquier franja negra de la landing (el cierre, el hero de `/programa`) — el outline negro sería invisible ahí.

### Chips
- **Style:** Verde Tinte de fondo, texto Verde Profundo, píldora, 0.875rem 600 uppercase con tracking. Exclusivo de territorio claro — no tiene variante oscura, no se usa en `/programa`.
- **Uso:** eslogan del hero y badge "Recomendado". No es gramática de sección — ver Do's and Don'ts.

### Cards / Containers — territorio claro
- **Corner Style:** 16px.
- **Background:** blanco sobre secciones Gris Superficie.
- **Shadow Strategy:** Reposo → Respuesta (ver Elevation & Depth).
- **Border:** ninguno; la destacada lleva barra superior de 4px con el degradado del aro (nunca lateral).
- **Internal Padding:** 24px; contenido en rieles (badge-row fija, título a 2 líneas, precio anclado abajo con `margin-top: auto`, metadatos con reserva de 2 filas).

### Cards / Containers — territorio oscuro
- **Corner Style:** 16px (mismo radio que territorio claro — el sistema no cambia su escala de forma, solo su vocabulario de profundidad).
- **Background:** Negro Marca, texto Gris Borde.
- **Shadow Strategy:** ninguna — hairline (`1px solid rgba(255,255,255,0.08)`) en su lugar.
- **Ejemplo:** las cards de módulo de `/programa` (Día 1–30 / 31–60 / 61–90): número en círculo degradado (48px, eco del aro), nombre en blanco (el h3 global fuerza negro, invisible sobre la card oscura), días en Verde Arranque brillante uppercase.

### Inputs / Fields (`/agendar`, Sprint 2 HU-04 · estética actualizada Sprint 4 Bloque 6)
- **Style:** borde Gris Borde, radio 8px, labels siempre visibles (nunca solo placeholder). **Sin ningún tratamiento decorativo** — en un formulario, la claridad manda sobre la estética, incluso dentro de un sistema que en otras páginas sí usa duotono y glow.
- **Focus:** ring sólido Verde Profundo de 3px, sin eliminar outline.
- **Error:** Rojo Error bajo el campo con `role="alert"`.
- **Pills de horario** (`.na-slot`): mismo lenguaje que un chip pero interactivo — 44px de alto mínimo, seleccionado = fondo Verde Arranque + texto negro + sombra Reposo (nunca un glow: territorio claro no lo admite aunque el elemento esté "encendido").

### Navigation
- Navbar sticky blanca con borde inferior Gris Borde; enlaces Barlow 500 negro con subrayado animado izquierda→derecha (150ms, 2px Verde Arranque); CTA píldora siempre visible fuera del collapse; targets ≥44px. No cambia al navegar a `/programa` — la navbar es la única franja que se mantiene clara en todo el sitio, ancla de orientación entre territorios.

### El indicador de pasos (`.na-stepper`, componente nuevo — Sprint 4 Bloque 6)
- **Uso:** exclusivo de `/agendar`, arriba de los 3 pasos del formulario.
- **Estado:** derivado directo del modelo (qué servicio/fecha ya se eligieron) — nunca de scroll ni de JS de entrada. Hecho = dot con degradado de marca + línea de degradado hacia el siguiente; Actual = dot con anillo Verde Profundo; Pendiente = dot y línea gris. Expone el estado también por `aria-current="step"`, no solo por color.
- **Reemplaza** al número inline que cada `<h2>` de paso llevaba suelto — quedaba redundante una vez que este overview lo muestra arriba.

### FAB de WhatsApp (componente firma)
- Fijo inferior derecha, 56×56, Verde WhatsApp intocable, icono oficial blanco, safe-area respetada, entrada discreta (fade + scale 0.9, nunca desde 0) con excepción de reduced-motion. El contenido cercano recibe respiro (~88px) para que nunca tape un CTA. No cambia entre territorios.

### El Aro (Signature Component)

El aro degradado es el North Star del sistema hecho componente — y es deliberadamente polimórfico: el mismo anillo de 4px con `--na-gradient-brand` enmarca cosas distintas según qué territorio y qué sección lo necesite.

- **En el logo** (navbar, footer): el aro enmarca la figura atlética — su forma original.
- **En el hero de la landing** (territorio claro, `.na-hero-photo`): el aro enmarca la foto real de Andrés. La foto lleva un **duotono suave de marca** — desaturación parcial (`grayscale(0.45) contrast(1.1) saturate(0.9)`), tinte de marca a intensidad baja (`mix-blend-mode: color`, opacidad 0.16) y una viñeta **clara** que aclara hacia los bordes (hunde el ruido del gimnasio hacia el blanco de la página, no hacia el negro). Sombra: Reposo — nunca un glow, el territorio claro no lo admite. Entrada: patrón **Revelado del Aro** — el aro escala a su lugar (opacity + scale desde 0.86) y la foto se revela adentro con 120ms de rezago propio, como clímax tras el Ascenso Escalonado del texto.
- **En el hero de `/programa`** (territorio oscuro, `.na-programa-cifra`): el mismo aro, ahora hueco (máscara radial) con el **"90" en Barlow Condensed dentro**, count-up de 0 al valor real (`countup.js`, una sola vez, nunca en loop). Sombra: Glow Aro — la única del elemento. Entrada: patrón **Encendido** — opacity + scale desde 0.92, el glow ya puesto en reposo aparece junto con la opacidad, nunca animado aparte.
- **En "Sobre mí"** (territorio claro, `.na-photo-frame`): el marco degradado enmarca la foto documental de Andrés, en **color natural, sin ningún tratamiento** — a propósito. Es la foto que sostiene "soy una persona real" ante quien todavía no decidió agendar; su función es prueba social, no composición decorativa, así que el duotono de la landing no se le aplica (teñir la piel restaría a esa función).

**Por qué esto es un solo componente y no tres:** en los tres casos el marco es idéntico (mismo degradado, mismo grosor, misma sombra de reposo cuando aplica) — lo único que cambia es el contenido y, en el hero, si ese contenido lleva tratamiento de imagen. Esa es la Regla del Aro en acción: una forma, múltiples contenidos, nunca una forma nueva por sección.

### Foto duotono — fondo atmosférico de `/programa`

Distinto de El Aro: esto no es un marco, es el fondo de pantalla completa detrás del hero de `/programa` (`gym-ambiente.webp`). Es el duotono de **alto contraste** del territorio oscuro (Duotono Neón) — la receta opuesta en intensidad a la del hero de la landing:

- **Desaturación total** (`grayscale(1) contrast(1.15) brightness(0.7)`, no parcial como en la landing).
- **Tinte de marca a intensidad alta** (`mix-blend-mode: color`, opacidad 0.85, no 0.16).
- **Scrim direccional** (no viñeta radial pareja): casi opaco donde vive el texto, se aclara donde la foto debe leerse de verdad — protege el contraste sin apagar la foto por completo.
- Sombra: Glow Foto como único glow del elemento (`filter: drop-shadow(...)`), nunca sumado a otro.

Mismo mecanismo CSS que el duotono suave de la landing (filter + pseudo-elementos, sin librerías de imagen) — la diferencia es enteramente de intensidad y de dirección del scrim, no de técnica. Ninguna foto de la landing usa esta receta; ninguna foto de `/programa` usa la suave.

### La confirmación de cita (`/agendar/confirmacion/{token}`)

Aplica la Regla de la Cifra Protagonista al dato más accionable de la página: fecha y horario (lo que el visitante necesita para agendarlo en su propio calendario) se componen en tamaño Title/700 con el horario en Verde Profundo; "a nombre de" queda subordinado en tamaño Label tras un hairline claro (`1px solid var(--na-gris-borde)`) — es confirmación de contexto, no el dato accionable. El aviso sobre el envío de correo (best-effort, no bloquea la reserva) se mantiene textual, sin tratamiento visual — es información de riesgo, no un logro que celebrar.

## 8. Do's and Don'ts

### Do:
- **Do** componer texto verde sobre blanco únicamente con Verde Profundo, y sobre negro únicamente con Verde Arranque brillante — La Regla del Verde Legible, en cualquier dirección de superficie.
- **Do** usar el degradado del aro como firma: una forma o borde por sección, máximo dos — puede enmarcar logo, foto o cifra (El Aro, Signature Component), nunca ser fondo de texto corrido.
- **Do** animar solo `transform` y `opacity` (más color/box-shadow en Tinte al Tacto), 150–300ms con el ease-out fuerte (`cubic-bezier(0.16, 1, 0.3, 1)`) para toda interacción cotidiana; las secuencias cinematográficas declaradas del hero pueden llegar hasta 1100ms (Ascenso Escalonado 550ms, Encendido/Revelado del Aro 700–850ms, Barra de Impulso Luminosa 1100ms) — nunca más. Respetar `prefers-reduced-motion` en CSS **y** JS, sin excepción, en los seis patrones nombrados.
- **Do** mostrar datos reales del PO (precios en colones, horario GMT-6, voseo); los datos que faltan llevan marcador `.na-pending`, jamás valores inventados.
- **Do** mantener los rieles de las grillas y los radios: alturas derivadas de tokens y líneas de texto (`em`), radios interiores calculados desde el radio del marco — nunca píxeles mágicos sueltos.
- **Do** mantener `/agendar` y su confirmación en territorio claro sin importar que `/programa` sea oscuro — Salas Iluminadas: en un formulario, la claridad manda.

### Don't:
- **Don't** — texto blanco sobre Verde Arranque, ni #00C050 como texto sobre blanco, ni Verde Profundo como texto sobre negro. Prohibido sin excepción.
- **Don't** — "fitness agresivo de suplementos": antes/después exagerados, promesas milagro, tipografía de gimnasio a gritos (anti-referencia de PRODUCT.md).
- **Don't** — "clínica fría" ni "coach genérico de Instagram": tono hospitalario, frases motivacionales vacías, stock sonriente (anti-referencias de PRODUCT.md).
- **Don't** — "plantilla SaaS genérica": hero de métricas infladas, grid de features intercambiable (anti-referencia de PRODUCT.md).
- **Don't** — chip eyebrow encima de **cada** sección: es andamiaje de IA, no voz. El chip pertenece al eslogan del hero y al badge "Recomendado"; los h2 de sección jerarquizan solos con la Barra de Impulso.
- **Don't** — apilar más de un glow por elemento, ni usar glow fuera de `/programa` (territorio claro no lo admite en ningún componente, ni siquiera en los "encendidos").
- **Don't** — efectos cyberpunk en `/programa`: cero scanlines, glitch o parpadeo. El Presupuesto de Glow es luz contenida, no un HUD.
- **Don't** — recolorear el verde de WhatsApp, usar emojis como iconos (solo SVG stroke de un set), franjas laterales de color en cards, gradient text, glassmorphism, sombras apiladas o efectos 3D, en ningún territorio.
- **Don't** — aplicar el duotono de imagen (de ningún territorio) a la foto de "Sobre mí": es prueba social documental, no composición — teñirla resta a su función.
- **Don't** — inventar prueba social: sin material real del PO no hay testimonios, ni transformaciones, ni credenciales — "Sin prueba, sin promesa" (PRODUCT.md).

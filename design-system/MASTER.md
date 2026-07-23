# Sistema de Diseño — Nutrición Activa

> **Fuente de verdad global (Master).** Toda página o componente del sitio debe seguir estas reglas.
> Si existe un override en `design-system/pages/<pagina>.md`, ese archivo tiene prioridad para esa página.

---

## v2.1 — 2026-07-23: el PO revisa a mitad de rollout — dirección HÍBRIDA (D-21 actualizado)

Con `/programa` ya migrado (Bloque 4) y la apertura + el resto de la landing también
migrados (Bloques 2 y 3), el PO vio el sitio completo en Dirección A y revisó su
decisión: **"Neón de Madrugada" queda, pero no para todo el sitio.** El sistema pasa a
describir **dos territorios** en vez de una sola dirección global:

- **La landing (`index.html`) vuelve a superficie clara** — el "Energía atlética por
  bloques" de v1, tal como estaba *antes* de que arrancara el rollout oscuro (Bloque 2).
  Una sola evolución se conserva: el aro del hero ya no enmarca el logo, enmarca la
  **foto real de Andrés**, con tratamiento natural (no duotono) adaptado a la paleta
  clara — ver 6.3-bis.
- **`/programa` se queda en negro**, Dirección A completa, tal como está implementada
  hoy (fondo `gym-ambiente.jpg` ya corregido a scrim direccional). Al PO le gustó
  específicamente esta página — es la que sigue funcionando como la pieza insignia de
  la dirección oscura.
- **El "90" deja el aro.** Ya no es "el 90 dentro del aro degradado" (v2, 6.4b) — pasa a
  ser un **contador/tablero tipo calendario** (el lenguaje que la Dirección B ya había
  explorado para el tablero de números), pero con los colores y superficies de la
  Dirección A: fondo negro, cifras en verde brillante, hairline — no se importa la
  paleta de B, solo su vocabulario de "dato como bloque de calendario".

**Qué cambia respecto a v2:**
- §1: la fila "Dirección visual vigente" describe los dos territorios en vez de una
  dirección única.
- §6: la Dirección A deja de ser "la ley del sitio" y pasa a ser **la ley de
  `/programa`**; la landing vuelve a regirse por 6.1 (el v1 sin cambios) más la
  excepción puntual de la foto en el aro (6.3-bis, nueva).
- §6.4: el momento wow (a) — "el Encendido del hero de la landing" — **se retira**: la
  landing ya no tiene una entrada cinematográfica oscura, vuelve al reveal genérico de
  siempre. El momento (b) cambia de "el 90 dentro del aro" a **"el 90 como
  contador/tablero"**. El (c) — módulos como progreso luminoso — no cambia.
- Los tokens de glow (§2.5/§5) que referenciaban el aro (`--na-glow-ring`,
  `--na-glow-photo`) actualizan su descripción de uso — el token y su valor no cambian,
  cambia DÓNDE se usa (ver notas en 2.5).
- **Lo que NO cambia:** el vocabulario de movimiento (§8.1), los tokens (§2.5/§5) y las
  reglas duras (§6.6) — siguen existiendo tal cual, ahora con el alcance explícito de
  que rigen **el territorio oscuro de `/programa`**, no todo el sitio.
- **Pendiente de implementación (código, bloque futuro):** revertir `index.html` +
  `styles.css` al territorio claro, componer la foto de Andrés dentro del aro del hero,
  y rediseñar el "90" de `/programa` como contador/tablero. Este documento es solo la
  ley — el código todavía refleja el estado de Bloque 4 hasta que se implemente.
- **Nota de implementación a resolver en el próximo bloque de código:** `.na-cierre` es
  hoy una clase CSS **compartida** entre el cierre de `index.html` y el de
  `/programa` (Bloque 3/4). Con dos territorios de color, esa clase no puede seguir
  sirviendo a ambas páginas sin diferenciarse — el bloque de implementación debe
  decidir cómo separarla (dos clases, o una variante con modificador).

---

## v2 — 2026-07-21: dirección "Neón de Madrugada" elegida por el PO (D-21)

> **Revisado por v2.1 (arriba).** Esta sección queda como registro histórico de la
> decisión original ("toda la web en negro"); el PO la acotó después a `/programa`
> solamente. Donde v2 diga "el sitio"/"la landing", leer "`/programa`" salvo que v2.1
> diga lo contrario.

El PO eligió la **Dirección A "Neón de Madrugada"** entre las tres catas del Sprint 4
(`docs/briefs-sprint4/comparativa.md`) y pidió además rescatar una pieza de la Dirección B
("El programa, en números") para `/programa`. Esta versión codifica esa decisión como ley
del sistema, sin renumerar las secciones existentes (§1–§9 siguen significando lo mismo,
para no romper las referencias `MASTER §N` ya escritas en código, tests y otros docs).

**Cambios respecto a v1:**
- **Los colores de marca NO cambian de valor** — `--na-green` sigue siendo `#00C050`, etc.
  Lo que cambia es la **polaridad de superficie**: el negro pasa de acento a fondo
  dominante, el verde deja de ser "color de botón" y pasa a ser la única luz encendida.
- Tokens nuevos: superficies/tinta oscura (§2.5, §5), glow con presupuesto de una sombra
  por elemento (§2.5, §5), escala de display ampliada a póster (§3, `--na-fs-display`
  cambia de valor), bordes hairline (§5).
- Vocabulario de movimiento con nombre, 5 patrones (§8.1): **Encendido**, **Ascenso
  Escalonado**, **Revelado en Cascada**, **Tinte al Tacto**, **Barra de Impulso Luminosa**.
- Reglas de imaginería: tratamiento duotono verde/negro obligatorio para toda foto real
  del PO (§6.3).
- 3 momentos wow oficiales, nombrados y especificados (§6.4).
- Un componente importado de la Dirección B para `/programa` — el tablero "El programa,
  en números" — con los ajustes de contraste que exige la polaridad de A (§6.5).
- Reglas duras promovidas a ley explícita (§6.6): Regla del Verde Legible extendida,
  Presupuesto de Glow, Salas Iluminadas, Performance.
- **Lo que queda intacto de v1:** paleta primitiva (valores hex), tipografía (familias y
  pesos), escala de espaciado, radios, roles Bootstrap, botones (forma y par de color),
  orden de secciones de la landing (§7), checklist de accesibilidad base (§9).
- **Estado de implementación:** la apertura del index (hero + banner + trustbar) ya
  ships esta dirección desde Sprint 4 Bloque 2. El resto del sitio (`/programa`,
  `/agendar`, servicios, testimonios, footer) migra por fases; hasta que migre, sigue
  visualmente en v1 — no es una inconsistencia, es el rollout in progress.

---

## 1. Contexto de producto y marca

| | |
|---|---|
| **Producto** | Sitio web de nutricionista deportivo: landing con servicios y precios, agendamiento de citas online y botón de WhatsApp |
| **Marca** | Nutrición Activa — eslogan: *"Alimenta tu potencial"* |
| **Público** | Deportistas y personas que buscan transformación física (perder grasa / ganar músculo), Latinoamérica |
| **Logo** | `design-system/brand/logo.png` — figura atlética negra dentro de un círculo con degradado verde, sobre blanco |
| **Stack** | Spring Boot + Thymeleaf + **Bootstrap 5 vía WebJars**. **Prohibido Tailwind.** Tokens como variables CSS en `:root` |
| **Patrón de conversión** | *Before–After Transformation*: prueba visual de resultados, métricas concretas, CTA tras la evidencia |
| **Estilo** | Vibrante y enérgico, por bloques, alto contraste verde/negro/blanco — sin perder legibilidad ni profesionalismo clínico |
| **Dirección visual vigente (v2.1 — dos territorios)** | **La landing (`index.html`) es clara** — "Energía atlética por bloques" de v1, con una sola evolución: la foto de Andrés dentro del aro del hero (6.3-bis). **`/programa` es oscura** — "Neón de Madrugada" (Sprint 4, D-21): negro dominante, verde como única luz. Brief completo en `docs/briefs-sprint4/direccion-a-neon-de-madrugada.md`, referencia visual ejecutable en `docs/briefs-sprint4/catas/cata-a-neon-de-madrugada.html`. Codificada como ley en §6, con alcance de territorio explícito. |

---

## 2. Paleta de color

Derivada por muestreo directo del logo: verde dominante `#00C050`, degradado hacia verde-teal `#00A870`, negro `#000000`, fondo blanco. **v2 no cambia ningún valor hex de esta sección** — solo agrega superficies y roles nuevos en 2.5.

### 2.1 Tokens primitivos

| Token | Hex | Origen / uso |
|---|---|---|
| `--na-green` | `#00C050` | Verde brillante del logo. CTAs, acentos, iconos. **No usar para texto sobre blanco** (contraste 2.4:1) |
| `--na-green-teal` | `#00A870` | Extremo teal del degradado del logo. Acentos secundarios, hovers, degradados |
| `--na-green-dark` | `#007D3C` | Verde oscuro accesible (≈5.2:1 sobre blanco). Enlaces, texto verde, estados hover de botones |
| `--na-green-tint` | `#E6F9EE` | Verde al 8–10%. Fondos de secciones alternas, badges, iconos con fondo suave |
| `--na-black` | `#0A0A0A` | Negro de marca (figura del logo). Titulares, navbar, footer, secciones oscuras. **En v2 es además el fondo dominante de toda sección "Neón de Madrugada" — ver 2.5** |
| `--na-gray-700` | `#3D4440` | Texto de cuerpo sobre blanco (matiz verdoso neutro) |
| `--na-gray-500` | `#6B736E` | Texto secundario, captions (4.6:1 sobre blanco). **Solo sobre blanco — sobre negro se hunde, usar `--na-gray-200`** |
| `--na-gray-200` | `#DEE4E0` | Bordes, divisores. **En v2 es también el cuerpo de texto sobre fondo oscuro (ver 2.5)** |
| `--na-gray-100` | `#F4F7F5` | Fondos de cards y secciones "muted" |
| `--na-white` | `#FFFFFF` | Fondo principal sobre blanco; sobre negro pasa a ser el acento (titulares, foco) |
| `--na-whatsapp` | `#25D366` | Verde oficial de WhatsApp — usar **solo** en el botón de WhatsApp, sin recolorear |
| `--na-danger` | `#D93A3A` | Errores de formulario |
| `--na-warning` | `#B45309` | Avisos |

### 2.2 Degradado de marca

Replica el aro del logo (oscuro → brillante):

```css
--na-gradient-brand: linear-gradient(135deg, #00A870 0%, #00C050 100%);
```

Usos permitidos: fondo del hero (franja o forma geométrica), barra de acento sobre cards destacadas, aro decorativo. **No** usarlo como fondo de texto largo.

**v2 — roles ampliados del mismo degradado (ningún valor nuevo, solo más superficies):** es la fuente del glow del aro (`--na-glow-ring`, momento Encendido de `/programa`), el overlay de tinte sobre las fotos duotono (`mix-blend-mode: color`, §6.3) y el relleno de la Barra de Impulso Luminosa (§8.1). Sigue siendo un único degradado — no se crean variantes de color.

### 2.3 Roles semánticos (mapeo a Bootstrap)

| Rol | Token | Variable Bootstrap |
|---|---|---|
| Primary (botones/acción) | `--na-green` | `--bs-primary` |
| Texto sobre primary | `--na-black` | `--bs-btn-color` en `.btn-primary` |
| Enlaces | `--na-green-dark` | `--bs-link-color` |
| Cuerpo de texto | `--na-gray-700` | `--bs-body-color` |
| Fondo | `--na-white` | `--bs-body-bg` |
| Dark sections | `--na-black` | `--bs-dark` |
| Success | `--na-green-dark` | `--bs-success` |
| Danger | `--na-danger` | `--bs-danger` |

### 2.4 Reglas de contraste (obligatorias)

- Texto normal: mínimo **4.5:1**. `#00C050` sobre blanco **falla** → para texto verde sobre blanco usar siempre `--na-green-dark`.
- Botón primario: fondo `#00C050` + **texto negro** `#0A0A0A` (8.6:1 ✓). Nunca texto blanco sobre el verde brillante.
- Sobre secciones negras: texto `#FFFFFF` o `#00C050` (el verde brillante sí pasa sobre negro: 8.6:1 ✓).
- **v2 — la misma regla, en la dirección que ahora importa más:** `--na-green-dark` (#007D3C) rinde ~5.2:1 **sobre blanco**, pero se hunde a ~2:1 **sobre negro** — es ilegible. Sobre fondo oscuro el verde legible es siempre el brillante (`--na-green`) o el blanco, nunca el profundo. Esta es la **Regla del Verde Legible**, ver formulación completa en 6.6.
- No transmitir información solo con color (añadir icono o texto).

### 2.5 Polaridad de superficie y tokens oscuros — territorio de `/programa` (v2.1)

**Alcance actualizado por v2.1: esta sección rige `/programa`, no la landing.** La
Dirección A invierte la polaridad dominante dentro de ese territorio: donde v1 alternaba
blanco → gris → negro con el negro como acento puntual, en `/programa` el negro es el
escenario por defecto. La landing vuelve a la alternancia de v1 sin polaridad invertida
(§6.1) — estos tokens no aplican ahí, salvo que una página futura de la landing migre
explícitamente a este territorio.

| Token | Valor | Uso |
|---|---|---|
| `--na-dark-bg` | `#0A0A0A` (= `--na-black`) | Alias semántico: el fondo dominante de una sección oscura de `/programa`. No es un color nuevo — es una superficie nueva sobre un color que ya existía como acento. |
| `--na-dark-ink` | `#DEE4E0` (= `--na-gray-200`) | Cuerpo de texto sobre `--na-dark-bg`. Nunca `--na-gray-500` (Gris Secundario): ese tono solo pasa AA sobre blanco. |
| `--na-hairline` | `rgba(255, 255, 255, 0.08)` | Borde de superficie plana sobre fondo oscuro (divisores, marcos). Sustituye a la sombra: una sombra oscura no se ve sobre negro, un hairline sí. Sin glass, sin texturas. |
| `--na-glow-cta` | `0 0 32px rgba(0, 192, 80, 0.35)` | El único glow del CTA primario sobre fondo oscuro de `/programa` — "el botón emite luz". |
| `--na-glow-photo` | `0 0 48px rgba(0, 192, 80, 0.45)` | Glow para marcos de fotografía sobre fondo oscuro, si `/programa` los necesita. **Ya no aplica al hero de la landing** (v2.1 lo devuelve a superficie clara — ver 6.3-bis, tratamiento sin glow). |
| `--na-glow-ring` | `0 0 44px rgba(0, 192, 80, 0.45)` | **Reasignado por v2.1**: ya no es "el aro con el 90 adentro" (retirado, ver 6.4b) — es el glow disponible para el contador/tablero-calendario del "90" en `/programa`, si su diseño lo pide. |

Un elemento usa **como máximo uno** de los tres tokens de glow — nunca dos apilados
(Presupuesto de Glow, §6.6). `--na-dark-bg` y `--na-dark-ink` son alias deliberados de
tokens que ya existían (no duplican el color, documentan el rol nuevo); el código hoy
usa `--na-black`/`--na-gray-200` directamente en las secciones ya migradas — adoptar los
alias semánticos es una limpieza pendiente, no un bloqueante.

---

## 3. Tipografía

Pareja atlética/condensada, ideal para marcas deportivas (recomendación del motor de estilos para fitness):

| Rol | Fuente | Pesos |
|---|---|---|
| Titulares (h1–h4, precios, cifras) | **Barlow Condensed** | 600 (brand), 700 |
| Cuerpo, botones, formularios | **Barlow** | 400, 500, 600 |

**Import (colocar en el `<head>` del layout Thymeleaf, antes del CSS propio):**

```html
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Barlow+Condensed:wght@600;700&family=Barlow:wght@400;500;600&display=swap" rel="stylesheet">
```

Alternativa en CSS:

```css
@import url('https://fonts.googleapis.com/css2?family=Barlow+Condensed:wght@600;700&family=Barlow:wght@400;500;600&display=swap');
```

**Escala tipográfica** (base 16px, cuerpo con `line-height: 1.6`; titulares 1.1–1.2):

| Token | Tamaño | Uso |
|---|---|---|
| `--na-fs-display` | `clamp(3.5rem, 8vw, 6rem)` — **territorio `/programa` únicamente (v2.1)** | H1 a escala póster: mayúsculas, tracking apretado (0.01em, excepción declarada a la Regla del Tracking en Caps de DESIGN.md). Es el valor del H1 de `/programa`; el H1 de la landing usa la escala v1 (`clamp(2.5rem, 6vw, 4.5rem)`, ver nota de abajo) — el bloque de implementación decide el mecanismo (token separado o el mismo token con valor por territorio), esta tabla fija el tamaño resultante esperado en cada uno. |
| `--na-fs-h2` | `clamp(2rem, 4vw, 3rem)` | Títulos de sección |
| `--na-fs-h3` | `1.5rem` | Títulos de card / plan |
| `--na-fs-body` | `1rem` (16px) | Cuerpo — nunca menor en móvil |
| `--na-fs-small` | `0.875rem` | Captions, notas legales |
| `--na-fs-price` | `clamp(2rem, 3.5vw, 2.75rem)` | Cifras de precios (Barlow Condensed 700 + `font-variant-numeric: tabular-nums`) |

Longitud de línea del cuerpo: 60–75 caracteres (`max-width: 65ch` en párrafos largos).

**v2.1 — corrección de alcance:** v2 decía que la escala póster migraría a todo H1 de la
landing. Con la landing de vuelta a superficie clara, eso se revierte: el H1 del hero de
`index.html` vuelve a `clamp(2.5rem, 6vw, 4.5rem)` (v1). La escala póster queda como una
característica de `/programa` — el tamaño bruto del H1 sigue siendo, ahí, el mecanismo
que resuelve la jerarquía que señaló el critique baseline; en la landing, esa jerarquía
se resuelve como en v1 (composición y peso, no tamaño extremo).

---

## 4. Escala de espaciado

Sistema de 8px (compatible con los `$spacers` de Bootstrap). Landing de marketing → respirada, extremos amplios:

| Token | Valor | Uso |
|---|---|---|
| `--na-space-1` | `4px` | Gaps mínimos (icono–texto) |
| `--na-space-2` | `8px` | Interior de badges, gaps pequeños |
| `--na-space-3` | `16px` | Padding de inputs y botones, gaps de card |
| `--na-space-4` | `24px` | Padding interno de cards |
| `--na-space-5` | `32px` | Separación entre bloques relacionados |
| `--na-space-6` | `48px` | Separación entre grupos |
| `--na-space-7` | `64px` | Padding vertical de sección (móvil) |
| `--na-space-8` | `96px` | Padding vertical de sección (desktop) |

Contenedor: usar `.container` de Bootstrap con `max-width` estándar (1320px en XXL). Breakpoints: los de Bootstrap (576/768/992/1200/1400) — diseñar mobile-first desde 375px.

*(v2 no toca esta sección: el ritmo de 8px es independiente de la polaridad de color.)*

---

## 5. Bloque `:root` listo para usar

Colocar en `src/main/resources/static/css/tokens.css` (hoy vive integrado en
`styles.css`), cargado **después** del CSS de Bootstrap (WebJars) para que los overrides
ganen:

```css
:root {
  /* Primitivos de marca — sin cambios de valor en v2 */
  --na-green: #00C050;
  --na-green-teal: #00A870;
  --na-green-dark: #007D3C;
  --na-green-tint: #E6F9EE;
  --na-black: #0A0A0A;
  --na-gray-700: #3D4440;
  --na-gray-500: #6B736E;
  --na-gray-200: #DEE4E0;
  --na-gray-100: #F4F7F5;
  --na-white: #FFFFFF;
  --na-whatsapp: #25D366;
  --na-danger: #D93A3A;
  --na-gradient-brand: linear-gradient(135deg, #00A870 0%, #00C050 100%);

  /* Dirección A "Neón de Madrugada" (v2) — superficies oscuras y glow.
     Presupuesto de glow: 1 sombra verde por elemento, nunca apiladas. */
  --na-dark-bg: var(--na-black);
  --na-dark-ink: var(--na-gray-200);
  --na-hairline: rgba(255, 255, 255, 0.08);
  --na-glow-cta: 0 0 32px rgba(0, 192, 80, 0.35);
  --na-glow-photo: 0 0 48px rgba(0, 192, 80, 0.45);
  --na-glow-ring: 0 0 44px rgba(0, 192, 80, 0.45);

  /* Tipografía */
  --na-font-heading: "Barlow Condensed", "Arial Narrow", sans-serif;
  --na-font-body: "Barlow", "Helvetica Neue", Arial, sans-serif;
  --na-fs-display: clamp(3.5rem, 8vw, 6rem); /* territorio /programa (v2.1). H1 de la landing: clamp(2.5rem, 6vw, 4.5rem), escala v1 */
  --na-fs-h2: clamp(2rem, 4vw, 3rem);
  --na-fs-h3: 1.5rem;
  --na-fs-price: clamp(2rem, 3.5vw, 2.75rem);

  /* Espaciado (sistema de 8px) */
  --na-space-1: 4px;  --na-space-2: 8px;   --na-space-3: 16px;
  --na-space-4: 24px; --na-space-5: 32px;  --na-space-6: 48px;
  --na-space-7: 64px; --na-space-8: 96px;

  /* Forma y elevación */
  --na-radius-sm: 8px;      /* inputs, badges */
  --na-radius-md: 16px;     /* cards */
  --na-radius-pill: 999px;  /* botones y chips */
  --na-shadow-card: 0 4px 16px rgba(10, 10, 10, 0.08);
  --na-shadow-card-hover: 0 10px 28px rgba(0, 192, 80, 0.18);

  /* Movimiento — vocabulario con nombre, ver §8.1 */
  --na-ease-out: cubic-bezier(0.16, 1, 0.3, 1);
  --na-dur-fast: 150ms;       /* Tinte al Tacto: press */
  --na-dur-base: 250ms;       /* Tinte al Tacto: hover */
  --na-dur-cascada: 280ms;    /* Revelado en Cascada: reveal genérico de secciones */
  --na-dur-ascenso: 550ms;    /* Ascenso Escalonado: texto del hero */
  --na-dur-encendido: 850ms;  /* Encendido: clímax foto/aro — el evento más largo */
  --na-dur-impulso: 1100ms;   /* Barra de Impulso Luminosa: relleno de progreso */

  /* Overrides de Bootstrap 5 */
  --bs-primary: #00C050;
  --bs-primary-rgb: 0, 192, 80;
  --bs-success: #007D3C;
  --bs-success-rgb: 0, 125, 60;
  --bs-danger: #D93A3A;
  --bs-dark: #0A0A0A;
  --bs-dark-rgb: 10, 10, 10;
  --bs-body-color: #3D4440;
  --bs-body-bg: #FFFFFF;
  --bs-body-font-family: var(--na-font-body);
  --bs-body-line-height: 1.6;
  --bs-link-color: #007D3C;
  --bs-link-hover-color: #00A870;
  --bs-border-color: #DEE4E0;
  --bs-border-radius: 8px;
  --bs-border-radius-lg: 16px;
}

/* Botón primario: verde de marca + texto NEGRO (accesible) */
.btn-primary {
  --bs-btn-bg: var(--na-green);
  --bs-btn-border-color: var(--na-green);
  --bs-btn-color: var(--na-black);
  --bs-btn-hover-bg: var(--na-green-dark);
  --bs-btn-hover-border-color: var(--na-green-dark);
  --bs-btn-hover-color: var(--na-white);
  --bs-btn-active-bg: var(--na-green-dark);
  --bs-btn-active-color: var(--na-white);
  --bs-btn-focus-shadow-rgb: 0, 192, 80;
  border-radius: var(--na-radius-pill);
  font-weight: 600;
  padding: 0.75rem 2rem;
}

h1, h2, h3, h4, .display-1, .display-2, .display-3 {
  font-family: var(--na-font-heading);
  font-weight: 700;
  line-height: 1.15;
  color: var(--na-black);
}
```

> Nota: Bootstrap 5 compila algunos colores en reglas Sass (no todo lee las variables `--bs-*` en runtime). Los overrides de componente al estilo `.btn-primary { --bs-btn-* }` de arriba son el mecanismo oficial en v5.3 y cubren los casos del sitio. Si algún componente no responde, sobrescribir su clase directamente en `tokens.css` — nunca editar el CSS del WebJar.

---

## 6. Estilo visual recomendado

**v2.1 — dos territorios.** El PO revisó su decisión a mitad de rollout: "Neón de
Madrugada" ya no es la ley de todo el sitio, es la ley de **`/programa`**. La
**landing** (`index.html`) vuelve a regirse por v1 ("Energía atlética por bloques",
6.1) sin polaridad invertida — blanco/gris/negro-acento, como antes del Bloque 2 —
con una sola pieza nueva: la foto real de Andrés dentro del aro del hero, tratada en
color natural (6.3-bis), no en duotono.

`/programa` sigue siendo el gimnasio a las 5 de la mañana: oscuridad total, un solo
color encendido, la sensación de que el mundo todavía duerme y vos ya estás
entrenando (6.2–6.5). Todo lo que sigue de 6.2 en adelante, salvo que diga lo
contrario, describe **ese territorio, no la landing**.

### 6.1 La landing: v1 sin cambios (territorio claro)

Vigente para toda `index.html` — hero, banner, trustbar, Sobre mí, servicios,
testimonios, cierre y footer. Nada de esto depende de la polaridad de color y no
cambió en ningún momento del rollout:

- **Cards**: fondo blanco sobre superficies claras, `--na-radius-md`, `--na-shadow-card`, borde superior de 4px con el degradado en la card destacada.
- **Botones**: pill (`--na-radius-pill`). Primario = verde + texto negro; secundario = outline negro sobre superficie clara.
- **Iconos**: SVG de un solo set (stroke), nunca emojis. En verde `--na-green-dark` (el único verde legible sobre blanco).
- **Cifras protagonistas**: métricas de resultados en Barlow Condensed gigante — el corazón del patrón before–after.
- **Formas geométricas de acento**: el aro degradado es la firma decorativa (máx. 1–2 por sección) — en el hero, a partir de v2.1, enmarca la foto real de Andrés en vez del logo (6.3-bis).

### 6.2 `/programa`: mood y decisiones de superficie (territorio oscuro)

El fondo dominante es negro de marca en toda la página. El verde deja de ser "color de
botón" y pasa a ser fuente de luz: rim-light, glow contenido, el degradado como única
fuente de brillo. Las superficies son planas y profundas, con borde hairline
(`--na-hairline`) en vez de sombra — una sombra oscura no se ve sobre negro. Nada de
glass, texturas ni efectos 3D. *(Antes de v2.1 esta descripción regía "la apertura,
progresivamente el resto del sitio" — v2.1 la acota a `/programa`.)*

### 6.3 `/programa`: tratamiento de imaginería — Duotono Neón (territorio oscuro)

Toda foto real del PO que viva **en `/programa`** (hoy: `gym-ambiente.jpg` como fondo
atmosférico del hero) se integra a la paleta oscura con tratamiento CSS — grayscale +
contraste para desaturar el original, un overlay que tiñe con el degradado de marca, y
un scrim que protege el contraste del texto sin apagar la foto por completo (ajustado
tras feedback del PO: el oscurecido debe ser *direccional*, no uniforme — más oscuro
donde vive el texto, más claro donde la foto debe leerse de verdad):

```css
.foto-duotono {
  position: relative;
  overflow: hidden;
  filter: drop-shadow(var(--na-glow-photo)); /* único glow del elemento */
}
.foto-duotono img {
  display: block;
  width: 100%; height: 100%;
  object-fit: cover;
  filter: grayscale(1) contrast(1.15) brightness(0.7);
}
/* Tiñe de verde de marca sobre la imagen ya en escala de grises */
.foto-duotono::after {
  content: ""; position: absolute; inset: 0;
  background: var(--na-gradient-brand);
  mix-blend-mode: color;
  opacity: 0.85;
}
/* Hunde el ruido de fondo: el sujeto queda al centro de la luz.
   Para fondos atmosféricos (sin sujeto que preservar, p. ej. gym-ambiente.jpg)
   este scrim puede ser direccional (gradiente lineal) en vez de radial parejo —
   ver la implementación real en styles.css para el ejemplo vigente. */
.foto-duotono::before {
  content: ""; position: absolute; inset: 0; z-index: 1;
  background: radial-gradient(circle at 50% 32%, transparent 40%, rgba(10, 10, 10, 0.75) 100%);
}
```

Esto es **tratamiento, no maquillaje**: sigue rigiendo "Sin prueba, sin promesa"
(PRODUCT.md) — el duotono no disfraza la ausencia de material real, solo unifica
cualquier foto real que el PO entregue a la paleta oscura, sin importar su calidad de
origen (gimnasio con luces mixtas, fondo ocupado, etc.).

**v2.1 — ya no aplica a la landing.** Antes de esta revisión, el plan era migrar todas
las fotos reales (incluida `andres-hero.jpeg` del hero y `andres.webp` de "Sobre mí") a
este tratamiento. Eso se revierte: ninguna foto de la landing usa duotono — ver 6.3-bis.

### 6.3-bis La landing: la foto de Andrés en el aro (territorio claro, nuevo en v2.1)

El hero de `index.html` reemplaza el logo dentro del aro por la **foto real de
Andrés**, en color natural — nada de grayscale, nada de overlay de tinte, nada de
glow. Es una superficie clara: se integra por el **marco**, no por el color, igual
criterio que ya validó el PO para "Sobre mí" antes de que esa sección volviera a la
landing clara:

- El aro degradado (`--na-gradient-brand`) sigue siendo el marco — el mismo "eco del
  aro del logo" que ya describía v1, ahora con contenido real adentro en vez del
  logotipo.
- Sombra: `--na-shadow-card` (la sombra ambiental de reposo de v1) — **no**
  `--na-glow-photo` ni ningún glow verde. Los glows son vocabulario del territorio
  oscuro (§2.5); una foto en color natural sobre fondo blanco no los necesita ni los
  admite sin romper la Regla del Verde Legible en superficie clara.
- Encuadre: igual criterio que ya se afinó para el hero oscuro (object-position +
  escala si hace falta) para que Andrés se vea reconocible dentro del círculo, no
  recortado — el objetivo compositivo no cambia, solo la paleta.

### 6.4 Momentos wow oficiales (territorio `/programa` — v2.1 los acota ahí)

Los tres momentos wow ya vivían todos en `/programa` o en la apertura de la landing;
v2.1 confirma que, con la landing de vuelta a superficie clara, **los momentos wow son
un concepto exclusivo de `/programa`** — la página insignia de la Dirección A.

**(a) [RETIRADO] El Encendido del hero de la landing.** Ya no existe: la landing volvió
a superficie clara (6.1) y no tiene una entrada cinematográfica oscura — el hero usa el
Revelado en Cascada genérico de siempre, igual que el resto del sitio en v1. La foto de
Andrés sigue siendo protagonista del hero (dentro del aro, 6.3-bis), pero como elemento
compuesto de siempre, no como clímax de una secuencia Encendido.

**(b) El "90" de `/programa` — de "dentro del aro" a contador/tablero.** *Redefinido
por v2.1.* Ya no vive dentro de un aro degradado encendido (esa versión se retira junto
con su glow `--na-glow-ring` asociado a esa forma — el token sigue existiendo, ver
2.5). Pasa a presentarse como un **contador/tablero tipo calendario**: la Cifra
Protagonista ("90") sobre una superficie plana con hairline (no un aro), en la
gramática de "dato de tablero" que exploró la Dirección B ("Dorsal 90"), pero con los
colores y superficies de la Dirección A — fondo negro, cifra en verde brillante o
blanco, hairline en vez de sombra. *No confundir con el tablero de 6.5* ("El programa,
en números"): son dos piezas distintas — esta es la pieza protagonista del hero: el
componente de 6.5 es la sección de datos que va después, sin cambios. El diseño visual
exacto del contador/calendario queda para el bloque de implementación que lo construya;
esta entrada fija el criterio (calendario/tablero, no aro; paleta A). *Pendiente de
implementación.*

**(c) La banda de módulos como progreso luminoso.** Sin cambios por v2.1. La secuencia
"Día 1–30 / 31–60 / 61–90" se dibuja como una barra de progreso con el degradado de
marca que se llena al hacer scroll — patrón Barra de Impulso Luminosa (§8.1),
transform/opacity puro. *Implementado — Sprint 4, Bloque 4.*

### 6.5 Componente importado de la Dirección B: "El programa, en números"

El PO pidió rescatar de la Dirección B ("Dorsal 90") el tablero de datos para
`/programa`, aunque eligió A como dirección general. El tablero es, por construcción,
casi nativo de A — fondo negro, cifras en verde brillante — con dos ajustes obligatorios
al importarlo:

1. **El subtítulo de cada dato (`.stat-sub`, ej. "≈ ₡35 000 por módulo") usa
   `--na-dark-ink` (Gris Borde), nunca Gris Secundario.** La cata de origen (Dirección B)
   usaba Gris Secundario, que es ilegible sobre negro (2.4) — un tablero clonado tal cual
   heredaría un fallo de AA.
2. **Jerarquía de precio:** ₡105.000 (programa completo) es el dato protagonista, del
   mismo tamaño que 90/3/6; el equivalente por módulo queda subordinado en `.stat-sub`
   — nunca al mismo peso visual, para que no se confunda con el precio real.

El resto del componente no cambia al importarlo: cifras en Barlow Condensed 700
tabular-nums (Cifra Protagonista, ya vigente), un hairline o filete de 2px verde por
ítem (compatible con "planas + hairline", no es una sombra), y — si se anima — un
count-up de una sola vez, ≤1s, con los valores reales del PO, nunca en loop.

### 6.6 Reglas duras (no negociables) — territorio `/programa`, salvo la última

Estas reglas siguen existiendo tal cual las definió v2; v2.1 solo aclara **dónde**
rigen: las tres primeras son del territorio oscuro (`/programa`); Performance y las
heredadas de v1 son del sitio entero, sin importar el territorio.

- **Regla del Verde Legible (extendida).** El verde brillante (`--na-green`) es el único
  verde que pasa AA sobre negro (8.6:1); el verde profundo (`--na-green-dark`) es el
  único que pasa AA sobre blanco (5.2:1) — **nunca al revés**. Sobre fondo oscuro, texto
  verde = brillante o nada; sobre fondo claro, texto verde = profundo o nada. Aplica en
  ambos territorios (la mitad "sobre blanco" es, de hecho, la que ahora vuelve a regir
  toda la landing).
- **Presupuesto de Glow.** Máximo **una** sombra verde por elemento (`--na-glow-cta`,
  `--na-glow-photo` o `--na-glow-ring`, nunca combinadas en el mismo elemento). Cero
  `text-shadow` en cuerpo de texto. Cero efectos cyberpunk: nada de scanlines, glitch,
  ni parpadeo. **Alcance: `/programa`** — la landing, en superficie clara, no usa glow
  verde (ver 6.3-bis: la foto del hero lleva `--na-shadow-card`, no un glow).
- **Salas Iluminadas.** El formulario de `/agendar` y su pantalla de confirmación
  permanecen sobre superficie **clara** aunque `/programa` sea oscuro — en un
  formulario, la claridad manda sobre la estética. No es una excepción vergonzosa a la
  dirección: es la mitigación de riesgo que el brief pidió desde el diseño. (Con la
  landing también clara desde v2.1, esta regla queda relevante sobre todo para
  diferenciar `/agendar` de `/programa`, que sí es oscuro.)
- **Performance.** CSS/JS vanilla, sin librerías nuevas. Imágenes reales optimizadas
  (WebP cuando sea posible); el tratamiento duotono es 100% CSS (`filter` +
  `mix-blend-mode`), cero peso adicional de imagen por variante de color. Rige en
  ambos territorios.
- *(Siguen vigentes de v1 en todo el sitio, sin cambios: nunca texto blanco sobre Verde
  Arranque; nunca `#00C050` como texto sobre blanco; sin prueba social inventada —
  PRODUCT.md.)*

---

## 7. Landing page — orden de secciones

Patrón *Before–After Transformation* (optimizado a conversión para servicios de transformación física):

1. **Navbar** (sticky, blanca, logo izquierda) — enlaces: Servicios · Resultados · Precios · CTA "Agendar cita" (btn-primary, siempre visible).
2. **Hero** — H1 con la promesa ("Alimenta tu potencial: pierde grasa, gana músculo"), subtítulo con especificidad (deportistas, online, toda LATAM), CTA primario "Agendar mi cita" + CTA secundario WhatsApp. **v2.1: fondo claro (v1) — el paso por fondo negro/duotono de v2 (Bloque 2) se revirtió. Única pieza nueva: la foto real de Andrés dentro del aro, en vez del logo (6.3-bis). Pendiente de implementación (revertir Bloque 2/3).**
3. **Barra de confianza** — cifras rápidas: años de experiencia, clientes transformados, países atendidos. Fondo negro de v1 (acento puntual, no territorio oscuro) — el hairline que le agregó v2 como transición de la apertura oscura ya no aplica; revertir junto con el hero.
4. **Transformaciones (before–after)** — comparativas reales con métricas concretas; antes en gris desaturado, después a color con acentos verdes. La sección de mayor peso visual (fondo negro).
5. **Cómo funciona** — 3 pasos: 1) Agenda tu evaluación → 2) Recibe tu plan personalizado → 3) Seguimiento y ajustes. Iconos + números grandes.
6. **Servicios y precios** — cards de planes (4, catálogo final del PO 2026-07-12), plan recomendado destacado con borde degradado y badge; precio en grande, lista de incluye, CTA por card. Aclarar moneda (USD/CRC) por el público LATAM.
7. **Testimonios** — citas breves con foto, nombre, deporte y resultado medible.
8. **FAQ** — accordion de Bootstrap: consultas online, formas de pago, tiempos de resultados.
9. **CTA final** — franja negra o degradado, eslogan + botón "Agendar cita" + botón WhatsApp.
10. **Footer** — logo, contacto, redes, aviso legal.

**Flotante:** botón de WhatsApp fijo (`position: fixed`, inferior derecha, 56×56px mínimo, `#25D366`, icono oficial blanco, `aria-label="Escribir por WhatsApp"`), separado del borde con safe-area y sin tapar CTAs.

*(Ni v2 ni v2.1 reordenan ni agregan secciones aquí — el rollout restylea secciones
existentes por fases, no cambia la arquitectura de información. El banner del programa
(HU-12) se mantiene entre Hero y Barra de confianza, como ya fijó Sprint 3; con la
landing de vuelta a superficie clara, su fondo Verde Tinte deja de ser un "respiro"
entre bloques negros y vuelve a ser, simplemente, la sección destacada que ya era en v1.)*

---

## 8. Micro-interacciones — tono y especificación

**Tono: enérgico pero controlado.** El movimiento comunica impulso (la marca es acción física), pero nunca circo: transiciones cortas, con propósito, una idea animada por sección.

### 8.1 Vocabulario de movimiento (con nombre) — v2

Cinco patrones nombrados, reutilizables en todo el rollout. Todos animan **solo
`transform` y `opacity`** (nunca width/height/top); con `prefers-reduced-motion: reduce`
el contenido queda visible al instante, sin excepción.

| Patrón | Qué anima | Duración | Easing | Reduced-motion |
|---|---|---|---|---|
| **Encendido** | opacity + scale (desde 0.85–0.92, nunca desde 0); el glow del elemento ya está presente y aparece con la opacidad, no se anima aparte | `--na-dur-encendido` (850ms) | `--na-ease-out` | Visible al instante, sin scale |
| **Ascenso Escalonado** | opacity + `translateY(32px→0)`, stagger ~120ms entre elementos | `--na-dur-ascenso` (550ms) | `--na-ease-out` | Visible al instante, sin desplazamiento |
| **Revelado en Cascada** | opacity + `translateY(16px→0)`, stagger 40ms, dispara una sola vez vía IntersectionObserver | `--na-dur-cascada` (280ms) | `--na-ease-out` | Visible al instante |
| **Tinte al Tacto** | color/fondo + glow o sombra-tinte + `translateY(-2px)`/`scale(0.97)` en respuesta a hover/press | `--na-dur-fast` (150ms) / `--na-dur-base` (250ms) | `--na-ease-out` | No es animación de entrada; el motion de transform igual respeta el media query |
| **Barra de Impulso Luminosa** | `scaleX(0→1)` con `transform-origin: left`, dispara una sola vez al entrar en viewport | `--na-dur-impulso` (1100ms) | `--na-ease-out` | Nace llena (`scaleX(1)` inmediato) |

**Dónde se usa cada uno (v2.1 — el vocabulario sigue siendo del sitio entero, pero
Encendido y Ascenso Escalonado en la práctica solo tienen candidatos en `/programa`
ahora que la landing volvió a superficie clara y retiró su Encendido, 6.4a):**
- *Encendido* → sin uso confirmado hoy (el del hero de la landing se retiró, 6.4a). Si
  el contador/tablero de 6.4b termina necesitando una entrada con clímax, este es el
  patrón candidato — a decidir en el bloque de implementación.
- *Ascenso Escalonado* → sin dueño fijo tras retirar 6.4a; sigue disponible para
  cualquier bloque de texto de `/programa` que quiera un preludio escalonado antes de
  un Encendido.
- *Revelado en Cascada* → el reveal genérico que el sitio entero usa desde HU-01
  (`data-reveal`/`reveal.js`), en ambos territorios: landing clara y `/programa` oscuro.
- *Tinte al Tacto* → ya nombrado en DESIGN.md §4; hover de botones y cards, en ambos
  territorios. Sobre superficie oscura el tinte es glow (box-shadow verde) en vez de
  elevación con sombra gris; sobre superficie clara, sigue siendo la sombra-tinte + rise
  de siempre.
- *Barra de Impulso Luminosa* → la banda de módulos de `/programa` (6.4c, implementado);
  evolución de "La Barra de Impulso" (DESIGN.md) para secuencias de datos reales, no
  solo el kicker de un h2.

### 8.2 Interacción → especificación

| Interacción | Especificación | Patrón |
|---|---|---|
| Hover botón primario | Fondo → `--na-green-dark` + `transform: translateY(-2px)`, `250ms` ease-out | Tinte al Tacto |
| Press/tap botón | `transform: scale(0.97)`, `150ms`; feedback visual < 100ms | Tinte al Tacto |
| Hover card | Elevación a `--na-shadow-card-hover` (tinte verde) + `translateY(-4px)`, `250ms` | Tinte al Tacto |
| Entrada de secciones al scroll | Fade + `translateY(16px→0)`, stagger de 40ms entre cards (IntersectionObserver vanilla, una sola vez) | Revelado en Cascada |
| Entrada del hero de la landing | Revelado en Cascada genérico, como el resto de v1 (v2.1 retiró el Encendido, 6.4a) | Revelado en Cascada |
| Cifras del "trust bar" / tablero de números | Count-up al entrar en viewport, ≤ 1s, una sola vez, valores reales del PO | — (sin nombre propio; regla compartida con Barra de Impulso Luminosa: nunca en loop) |
| Reveal before–after | Slider o crossfade controlado por el usuario — nunca autoplay | — |
| Botón WhatsApp | Sutil pulse del anillo cada ~6s (opacity/transform only); se detiene tras la primera interacción | — |
| Submit de formularios | Botón deshabilitado + spinner durante el envío; éxito con check verde; error en rojo junto al campo | — |
| Enlaces navbar | Subrayado animado de izquierda a derecha, `150ms` | Tinte al Tacto |

### 8.3 Reglas duras del movimiento

- Duraciones 150–300ms para toda interacción de Tinte al Tacto y Revelado en Cascada;
  la única excepción declarada son las secuencias cinematográficas del hero (Ascenso
  Escalonado 550ms, Encendido 850ms, Barra de Impulso Luminosa 1100ms) — nunca > 1200ms.
- Animar **solo** `transform` y `opacity` (nunca width/height/top — evita reflow y CLS).
  El glow (`box-shadow`/`filter: drop-shadow`) no se anima por separado: aparece con la
  opacidad del elemento que ya lo trae puesto.
- Respetar `prefers-reduced-motion: reduce` en **todos** los patrones sin excepción:
  entradas, pulse, count-up y barras de progreso quedan en su estado final desde el
  primer frame.
- Máximo 1–2 elementos animados por vista; el contenido debe ser legible sin esperar
  animaciones.

---

## 9. Accesibilidad y calidad — checklist de entrega

- [ ] Contraste: texto normal ≥ 4.5:1 (texto verde solo con `--na-green-dark` sobre blanco, `--na-green` sobre negro — nunca al revés, ver Regla del Verde Legible en 6.6)
- [ ] Botón primario verde siempre con texto negro
- [ ] Targets táctiles ≥ 44×44px (botones, navbar, WhatsApp flotante)
- [ ] Labels visibles en el formulario de citas (no solo placeholder); errores bajo el campo con `role="alert"`; tipos semánticos (`email`, `tel`, `date`)
- [ ] Focus visible en todos los interactivos: anillo sólido `#007D3C` (3px) sobre superficies claras — el translúcido `rgba(0,192,80,.4)` rendía ~1.4:1 y falla WCAG 1.4.11; **sobre superficies oscuras el anillo pasa a blanco** (verde-profundo rinde ~3.7:1 sobre negro, insuficiente) — no eliminar outline
- [ ] Un solo H1 (hero); jerarquía h1→h2→h3 sin saltos
- [ ] `alt` descriptivo en fotos de transformaciones; `aria-label` en botones de solo icono
- [ ] Imágenes WebP con dimensiones declaradas; lazy load bajo el fold; CLS < 0.1 (excepción documentada: `andres-hero.jpeg` con `loading="eager"` por ser LCP del hero — pendiente de conversión a WebP, ver `docs/pendientes.md`)
- [ ] Sin scroll horizontal en 375px; body ≥ 16px en móvil
- [ ] `prefers-reduced-motion` respetado en los 5 patrones de 8.1, sin excepción
- [ ] **Presupuesto de Glow respetado** (territorio `/programa`): ninguna sección apila más de un `--na-glow-*` sobre el mismo elemento (6.6); la landing no usa glow verde en absoluto (6.3-bis)
- [ ] **Salas Iluminadas intactas**: `/agendar` y su confirmación siguen en superficie clara aunque la sección que las precede sea oscura (6.6)
- [ ] Probado en 375 / 768 / 1024 / 1440px

---

*Generado con la skill `ui-ux-pro-max` (patrón Before–After Transformation + estilo Vibrant & Block-based + tipografía Barlow) el 2026-07-10. Paleta muestreada del logo real.*
*Actualizado a v2 el 2026-07-21: Dirección A "Neón de Madrugada" elegida por el PO (D-21, Sprint 4). Overrides por página: crear `design-system/pages/<pagina>.md`.*
*Actualizado a v2.1 el 2026-07-23: el PO acota la Dirección A a `/programa`; la landing vuelve a superficie clara con la foto de Andrés en el aro del hero; el "90" pasa de vivir en el aro a un contador/tablero tipo calendario (D-21 actualizado, Sprint 4).*

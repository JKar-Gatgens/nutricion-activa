# Sistema de Diseño — Nutrición Activa

> **Fuente de verdad global (Master).** Toda página o componente del sitio debe seguir estas reglas.
> Si existe un override en `design-system/pages/<pagina>.md`, ese archivo tiene prioridad para esa página.

---

## v2.5 — 2026-07-23: `/agendar` y su confirmación pasan a la estética actualizada (nuevo 6.1-bis)

Sprint 4, Bloque 6. `/agendar` (el formulario de 3 pasos) y `/agendar/confirmacion/{token}`
no habían recibido ningún trabajo visual desde que se construyeron en Sprint 2 (HU-04) —
seguían con la estética previa al rollout, aunque ya vivían en territorio claro por
"Salas Iluminadas" (§6.6). Este bloque los actualiza sin tocar esa regla: **en un
formulario, la claridad manda** — la energía visual entra por el marco (encabezados,
indicador de pasos, pills de horario, CTA), nunca por los campos.

- **Nuevo componente con nombre: el indicador de pasos (`.na-stepper`)** — dots
  conectados por una línea que se llena con el degradado de marca solo cuando ese
  tramo ya se completó; estado derivado directo del modelo (qué servicio/fecha ya se
  eligieron), no de scroll ni de JS. Reemplaza al número inline que cada paso llevaba
  suelto (quedaba redundante una vez que el overview lo muestra arriba).
- **Encabezados del formulario**: el `<h1>` y cada `<h2>` de paso pasan a compartir la
  firma `.na-section-title` (barra de degradado) que ya usa el resto del sitio — antes
  eran texto plano sin relación visual con la landing.
- **Confirmación: jerarquía nueva del resumen.** Fecha y horario — el dato que el
  visitante necesita para actuar — pasan a Cifra Protagonista; "a nombre de" queda
  subordinado tras un hairline. Mismos `dt`/`dd`, mismo copy, mismos valores — el
  cambio es puramente de peso visual (CSS). El aviso honesto sobre el envío de correo
  no cambia una palabra.
- **Sin tokens nuevos, sin glow en ningún punto**: el pill de horario elegido suma
  `--na-shadow-card` (profundidad de reposo), nunca un glow — territorio claro, no
  gasta el Presupuesto de Glow porque ese presupuesto ni aplica acá (§6.6 lo acota a
  `/programa`; la landing y `/agendar` sencillamente no usan glow verde).
- **Todo esto se documenta en 6.1-bis (nueva)**, junto a 6.1: ambas páginas del
  formulario son, igual que la landing, territorio claro sin excepción.

---

## v2.4 — 2026-07-23: el PO descarta el tablero — el "90" vuelve al aro (corrige 6.4b)

v2.3 había redefinido el "90" de `/programa` como contador/tablero tipo calendario
(ejecutando lo que ya pedía v2.1). El PO probó esa versión y la descartó: **vuelve la
composición original de Bloque 4** — el "90" dentro del aro degradado encendido sobre
negro, tal como estaba antes de v2.1/v2.3. La única pieza que se conserva del intento
del tablero es el **count-up**: la cifra ahora cuenta de 0 a 90 una sola vez al entrar
en viewport, dentro del aro (antes nacía directamente en su valor final).

- **6.4(b) se corrige de nuevo**: ya no dice "contador/tablero tipo calendario" — ese
  texto describía una decisión que el PO revirtió. El "90" vive otra vez en el aro
  (`.na-programa-cifra`, no `.na-programa-contador` — clase retirada). El glow
  `--na-glow-ring` vuelve a estar en uso (dejó de estar "disponible pero sin uso").
  Encendido sigue siendo el clímax de entrada, sin cambios de marco.
- **Se conserva de v2.3**: el count-up (`countup.js`, una sola vez, nunca en loop, rama
  `prefers-reduced-motion` con el valor final estático) — ahora corriendo sobre
  `.na-programa-cifra-num` en vez de `.na-programa-contador-num`.
- **Sin cambios en la landing**: 6.3-bis/6.3-ter y Revelado del Aro (territorio claro,
  v2.2/v2.3) no se tocan — esta corrección es exclusiva de `/programa`.
- **Sin tokens nuevos.** Ninguno de los tokens usados por el tablero descartado se
  elimina (los introdujo v2/v2.1, no v2.3): siguen existiendo tal cual, ya no hay
  ningún consumidor del patrón de tablero para el "90" específicamente.

---

## v2.3 — 2026-07-23: entrada cinematográfica clara (6.4a) + el "90" como tablero (6.4b, implementado — REVERTIDO por v2.4)

> **Revertido por v2.4 (arriba) en la parte del "90".** El PO probó el
> contador/tablero tipo calendario y pidió volver al aro degradado — ver 6.4b
> corregido. La parte de esta sección sobre el hero de la landing (6.4a, Revelado
> del Aro) sigue vigente tal cual, no se tocó.

Dos cierres pendientes del rollout, uno por territorio.

- **Territorio claro — momento wow (a), reincorporado.** v2.1 lo había retirado
  ("la landing no tiene entrada cinematográfica"); se reincorpora, pero como versión
  propia de superficie clara, no como el Encendido oscuro que existía antes del
  Bloque 5. Nuevo patrón con nombre: **Revelado del Aro** (§8.1) — en fondo blanco el
  glow no sirve (Regla del Verde Legible), así que el drama es puro movimiento: el
  texto del hero pasa a Ascenso Escalonado (antes usaba Cascada genérica) y, como
  clímax, el aro escala a su lugar mientras la foto se revela adentro con un instante
  propio de rezago. Implementado en `styles.css`/`index.html`.
- **Territorio oscuro — momento wow (b), implementado.** El "90" ejecuta la
  redefinición que ya fijó v2.1: deja el aro degradado (y su glow `--na-glow-ring`,
  que sigue existiendo como token pero ya no lo usa este elemento) y pasa a ser un
  contador/tablero tipo calendario — superficie plana con hairline, cifra verde
  brillante, misma gramática que el tablero de 6.5 pero como pieza protagonista.
  Conserva Encendido como clímax de entrada (ya no anima un aro, anima la tarjeta) y
  suma count-up de una sola vez (`countup.js`, nunca en loop, rama reduced-motion con
  el valor final estático desde el primer render — el HTML ya lo trae en texto).
- **Sin tokens nuevos de color/sombra.** Solo una duración nueva: `--na-dur-revelado`
  (700ms), hermana de `--na-dur-encendido` para el clímax de territorio claro.
- **Sin cambios de copy ni de estructura de secciones** — ambos son tratamiento de
  presentación de datos/imágenes ya existentes.

---

## v2.2 — 2026-07-23: la foto del hero es pieza de diseño, no foto cruda (corrige 6.3-bis)

El PO vio la landing clara ya implementada (Sprint 4, Bloque 5) y corrigió una
instrucción propia: había pedido "color natural, sin duotono ni glow" para la foto del
hero pensando en el criterio que ya valía para "Sobre mí" (foto documental, de
confianza) — pero el hero es una pieza compuesta de diseño (el aro, el marco, la firma
de marca), no una foto suelta. Sin tratamiento se veía plana y pegada sobre el fondo
claro, con el ruido del gimnasio (pelotas, bandas) compitiendo con Andrés por la
atención.

- **Nueva receta de imaginería para territorio claro** (6.3-ter): duotono suave de
  marca — desaturación **parcial** (no total, a diferencia de 6.3) + tinte verde a
  **baja** intensidad + viñeta **clara** que aclara el fondo hacia los bordes en vez de
  oscurecerlo (dirección opuesta a la viñeta de 6.3, que es para fondo oscuro) + realce
  leve de contraste para que no se vea lavada sobre blanco.
- **6.3-bis se corrige**: ya no dice "color natural, sin duotono, sin overlay de
  tinte" — esa instrucción era la equivocada. El criterio de marco que sí sigue vigente
  tal cual (el aro degradado como marco, `--na-shadow-card` como única sombra, sin
  glow) no cambia; lo que cambia es el tratamiento de la imagen dentro del aro, ahora
  descrito en 6.3-ter.
- **"Sobre mí" NO cambia** — sigue en color natural, sin tratamiento. Es la foto
  documental que sostiene "soy una persona real" (PRODUCT.md, "Sin prueba, sin
  promesa"), no una pieza decorativa; teñirla arriesga la piel y le resta a su función
  de prueba social. Se evaluó aplicarle 6.3-ter y se descartó explícitamente por esta
  razón.
- **Sin tokens nuevos**: la receta reutiliza `--na-gradient-brand` (el mismo degradado
  ya presente en el marco) a una opacidad baja. El Presupuesto de Glow (§6.6) no se
  toca: `--na-shadow-card` sigue siendo la única sombra del elemento — el tinte y la
  viñeta son overlays de color, no sombras, y no se apila ningún `--na-glow-*`.

---

## v2.1 — 2026-07-23: el PO revisa a mitad de rollout — dirección HÍBRIDA (D-21 actualizado)

> **Corregido por v2.2 (arriba) en el punto de la foto del hero**: donde este bloque
> dice "tratamiento natural (no duotono)" para la foto del aro, leer "duotono suave de
> marca" (6.3-ter). El resto de esta sección (los dos territorios, el resto de
> decisiones) sigue vigente tal cual.

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
| `--na-glow-ring` | `0 0 44px rgba(0, 192, 80, 0.45)` | El glow del aro que enciende el "90" de `/programa` (6.4b). v2.1/v2.3 lo habían dado de baja probando un tablero plano sin aro; v2.4 revirtió esa prueba y el token volvió a su uso original. |

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
con una sola pieza nueva: la foto real de Andrés dentro del aro del hero, con un
duotono suave de marca pensado para superficie clara (6.3-bis, 6.3-ter) — no el
duotono de alto contraste de `/programa` (6.3).

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

### 6.1-bis `/agendar` y su confirmación (territorio claro, nuevo en v2.5)

`/agendar` (formulario de 3 pasos) y `/agendar/confirmacion/{token}` viven en el mismo
territorio claro que la landing — así lo fija Salas Iluminadas (§6.6): sin importar que
`/programa` sea oscuro, un formulario nunca lo es. Regla superior a cualquier otra
decisión de esta sección: **en un formulario, la claridad manda**. La energía visual
entra por el marco — encabezados, indicador de pasos, pills de horario, CTA — nunca
por los campos: inputs, labels y mensajes de validación quedan sin ningún tratamiento
que les reste legibilidad (Bootstrap `.form-control` de siempre, foco con anillo sólido
`--na-green-dark`, §9).

- **El indicador de pasos (`.na-stepper`), componente nuevo del sistema.** Una fila de
  3 dots numerados conectados por una línea: gris por defecto, degradado de marca
  (`--na-gradient-brand`) en el tramo ya completado. El estado (hecho / actual /
  pendiente) sale directo del modelo — `servicioSeleccionado`, `fechaSeleccionada` —
  nunca de JS ni de scroll, así que no depende de ningún patrón de `data-reveal`; el
  único movimiento que lleva es la transición de color al navegar entre pasos (color,
  no transform — `prefers-reduced-motion` lo conserva igual, ver 8.3). Cada paso
  expone su estado también por `aria-current="step"`, no solo por color. Reemplaza al
  número inline que cada `<h2>` de paso llevaba suelto (`.na-step-num`, retirada): con
  el overview arriba, repetirlo en cada encabezado era redundante.
- **Encabezados que comparten la firma del sitio.** El `<h1>` de la página y el `<h2>`
  de cada paso (`.na-step-title`) pasan a llevar la misma barra de degradado que
  `.na-section-title` en el resto de la landing — antes eran texto plano sin relación
  visual con las demás secciones.
- **Paso 1 (servicio)**: las cards de `.na-servicio-opcion` adoptan el mismo Tinte al
  Tacto que `.na-service-card` — rise de 4px + `--na-shadow-card-hover` al pasar el
  mouse — para que el marco del formulario se sienta del mismo sistema que Servicios.
- **Paso 3 (horario)**: el pill elegido (`.na-slot input:checked + span`) suma
  `--na-shadow-card` como profundidad de reposo. **Nunca un glow** — no porque gaste el
  Presupuesto de Glow (ese presupuesto ni aplica: está acotado a `/programa`, §6.6),
  sino porque ningún elemento de superficie clara usa glow verde, con o sin
  presupuesto de por medio.
- **Confirmación: jerarquía nueva del resumen.** Fecha y horario — el dato que el
  visitante necesita para actuar (agendarlo en su propio calendario) — pasan a **Cifra
  Protagonista**: Barlow 700, el horario en `--na-green-dark` (Regla del Verde
  Legible: profundo sobre blanco). "A nombre de" queda con el tamaño chico de siempre,
  separado por un hairline (`--na-gray-200`) — es confirmación de contexto, no el dato
  accionable. Mismos `dt`/`dd` que antes, mismo copy, mismos valores: el cambio es
  puramente de peso visual, vía CSS (`.na-confirmacion-fecha`, `.na-confirmacion-hora`).
  **El aviso honesto sobre el envío de correo no cambia una palabra** — sigue
  reflejando que el envío es best-effort y no bloquea la reserva (HU-05,
  `docs/product-backlog.md`).
- **Sin tokens nuevos.** Todo lo de arriba reutiliza tokens y componentes que ya
  existían (`--na-gradient-brand`, `--na-shadow-card`, `--na-green-dark`,
  `.na-section-title`) — Bloque 6 no introduce ninguna variable ni color.

### 6.2 `/programa`: mood y decisiones de superficie (territorio oscuro)

El fondo dominante es negro de marca en toda la página. El verde deja de ser "color de
botón" y pasa a ser fuente de luz: rim-light, glow contenido, el degradado como única
fuente de brillo. Las superficies son planas y profundas, con borde hairline
(`--na-hairline`) en vez de sombra — una sombra oscura no se ve sobre negro. Nada de
glass, texturas ni efectos 3D. *(Antes de v2.1 esta descripción regía "la apertura,
progresivamente el resto del sitio" — v2.1 la acota a `/programa`.)*

### 6.3 `/programa`: tratamiento de imaginería — Duotono Neón (territorio oscuro)

Toda foto real del PO que viva **en `/programa`** (hoy: `gym-ambiente.webp` como fondo
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
   Para fondos atmosféricos (sin sujeto que preservar, p. ej. gym-ambiente.webp)
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

**v2.1 — ya no aplica igual en la landing.** Antes de esta revisión, el plan era migrar
todas las fotos reales (incluida `andres-hero.jpeg` del hero y `andres.webp` de "Sobre
mí") a este tratamiento de alto contraste. Eso se revierte para las dos: ninguna foto
de la landing usa **este** duotono (grayscale total + tinte al 0.85 + viñeta oscura) —
la de "Sobre mí" queda en color natural sin tratamiento, y la del hero recibe en
cambio una versión propia, mucho más suave, pensada para superficie clara — ver
6.3-bis y 6.3-ter (nueva en v2.2).

### 6.3-bis La landing: la foto de Andrés en el aro (territorio claro, nuevo en v2.1)

El hero de `index.html` reemplaza el logo dentro del aro por la **foto real de
Andrés**. Es una superficie clara: se integra primero por el **marco**, no por el
color — el mismo criterio que ya rige "Sobre mí" (marco degradado + sombra de
reposo, sin glow):

- El aro degradado (`--na-gradient-brand`) sigue siendo el marco — el mismo "eco del
  aro del logo" que ya describía v1, ahora con contenido real adentro en vez del
  logotipo.
- Sombra: `--na-shadow-card` (la sombra ambiental de reposo de v1) — **no**
  `--na-glow-photo` ni ningún glow verde. Los glows son vocabulario del territorio
  oscuro (§2.5); una foto sobre fondo blanco no los necesita ni los admite sin romper
  la Regla del Verde Legible en superficie clara.
- Encuadre: igual criterio que ya se afinó para el hero oscuro (object-position +
  escala si hace falta) para que Andrés se vea reconocible dentro del círculo, no
  recortado — el objetivo compositivo no cambia, solo la paleta.

**Corregido por v2.2**: el tratamiento de la imagen misma (más allá del marco) **no**
es color natural sin retocar — es el duotono suave de 6.3-ter. La versión anterior de
esta entrada decía "nada de grayscale, nada de overlay de tinte"; esa instrucción del
PO estaba pensada para "Sobre mí" (foto documental) y no para el hero (pieza de
diseño) — ver 6.3-ter para el criterio corregido y la receta.

### 6.3-ter La landing: duotono suave de marca para el hero (territorio claro, nuevo en v2.2)

La foto del hero es una **pieza compuesta de diseño** — vive dentro del aro, junto al
resto de la composición del hero — no una foto documental suelta como la de "Sobre
mí" (que sí queda en color natural, ver nota al final). Sin tratamiento, se lee como
una foto cruda pegada sobre el fondo claro: plana, con el ruido del gimnasio (pelotas,
bandas de la imagen fuente) compitiendo con Andrés por la atención. La receta es un
duotono de marca **mucho más suave** que el de `/programa` (6.3) — misma lógica
(desaturar, teñir con el degradado, hundir el fondo), intensidad muy por debajo para
que la imagen conserve su lugar en una superficie clara:

```css
/* Contenedor que aloja el tratamiento — separado del aro (.na-hero-photo)
   para que el tinte/viñeta no bañen también la banda del marco. */
.na-hero-photo-frame {
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/* Desaturación PARCIAL (0.45, no 1 como en 6.3) + contraste leve: reconocible
   como foto real, pero ya no compite en saturación con el fondo blanco. */
.na-hero-photo-frame img {
  display: block;
  width: 100%; height: 100%;
  object-fit: cover;
  filter: grayscale(0.45) contrast(1.1) saturate(0.9);
}
/* Tinte de marca a intensidad BAJA (0.16, no 0.85 como en 6.3) */
.na-hero-photo-frame::after {
  content: ""; position: absolute; inset: 0;
  background: var(--na-gradient-brand);
  mix-blend-mode: color;
  opacity: 0.16;
}
/* Viñeta CLARA — opuesta a la de 6.3: aclara hacia los bordes en vez de
   oscurecer, hundiendo el ruido de fondo hacia el blanco de la página. */
.na-hero-photo-frame::before {
  content: ""; position: absolute; inset: 0; z-index: 1;
  background: radial-gradient(circle at 50% 24%, transparent 45%, rgba(255, 255, 255, 0.7) 100%);
}
```

Reglas duras de esta receta (obligatorias, no ajustables por bloque de implementación
sin volver a pasar por este documento):

- **Andrés debe seguir siendo reconocible** — la cara y la camiseta de marca son lo
  que vende confianza; ninguna combinación de filtro/tinte puede llevarlo a
  silueta o a irreconocible. Si un ajuste futuro de intensidad lo compromete, retroceder
  el valor, no el criterio.
- **Una sola sombra por elemento** (Presupuesto de Glow, §6.6): esta receta no agrega
  ninguna — `--na-shadow-card` en `.na-hero-photo` (6.3-bis) sigue siendo la única. El
  tinte y la viñeta son overlays de color (`mix-blend-mode`, `radial-gradient`), no
  `box-shadow`/`filter: drop-shadow`.
- **CSS vanilla**, sin librerías de imagen ni canvas — mismo mecanismo que 6.3
  (filter + pseudo-elementos), solo con otros valores y otra dirección de viñeta.
- **No es maquillaje de "Sin prueba, sin promesa"** (PRODUCT.md): el tratamiento no
  disfraza que la foto es real, solo la integra a la paleta — igual principio que 6.3,
  aplicado en la otra dirección de superficie.

**"Sobre mí" queda fuera de esta receta, a propósito.** Es la foto documental que
sostiene "soy una persona real" ante quien todavía no decidió agendar — su función es
prueba social, no composición decorativa. Teñirla arriesga la piel (el detalle que más
importa reconocer ahí) a cambio de una coherencia visual marginal. Si un bloque futuro
quiere evaluarlo de nuevo, el criterio de corte es ese: solo aplicar 6.3-ter ahí si el
resultado no tiñe la piel de forma perceptible: si lo hace, se descarta.

### 6.4 Momentos wow oficiales (uno por territorio, más el de `/programa`)

Los tres momentos wow ya vivían todos en `/programa` o en la apertura de la landing.
v2.1 los había acotado a `/programa` en solitario al retirar el (a); **v2.2/v2.3 lo
reincorporan** con una versión propia de superficie clara — los momentos wow vuelven a
repartirse entre los dos territorios, cada uno con el vocabulario que le corresponde
(glow en `/programa`, movimiento puro en la landing).

**(a) El clímax del hero — versión por territorio.** *Reincorporado por v2.2/v2.3,
implementado.* v2.1 lo había retirado por completo; se reincorpora porque la foto del
hero SÍ necesita ser un clímax de composición (6.3-ter), solo que ya no puede ser el
Encendido oscuro original:
- **Landing (territorio claro):** el texto pasa a Ascenso Escalonado (antes usaba
  Cascada genérica) y el clímax es **Revelado del Aro** (§8.1, nuevo) — el aro escala a
  su lugar y la foto se revela adentro con un instante propio de rezago. Sin glow (no
  funciona sobre blanco, Regla del Verde Legible): el drama es puro movimiento.
- **`/programa` (territorio oscuro):** sin cambios — Encendido original, glow incluido.

**(b) El "90" de `/programa` — dentro del aro, con count-up.** *v2.1 lo había
redefinido a contador/tablero tipo calendario; v2.3 llegó a implementarlo; el PO lo
probó y pidió volver a la composición original de Bloque 4 — v2.4 revierte.* El "90"
vive de nuevo **dentro del aro degradado encendido sobre negro** (`.na-programa-cifra`,
aro hueco vía `mask`, glow `--na-glow-ring` como única sombra del elemento) —
exactamente como estaba antes de v2.1. *No confundir con el tablero de 6.5* ("El
programa, en números"): son dos piezas distintas — esta es la pieza protagonista del
hero: el componente de 6.5 es la sección de datos que va después, sin cambios.
Encendido (opacity + scale) sigue siendo el clímax de entrada del aro, sin cambios.
**Lo único nuevo que sí se conserva del intento del tablero:** el número hace count-up
de una sola vez (`countup.js`, nunca en loop) dentro del aro — antes nacía
directamente en su valor final. Con `prefers-reduced-motion` o sin
`IntersectionObserver`, el HTML ya trae el valor final en texto, así que se lee
estático desde el primer render.

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
  diferenciar `/agendar` de `/programa`, que sí es oscuro.) Ver 6.1-bis para el
  detalle de cómo se actualizó su estética sin salir de este territorio.
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
2. **Hero** — H1 con la promesa ("Alimenta tu potencial: pierde grasa, gana músculo"), subtítulo con especificidad (deportistas, online, toda LATAM), CTA primario "Agendar mi cita" + CTA secundario WhatsApp. **Fondo claro (v1). Pieza nueva: la foto real de Andrés dentro del aro, con duotono suave de marca (6.3-bis/6.3-ter) y Revelado del Aro como clímax de entrada tras el Ascenso Escalonado del texto (6.4a). Implementado — v2.2/v2.3.**
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

### 8.1 Vocabulario de movimiento (con nombre) — v2.3

Seis patrones nombrados (cinco de v2 + Revelado del Aro, nuevo en v2.3), reutilizables
en todo el rollout. Todos animan **solo `transform` y `opacity`** (nunca
width/height/top); con `prefers-reduced-motion: reduce` el contenido queda visible al
instante, sin excepción.

| Patrón | Qué anima | Duración | Easing | Reduced-motion |
|---|---|---|---|---|
| **Encendido** | opacity + scale (desde 0.85–0.92, nunca desde 0); el glow del elemento ya está presente y aparece con la opacidad, no se anima aparte | `--na-dur-encendido` (850ms) | `--na-ease-out` | Visible al instante, sin scale |
| **Revelado del Aro** *(nuevo, territorio claro)* | Equivalente de Encendido sin glow: el aro escala a su lugar (opacity + scale desde 0.86) y, con 120ms de rezago fijo, la foto adentro se revela (opacity + scale desde 1.18) | `--na-dur-revelado` (700ms) | `--na-ease-out` | Visible al instante, sin scale, ambos elementos |
| **Ascenso Escalonado** | opacity + `translateY(32px→0)`, stagger ~120ms entre elementos | `--na-dur-ascenso` (550ms) | `--na-ease-out` | Visible al instante, sin desplazamiento |
| **Revelado en Cascada** | opacity + `translateY(16px→0)`, stagger 40ms, dispara una sola vez vía IntersectionObserver | `--na-dur-cascada` (280ms) | `--na-ease-out` | Visible al instante |
| **Tinte al Tacto** | color/fondo + glow o sombra-tinte + `translateY(-2px)`/`scale(0.97)` en respuesta a hover/press | `--na-dur-fast` (150ms) / `--na-dur-base` (250ms) | `--na-ease-out` | No es animación de entrada; el motion de transform igual respeta el media query |
| **Barra de Impulso Luminosa** | `scaleX(0→1)` con `transform-origin: left`, dispara una sola vez al entrar en viewport | `--na-dur-impulso` (1100ms) | `--na-ease-out` | Nace llena (`scaleX(1)` inmediato) |

**Dónde se usa cada uno (v2.3 — el vocabulario vuelve a repartirse entre los dos
territorios, cada uno con la variante que le corresponde, 6.4a):**
- *Encendido* → el "90" de `/programa` (6.4b, implementado) y, si algún bloque futuro
  lo necesita, cualquier otra pieza de `/programa` con glow propio.
- *Revelado del Aro* → exclusivo del hero de la landing (6.4a, implementado) — la
  versión de territorio claro de Encendido, sin glow.
- *Ascenso Escalonado* → el texto de ambos heros (landing y `/programa`) como preludio
  de su clímax respectivo (Revelado del Aro / Encendido).
- *Revelado en Cascada* → el reveal genérico que el sitio entero usa desde HU-01
  (`data-reveal`/`reveal.js`), en ambos territorios: landing clara y `/programa` oscuro.
- *Tinte al Tacto* → ya nombrado en DESIGN.md §5 (Elevation & Depth); hover de botones y cards, en ambos
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
| Entrada del hero de la landing | Ascenso Escalonado en el texto, clímax Revelado del Aro en la foto (6.4a, implementado — v2.3) | Ascenso Escalonado + Revelado del Aro |
| Entrada del hero de `/programa` | Ascenso Escalonado en el texto, clímax Encendido en el aro con el "90" (6.4b) | Ascenso Escalonado + Encendido |
| Cifra del "90" de `/programa` | Count-up 0→valor real al entrar en viewport, 900ms, una sola vez, nunca en loop (`countup.js`, implementado, 6.4b) | — (sin nombre propio; regla compartida con Barra de Impulso Luminosa: nunca en loop) |
| Cifras del "trust bar" (landing) / tablero de números (6.5) | Mismo criterio si un bloque futuro las anima: ≤ 1s, una sola vez, valores reales del PO. *Pendiente — hoy nacen con su valor final, sin count-up.* | — (sin nombre propio; regla compartida con Barra de Impulso Luminosa: nunca en loop) |
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
- [x] Imágenes WebP con dimensiones declaradas; lazy load bajo el fold; CLS < 0.1 (`andres-hero.webp`/`gym-ambiente.webp` llevan `loading="eager"` por ser el LCP de su hero — es la única excepción a lazy load, documentada, no un pendiente)
- [ ] Sin scroll horizontal en 375px; body ≥ 16px en móvil
- [ ] `prefers-reduced-motion` respetado en los 6 patrones de 8.1 (incluye Revelado del Aro y el count-up de `countup.js`), sin excepción
- [ ] **Presupuesto de Glow respetado** (territorio `/programa`): ninguna sección apila más de un `--na-glow-*` sobre el mismo elemento (6.6); la landing no usa glow verde en absoluto (6.3-bis)
- [ ] **Salas Iluminadas intactas**: `/agendar` y su confirmación siguen en superficie clara aunque la sección que las precede sea oscura (6.6, estética actualizada en 6.1-bis)
- [ ] Probado en 375 / 768 / 1024 / 1440px

---

*Generado con la skill `ui-ux-pro-max` (patrón Before–After Transformation + estilo Vibrant & Block-based + tipografía Barlow) el 2026-07-10. Paleta muestreada del logo real.*
*Actualizado a v2 el 2026-07-21: Dirección A "Neón de Madrugada" elegida por el PO (D-21, Sprint 4). Overrides por página: crear `design-system/pages/<pagina>.md`.*
*Actualizado a v2.1 el 2026-07-23: el PO acota la Dirección A a `/programa`; la landing vuelve a superficie clara con la foto de Andrés en el aro del hero; el "90" pasa de vivir en el aro a un contador/tablero tipo calendario (D-21 actualizado, Sprint 4).*
*Actualizado a v2.2 el 2026-07-23: el PO corrige su propia instrucción sobre la foto del hero — pieza de diseño, no foto cruda; nueva receta de imaginería en territorio claro (6.3-ter).*
*Actualizado a v2.3 el 2026-07-23: se implementan el momento (a) reincorporado (Revelado del Aro, territorio claro) y el momento (b) redefinido (el "90" como contador/tablero, territorio oscuro).*
*Actualizado a v2.4 el 2026-07-23: el PO prueba el tablero del "90" y pide volver al aro degradado de Bloque 4 — se revierte esa parte de v2.3, conservando el count-up dentro del aro.*
*Actualizado a v2.5 el 2026-07-23: `/agendar` y su confirmación pasan a la estética actualizada (Sprint 4, Bloque 6) — indicador de pasos nuevo (`.na-stepper`) y jerarquía nueva del resumen de la confirmación, ambos en territorio claro bajo Salas Iluminadas (6.1-bis, nueva).*

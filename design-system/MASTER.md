# Sistema de Diseño — Nutrición Activa

> **Fuente de verdad global (Master).** Toda página o componente del sitio debe seguir estas reglas.
> Si existe un override en `design-system/pages/<pagina>.md`, ese archivo tiene prioridad para esa página.

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

---

## 2. Paleta de color

Derivada por muestreo directo del logo: verde dominante `#00C050`, degradado hacia verde-teal `#00A870`, negro `#000000`, fondo blanco.

### 2.1 Tokens primitivos

| Token | Hex | Origen / uso |
|---|---|---|
| `--na-green` | `#00C050` | Verde brillante del logo. CTAs, acentos, iconos. **No usar para texto sobre blanco** (contraste 2.4:1) |
| `--na-green-teal` | `#00A870` | Extremo teal del degradado del logo. Acentos secundarios, hovers, degradados |
| `--na-green-dark` | `#007D3C` | Verde oscuro accesible (≈5.2:1 sobre blanco). Enlaces, texto verde, estados hover de botones |
| `--na-green-tint` | `#E6F9EE` | Verde al 8–10%. Fondos de secciones alternas, badges, iconos con fondo suave |
| `--na-black` | `#0A0A0A` | Negro de marca (figura del logo). Titulares, navbar, footer, secciones oscuras |
| `--na-gray-700` | `#3D4440` | Texto de cuerpo sobre blanco (matiz verdoso neutro) |
| `--na-gray-500` | `#6B736E` | Texto secundario, captions (4.6:1 sobre blanco) |
| `--na-gray-200` | `#DEE4E0` | Bordes, divisores |
| `--na-gray-100` | `#F4F7F5` | Fondos de cards y secciones "muted" |
| `--na-white` | `#FFFFFF` | Fondo principal (el logo vive sobre blanco) |
| `--na-whatsapp` | `#25D366` | Verde oficial de WhatsApp — usar **solo** en el botón de WhatsApp, sin recolorear |
| `--na-danger` | `#D93A3A` | Errores de formulario |
| `--na-warning` | `#B45309` | Avisos |

### 2.2 Degradado de marca

Replica el aro del logo (oscuro → brillante):

```css
--na-gradient-brand: linear-gradient(135deg, #00A870 0%, #00C050 100%);
```

Usos permitidos: fondo del hero (franja o forma geométrica), barra de acento sobre cards destacadas, aro decorativo. **No** usarlo como fondo de texto largo.

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

- Texto normal: mínimo **4.5:1**. `#00C050` sobre blanco **falla** → para texto verde usar siempre `--na-green-dark`.
- Botón primario: fondo `#00C050` + **texto negro** `#0A0A0A` (8.6:1 ✓). Nunca texto blanco sobre el verde brillante.
- Sobre secciones negras: texto `#FFFFFF` o `#00C050` (el verde brillante sí pasa sobre negro: 8.6:1 ✓).
- No transmitir información solo con color (añadir icono o texto).

---

## 3. Tipografía

Pareja atlética/condensada, ideal para marcas deportivas (recomendación del motor de estilos para fitness):

| Rol | Fuente | Pesos |
|---|---|---|
| Titulares (h1–h4, precios, cifras) | **Barlow Condensed** | 500, 600, 700 |
| Cuerpo, botones, formularios | **Barlow** | 400, 500, 600, 700 |

**Import (colocar en el `<head>` del layout Thymeleaf, antes del CSS propio):**

```html
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Barlow+Condensed:wght@500;600;700&family=Barlow:wght@400;500;600;700&display=swap" rel="stylesheet">
```

Alternativa en CSS:

```css
@import url('https://fonts.googleapis.com/css2?family=Barlow+Condensed:wght@500;600;700&family=Barlow:wght@400;500;600;700&display=swap');
```

**Escala tipográfica** (base 16px, cuerpo con `line-height: 1.6`; titulares 1.1–1.2):

| Token | Tamaño | Uso |
|---|---|---|
| `--na-fs-display` | `clamp(2.5rem, 6vw, 4.5rem)` | H1 del hero (Barlow Condensed 700, mayúsculas opcionales) |
| `--na-fs-h2` | `clamp(2rem, 4vw, 3rem)` | Títulos de sección |
| `--na-fs-h3` | `1.5rem` | Títulos de card / plan |
| `--na-fs-body` | `1rem` (16px) | Cuerpo — nunca menor en móvil |
| `--na-fs-small` | `0.875rem` | Captions, notas legales |
| `--na-fs-price` | `clamp(2rem, 3.5vw, 2.75rem)` | Cifras de precios (Barlow Condensed 700 + `font-variant-numeric: tabular-nums`) |

Longitud de línea del cuerpo: 60–75 caracteres (`max-width: 65ch` en párrafos largos).

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

---

## 5. Bloque `:root` listo para usar

Colocar en `src/main/resources/static/css/tokens.css`, cargado **después** del CSS de Bootstrap (WebJars) para que los overrides ganen:

```css
:root {
  /* Primitivos de marca */
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

  /* Tipografía */
  --na-font-heading: "Barlow Condensed", "Arial Narrow", sans-serif;
  --na-font-body: "Barlow", "Helvetica Neue", Arial, sans-serif;
  --na-fs-display: clamp(2.5rem, 6vw, 4.5rem);
  --na-fs-h2: clamp(2rem, 4vw, 3rem);
  --na-fs-h3: 1.5rem;
  --na-fs-price: clamp(2rem, 3.5vw, 2.75rem);

  /* Espaciado */
  --na-space-1: 4px;  --na-space-2: 8px;   --na-space-3: 16px;
  --na-space-4: 24px; --na-space-5: 32px;  --na-space-6: 48px;
  --na-space-7: 64px; --na-space-8: 96px;

  /* Forma y elevación */
  --na-radius-sm: 8px;      /* inputs, badges */
  --na-radius-md: 16px;     /* cards */
  --na-radius-pill: 999px;  /* botones y chips */
  --na-shadow-card: 0 4px 16px rgba(10, 10, 10, 0.08);
  --na-shadow-card-hover: 0 10px 28px rgba(0, 192, 80, 0.18);

  /* Movimiento */
  --na-ease-out: cubic-bezier(0.16, 1, 0.3, 1);
  --na-dur-fast: 150ms;
  --na-dur-base: 250ms;

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

**Dirección: "Energía atlética por bloques"** — vibrante y de alto contraste, estructurado en secciones bien delimitadas que alternan blanco / gris claro / negro.

- **Secciones en bloques** con mucho aire (64–96px vertical). Alternancia: hero blanco → servicios `--na-gray-100` → transformaciones negro → precios blanco.
- **Formas geométricas de acento**: arcos y círculos que eco­an el aro del logo (CSS puro o SVG), con el degradado de marca. Máximo 1–2 por sección.
- **Cards**: fondo blanco, `--na-radius-md`, `--na-shadow-card`, borde superior de 4px con el degradado en la card destacada (plan recomendado).
- **Botones**: pill (`--na-radius-pill`). Primario = verde + texto negro; secundario = outline negro; sobre fondo negro, el primario verde brilla al máximo.
- **Iconos**: SVG de un solo set (Bootstrap Icons vía WebJar o Lucide), trazo consistente, **nunca emojis**. En verde `--na-green-dark` o dentro de círculos `--na-green-tint`.
- **Fotografía**: real y de alta energía (atletas entrenando, comida real). Duotono negro/verde permitido para fondos del hero. Formato WebP, `loading="lazy"` bajo el fold, `width`/`height` declarados.
- **Cifras protagonistas**: métricas de resultados ("−8 kg de grasa", "+4 kg músculo") en Barlow Condensed gigante — es el corazón del patrón before–after.
- **Evitar** (anti-patrones del estilo): sombras complejas apiladas, efectos 3D, paletas apagadas, secciones densas sin aire, degradado de marca como fondo de párrafos.

---

## 7. Landing page — orden de secciones

Patrón *Before–After Transformation* (optimizado a conversión para servicios de transformación física):

1. **Navbar** (sticky, blanca, logo izquierda) — enlaces: Servicios · Resultados · Precios · CTA "Agendar cita" (btn-primary, siempre visible).
2. **Hero** — H1 con la promesa ("Alimenta tu potencial: pierde grasa, gana músculo"), subtítulo con especificidad (deportistas, online, toda LATAM), CTA primario "Agendar mi cita" + CTA secundario WhatsApp. Foto/figura atlética con arco degradado.
3. **Barra de confianza** — cifras rápidas: años de experiencia, clientes transformados, países atendidos.
4. **Transformaciones (before–after)** — comparativas reales con métricas concretas; antes en gris desaturado, después a color con acentos verdes. La sección de mayor peso visual (fondo negro).
5. **Cómo funciona** — 3 pasos: 1) Agenda tu evaluación → 2) Recibe tu plan personalizado → 3) Seguimiento y ajustes. Iconos + números grandes.
6. **Servicios y precios** — cards de planes (4, catálogo final del PO 2026-07-12), plan recomendado destacado con borde degradado y badge; precio en grande, lista de incluye, CTA por card. Aclarar moneda (USD/CRC) por el público LATAM.
7. **Testimonios** — citas breves con foto, nombre, deporte y resultado medible.
8. **FAQ** — accordion de Bootstrap: consultas online, formas de pago, tiempos de resultados.
9. **CTA final** — franja negra o degradado, eslogan + botón "Agendar cita" + botón WhatsApp.
10. **Footer** — logo, contacto, redes, aviso legal.

**Flotante:** botón de WhatsApp fijo (`position: fixed`, inferior derecha, 56×56px mínimo, `#25D366`, icono oficial blanco, `aria-label="Escribir por WhatsApp"`), separado del borde con safe-area y sin tapar CTAs.

---

## 8. Micro-interacciones — tono y especificación

**Tono: enérgico pero controlado.** El movimiento comunica impulso (la marca es acción física), pero nunca circo: transiciones cortas, con propósito, una idea animada por sección.

| Interacción | Especificación |
|---|---|
| Hover botón primario | Fondo → `--na-green-dark` + `transform: translateY(-2px)`, `250ms` ease-out |
| Press/tap botón | `transform: scale(0.97)`, `150ms`; feedback visual < 100ms |
| Hover card | Elevación a `--na-shadow-card-hover` (tinte verde) + `translateY(-4px)`, `250ms` |
| Entrada de secciones al scroll | Fade + `translateY(16px→0)`, `250–300ms`, stagger de 40ms entre cards (IntersectionObserver, una sola vez) |
| Cifras del "trust bar" | Count-up al entrar en viewport, ≤ 1s, una sola vez |
| Reveal before–after | Slider o crossfade controlado por el usuario — nunca autoplay |
| Botón WhatsApp | Sutil pulse del anillo cada ~6s (opacity/transform only); se detiene tras la primera interacción |
| Submit de formularios | Botón deshabilitado + spinner durante el envío; éxito con check verde; error en rojo junto al campo |
| Enlaces navbar | Subrayado animado de izquierda a derecha, `150ms` |

**Reglas duras:**

- Duraciones 150–300ms; nada > 400ms. Easing: `--na-ease-out` para entradas, ease-in para salidas (salidas ~30% más cortas).
- Animar **solo** `transform` y `opacity` (nunca width/height/top — evita reflow y CLS).
- Respetar `prefers-reduced-motion: reduce`: desactivar entradas al scroll, pulse y count-up.
- Máximo 1–2 elementos animados por vista; el contenido debe ser legible sin esperar animaciones.

---

## 9. Accesibilidad y calidad — checklist de entrega

- [ ] Contraste: texto normal ≥ 4.5:1 (texto verde solo con `--na-green-dark`; nunca `#00C050` sobre blanco)
- [ ] Botón primario verde siempre con texto negro
- [ ] Targets táctiles ≥ 44×44px (botones, navbar, WhatsApp flotante)
- [ ] Labels visibles en el formulario de citas (no solo placeholder); errores bajo el campo con `role="alert"`; tipos semánticos (`email`, `tel`, `date`)
- [ ] Focus visible en todos los interactivos (anillo `rgba(0,192,80,.4)`, 3px) — no eliminar outline
- [ ] Un solo H1 (hero); jerarquía h1→h2→h3 sin saltos
- [ ] `alt` descriptivo en fotos de transformaciones; `aria-label` en botones de solo icono
- [ ] Imágenes WebP con dimensiones declaradas; lazy load bajo el fold; CLS < 0.1
- [ ] Sin scroll horizontal en 375px; body ≥ 16px en móvil
- [ ] `prefers-reduced-motion` respetado
- [ ] Probado en 375 / 768 / 1024 / 1440px

---

*Generado con la skill `ui-ux-pro-max` (patrón Before–After Transformation + estilo Vibrant & Block-based + tipografía Barlow) el 2026-07-10. Paleta muestreada del logo real. Overrides por página: crear `design-system/pages/<pagina>.md`.*

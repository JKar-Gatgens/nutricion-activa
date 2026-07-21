# Dirección A — "Neón de Madrugada" *(la más audaz)*

> Brief de dirección estética para el Sprint 4 (HU-09a, temática gym — D-18).
> Cata visual: `catas/cata-a-neon-de-madrugada.html` (abrir en navegador).
> Restricciones compartidas: paleta y logo intocables, estructura de secciones intocable, CSS/JS vanilla, AA, `prefers-reduced-motion`, sin librerías nuevas, sin stock.

## Mood

El gimnasio a las 5 de la mañana: todo apagado menos vos. Oscuridad total, un solo color encendido, la sensación de que el mundo todavía duerme y vos ya estás entrenando. Es la página que se siente como ponerse los audífonos antes de la primera serie.

## Decisiones concretas

- **Fondo dominante: negro marca en toda la página.** Hoy el negro es acento (trustbar, cierre); acá se invierte la polaridad — el sitio ES negro y el blanco pasa a ser el acento de secciones puntuales. Justificación de sistema, no capricho: DESIGN.md ya documenta que el verde eléctrico rinde su máximo sobre negro (8.6:1) y hoy casi nunca lo aprovecha.
- **Papel del verde:** la única luz encendida. Deja de ser "color de botón" y pasa a ser fuente de luz: rim-light en el aro, glow contenido (una sola sombra verde por elemento, nunca apiladas), el degradado del aro como único gradiente.
- **Tipografía:** Barlow Condensed a escala póster — display a `clamp(3.5rem, 8vw, 6rem)`, tracking apretado, mayúsculas; el H1 de `/programa` recupera su jerarquía por tamaño bruto. Cuerpo en Barlow 400 `#DEE4E0` sobre negro (AA holgado).
- **Superficies:** planas profundas con borde hairline `rgba(255,255,255,.08)` — sin glass, sin texturas. La elevación es luz: hover = el borde se enciende en verde.
- **Movimiento: cinematográfico orquestado.** Una sola secuencia de entrada del hero (fade + rise escalonado, 400–600ms, ease-out fuerte, IntersectionObserver vanilla); el resto de la página quieta. Con reduced-motion: todo visible al instante.
- **Imaginería:** sin fotos hasta que el PO entregue material; cuando llegue, tratamiento "madrugada": foto casi a oscuras con rim-light verde vía `filter` + `mix-blend-mode` CSS. Mientras tanto, el aro como forma luminosa ES la imaginería.

## 3 momentos wow

1. **La entrada del hero**: página negra, el aro se enciende (el degradado aparece con un fade de opacidad) y la tipografía condensada sube escalonada — el "se prendió la luz del gym".
2. **El 90 dentro del aro en `/programa`**: la cifra blanca gigante dentro del aro degradado brillando sobre negro — el momento wow que el critique baseline pidió, en su versión de máximo contraste.
3. **Los módulos como cuenta regresiva luminosa**: la banda "Día 1–30 / 31–60 / 61–90" es una barra de progreso con el degradado del aro que se llena al hacer scroll (transform/opacity puro) — la Barra de Impulso convertida en metáfora del producto.

## Riesgos y mitigación

1. **Legibilidad en textos largos sobre negro**: cuerpo nunca bajo `#DEE4E0`, line-height +0.05, medida 65ch — y las secciones de lectura densa (formulario de `/agendar`) se mantienen claras como **"salas iluminadas"** sin romper la dirección. *La cata evidencia esta mitigación con una franja clara que incluye card de servicio y campo de formulario: el PO debe ver que los formularios no serán negros.*
2. **El glow degenera en cyberpunk kitsch**: presupuesto duro de 1 sombra verde por elemento, cero text-shadow en cuerpo, cero scanlines/glitch.
3. **Percepción de "pesado"**: todo es CSS (gradientes y sombras, cero imágenes nuevas), performance igual a hoy.
4. **AA**: verde brillante solo sobre negro (regla existente), verde tinte desaparece de fondos oscuros, focus ring pasa a blanco 3px sobre negro.

## Hallazgos del baseline que ataca

H1 invertido (escala póster), el aro ausente (el aro como luz es la firma de la dirección), el 90 sin componer (momento wow 2), módulos sin tratamiento de secuencia (momento wow 3), tercio medio genérico (la polaridad oscura mata la lectura "plantilla blanca de nutricionista").

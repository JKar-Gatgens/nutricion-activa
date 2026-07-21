# Dirección C — "Peso Completo" *(el intermedio distinto — riesgo tipográfico, no cromático)*

> Brief de dirección estética para el Sprint 4 (HU-09a, temática gym — D-18).
> Cata visual: `catas/cata-c-peso-completo.html` (abrir en navegador).
> Restricciones compartidas: paleta y logo intocables, estructura de secciones intocable, CSS/JS vanilla, AA, `prefers-reduced-motion`, sin librerías nuevas, sin stock.

## Mood

El cartel de una velada de boxeo pegado en la pared del gimnasio: tinta negra sobre papel blanco, letras enormes que se leen desde la otra acera, cero decoración. La confianza del que no necesita adornos — el rigor clínico de la marca llevado a volumen de póster.

## Decisiones concretas

- **Fondo dominante: blanco, más blanco que hoy.** Se eliminan los grises de superficie como fondo de sección; la alternancia pasa a ser blanco/negro puro, sin pasos intermedios. El gris queda solo para texto secundario.
- **Papel del verde:** un solo golpe por viewport — la palabra clave del titular en verde profundo, la Barra de Impulso, el CTA. Estrategia "acento ≤10%" ejecutada con violencia: cuando aparece verde, ES la acción o ES el dato.
- **Tipografía: el diseño ES la tipografía.** Display sube a `clamp(3rem, 9vw, 6rem)` peso 700 condensado, titulares edge-to-edge que casi tocan los márgenes, jerarquía por escala brutal (ratio ≥1.4 entre pasos). Los títulos de sección dejan de preguntar y afirman como cartel de pelea: "90 DÍAS. 3 BLOQUES." / "TODO ESTO INCLUYE." / "¿VAS A ENTRENAR EN SERIO?" — el voseo costarricense como estilo de cartel.
- **Superficies:** ninguna. Cero sombras, cero cards donde no haya afordancia real — el grid de 6 ítems de incluye se convierte en dos columnas tipográficas con numeración (01–06) estilo ficha técnica, matando el "grid de features" del baseline sin iconos-círculo.
- **Movimiento: casi estático con 1-2 golpes.** Reveal de titulares por `clip-path` (la letra "sube" desde una línea, 300ms) y estados de press fuertes (`scale(0.97)` ya existente). Nada más se mueve. Es la dirección más rápida y la más robusta con reduced-motion.
- **Imaginería:** la foto de Andrés en alto contraste B/N (filtro CSS `grayscale + contrast`) integrada al cartel como los retratos de los boxeadores — un humano por página, tratamiento de póster, sin stock jamás.

## 3 momentos wow

1. **El hero-cartel del index**: "PERDÉ GRASA. GANÁ MÚSCULO." edge-to-edge en dos líneas que llenan el viewport, con "GANÁ MÚSCULO" en verde profundo — la promesa leída desde la otra acera.
2. **El 90 de `/programa` a escala de pared**: la cifra ocupando el alto completo del hero junto al aro en línea fina (el aro como trazo, no como glow) — la versión "tinta" del momento wow del baseline.
3. **El precio como cartel de bolsa**: "₡105 000" en el tamaño tipográfico más grande de todo el sitio — la Cifra Protagonista llevada a su conclusión lógica: si el número es el héroe, que sea ENORME.

## Riesgos y mitigación

1. **Frialdad — perder la cercanía de marca**: el voseo hace el trabajo emocional (los titulares hablan como Andrés, no como cartel genérico) y el retrato B/N mete al humano; se valida con el PO en las variaciones.
2. **Overflow tipográfico en móvil**: los clamp se testean a 375px con el copy real — regla dura: si un titular parte mal, se reescribe el copy, no se encoge la letra.
3. **"Brutalism" mal entendido**: se conservan radios píldora en botones y focus rings del sistema — es póster, no anti-diseño.

## Hallazgos del baseline que ataca

Composición genérica del tercio medio (el único ataque que no depende de color ni motion: mata cards clónicas, grid de features e interrogativa de un solo golpe tipográfico), el 90 sin componer (momento wow 2), Cifra Protagonista subutilizada (momento wow 3), chunking de los 6 ítems (ficha técnica 01–06 en dos columnas).

# Dirección B — "Dorsal 90" *(la equilibrada — evolución, no revolución)*

> Brief de dirección estética para el Sprint 4 (HU-09a, temática gym — D-18).
> Cata visual: `catas/cata-b-dorsal-90.html` (abrir en navegador).
> Restricciones compartidas: paleta y logo intocables, estructura de secciones intocable, CSS/JS vanilla, AA, `prefers-reduced-motion`, sin librerías nuevas, sin stock.

## Mood

Día de competencia: el dorsal prendido a la camiseta, el cronómetro corriendo, el tablero de resultados. La página se siente como un evento deportivo bien organizado — energía real, pero con números, no con gritos.

## Decisiones concretas

- **Fondo dominante: mixto, la alternancia actual endurecida.** Se conservan los bloques blanco → gris → negro (el patrimonio del MASTER §6) pero los bloques negros crecen de franjas a secciones protagonistas, y cada bloque tiene un "dato de tablero" grande.
- **Papel del verde:** señalética de competencia — el color del cronómetro y del dorsal. Aparece en cifras, en la Barra de Impulso (que se convierte en sistema: barras de progreso reales con anchos que significan algo) y en los CTA. Nada de verde decorativo.
- **Tipografía:** la Cifra Protagonista se vuelve gramática de página: cada sección abre con su número compuesto como dorsal (el `tabular-nums` condensado gigante con un marco fino tipo dorsal de tela). "¿Cómo funciona?" muere y lo reemplaza el lenguaje de tablero: "90 DÍAS · 3 BLOQUES", "6 ENTREGABLES", "₡35.000 / MÓDULO" — ataque directo a la tríada interrogativa genérica del baseline.
- **Superficies:** planas duras, bordes 2px en vez de sombras suaves; las cards de servicios conservan sus rieles pero ganan el marco de dorsal.
- **Movimiento: sutil con propósito** — count-up de cifras al entrar en viewport (una vez, ≤1s, ya previsto en MASTER §8 y nunca implementado), barras que se llenan, cronómetro que "arranca" en el hero. Reduced-motion: cifras estáticas en su valor final.
- **Imaginería:** duotono negro/verde (ya permitido por MASTER §6) para la foto de Andrés y las futuras fotos del PO — el tratamiento unifica cualquier calidad de foto que llegue, sin stock.

## 3 momentos wow

1. **El cronómetro del hero**: el eslogan acompañado de un contador que arranca en 00:00 y se detiene en 90:00 (count-up de una vez) — "tus 90 días empiezan cuando vos digás".
2. **La timeline de módulos como tabla de parciales**: 1→2→3 conectados por la barra de progreso, cada uno con su "parcial" (Día 1–30…), como los splits de una carrera — la secuencia dibujada como secuencia, exactamente lo que pidió el baseline.
3. **El hover de las cards de servicio**: el marco de dorsal se "prende" (borde a verde + la cifra del precio hace un tick de count-up de 2-3 dígitos) — Tinte al Tacto evolucionado.

## Riesgos y mitigación

1. **Quedarse corta — "más de lo mismo"**: el criterio de éxito es que el tablero/dorsal aparezca en TODAS las secciones (si solo toca el hero, falló; se audita contra el baseline con re-critique).
2. **Count-ups como ruido**: solo cifras que son datos reales del PO, una vez, nunca en loop.
3. **AA**: sin cambios de pares de color — hereda los contrastes ya verificados del sistema actual; es la dirección de menor riesgo de accesibilidad.

## Hallazgos del baseline que ataca

Tríada interrogativa genérica (muere y la reemplaza el lenguaje de tablero), módulos sin secuencia (momento wow 2), la repetición ×4 de "90 días · 3 módulos" (cada aparición se vuelve un dato de tablero distinto: duración, parciales, precio/módulo), H1 (el dorsal del hero manda por composición), count-up del MASTER §8 nunca implementado.

package com.nutricionactiva.model;

import java.util.List;

/**
 * Módulo del programa "Fuerte y Definido" (HU-12, D-16/D-19 en
 * {@code docs/product-backlog.md}).
 * <p>
 * {@code descripcion} admite {@code null} mientras el PO no haya diseñado el
 * contenido de ese módulo (D-19: el programa se construye por fases, ~30%
 * desarrollado): la vista muestra solo la estructura (nombre y duración), sin
 * texto placeholder ni "próximamente" — solo se publica el nivel de detalle
 * que ya existe (principio D-11, "sin prueba, sin promesa").
 * <p>
 * {@code detalles} es la lista de entregables/contenidos del módulo; vacía
 * significa lo mismo que {@code descripcion == null}: aún no hay detalle
 * publicable. Activar el detalle es agregar datos en {@code ProgramaService},
 * cero cambios de vista (HU-13).
 */
public record Modulo(
        int numero,
        String nombre,
        int duracionDias,
        String descripcion,
        List<String> detalles
) {
    public Modulo {
        // Normaliza los dos "sin detalle todavía" a una sola representación:
        // la vista pregunta descripcion != null y detalles.isEmpty(), así que
        // un "" o un null accidentales renderizarían un <p> vacío o fallarían
        // con NPE en vez de mostrar solo la estructura (code review HU-12).
        if (descripcion != null && descripcion.isBlank()) {
            descripcion = null;
        }
        detalles = detalles == null ? List.of() : List.copyOf(detalles);
    }
}

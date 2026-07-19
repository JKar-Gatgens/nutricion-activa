package com.nutricionactiva.model;

import java.util.List;

/**
 * El programa "Fuerte y Definido" (HU-12, D-16 en
 * {@code docs/product-backlog.md}): producto estrella de la página.
 * <p>
 * Todos los datos provienen de la propuesta aprobada por el PO el 16 de julio
 * de 2026 (D-16): nada acá es inventado ni negociable desde la vista. El
 * detalle progresivo de los módulos llega como datos (D-19), ver
 * {@link Modulo}.
 */
public record Programa(
        String nombre,
        String eslogan,
        String descripcion,
        int precioColones,
        int duracionDias,
        String mensajeVenta,
        String poblacion,
        List<String> incluye,
        List<Modulo> modulos
) {
}

package com.nutricionactiva.model;

/**
 * Testimonio de un cliente para la sección de prueba social (HU-10).
 * <p>
 * {@code detalle} es opcional ({@code null} si no aplica): un resultado
 * medible corto como "Perdió 8kg en 4 meses". {@code nombrePublico} es el
 * nombre o inicial que el cliente autorizó publicar, nunca su nombre legal
 * completo salvo permiso explícito.
 */
public record Testimonio(
        String nombrePublico,
        String texto,
        String detalle
) {
}

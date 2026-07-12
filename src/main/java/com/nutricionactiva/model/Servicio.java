package com.nutricionactiva.model;

/**
 * Servicio del catálogo de Nutrición Activa.
 * Los campos {@code precioColones} y {@code duracionMinutos} admiten {@code null}
 * cuando el dato está pendiente de confirmación del PO: la vista los muestra
 * como marcador pendiente, nunca inventa valores.
 * <p>
 * {@code destacado} marca el servicio recomendado (la vista no conoce ids) y
 * {@code orden} define la posición de exhibición en el catálogo.
 */
public record Servicio(
        String id,
        String nombre,
        String descripcion,
        Integer precioColones,
        Integer duracionMinutos,
        String modalidad,
        boolean destacado,
        int orden
) {
}

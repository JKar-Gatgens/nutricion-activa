package com.nutricionactiva.model;

/**
 * Servicio del catálogo de Nutrición Activa.
 * <p>
 * {@code precioColones} admite {@code null} solo si el dato estuviera pendiente
 * de confirmación del PO: la vista lo muestra como marcador pendiente, nunca
 * inventa valores. {@code duracionMinutos} en {@code null} significa que la
 * duración NO aplica (servicios tipo plan, sin sesión): la vista omite el
 * metadato por completo.
 * <p>
 * {@code destacado} marca el servicio recomendado (la vista no conoce ids) y
 * {@code orden} define la posición de exhibición en el catálogo.
 * <p>
 * {@code agendable} indica si el servicio se reserva con horario propio
 * (consultas) o se solicita por WhatsApp (planes tipo protocolo/rutina, sin
 * sesión de duración fija).
 */
public record Servicio(
        String id,
        String nombre,
        String descripcion,
        Integer precioColones,
        Integer duracionMinutos,
        String modalidad,
        boolean destacado,
        int orden,
        boolean agendable
) {
}

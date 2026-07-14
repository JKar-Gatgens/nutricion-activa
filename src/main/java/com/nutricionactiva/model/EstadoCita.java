package com.nutricionactiva.model;

/**
 * Estado de una {@link Cita}. Hoy solo existe {@code CONFIRMADA} (HU-04,
 * Sprint 2): toda cita nace confirmada, sin registro de usuario. Sprint 3
 * agrega más estados (cancelación, reprogramación) — se suman como literales
 * nuevos, sin tocar el tipo de columna ({@code VARCHAR}, mapeado con
 * {@code @Enumerated(EnumType.STRING)}).
 */
public enum EstadoCita {
    CONFIRMADA
}

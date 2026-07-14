package com.nutricionactiva.model;

import java.time.LocalTime;

/**
 * Un horario de inicio/fin disponible para reservar, expresado en hora de
 * Costa Rica (la zona en la que piensa el negocio y en la que se le muestra
 * al visitante). La conversión a/desde UTC vive únicamente en
 * {@link com.nutricionactiva.service.DisponibilidadService}.
 */
public record FranjaHoraria(LocalTime inicio, LocalTime fin) {
}

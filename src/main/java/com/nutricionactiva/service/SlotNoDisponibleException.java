package com.nutricionactiva.service;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * El horario solicitado ya no está disponible: se ocupó (por otra reserva
 * concurrente u otro motivo) entre que el visitante lo vio libre y el momento
 * en que {@code ReservaCitaService} recalculó la disponibilidad con el
 * candado de {@code agenda_dia} en mano
 * (docs/arquitectura-agendamiento.md, sección 3).
 */
public class SlotNoDisponibleException extends RuntimeException {

    public SlotNoDisponibleException(LocalDate fecha, LocalTime horaInicio) {
        super("El horario " + horaInicio + " del " + fecha + " ya no está disponible");
    }
}

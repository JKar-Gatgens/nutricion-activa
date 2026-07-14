package com.nutricionactiva.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.nutricionactiva.model.FranjaHoraria;
import com.nutricionactiva.repository.AgendaDiaRepository;
import com.nutricionactiva.repository.CitaRepository;

/**
 * Test unitario puro (sin Spring, sin BD) del camino de rechazo: cubre el
 * caso en que, con el candado de {@code agenda_dia} ya en mano, el horario
 * solicitado ya no aparece en la disponibilidad recalculada. El caso 7 del
 * catálogo de 8 casos (docs/arquitectura-agendamiento.md) — la reserva
 * simultánea real contra MySQL con Testcontainers — lo agrega el agente
 * test-automator por separado.
 */
class ReservaCitaServiceTest {

    private final AgendaDiaRepository agendaDiaRepository = mock(AgendaDiaRepository.class);
    private final DisponibilidadService disponibilidadService = mock(DisponibilidadService.class);
    private final CitaRepository citaRepository = mock(CitaRepository.class);

    private final ReservaCitaService reservaCitaService =
            new ReservaCitaService(agendaDiaRepository, disponibilidadService, citaRepository);

    @Test
    void slotYaNoDisponibleLanzaExcepcionYNoGuardaNada() {
        LocalDate fecha = LocalDate.of(2026, 7, 20);
        LocalTime horaSolicitada = LocalTime.of(14, 0);

        // El candado ya en mano, la disponibilidad recalculada ya no incluye
        // las 14:00 (otra reserva se la llevo primero).
        when(disponibilidadService.calcularSlotsDisponibles("consulta-nutricion", fecha))
                .thenReturn(List.of(new FranjaHoraria(LocalTime.of(15, 0), LocalTime.of(16, 30))));

        assertThatThrownBy(() -> reservaCitaService.reservar(
                "consulta-nutricion", fecha, horaSolicitada,
                "Beto", "beto@correo.com", "8888-1111", "quiero bajar de peso"))
                .isInstanceOf(SlotNoDisponibleException.class);

        verify(agendaDiaRepository).asegurarFilaConCandado(fecha);
        verify(citaRepository, never()).save(any());
    }
}

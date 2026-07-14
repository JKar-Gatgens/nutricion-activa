package com.nutricionactiva.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.nutricionactiva.model.Cita;
import com.nutricionactiva.model.FranjaHoraria;
import com.nutricionactiva.model.Servicio;
import com.nutricionactiva.repository.CitaRepository;

/**
 * Cubre los casos 1 a 6 del catálogo de 8 casos de
 * docs/arquitectura-agendamiento.md (los casos 7 y 8, concurrencia y
 * validación del formulario, llegan en los pasos 5 y 6). Test unitario puro
 * (sin Spring, sin BD): {@link CatalogoServicios} y {@link CitaRepository}
 * se mockean, y el reloj se fija para que las reglas de "hoy"/domingo sean
 * deterministas sin importar cuándo se corra la suite.
 */
class DisponibilidadServiceTest {

    private static final ZoneId ZONA_NEGOCIO = DisponibilidadService.ZONA_NEGOCIO;
    private static final Clock RELOJ_FIJO = Clock.fixed(Instant.parse("2026-07-14T12:00:00Z"), ZONA_NEGOCIO);

    private static final Servicio CONSULTA_NUTRICION = new Servicio(
            "consulta-nutricion", "Consulta de nutrición", "desc", 30_000, 90,
            "Virtual o a domicilio", true, 1, true);

    private static final Servicio CONSULTA_SEGUIMIENTO = new Servicio(
            "consulta-seguimiento", "Consulta de seguimiento", "desc", 30_000, 40,
            "Virtual o a domicilio", false, 2, true);

    private static final Servicio PROTOCOLO_NO_AGENDABLE = new Servicio(
            "protocolo-competencias", "Protocolo de competencia", "desc", 22_000, null,
            "Virtual", false, 3, false);

    private final CatalogoServicios catalogoServicios = mock(CatalogoServicios.class);
    private final CitaRepository citaRepository = mock(CitaRepository.class);
    private final DisponibilidadService servicio =
            new DisponibilidadService(catalogoServicios, citaRepository, RELOJ_FIJO);

    private static LocalDate hoyCR() {
        return LocalDate.now(RELOJ_FIJO);
    }

    private static LocalDate proximoLunes() {
        return hoyCR().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }

    private static LocalDate proximoDomingo() {
        return hoyCR().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
    }

    @BeforeEach
    void sinCitasPorDefecto() {
        when(citaRepository.findConfirmadasEntre(any(), any())).thenReturn(List.of());
    }

    @Test
    void servicioInexistenteLanzaExcepcion() {
        when(catalogoServicios.buscarPorId("no-existe")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> servicio.calcularSlotsDisponibles("no-existe", proximoLunes()))
                .isInstanceOf(ServicioNoAgendableException.class);
    }

    @Test
    void servicioNoAgendableLanzaExcepcion() {
        when(catalogoServicios.buscarPorId("protocolo-competencias"))
                .thenReturn(Optional.of(PROTOCOLO_NO_AGENDABLE));

        assertThatThrownBy(() -> servicio.calcularSlotsDisponibles("protocolo-competencias", proximoLunes()))
                .isInstanceOf(ServicioNoAgendableException.class);
    }

    @Test
    void servicioAgendableSinDuracionLanzaExcepcionEnVezDeNpe() {
        // Invariante rota a proposito: agendable=true pero duracion nula. No
        // deberia pasar con el seed real (V1), pero si algun dia se rompe, no
        // debe explotar con un NullPointerException al deshacer el boxing.
        Servicio agendableMalConfigurado = new Servicio(
                "consulta-corrupta", "Consulta corrupta", "desc", 30_000, null,
                "Virtual o a domicilio", false, 5, true);
        when(catalogoServicios.buscarPorId("consulta-corrupta"))
                .thenReturn(Optional.of(agendableMalConfigurado));

        assertThatThrownBy(() -> servicio.calcularSlotsDisponibles("consulta-corrupta", proximoLunes()))
                .isInstanceOf(ServicioNoAgendableException.class);
    }

    @Test
    void domingoNoOfreceHorarios() {
        when(catalogoServicios.buscarPorId("consulta-nutricion")).thenReturn(Optional.of(CONSULTA_NUTRICION));

        List<FranjaHoraria> slots = servicio.calcularSlotsDisponibles("consulta-nutricion", proximoDomingo());

        assertThat(slots).isEmpty();
    }

    @Test
    void anticipacionMinimaExcluyeHoyYFechasPasadas() {
        when(catalogoServicios.buscarPorId("consulta-nutricion")).thenReturn(Optional.of(CONSULTA_NUTRICION));

        assertThat(servicio.calcularSlotsDisponibles("consulta-nutricion", hoyCR())).isEmpty();
        assertThat(servicio.calcularSlotsDisponibles("consulta-nutricion", hoyCR().minusDays(1))).isEmpty();
    }

    @Test
    void limiteDeLas18HorasEsInclusivo() {
        when(catalogoServicios.buscarPorId("consulta-nutricion")).thenReturn(Optional.of(CONSULTA_NUTRICION));

        List<FranjaHoraria> slots = servicio.calcularSlotsDisponibles("consulta-nutricion", proximoLunes());

        // 90 min: el ultimo slot valido empieza a las 16:30 (termina exactamente a las 18:00, limite inclusivo).
        assertThat(slots).contains(new FranjaHoraria(LocalTime.of(16, 30), LocalTime.of(18, 0)));
        // 17:00 + 90min = 18:30, se pasa del cierre: no debe ofrecerse.
        assertThat(slots).noneMatch(f -> f.inicio().equals(LocalTime.of(17, 0)));
    }

    @Test
    void solapamientoEntreServiciosDeDistintaDuracionExcluyeElSlot() {
        when(catalogoServicios.buscarPorId("consulta-seguimiento"))
                .thenReturn(Optional.of(CONSULTA_SEGUIMIENTO));

        LocalDate fecha = proximoLunes();
        // Cita existente de 90 min, 10:00-11:30 CR (otro servicio, otra duracion).
        Instant inicioOcupadoUtc = ZonedDateTime.of(fecha, LocalTime.of(10, 0), ZONA_NEGOCIO).toInstant();
        Instant finOcupadoUtc = ZonedDateTime.of(fecha, LocalTime.of(11, 30), ZONA_NEGOCIO).toInstant();
        Cita citaExistente = new Cita("consulta-nutricion", 90, "Ana", "ana@correo.com", "8888-0000",
                "motivo", inicioOcupadoUtc, finOcupadoUtc);
        when(citaRepository.findConfirmadasEntre(any(), any())).thenReturn(List.of(citaExistente));

        List<FranjaHoraria> slots = servicio.calcularSlotsDisponibles("consulta-seguimiento", fecha);

        // Los candidatos de 40 min que chocarian con la cita 10:00-11:30 no deben aparecer.
        assertThat(slots).noneMatch(f -> f.inicio().equals(LocalTime.of(10, 0)));
        assertThat(slots).noneMatch(f -> f.inicio().equals(LocalTime.of(10, 30)));
        // Un horario bien separado si debe seguir disponible.
        assertThat(slots).contains(new FranjaHoraria(LocalTime.of(9, 0), LocalTime.of(9, 40)));
    }

    @Test
    void conversionCrAUtcExacta() {
        when(catalogoServicios.buscarPorId("consulta-nutricion")).thenReturn(Optional.of(CONSULTA_NUTRICION));
        LocalDate fecha = proximoLunes();

        servicio.calcularSlotsDisponibles("consulta-nutricion", fecha);

        ArgumentCaptor<Instant> captor = ArgumentCaptor.forClass(Instant.class);
        verify(citaRepository).findConfirmadasEntre(captor.capture(), captor.capture());
        List<Instant> capturados = captor.getAllValues();

        // 8:00 CR (apertura) es UTC-6 -> 14:00 UTC del mismo dia natural.
        Instant esperadoInicio = ZonedDateTime.of(fecha, LocalTime.of(8, 0), ZONA_NEGOCIO).toInstant();
        Instant esperadoFin = ZonedDateTime.of(fecha, LocalTime.of(18, 0), ZONA_NEGOCIO).toInstant();
        assertThat(capturados.get(0)).isEqualTo(esperadoInicio);
        assertThat(capturados.get(1)).isEqualTo(esperadoFin);
        assertThat(esperadoInicio.toString()).contains("14:00:00Z");
    }
}

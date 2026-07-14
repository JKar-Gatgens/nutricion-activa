package com.nutricionactiva.service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nutricionactiva.model.Cita;
import com.nutricionactiva.model.FranjaHoraria;
import com.nutricionactiva.model.Servicio;
import com.nutricionactiva.repository.CitaRepository;

/**
 * Calcula los horarios de inicio disponibles para reservar un servicio
 * agendable en una fecha dada (docs/arquitectura-agendamiento.md, sección
 * 2). Reglas de negocio: lunes a sábado, 8:00–18:00 hora de Costa Rica; la
 * cita debe TERMINAR dentro de ese horario (límite inclusivo a las 18:00);
 * anticipación mínima de 1 día; sin solapamiento de rangos con citas
 * confirmadas, sin importar la duración de cada una.
 *
 * <p>Todas las reglas se piensan en hora de Costa Rica; la conversión a UTC
 * (lo único que se persiste) pasa únicamente por {@link #aInstante}.
 */
@Service
public class DisponibilidadService {

    public static final ZoneId ZONA_NEGOCIO = ZoneId.of("America/Costa_Rica");

    private static final LocalTime APERTURA = LocalTime.of(8, 0);
    private static final LocalTime CIERRE = LocalTime.of(18, 0);
    private static final int PASO_SLOT_MINUTOS = 30;
    private static final int ANTICIPACION_MINIMA_DIAS = 1;

    private final CatalogoServicios catalogoServicios;
    private final CitaRepository citaRepository;
    private final Clock reloj;

    @Autowired
    public DisponibilidadService(CatalogoServicios catalogoServicios, CitaRepository citaRepository) {
        this(catalogoServicios, citaRepository, Clock.system(ZONA_NEGOCIO));
    }

    /**
     * Constructor de paquete para tests: permite fijar el reloj y así hacer
     * determinísticas las reglas de "hoy" y "domingo".
     */
    DisponibilidadService(CatalogoServicios catalogoServicios, CitaRepository citaRepository, Clock reloj) {
        this.catalogoServicios = catalogoServicios;
        this.citaRepository = citaRepository;
        this.reloj = reloj;
    }

    public List<FranjaHoraria> calcularSlotsDisponibles(String servicioId, LocalDate fechaCR) {
        Servicio servicio = catalogoServicios.buscarPorId(servicioId)
                .filter(Servicio::agendable)
                .orElseThrow(() -> new ServicioNoAgendableException(servicioId));

        LocalDate hoyCR = LocalDate.now(reloj);
        if (fechaCR.isBefore(hoyCR.plusDays(ANTICIPACION_MINIMA_DIAS))) {
            return List.of();
        }
        if (fechaCR.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return List.of();
        }

        Integer duracionMinutosServicio = servicio.duracionMinutos();
        if (duracionMinutosServicio == null) {
            // Invariante del catálogo: agendable=true implica duración no nula
            // (V1__crear_tabla_servicio.sql). Si algún día se rompe (nueva
            // migración, panel de admin), fallar con una excepción de negocio
            // clara en vez de un NullPointerException al deshacer el boxing.
            throw new ServicioNoAgendableException(servicioId);
        }
        int duracionMinutos = duracionMinutosServicio;
        int aperturaMinutos = APERTURA.toSecondOfDay() / 60;
        int cierreMinutos = CIERRE.toSecondOfDay() / 60;

        Instant inicioVentanaUtc = aInstante(fechaCR, APERTURA);
        Instant finVentanaUtc = aInstante(fechaCR, CIERRE);
        List<Cita> ocupadas = citaRepository.findConfirmadasEntre(inicioVentanaUtc, finVentanaUtc);

        List<FranjaHoraria> libres = new ArrayList<>();
        for (int inicioMinutos = aperturaMinutos; inicioMinutos < cierreMinutos; inicioMinutos += PASO_SLOT_MINUTOS) {
            int finMinutos = inicioMinutos + duracionMinutos;
            if (finMinutos > cierreMinutos) {
                continue;
            }

            LocalTime inicio = LocalTime.ofSecondOfDay(inicioMinutos * 60L);
            LocalTime fin = LocalTime.ofSecondOfDay(finMinutos * 60L);

            Instant inicioCandidatoUtc = aInstante(fechaCR, inicio);
            Instant finCandidatoUtc = aInstante(fechaCR, fin);
            boolean ocupado = ocupadas.stream().anyMatch(cita -> seSolapa(
                    inicioCandidatoUtc, finCandidatoUtc,
                    cita.getFechaHoraInicioUtc(), cita.getFechaHoraFinUtc()));
            if (ocupado) {
                continue;
            }

            libres.add(new FranjaHoraria(inicio, fin));
        }
        return libres;
    }

    /**
     * Empaquetado (no privado): {@link ReservaCitaService} lo reusa para
     * convertir el {@link FranjaHoraria} elegido a los instantes UTC que
     * persiste en {@code Cita}, sin duplicar la conversión CR→UTC.
     */
    static Instant aInstante(LocalDate fecha, LocalTime hora) {
        return ZonedDateTime.of(fecha, hora, ZONA_NEGOCIO).toInstant();
    }

    private static boolean seSolapa(Instant aInicio, Instant aFin, Instant bInicio, Instant bFin) {
        return aInicio.isBefore(bFin) && bInicio.isBefore(aFin);
    }
}

package com.nutricionactiva.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutricionactiva.model.Cita;
import com.nutricionactiva.model.FranjaHoraria;
import com.nutricionactiva.repository.AgendaDiaRepository;
import com.nutricionactiva.repository.CitaRepository;

/**
 * Reserva una cita evitando el doble booking cuando dos visitantes compiten
 * por el mismo horario (docs/arquitectura-agendamiento.md, sección 3). Dentro
 * de una única transacción: asegura la fila de {@code agenda_dia} del día,
 * toma su candado exclusivo, recalcula disponibilidad ya con el candado en
 * mano, y solo entonces guarda la cita. El candado es sobre el día completo,
 * no sobre el horario específico: la segunda transacción que compite por
 * cualquier horario de ese mismo día espera, y al recalcular ve la cita recién
 * guardada por la primera.
 *
 * <p>Al final, dentro de la misma transacción, publica
 * {@link CitaReservadaEvent} (HU-05): {@link NotificacionCitaListener} lo
 * recibe recién después del commit ({@code @TransactionalEventListener(AFTER_COMMIT)})
 * y envía los correos de forma asíncrona, así que un fallo de envío o un
 * SMTP lento nunca afectan esta transacción ni la respuesta al visitante, y
 * si la reserva termina en rollback (p. ej. {@link SlotNoDisponibleException})
 * ningún correo sale.
 */
@Service
public class ReservaCitaService {

    private final AgendaDiaRepository agendaDiaRepository;
    private final DisponibilidadService disponibilidadService;
    private final CitaRepository citaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ReservaCitaService(
            AgendaDiaRepository agendaDiaRepository,
            DisponibilidadService disponibilidadService,
            CitaRepository citaRepository,
            ApplicationEventPublisher eventPublisher) {
        this.agendaDiaRepository = agendaDiaRepository;
        this.disponibilidadService = disponibilidadService;
        this.citaRepository = citaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Cita reservar(
            String servicioId,
            LocalDate fechaCR,
            LocalTime horaInicioCR,
            String clienteNombre,
            String clienteCorreo,
            String clienteTelefono,
            String motivo) {
        agendaDiaRepository.asegurarFilaConCandado(fechaCR);

        FranjaHoraria franja = disponibilidadService.calcularSlotsDisponibles(servicioId, fechaCR).stream()
                .filter(libre -> libre.inicio().equals(horaInicioCR))
                .findFirst()
                .orElseThrow(() -> new SlotNoDisponibleException(fechaCR, horaInicioCR));

        int duracionMinutosSnapshot = (int) Duration.between(franja.inicio(), franja.fin()).toMinutes();
        Instant inicioUtc = DisponibilidadService.aInstante(fechaCR, franja.inicio());
        Instant finUtc = DisponibilidadService.aInstante(fechaCR, franja.fin());

        Cita cita = new Cita(
                servicioId,
                duracionMinutosSnapshot,
                clienteNombre,
                clienteCorreo,
                clienteTelefono,
                motivo,
                inicioUtc,
                finUtc);
        Cita citaGuardada = citaRepository.save(cita);
        eventPublisher.publishEvent(new CitaReservadaEvent(citaGuardada.getId()));
        return citaGuardada;
    }
}

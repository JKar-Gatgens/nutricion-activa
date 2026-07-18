package com.nutricionactiva.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.nutricionactiva.config.MailProperties;
import com.nutricionactiva.config.WhatsappProperties;
import com.nutricionactiva.model.Cita;
import com.nutricionactiva.model.Servicio;
import com.nutricionactiva.repository.CitaRepository;

import jakarta.mail.internet.MimeMessage;

/**
 * Envía los dos correos de HU-05 (confirmación al cliente, aviso al
 * nutricionista) cuando una reserva se confirma. Reacciona a
 * {@link CitaReservadaEvent}, publicado por {@link ReservaCitaService} al
 * final de {@code reservar(...)}.
 *
 * <p>{@code @TransactionalEventListener(phase = AFTER_COMMIT)} garantiza que
 * el correo solo sale si la transacción que guardó la {@code Cita}
 * efectivamente confirmó (si {@code reservar(...)} termina en rollback -por
 * ejemplo {@link SlotNoDisponibleException}- este método nunca se ejecuta,
 * cero interacción con {@link JavaMailSender}). {@code @Async} garantiza que
 * ese envío corre en otro hilo, así que ni la latencia del SMTP ni un fallo
 * de envío afectan la respuesta HTTP de la reserva -la cita ya quedó
 * guardada antes de que este método siquiera empiece-. Por eso cada envío
 * queda envuelto en su propio try/catch: un fallo se loguea con el id de la
 * cita y nunca se relanza (un método {@code @Async void} no tiene a quién
 * propagarle una excepción de todas formas -terminaría en el
 * {@code AsyncUncaughtExceptionHandler} genérico sin contexto de negocio-), y
 * el fallo del correo al cliente no le impide intentar el aviso al
 * nutricionista, ni viceversa.
 *
 * <p>El evento porta solo el {@code id} de la cita (no la entidad), así que
 * esta clase la recarga por su cuenta: corre en un hilo distinto al que la
 * guardó, con su propia transacción de solo lectura de Spring Data.
 */
@Component
public class NotificacionCitaListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacionCitaListener.class);

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es"));
    private static final DateTimeFormatter FORMATO_HORA =
            DateTimeFormatter.ofPattern("h:mm a", Locale.forLanguageTag("es"));

    private final JavaMailSender mailSender;
    private final CitaRepository citaRepository;
    private final CatalogoServicios catalogoServicios;
    private final MailProperties mailProperties;
    private final WhatsappProperties whatsappProperties;
    private final String remitenteCorreo;

    public NotificacionCitaListener(
            JavaMailSender mailSender,
            CitaRepository citaRepository,
            CatalogoServicios catalogoServicios,
            MailProperties mailProperties,
            WhatsappProperties whatsappProperties,
            @Value("${spring.mail.username}") String remitenteCorreo) {
        this.mailSender = mailSender;
        this.citaRepository = citaRepository;
        this.catalogoServicios = catalogoServicios;
        this.mailProperties = mailProperties;
        this.whatsappProperties = whatsappProperties;
        this.remitenteCorreo = remitenteCorreo;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCitaReservada(CitaReservadaEvent evento) {
        Cita cita;
        try {
            cita = citaRepository.findById(evento.citaId()).orElse(null);
        } catch (Exception e) {
            log.error("No se pudo recargar la cita {} para enviar las notificaciones de HU-05", evento.citaId(), e);
            return;
        }
        if (cita == null) {
            log.error("Cita {} no encontrada al intentar enviar las notificaciones de HU-05 (evento post-commit)",
                    evento.citaId());
            return;
        }

        Servicio servicio = catalogoServicios.buscarPorId(cita.getServicioId()).orElse(null);

        try {
            enviarCorreoCliente(cita, servicio);
        } catch (Exception e) {
            log.error("No se pudo enviar el correo de confirmación al cliente para la cita {}", cita.getId(), e);
        }

        try {
            enviarCorreoNutricionista(cita, servicio);
        } catch (Exception e) {
            log.error("No se pudo enviar el correo de aviso al nutricionista para la cita {}", cita.getId(), e);
        }
    }

    private void enviarCorreoCliente(Cita cita, Servicio servicio) throws Exception {
        String nombreServicio = servicio != null ? servicio.nombre() : cita.getServicioId();
        String modalidad = servicio != null && servicio.modalidad() != null ? servicio.modalidad() : "Por confirmar";

        ZonedDateTime inicioCR = cita.getFechaHoraInicioUtc().atZone(DisponibilidadService.ZONA_NEGOCIO);
        ZonedDateTime finCR = cita.getFechaHoraFinUtc().atZone(DisponibilidadService.ZONA_NEGOCIO);

        String cuerpo = """
                Hola %s,

                Tu cita con Nutrición Activa quedó confirmada:

                Servicio: %s
                Modalidad: %s
                Fecha: %s
                Hora: %s a %s (hora de Costa Rica)

                Motivo de consulta: %s

                Si necesitás cambiar o cancelar tu cita, escribinos por WhatsApp al %s.

                ¡Te esperamos!
                Nutrición Activa
                """.formatted(
                cita.getClienteNombre(),
                nombreServicio,
                modalidad,
                FORMATO_FECHA.format(inicioCR),
                FORMATO_HORA.format(inicioCR),
                FORMATO_HORA.format(finCR),
                cita.getMotivo(),
                whatsappProperties.numeroVisible());

        MimeMessage mensaje = crearMensaje(cita.getClienteCorreo(), "Confirmación de tu cita - Nutrición Activa", cuerpo);
        mailSender.send(mensaje);
    }

    private void enviarCorreoNutricionista(Cita cita, Servicio servicio) throws Exception {
        String nombreServicio = servicio != null ? servicio.nombre() : cita.getServicioId();

        ZonedDateTime inicioCR = cita.getFechaHoraInicioUtc().atZone(DisponibilidadService.ZONA_NEGOCIO);
        ZonedDateTime finCR = cita.getFechaHoraFinUtc().atZone(DisponibilidadService.ZONA_NEGOCIO);

        String cuerpo = """
                Se agendó una nueva cita:

                Cliente: %s
                Correo: %s
                Teléfono: %s

                Servicio: %s
                Fecha: %s
                Hora: %s a %s (hora de Costa Rica)

                Motivo de consulta: %s
                """.formatted(
                cita.getClienteNombre(),
                cita.getClienteCorreo(),
                cita.getClienteTelefono(),
                nombreServicio,
                FORMATO_FECHA.format(inicioCR),
                FORMATO_HORA.format(inicioCR),
                FORMATO_HORA.format(finCR),
                cita.getMotivo());

        MimeMessage mensaje = crearMensaje(
                mailProperties.correoNutricionista(),
                "Nueva cita agendada: " + cita.getClienteNombre(),
                cuerpo);
        mailSender.send(mensaje);
    }

    private MimeMessage crearMensaje(String destinatario, String asunto, String cuerpoTextoPlano) throws Exception {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, false, "UTF-8");
        helper.setFrom(remitenteCorreo, mailProperties.remitenteNombre());
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(cuerpoTextoPlano, false);
        return mensaje;
    }
}

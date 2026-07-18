package com.nutricionactiva.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import com.nutricionactiva.config.MailProperties;
import com.nutricionactiva.config.WhatsappProperties;
import com.nutricionactiva.model.Cita;
import com.nutricionactiva.model.Servicio;
import com.nutricionactiva.repository.CitaRepository;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

/**
 * Test unitario puro (sin Spring, sin BD, sin SMTP real) de HU-05: cubre el
 * contenido de ambos correos, que la hora mostrada esté en Costa Rica (no en
 * UTC), y las dos garantías críticas de {@link NotificacionCitaListener} — un
 * fallo de envío se loguea sin propagarse y sin impedir el otro correo, y una
 * cita no encontrada no intenta enviar nada.
 *
 * <p>Como este test instancia {@link NotificacionCitaListener} directamente
 * (sin contexto de Spring), {@code @Async}/{@code @TransactionalEventListener}
 * no aplican aquí — llama a {@code onCitaReservada} de forma síncrona, que es
 * justamente lo que permite hacer estas aserciones deterministas sin
 * {@code Awaitility}. La interacción real con esas dos anotaciones (el correo
 * solo sale después del commit) la cubre
 * {@link com.nutricionactiva.service.NotificacionCitaAfterCommitTest}.
 */
class NotificacionCitaListenerTest {

    private static final Servicio CONSULTA = new Servicio(
            "consulta-nutricion", "Consulta de nutrición", "desc", 30_000, 90,
            "Virtual o a domicilio", true, 1, true);

    private final JavaMailSender mailSender = mock(JavaMailSender.class);
    private final CitaRepository citaRepository = mock(CitaRepository.class);
    private final CatalogoServicios catalogoServicios = mock(CatalogoServicios.class);
    private final MailProperties mailProperties = new MailProperties("Nutrición Activa", "nutricionista@correo.com");
    private final WhatsappProperties whatsappProperties = new WhatsappProperties("50688888888", "mensaje de prueba");

    private final NotificacionCitaListener listener = new NotificacionCitaListener(
            mailSender, citaRepository, catalogoServicios, mailProperties, whatsappProperties,
            "remitente@gmail.com");

    @BeforeEach
    void mailSenderCreaMensajesReales() {
        // JavaMailSender esta mockeado por completo: sin esto,
        // createMimeMessage() devolveria null y MimeMessageHelper fallaria.
        // Cada invocacion devuelve una instancia nueva (se envian dos correos).
        when(mailSender.createMimeMessage())
                .thenAnswer(invocacion -> new MimeMessage(Session.getDefaultInstance(new Properties())));
    }

    @Test
    void enviaCorreoAlClienteYAlNutricionistaConHoraEnCostaRicaNoEnUtc() throws Exception {
        // 2026-07-20T14:00:00Z UTC = 8:00 AM Costa Rica (UTC-6); la duracion de
        // 90 min de "consulta-nutricion" cierra a las 9:30 AM CR.
        Cita cita = citaDePrueba(
                Instant.parse("2026-07-20T14:00:00Z"),
                Instant.parse("2026-07-20T15:30:00Z"));
        setId(cita, 1L);

        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(catalogoServicios.buscarPorId("consulta-nutricion")).thenReturn(Optional.of(CONSULTA));

        listener.onCitaReservada(new CitaReservadaEvent(1L));

        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender, times(2)).send(captor.capture());
        List<MimeMessage> mensajes = captor.getAllValues();

        MimeMessage correoCliente = mensajes.get(0);
        assertThat(destinatarios(correoCliente)).containsExactly("ana@correo.com");
        assertThat(correoCliente.getSubject()).contains("Confirmación");
        String cuerpoCliente = (String) correoCliente.getContent();
        assertThat(cuerpoCliente)
                .contains("Ana Pérez")
                .contains("Consulta de nutrición")
                .contains("Virtual o a domicilio")
                .contains("Quiero bajar de grasa corporal")
                .contains("8:00")
                // la hora en Costa Rica del ejemplo (8:00-9:30 AM) nunca coincide con
                // las horas UTC crudas (14:00/15:30): si aparecieran, la conversion
                // de zona se habria roto.
                .doesNotContain("14:00")
                .doesNotContain("15:30");

        MimeMessage correoNutricionista = mensajes.get(1);
        assertThat(destinatarios(correoNutricionista)).containsExactly("nutricionista@correo.com");
        assertThat(correoNutricionista.getSubject()).contains("Ana Pérez");
        String cuerpoNutricionista = (String) correoNutricionista.getContent();
        assertThat(cuerpoNutricionista)
                .contains("Ana Pérez")
                .contains("ana@correo.com")
                .contains("8888-0000")
                .contains("Consulta de nutrición")
                .contains("Quiero bajar de grasa corporal")
                .contains("8:00")
                .doesNotContain("14:00")
                .doesNotContain("15:30");
    }

    @Test
    void servicioSinModalidadUsaTextoPorConfirmarEnVezDeRomper() throws Exception {
        Cita cita = citaDePrueba(Instant.parse("2026-07-20T14:00:00Z"), Instant.parse("2026-07-20T15:30:00Z"));
        setId(cita, 2L);

        when(citaRepository.findById(2L)).thenReturn(Optional.of(cita));
        // El servicio ya no existe en el catalogo (p. ej. se elimino despues de
        // reservar): NotificacionCitaListener no debe fallar por eso.
        when(catalogoServicios.buscarPorId("consulta-nutricion")).thenReturn(Optional.empty());

        listener.onCitaReservada(new CitaReservadaEvent(2L));

        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender, times(2)).send(captor.capture());
        String cuerpoCliente = (String) captor.getAllValues().get(0).getContent();
        assertThat(cuerpoCliente).contains("consulta-nutricion");
    }

    @Test
    void fallaDeEnvioAlClienteSeLogueaSinPropagarYNoImpideElAvisoAlNutricionista() {
        Cita cita = citaDePrueba(Instant.parse("2026-07-20T14:00:00Z"), Instant.parse("2026-07-20T15:30:00Z"));
        setId(cita, 3L);

        when(citaRepository.findById(3L)).thenReturn(Optional.of(cita));
        when(catalogoServicios.buscarPorId("consulta-nutricion")).thenReturn(Optional.of(CONSULTA));
        doThrow(new MailSendException("SMTP caído")).when(mailSender).send(any(MimeMessage.class));

        // Requisito duro de HU-05: un fallo de correo nunca se propaga (nada que
        // capture la excepcion en el hilo async de todas formas la veria).
        assertThatCode(() -> listener.onCitaReservada(new CitaReservadaEvent(3L)))
                .doesNotThrowAnyException();

        // Ambos envios se intentan de forma independiente: el fallo del correo al
        // cliente no cancela el intento del aviso al nutricionista.
        verify(mailSender, times(2)).send(any(MimeMessage.class));
    }

    @Test
    void citaNoEncontradaTrasElCommitNoIntentaEnviarNingunCorreo() {
        when(citaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatCode(() -> listener.onCitaReservada(new CitaReservadaEvent(99L)))
                .doesNotThrowAnyException();

        verifyNoInteractions(mailSender);
    }

    private static Cita citaDePrueba(Instant inicioUtc, Instant finUtc) {
        return new Cita(
                "consulta-nutricion", 90, "Ana Pérez", "ana@correo.com", "8888-0000",
                "Quiero bajar de grasa corporal", inicioUtc, finUtc);
    }

    private static List<String> destinatarios(MimeMessage mensaje) throws MessagingException {
        return Arrays.stream(mensaje.getAllRecipients()).map(Address::toString).toList();
    }

    /** La Cita real solo asigna {@code id} vía JPA al persistir; en un test unitario sin BD se fija a mano. */
    private static void setId(Cita cita, Long id) {
        try {
            var campo = Cita.class.getDeclaredField("id");
            campo.setAccessible(true);
            campo.set(cita, id);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

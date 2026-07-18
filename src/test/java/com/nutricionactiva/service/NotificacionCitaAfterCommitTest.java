package com.nutricionactiva.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

/**
 * Cubre la garantía central del mecanismo de HU-05:
 * {@code @TransactionalEventListener(phase = AFTER_COMMIT)} solo dispara el
 * envío de correo cuando la transacción de {@link ReservaCitaService#reservar}
 * efectivamente confirma. Corre contra MySQL real vía Testcontainers -mismo
 * patrón que {@code ReservaCitaServiceConcurrenciaTest}- porque
 * {@code @TransactionalEventListener} depende de sincronización real de
 * transacción JDBC, no solo de un mock de repositorio.
 * {@link JavaMailSender} se mockea: nada de esto toca un SMTP real.
 */
@Testcontainers
@SpringBootTest
class NotificacionCitaAfterCommitTest {

    @Container
    static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("nutricion_activa")
            .withUrlParam("connectionTimeZone", "UTC")
            .withUrlParam("useSSL", "false")
            .withUrlParam("allowPublicKeyRetrieval", "true");

    @DynamicPropertySource
    static void propiedadesBaseDeDatos(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }

    @Autowired
    private ReservaCitaService reservaCitaService;

    @MockitoBean
    private JavaMailSender mailSender;

    @BeforeEach
    void mailSenderCreaMensajesReales() {
        when(mailSender.createMimeMessage())
                .thenAnswer(invocacion -> new MimeMessage(Session.getDefaultInstance(new Properties())));
    }

    @Test
    void reservaConfirmadaEnviaAmbosCorreosDespuesDelCommit() {
        LocalDate fecha = fechaHabilFutura(2);

        reservaCitaService.reservar(
                "consulta-nutricion", fecha, LocalTime.of(9, 0),
                "Ana Pérez", "ana@correo.com", "8888-0000", "Quiero bajar de grasa");

        // El envío corre en el pool de @Async, en otro hilo: se espera con
        // Awaitility en vez de asumir que ya terminó al volver reservar().
        await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(mailSender, times(2)).send(any(MimeMessage.class)));
    }

    @Test
    void reservaRechazadaPorServicioNoAgendableNuncaLlamaAlMailSender() {
        LocalDate fecha = fechaHabilFutura(3);

        // "protocolo-competencias" no es agendable (V1__crear_tabla_servicio.sql):
        // ReservaCitaService lanza la excepción antes de guardar ninguna Cita, así
        // que CitaReservadaEvent nunca se publica y AFTER_COMMIT no tiene nada que
        // disparar.
        assertThatThrownBy(() -> reservaCitaService.reservar(
                "protocolo-competencias", fecha, LocalTime.of(9, 0),
                "Cliente", "cliente@correo.com", "8888-0000", "motivo"))
                .isInstanceOf(ServicioNoAgendableException.class);

        // Mockito.after espera el tiempo indicado y RECIÉN ENTONCES verifica: es
        // la forma correcta de afirmar "nunca ocurrió" contra código asíncrono,
        // en vez de asumir que la ausencia inmediata ya es definitiva.
        verify(mailSender, after(1000).never()).send(any(MimeMessage.class));
    }

    private static LocalDate fechaHabilFutura(int diasDeSeparacion) {
        LocalDate fecha = LocalDate.now(DisponibilidadService.ZONA_NEGOCIO).plusDays(diasDeSeparacion);
        while (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fecha = fecha.plusDays(1);
        }
        return fecha;
    }
}

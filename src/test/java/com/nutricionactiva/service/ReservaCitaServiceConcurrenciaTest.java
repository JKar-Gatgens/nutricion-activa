package com.nutricionactiva.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.nutricionactiva.model.Cita;
import com.nutricionactiva.repository.CitaRepository;

/**
 * Caso 7 del catálogo de 8 casos (docs/arquitectura-agendamiento.md): la
 * reserva simultánea del mismo horario (el escenario Ana/Beto de la sección
 * 3). Corre contra MySQL real vía Testcontainers -no H2, no mocks- porque el
 * candado exclusivo de {@code agenda_dia} y el upsert nativo que lo respalda
 * son comportamiento específico de MySQL. Diez hilos compiten por el mismo
 * servicio/fecha/hora: exactamente uno debe reservar, los otros nueve deben
 * recibir {@link SlotNoDisponibleException}, nunca un error genérico.
 */
@Testcontainers
@SpringBootTest
class ReservaCitaServiceConcurrenciaTest {

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

    @Autowired
    private CitaRepository citaRepository;

    private static final String SERVICIO_ID = "consulta-nutricion";
    private static final LocalTime HORA_INICIO = LocalTime.of(10, 0);

    /**
     * Para que este test pruebe contención real (los 10 hilos peleando por el
     * candado en la base de datos) y no una cola artificial esperando
     * conexión, el pool de Hikari debe tener al menos {@code NUMERO_HILOS}
     * conexiones simultáneas disponibles. Hoy eso se cumple porque
     * {@code DatabaseConfig} construye el {@code DataSource} a mano y no lee
     * {@code spring.datasource.hikari.*} (deuda registrada en
     * docs/pendientes.md, "Mejoras a DatabaseConfig"), así que el pool cae en
     * el default de HikariCP (10) — una coincidencia sin margen, no una
     * garantía. Si se sube este número, hay que arreglar esa deuda primero (o
     * el test empezaría a poner hilos en cola en vez de competir por el
     * candado, y seguiría en verde probando menos de lo que dice).
     */
    private static final int NUMERO_HILOS = 10;

    @Test
    void diezReservasConcurrentesDelMismoHorarioSoloUnaTrunfa() throws InterruptedException {
        LocalDate fecha = fechaHabilFutura();

        CountDownLatch salida = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(NUMERO_HILOS);
        AtomicInteger exitos = new AtomicInteger(0);
        CopyOnWriteArrayList<Throwable> fallos = new CopyOnWriteArrayList<>();

        for (int i = 0; i < NUMERO_HILOS; i++) {
            int indice = i;
            executor.submit(() -> {
                try {
                    salida.await();
                    reservaCitaService.reservar(
                            SERVICIO_ID, fecha, HORA_INICIO,
                            "Cliente " + indice,
                            "cliente" + indice + "@correo.com",
                            "8888-000" + indice,
                            "motivo de prueba " + indice);
                    exitos.incrementAndGet();
                } catch (Throwable error) {
                    fallos.add(error);
                }
            });
        }

        salida.countDown();
        executor.shutdown();
        boolean terminoATiempo = executor.awaitTermination(1, TimeUnit.MINUTES);
        assertThat(terminoATiempo).isTrue();

        assertThat(exitos.get()).isEqualTo(1);
        assertThat(fallos).hasSize(NUMERO_HILOS - 1);
        assertThat(fallos).allSatisfy(error -> assertThat(error).isInstanceOf(SlotNoDisponibleException.class));

        List<Cita> citasPersistidas = citaRepository.findAll().stream()
                .filter(cita -> cita.getServicioId().equals(SERVICIO_ID))
                .toList();
        assertThat(citasPersistidas).hasSize(1);
    }

    private static LocalDate fechaHabilFutura() {
        LocalDate fecha = LocalDate.now(DisponibilidadService.ZONA_NEGOCIO).plusDays(2);
        while (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fecha = fecha.plusDays(1);
        }
        return fecha;
    }
}

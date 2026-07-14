package com.nutricionactiva.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.nutricionactiva.model.Cita;

import jakarta.persistence.EntityManager;

/**
 * Prueba de round-trip contra MySQL real (perfil dev): confirma que un
 * {@link Instant} sobrevive exacto al guardarse en las columnas DATETIME de
 * {@code cita} y releerse. Existe porque el driver de MySQL, sin
 * {@code connectionTimeZone=UTC} en la URL, usa la zona horaria por defecto
 * de la JVM para esa conversión — en las máquinas de este equipo eso es
 * America/Costa_Rica, no UTC, lo que corrompería cada hora guardada por 6
 * horas (docs/arquitectura-agendamiento.md, sección 4). @Transactional hace
 * rollback al final: no deja datos de prueba en la tabla.
 */
@SpringBootTest
@ActiveProfiles("dev")
@Transactional
class CitaRepositoryTest {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void unInstanteGuardadoSeReleeExactoIgual() {
        Instant inicioUtc = Instant.parse("2026-07-20T14:00:00Z");
        Instant finUtc = Instant.parse("2026-07-20T15:30:00Z");

        Cita cita = new Cita(
                "consulta-nutricion", 90,
                "Test Round-Trip", "test@correo.com", "8888-0000",
                "verificacion de conversion UTC",
                inicioUtc, finUtc);

        Cita guardada = citaRepository.saveAndFlush(cita);

        // Fuerza una lectura real desde MySQL: sin esto, findById devolveria
        // la misma instancia en memoria desde el primer-level cache de
        // Hibernate y el test no probaria nada sobre el driver JDBC.
        entityManager.clear();

        citaRepository.findById(guardada.getId())
                .ifPresentOrElse(
                        releida -> {
                            assertThat(releida.getFechaHoraInicioUtc()).isEqualTo(inicioUtc);
                            assertThat(releida.getFechaHoraFinUtc()).isEqualTo(finUtc);
                        },
                        () -> { throw new AssertionError("La cita guardada no se pudo releer"); });
    }
}

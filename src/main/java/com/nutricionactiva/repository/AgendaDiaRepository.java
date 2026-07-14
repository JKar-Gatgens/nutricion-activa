package com.nutricionactiva.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutricionactiva.model.AgendaDia;

/**
 * Usado por {@code ReservaCitaService} para serializar reservas concurrentes
 * del mismo día (docs/arquitectura-agendamiento.md, sección 3):
 * {@link #asegurarFilaConCandado(LocalDate)} crea la fila si no existe y, en
 * la misma sentencia, toma el candado exclusivo sobre ella.
 */
public interface AgendaDiaRepository extends JpaRepository<AgendaDia, LocalDate> {

    /**
     * {@code INSERT ... ON DUPLICATE KEY UPDATE} nativo de MySQL: una sola
     * sentencia que crea la fila si no existe o, si ya existe, la actualiza a
     * sí misma — en ambos casos terminando con el candado exclusivo de la fila
     * en manos de esta transacción, sin una sentencia de lectura aparte.
     *
     * <p>Se descartó a propósito la combinación original de dos sentencias
     * ({@code INSERT IGNORE} + {@code SELECT ... FOR UPDATE} separado): bajo
     * contención real (varias transacciones creando la fila del mismo día por
     * primera vez al mismo tiempo), el {@code INSERT IGNORE} sobre una fila
     * duplicada toma un candado compartido para el chequeo de duplicado, y la
     * siguiente sentencia necesitaba subirlo a exclusivo — con varias
     * transacciones sosteniendo el candado compartido a la vez, ninguna podía
     * subir a exclusivo, y MySQL resolvía el ciclo matando transacciones por
     * deadlock (confirmado con el test de 10 reservas concurrentes,
     * {@code ReservaCitaServiceConcurrenciaTest}). {@code ON DUPLICATE KEY
     * UPDATE} evita el problema de raíz: toma el candado exclusivo en una sola
     * sentencia, sin paso intermedio de candado compartido que subir.
     */
    @Modifying
    @Query(value = "INSERT INTO agenda_dia (fecha) VALUES (:fecha) ON DUPLICATE KEY UPDATE fecha = fecha",
            nativeQuery = true)
    void asegurarFilaConCandado(@Param("fecha") LocalDate fecha);
}

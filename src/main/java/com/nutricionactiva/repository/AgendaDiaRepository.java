package com.nutricionactiva.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutricionactiva.model.AgendaDia;

/**
 * El paso 5 agrega la consulta de upsert + candado
 * ({@code SELECT ... FOR UPDATE}) que usa {@code ReservaCitaService}. Por
 * ahora solo el CRUD estándar de {@link JpaRepository}.
 */
public interface AgendaDiaRepository extends JpaRepository<AgendaDia, LocalDate> {
}

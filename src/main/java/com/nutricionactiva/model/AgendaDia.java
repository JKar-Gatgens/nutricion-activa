package com.nutricionactiva.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Fila de sincronización por día (tabla {@code agenda_dia}, Flyway V2). No
 * representa ningún concepto de negocio — solo existe para que
 * {@code ReservaCitaService} (paso 5) pueda tomar un candado
 * ({@code SELECT ... FOR UPDATE}) sobre un día concreto y serializar
 * reservas concurrentes de ese día
 * (docs/arquitectura-agendamiento.md, sección 3). En este paso solo se
 * modela la entidad; el candado se implementa en el paso 5.
 */
@Entity
@Table(name = "agenda_dia")
public class AgendaDia {

    @Id
    private LocalDate fecha;

    protected AgendaDia() {
        // JPA
    }

    public AgendaDia(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}

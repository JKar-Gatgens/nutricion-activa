package com.nutricionactiva.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutricionactiva.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Citas confirmadas cuyo rango se solapa con la ventana
     * [{@code inicioVentana}, {@code finVentana}). Usada por
     * {@link com.nutricionactiva.service.DisponibilidadService} para traer,
     * en una sola consulta, todas las citas del día y filtrar los slots
     * ocupados en memoria.
     */
    @Query("SELECT c FROM Cita c WHERE c.estado = com.nutricionactiva.model.EstadoCita.CONFIRMADA "
            + "AND c.fechaHoraInicioUtc < :finVentana AND c.fechaHoraFinUtc > :inicioVentana")
    List<Cita> findConfirmadasEntre(
            @Param("inicioVentana") Instant inicioVentana,
            @Param("finVentana") Instant finVentana);
}

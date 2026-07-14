package com.nutricionactiva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutricionactiva.model.ServicioEntity;

public interface ServicioRepository extends JpaRepository<ServicioEntity, String> {

    /**
     * Preserva el {@code ORDER BY orden} que {@link
     * com.nutricionactiva.service.CatalogoServicios#obtenerTodos()} promete
     * como parte de su contrato.
     */
    List<ServicioEntity> findAllByOrderByOrdenAsc();
}

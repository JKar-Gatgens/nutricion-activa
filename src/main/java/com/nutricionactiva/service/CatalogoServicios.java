package com.nutricionactiva.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nutricionactiva.model.Servicio;
import com.nutricionactiva.model.ServicioEntity;
import com.nutricionactiva.repository.ServicioRepository;

/**
 * Catálogo de servicios respaldado por la tabla {@code servicio} (Flyway V1,
 * Sprint 2). El contrato con la vista y el controller es el mismo que la
 * implementación en memoria de Sprint 1: {@link #obtenerTodos()} devuelve el
 * catálogo ordenado por {@link Servicio#orden()}, sin exponer nunca {@link
 * ServicioEntity}.
 */
@Service
public class CatalogoServicios {

    private final ServicioRepository servicioRepository;

    public CatalogoServicios(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    /**
     * Devuelve el catálogo completo ordenado ascendentemente por
     * {@link Servicio#orden()}. Ese orden es parte del contrato: la vista
     * exhibe los servicios tal como llegan. El orden lo garantiza la consulta
     * ({@code ORDER BY orden} vía {@link ServicioRepository#findAllByOrderByOrdenAsc()}),
     * no un reordenamiento en memoria.
     */
    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAllByOrderByOrdenAsc().stream()
                .map(CatalogoServicios::aServicio)
                .toList();
    }

    /**
     * Subconjunto de {@link #obtenerTodos()} con los servicios que se reservan
     * con horario propio (HU-04). Los que no son agendables se solicitan por
     * WhatsApp.
     */
    public List<Servicio> obtenerAgendables() {
        return obtenerTodos().stream()
                .filter(Servicio::agendable)
                .toList();
    }

    private static Servicio aServicio(ServicioEntity entity) {
        return new Servicio(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecioColones(),
                entity.getDuracionMinutos(),
                entity.getModalidad(),
                entity.isDestacado(),
                entity.getOrden(),
                entity.isAgendable());
    }
}

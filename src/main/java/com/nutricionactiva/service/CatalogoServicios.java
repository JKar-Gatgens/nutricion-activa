package com.nutricionactiva.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nutricionactiva.model.Servicio;

/**
 * Catálogo de servicios en memoria. En Sprint 2 esta implementación migra a
 * base de datos manteniendo el mismo contrato, así que ni la vista ni el
 * controller deben cambiar.
 */
@Service
public class CatalogoServicios {

    private static final List<Servicio> SERVICIOS = List.of(
            new Servicio(
                    "consulta-nutricion",
                    "Consulta de nutrición",
                    "Evaluación inicial completa: composición corporal, hábitos y objetivos "
                            + "para construir tu plan personalizado.",
                    30_000,
                    90,
                    "Virtual o a domicilio",
                    true,
                    1),
            new Servicio(
                    "consulta-seguimiento",
                    "Consulta de seguimiento",
                    "Ajustes del plan según tu progreso: mediciones, revisión de adherencia "
                            + "y nuevas metas.",
                    null, // precio pendiente de confirmación del PO
                    40,
                    "Virtual o a domicilio",
                    false,
                    2),
            new Servicio(
                    "protocolo-competencias",
                    "Protocolo para competencias deportivas",
                    "Plan de 3 días para llegar a tu competencia en el punto ideal: carga de "
                            + "energía, hidratación y timing de comidas.",
                    22_000,
                    null, // duración de la sesión pendiente (los 3 días son el alcance del plan)
                    "Virtual",
                    false,
                    3),
            new Servicio(
                    "rutina-entrenamiento",
                    "Rutina de entrenamiento",
                    "Rutina de fuerza o acondicionamiento alineada con tu plan nutricional "
                            + "y tus objetivos.",
                    15_000,
                    null, // duración de la sesión pendiente
                    "Virtual",
                    false,
                    4),
            // Servicio sujeto a confirmación del PO: podría fusionarse con la
            // consulta de nutrición.
            new Servicio(
                    "nutricion-clinica",
                    "Nutrición clínica",
                    "Abordaje nutricional de condiciones de salud con un enfoque activo, "
                            + "integrado a tu entrenamiento y estilo de vida.",
                    null, // precio pendiente de confirmación del PO
                    null, // duración pendiente de confirmación del PO
                    "Virtual o a domicilio",
                    false,
                    5));

    /**
     * Devuelve el catálogo completo ordenado ascendentemente por
     * {@link Servicio#orden()}. Ese orden es parte del contrato: la vista
     * exhibe los servicios tal como llegan, y la implementación con base de
     * datos del Sprint 2 debe conservarlo (equivale a un {@code ORDER BY orden}).
     */
    public List<Servicio> obtenerTodos() {
        return SERVICIOS.stream()
                .sorted(Comparator.comparingInt(Servicio::orden))
                .toList();
    }
}

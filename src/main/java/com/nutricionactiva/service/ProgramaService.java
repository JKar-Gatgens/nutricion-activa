package com.nutricionactiva.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nutricionactiva.model.Modulo;
import com.nutricionactiva.model.Programa;

/**
 * Programa "Fuerte y Definido" (HU-12, D-16 a D-19 en
 * {@code docs/product-backlog.md}). Mismo patrón data-ready que
 * {@link TestimonioService}: la estructura de la página existe desde ya y el
 * detalle se agrega ACÁ como datos a medida que el PO lo produzca (D-19),
 * cero cambios en controller/vista/CSS.
 *
 * <p>Los módulos llevan {@code descripcion == null} y {@code detalles} vacíos
 * a propósito: el PO tiene el programa ~30% desarrollado y solo se publica el
 * nivel de detalle que ya existe (D-11, "sin prueba, sin promesa"). Cuando el
 * PO diseñe un módulo, se completan sus campos acá (HU-13). En memoria y sin
 * tabla propia deliberadamente: es UN programa con datos que cambian a ritmo
 * de sprint, no un catálogo; si algún día hay varios programas o edición sin
 * deploy, migrar a base de datos siguiendo el camino de {@link CatalogoServicios}.
 */
@Service
public class ProgramaService {

    private static final Programa FUERTE_Y_DEFINIDO = new Programa(
            "Fuerte y Definido",
            "Nutrición + entrenamiento · 90 días",
            "Un programa de 90 días en 3 módulos de 30: plan nutricional y "
                    + "entrenamiento estructurado con seguimiento y atención en "
                    + "tiempo real, online o presencial.",
            105_000,
            90,
            "Cambio físico en 90 días sin dietas restrictivas, con planes "
                    + "ajustados a tu rutina diaria.",
            "Hombres y mujeres de 16 años o más que entrenan al menos 3 veces "
                    + "por semana, de Costa Rica o el extranjero.",
            List.of(
                    "Plan nutricional personalizado",
                    "Guía de entrenamiento estructurado",
                    "Sesiones de seguimiento online o presencial",
                    "Listas de intercambio de alimentos",
                    "Guía de suplementación deportiva",
                    "Atención en tiempo real"),
            List.of(
                    new Modulo(1, "Módulo 1", 30, null, List.of()),
                    new Modulo(2, "Módulo 2", 30, null, List.of()),
                    new Modulo(3, "Módulo 3", 30, null, List.of())));

    public Programa obtenerPrograma() {
        return FUERTE_Y_DEFINIDO;
    }
}

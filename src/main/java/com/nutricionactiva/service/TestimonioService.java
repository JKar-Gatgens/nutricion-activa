package com.nutricionactiva.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nutricionactiva.model.Testimonio;

/**
 * Testimonios de clientes para la sección de prueba social (HU-10, D-11/D-15
 * en {@code docs/product-backlog.md}). Sección "data-ready": la vista
 * ({@code index.html}) se renderiza solo si {@link #obtenerTodos()} devuelve
 * elementos, así que hoy no aparece nada en el HTML publicado.
 *
 * <p>Deliberadamente vacía y en memoria, sin tabla propia: el PO todavía no
 * tiene ningún testimonio con permiso explícito de uso web ("sin prueba, sin
 * promesa", PRODUCT.md). Activar la sección cuando lleguen los primeros casos
 * reales es agregar entradas acá — cero cambios en el controller, la vista o
 * el CSS. Si el volumen justifica persistencia, migra a base de datos
 * siguiendo el mismo camino que {@link CatalogoServicios} en Sprint 2 (paso 3).
 */
@Service
public class TestimonioService {

    public List<Testimonio> obtenerTodos() {
        return List.of();
    }
}

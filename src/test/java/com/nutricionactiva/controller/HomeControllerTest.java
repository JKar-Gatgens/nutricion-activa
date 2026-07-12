package com.nutricionactiva.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nutricionactiva.service.CatalogoServicios;

/**
 * Test de contrato de la página principal ("/"): fija, ANTES de la migración
 * del catálogo a base de datos (Sprint 2), el contrato observable entre
 * {@link HomeController}, {@link CatalogoServicios} (implementación en
 * memoria REAL, no mockeada) y la plantilla {@code index.html}.
 *
 * <p>Cualquier cambio de implementación del catálogo que rompa este test
 * rompe también el contrato con la vista.
 */
@WebMvcTest(HomeController.class)
@Import(CatalogoServicios.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void devuelveVistaIndexConLosCincoServiciosDelCatalogo() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("servicios"))
                .andExpect(model().attribute("servicios", hasSize(5)));
    }

    @Test
    void renderizaUnaTarjetaPorServicioConLosMarcadoresDePendienteYElDestacadoCorrectos() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String html = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        // 1 tarjeta <article class="na-service-card"> por cada uno de los 5 servicios.
        assertThat(contarOcurrencias(html, "na-service-card")).isEqualTo(5);

        // precioColones == null -> consulta-seguimiento y nutricion-clinica.
        assertThat(contarOcurrencias(html, "[PRECIO PENDIENTE]")).isEqualTo(2);

        // duracionMinutos == null -> protocolo-competencias, rutina-entrenamiento y nutricion-clinica.
        assertThat(contarOcurrencias(html, "[DURACIÓN PENDIENTE]")).isEqualTo(3);

        // Único servicio destacado (consulta-nutricion) -> exactamente una tarjeta con clase extra.
        assertThat(contarOcurrencias(html, "na-card-featured")).isEqualTo(1);
    }

    private static int contarOcurrencias(String texto, String subcadena) {
        int contador = 0;
        int indice = 0;
        while ((indice = texto.indexOf(subcadena, indice)) != -1) {
            contador++;
            indice += subcadena.length();
        }
        return contador;
    }
}

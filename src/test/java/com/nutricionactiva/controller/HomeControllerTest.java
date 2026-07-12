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
    void devuelveVistaIndexConLosCuatroServiciosDelCatalogo() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("servicios"))
                .andExpect(model().attribute("servicios", hasSize(4)));
    }

    @Test
    void renderizaUnaTarjetaPorServicioSinMarcadoresPendientesYConElDestacado() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String html = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        // 1 tarjeta <article class="na-service-card"> por cada uno de los 4 servicios
        // del catálogo definitivo del PO (2026-07-12).
        assertThat(contarOcurrencias(html, "na-service-card")).isEqualTo(4);

        // Catálogo final: todos los precios confirmados y la duración null significa
        // "no aplica" (la vista omite el metadato). No queda ningún marcador pendiente
        // en toda la página.
        assertThat(html).doesNotContain("[PRECIO PENDIENTE]");
        assertThat(html).doesNotContain("[DURACIÓN PENDIENTE]");
        assertThat(html)
                .as("ningún marcador pendiente (.na-pending) debe llegar al HTML publicado; "
                        + "si esto falla, un dato volvió a estado pendiente o se reutilizó el chip")
                .doesNotContain("na-pending");

        // Único servicio destacado (consulta-nutricion) -> exactamente una tarjeta con clase extra.
        assertThat(contarOcurrencias(html, "na-card-featured")).isEqualTo(1);
    }

    @Test
    void construyeLosEnlacesDeWhatsappDesdeLasPropiedades() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String html = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        // El número vive en application.properties y llega a la vista vía
        // GlobalModelAttributes (HU-03): FAB, footer (x2) y CTA del hero.
        assertThat(html).contains("wa.me/50689592110?text=");

        // El mensaje viaja URL-encodeado en UTF-8 simple: %C3%A9 es "é".
        // Pin del bug real de doble codificación (los .properties son ISO-8859-1;
        // el mensaje usa escapes unicode por eso).
        assertThat(html).contains("%C3%A9");

        // Botón flotante accesible presente en el layout compartido.
        assertThat(html).contains("aria-label=\"Escribir por WhatsApp\"");
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

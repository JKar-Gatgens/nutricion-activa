package com.nutricionactiva.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nutricionactiva.model.ServicioEntity;
import com.nutricionactiva.repository.ServicioRepository;
import com.nutricionactiva.service.CatalogoServicios;
import com.nutricionactiva.service.TestimonioService;

/**
 * Test de contrato de la página principal ("/"): fija el contrato observable
 * entre {@link HomeController}, {@link CatalogoServicios} y la plantilla
 * {@code index.html}. Desde Sprint 2, {@link CatalogoServicios} lee de
 * {@link ServicioRepository} (base de datos); acá se mockea con los mismos 4
 * servicios y el mismo orden que sembró Flyway V1, así que el contrato queda
 * igual de real que con la implementación en memoria de Sprint 1.
 * {@link TestimonioService} se importa real (sin mock): hoy siempre devuelve
 * lista vacía, que es exactamente el escenario que este test cubre (HU-10).
 *
 * <p>Cualquier cambio de implementación del catálogo que rompa este test
 * rompe también el contrato con la vista.
 */
@WebMvcTest(HomeController.class)
@Import({ CatalogoServicios.class, TestimonioService.class })
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioRepository servicioRepository;

    @BeforeEach
    void configurarCatalogo() {
        when(servicioRepository.findAllByOrderByOrdenAsc()).thenReturn(List.of(
                new ServicioEntity(
                        "consulta-nutricion",
                        "Consulta de nutrición",
                        "Evaluación inicial completa: composición corporal, hábitos y objetivos "
                                + "para construir tu plan personalizado.",
                        30_000, 90, "Virtual o a domicilio", true, 1, true),
                new ServicioEntity(
                        "consulta-seguimiento",
                        "Consulta de seguimiento",
                        "Ajustes del plan según tu progreso: mediciones, revisión de adherencia "
                                + "y nuevas metas.",
                        30_000, 40, "Virtual o a domicilio", false, 2, true),
                new ServicioEntity(
                        "protocolo-competencias",
                        "Protocolo de competencia",
                        "Plan de 3 días para llegar a tu competencia en el punto ideal: carga de "
                                + "energía, hidratación y timing de comidas.",
                        22_000, null, "Virtual", false, 3, false),
                new ServicioEntity(
                        "rutina-entrenamiento",
                        "Rutina de entrenamiento",
                        "Rutina de fuerza o acondicionamiento alineada con tu plan nutricional "
                                + "y tus objetivos.",
                        15_000, null, "Virtual", false, 4, false)));
    }

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

    @Test
    void noRenderizaNadaDeLaSeccionDeTestimoniosMientrasNoHayaDatos() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String html = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        // HU-10 "data-ready": con TestimonioService.obtenerTodos() vacío (hoy,
        // sin material real con permiso del PO) no debe llegar al HTML ni la
        // sección ni un contenedor vacío — el th:if cubre el <section> entero.
        assertThat(html).doesNotContain("id=\"testimonios\"");
        assertThat(html).doesNotContain("na-testimonio-card");
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

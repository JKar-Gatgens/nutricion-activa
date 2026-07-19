package com.nutricionactiva.controller;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.nutricionactiva.service.ProgramaService;

/**
 * Test de contrato de la página del programa "Fuerte y Definido" (HU-12):
 * fija el contrato observable entre {@link ProgramaController},
 * {@link ProgramaService} y la plantilla {@code programa.html}.
 * {@link ProgramaService} se importa real (sin mock): sus datos SON el
 * contrato — la propuesta aprobada por el PO en D-16 — y el escenario actual
 * (módulos solo-estructura, D-19) es exactamente el que estos tests cubren.
 */
@WebMvcTest(ProgramaController.class)
@Import(ProgramaService.class)
class ProgramaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProgramaService programaService;

    @Test
    void devuelveLaVistaProgramaConElPrograma() throws Exception {
        mockMvc.perform(get("/programa"))
                .andExpect(status().isOk())
                .andExpect(view().name("programa"))
                .andExpect(model().attributeExists("programa"));
    }

    @Test
    void renderizaLosTresModulosSoloComoEstructura() throws Exception {
        String html = obtenerHtml();

        // D-16: 3 módulos de 30 días. Una card por módulo, con su número.
        assertThat(contarOcurrencias(html, "na-modulo-card")).isEqualTo(3);
        assertThat(html).contains("Módulo 1");
        assertThat(html).contains("Módulo 2");
        assertThat(html).contains("Módulo 3");
        assertThat(html).contains("30 días");

        // D-19: la vista publica EXACTAMENTE el nivel de detalle que existe en
        // los datos — ni más ni menos. La expectativa se deriva del service
        // real (no del dataset de hoy): cuando el PO produzca el detalle de un
        // módulo (HU-13), este test sigue en verde sin tocarlo, y solo falla
        // si la vista deja de reflejar los datos (contrato roto de verdad).
        long modulosConDescripcion = programaService.obtenerPrograma().modulos().stream()
                .filter(modulo -> modulo.descripcion() != null)
                .count();
        long modulosConDetalles = programaService.obtenerPrograma().modulos().stream()
                .filter(modulo -> !modulo.detalles().isEmpty())
                .count();
        assertThat(contarOcurrencias(html, "na-modulo-desc")).isEqualTo((int) modulosConDescripcion);
        assertThat(contarOcurrencias(html, "na-modulo-detalles")).isEqualTo((int) modulosConDetalles);

        // Invariantes verdaderas, independientes de los datos: jamás texto
        // placeholder ni marcadores pendientes en la página publicada (D-11).
        assertThat(html).doesNotContainIgnoringCase("próximamente");
        assertThat(html).doesNotContain("na-pending");
    }

    @Test
    void renderizaLosSeisElementosQueIncluye() throws Exception {
        String html = obtenerHtml();

        // Los 6 elementos de D-16, uno por ítem con icono.
        assertThat(contarOcurrencias(html, "na-incluye-item")).isEqualTo(6);
        assertThat(html).contains("Plan nutricional personalizado");
        assertThat(html).contains("Guía de entrenamiento estructurado");
        assertThat(html).contains("Sesiones de seguimiento online o presencial");
        assertThat(html).contains("Listas de intercambio de alimentos");
        assertThat(html).contains("Guía de suplementación deportiva");
        assertThat(html).contains("Atención en tiempo real");
    }

    @Test
    void muestraElPrecioCorregidoPorElPoYLaPoblacion() throws Exception {
        String html = obtenerHtml();

        // D-16: ₡105.000 el programa completo (precio corregido por el PO),
        // en el formato de cifra del proyecto (espacio de miles).
        assertThat(html).contains("₡105 000");
        assertThat(html).contains("Hombres y mujeres de 16 años o más");
    }

    @Test
    void elCtaEsInformacionPorWhatsappSinPagoNiInscripcion() throws Exception {
        String html = obtenerHtml();

        // D-17: la venta cierra por WhatsApp con mensaje precargado del
        // programa; el número sale de las propiedades (GlobalModelAttributes).
        assertThat(html).contains("Quiero más información");
        assertThat(html).contains("wa.me/50689592110?text=");
        // El nombre del programa viaja URL-encodeado en el mensaje.
        assertThat(html).contains("Fuerte%20y%20Definido");

        // Sin pago ni inscripción en línea (D-17): ningún formulario en la página.
        assertThat(html).doesNotContain("<form");
    }

    private String obtenerHtml() throws Exception {
        MvcResult result = mockMvc.perform(get("/programa"))
                .andExpect(status().isOk())
                .andReturn();
        return result.getResponse().getContentAsString(StandardCharsets.UTF_8);
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

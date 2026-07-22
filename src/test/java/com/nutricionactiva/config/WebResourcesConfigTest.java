package com.nutricionactiva.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Verifica el cache busting de recursos estáticos (Sprint 4, Bloque 1): el
 * HTML servido debe referenciar {@code styles.css} con el hash de su
 * contenido en el nombre, no la ruta plana. Sin {@link WebResourcesConfig}
 * (que registra {@code ResourceUrlEncodingFilter}), Thymeleaf devolvería
 * {@code /css/styles.css} sin hash aunque el resource chain de
 * {@code application.properties} ya supiera servir el nombre versionado —
 * este test cubre justo esa integración end-to-end, no solo la config aislada.
 *
 * <p>Carga el contexto completo (igual que {@link
 * com.nutricionactiva.NutricionActivaApplicationTests}): requiere MySQL local
 * con el perfil "dev".
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class WebResourcesConfigTest {

    private static final Pattern CSS_CON_HASH = Pattern.compile("/css/styles-[0-9a-f]{20,40}\\.css");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void stylesCssSeSirveConHashDeContenidoEnElNombre() throws Exception {
        String html = mockMvc.perform(get("/"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(html).containsPattern(CSS_CON_HASH);
    }
}

package com.nutricionactiva;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Carga el contexto completo, incluido el DataSource real (Sprint 2, HU-04).
 * Requiere MySQL local con el perfil "dev" configurado (ver
 * application-dev.properties.example) — igual que correr la app localmente.
 */
@SpringBootTest
@ActiveProfiles("dev")
class NutricionActivaApplicationTests {

    @Test
    void contextLoads() {
    }

}

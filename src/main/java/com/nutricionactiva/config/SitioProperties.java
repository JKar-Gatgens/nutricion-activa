package com.nutricionactiva.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos del sitio (app.sitio.* en application.properties).
 * {@code baseUrl} es la URL pública absoluta SIN barra final: los meta tags
 * Open Graph (og:url, og:image) exigen URLs absolutas y los scrapers de
 * WhatsApp/Facebook no resuelven relativas. En producción se sobreescribe
 * con la variable de entorno APP_SITIO_BASEURL (dominio real, pendiente).
 */
@Validated
@ConfigurationProperties(prefix = "app.sitio")
public record SitioProperties(@NotBlank String baseUrl) {
}

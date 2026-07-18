package com.nutricionactiva.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos de correo propios de la app (app.mail.* en application.properties),
 * distintos de las propiedades estándar {@code spring.mail.*} (host, puerto,
 * credenciales) que ya gestiona la autoconfiguración de
 * {@code spring-boot-starter-mail}.
 * <p>
 * {@code remitenteNombre} es el nombre visible del remitente (el correo real
 * usado para autenticar contra el SMTP es {@code spring.mail.username}, no
 * este). {@code correoNutricionista} es el destinatario del aviso de cada
 * reserva nueva (HU-05, D-03) — coincide hoy con el correo publicado en el
 * footer del sitio (interino, ver docs/pendientes.md).
 */
@Validated
@ConfigurationProperties(prefix = "app.mail")
public record MailProperties(@NotBlank String remitenteNombre, @NotBlank String correoNutricionista) {
}

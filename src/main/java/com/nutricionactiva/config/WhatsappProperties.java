package com.nutricionactiva.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos de contacto de WhatsApp (app.whatsapp.* en application.properties).
 * Única fuente del número: las vistas construyen los enlaces wa.me desde aquí.
 * <p>
 * Validado al arranque: si falta una propiedad en algún entorno, la app falla
 * rápido en vez de publicar los enlaces rotos en silencio.
 */
@Validated
@ConfigurationProperties(prefix = "app.whatsapp")
public record WhatsappProperties(@NotBlank String numero, @NotBlank String mensaje) {

    /**
     * Número formateado para mostrar en pantalla (p. ej. "+506 8959 2110").
     * El href de los enlaces debe seguir usando {@link #numero()} crudo.
     * Asume numeración de Costa Rica (506 + 8 dígitos); cualquier otro formato
     * se muestra crudo con el prefijo "+".
     */
    public String numeroVisible() {
        if (numero != null && numero.length() == 11 && numero.startsWith("506")) {
            return "+506 " + numero.substring(3, 7) + " " + numero.substring(7);
        }
        return "+" + numero;
    }
}

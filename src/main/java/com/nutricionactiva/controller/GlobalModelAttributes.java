package com.nutricionactiva.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nutricionactiva.config.WhatsappProperties;

/**
 * Atributos de modelo disponibles en todas las vistas (el botón flotante y el
 * footer viven en el layout compartido, así que cada página los necesita).
 */
@ControllerAdvice
// @EnableConfigurationProperties vive AQUÍ a propósito: @WebMvcTest incluye los
// @ControllerAdvice pero no escanea @Configuration, así que el slice test queda
// autocontenido. Si se mueve a una clase de configuración, HomeControllerTest rompe.
@EnableConfigurationProperties(WhatsappProperties.class)
public class GlobalModelAttributes {

    private final WhatsappProperties whatsapp;

    public GlobalModelAttributes(WhatsappProperties whatsapp) {
        this.whatsapp = whatsapp;
    }

    @ModelAttribute("whatsapp")
    public WhatsappProperties whatsapp() {
        return whatsapp;
    }
}

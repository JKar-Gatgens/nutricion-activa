package com.nutricionactiva.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nutricionactiva.config.SitioProperties;
import com.nutricionactiva.config.WhatsappProperties;

/**
 * Atributos de modelo disponibles en todas las vistas (el botón flotante, el
 * footer y los meta tags viven en el layout compartido, así que cada página
 * los necesita).
 */
@ControllerAdvice
// @EnableConfigurationProperties vive AQUÍ a propósito: @WebMvcTest incluye los
// @ControllerAdvice pero no escanea @Configuration, así que el slice test queda
// autocontenido. Si se mueve a una clase de configuración, HomeControllerTest rompe.
@EnableConfigurationProperties({ WhatsappProperties.class, SitioProperties.class })
public class GlobalModelAttributes {

    private final WhatsappProperties whatsapp;
    private final SitioProperties sitio;

    public GlobalModelAttributes(WhatsappProperties whatsapp, SitioProperties sitio) {
        this.whatsapp = whatsapp;
        this.sitio = sitio;
    }

    @ModelAttribute("whatsapp")
    public WhatsappProperties whatsapp() {
        return whatsapp;
    }

    @ModelAttribute("sitio")
    public SitioProperties sitio() {
        return sitio;
    }
}

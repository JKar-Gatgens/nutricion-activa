package com.nutricionactiva.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

/**
 * Cache busting de recursos estaticos (Sprint 4, Bloque 1).
 * <p>
 * spring.web.resources.chain.strategy.content.enabled=true (ver
 * application.properties) hace que Spring sirva cada recurso con un hash de
 * su contenido en el nombre (styles-{@code <hash>}.css), pero eso por si solo
 * NO reescribe las URLs que generan las plantillas Thymeleaf con
 * {@code th:href="@{...}"}. Thymeleaf resuelve esas URLs pasandolas por
 * {@code HttpServletResponse.encodeURL()} (confirmado en el jar de Thymeleaf:
 * {@code JakartaServletWebExchange.transformURL} delega ahi), y ese metodo
 * solo hace algo si un Filter lo intercepta.
 * <p>
 * {@link ResourceUrlEncodingFilter} es ese filtro -ya vive en spring-webmvc,
 * no hace falta una dependencia nueva- pero esta version de Boot no lo
 * registra automaticamente (no hay ninguna clase con ese nombre en
 * spring-boot-autoconfigure ni en spring-boot-webmvc): sin este bean, cada
 * th:href seguiria devolviendo /css/styles.css sin hash pese a que el
 * ResourceResolverChain ya sabe servir el nombre versionado.
 */
@Configuration
public class WebResourcesConfig {

    @Bean
    public FilterRegistrationBean<ResourceUrlEncodingFilter> resourceUrlEncodingFilter() {
        FilterRegistrationBean<ResourceUrlEncodingFilter> registration =
                new FilterRegistrationBean<>(new ResourceUrlEncodingFilter());
        registration.setName("resourceUrlEncodingFilter");
        return registration;
    }
}

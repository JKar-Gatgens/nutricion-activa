package com.nutricionactiva.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Habilita {@code @Async} (HU-05): sin {@code @EnableAsync}, Spring ignora la
 * anotación {@code @Async} en silencio -no hay excepción ni warning- y el
 * método se ejecuta de forma síncrona como si la anotación no existiera. El
 * executor usado es el {@code ThreadPoolTaskExecutor} que ya trae por defecto
 * {@code TaskExecutionAutoConfiguration} (parte del core de
 * {@code spring-boot-autoconfigure}, confirmado presente en esta versión):
 * no hace falta declarar un {@code @Bean Executor} propio para el volumen
 * actual del MVP.
 * <p>
 * {@link MailProperties} se registra aquí (no en un {@code @ControllerAdvice}
 * como {@link WhatsappProperties}/{@link SitioProperties}, ver
 * {@code GlobalModelAttributes}) porque no lo consume ninguna vista: solo lo
 * usa {@link com.nutricionactiva.service.NotificacionCitaListener}, así que
 * no necesita estar disponible en los slice tests {@code @WebMvcTest}.
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(MailProperties.class)
public class AsyncConfig {
}

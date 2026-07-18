package com.nutricionactiva.service;

/**
 * Evento de dominio publicado por {@link ReservaCitaService} al final de
 * {@code reservar(...)}, dentro de la misma transacción que guarda la
 * {@code Cita} (HU-05). Porta solo el {@code id}, no la entidad completa: el
 * listener que reacciona a este evento corre en otro hilo ({@code @Async})
 * después del commit ({@code @TransactionalEventListener(AFTER_COMMIT)}), así
 * que recarga la {@code Cita} por su cuenta en vez de arrastrar una entidad
 * potencialmente desconectada de la sesión de Hibernate original.
 */
public record CitaReservadaEvent(Long citaId) {
}

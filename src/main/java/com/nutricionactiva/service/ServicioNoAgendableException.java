package com.nutricionactiva.service;

/**
 * Se solicitó disponibilidad para un servicio que no existe en el catálogo o
 * que existe pero no es agendable (planes tipo protocolo/rutina, que se
 * solicitan por WhatsApp).
 */
public class ServicioNoAgendableException extends RuntimeException {

    public ServicioNoAgendableException(String servicioId) {
        super("El servicio '" + servicioId + "' no existe o no es agendable");
    }
}

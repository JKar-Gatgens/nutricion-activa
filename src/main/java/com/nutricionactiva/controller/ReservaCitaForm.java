package com.nutricionactiva.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Datos del formulario de {@code POST /agendar}. Clase mutable (no record) a
 * propósito: el binding clásico de Spring MVC para {@code @ModelAttribute}
 * (getters/setters) es el camino probado con {@code th:field} de Thymeleaf,
 * sin depender del soporte más nuevo y menos ejercitado de binding por
 * constructor para records.
 *
 * <p>Los límites de {@link Size} reflejan las columnas de la tabla
 * {@code cita} (Flyway V2): {@code cliente_nombre}/{@code cliente_correo}
 * VARCHAR(150), {@code cliente_telefono} VARCHAR(30), {@code motivo}
 * VARCHAR(500).
 *
 * <p>{@code paginaWeb} es un honeypot anti-bots: un campo que un visitante
 * real nunca ve ni llena (oculto visualmente y de lectores de pantalla en
 * la plantilla), pero que un bot que completa formularios automáticamente sí
 * suele rellenar. Sin anotaciones de validación a propósito: debe poder
 * llegar vacío.
 */
public class ReservaCitaForm {

    @NotBlank
    private String servicioId;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private LocalTime horaInicio;

    @NotBlank
    @Size(max = 150)
    private String nombre;

    @NotBlank
    @Email
    @Size(max = 150)
    private String correo;

    @NotBlank
    @Size(max = 30)
    private String telefono;

    @NotBlank
    @Size(max = 500)
    private String motivo;

    private String paginaWeb;

    public String getServicioId() {
        return servicioId;
    }

    public void setServicioId(String servicioId) {
        this.servicioId = servicioId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
}

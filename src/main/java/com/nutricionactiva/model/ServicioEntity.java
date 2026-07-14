package com.nutricionactiva.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Fila de la tabla {@code servicio} (Flyway V1). Es el contraparte de
 * persistencia del record {@link Servicio}: {@link
 * com.nutricionactiva.service.CatalogoServicios} la mapea internamente, así
 * que ni la vista ni el controller conocen esta clase. Catálogo de solo
 * lectura hoy (se administra vía migraciones, no desde la app), por eso no
 * tiene setters.
 */
@Entity
@Table(name = "servicio")
public class ServicioEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(name = "precio_colones")
    private Integer precioColones;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Column(nullable = false)
    private String modalidad;

    @Column(nullable = false)
    private boolean destacado;

    @Column(nullable = false)
    private int orden;

    @Column(nullable = false)
    private boolean agendable;

    protected ServicioEntity() {
        // JPA
    }

    public ServicioEntity(
            String id,
            String nombre,
            String descripcion,
            Integer precioColones,
            Integer duracionMinutos,
            String modalidad,
            boolean destacado,
            int orden,
            boolean agendable) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioColones = precioColones;
        this.duracionMinutos = duracionMinutos;
        this.modalidad = modalidad;
        this.destacado = destacado;
        this.orden = orden;
        this.agendable = agendable;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getPrecioColones() {
        return precioColones;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public String getModalidad() {
        return modalidad;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public int getOrden() {
        return orden;
    }

    public boolean isAgendable() {
        return agendable;
    }
}

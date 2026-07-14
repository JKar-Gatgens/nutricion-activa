package com.nutricionactiva.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Una reserva concreta (tabla {@code cita}, Flyway V2). Sin registro de
 * usuario: nace siempre {@link EstadoCita#CONFIRMADA}. {@code servicioId} es
 * una columna simple (la integridad la garantiza el FOREIGN KEY de la
 * migración, no una relación JPA) para evitar lazy-loading innecesario.
 * {@code duracionMinutosSnapshot} y {@code fechaHoraFinUtc} se calculan una
 * sola vez al reservar y quedan fijos: si el catálogo cambia la duración de
 * un servicio más adelante, las citas ya reservadas no se reescriben.
 * {@code fechaHoraInicioUtc}/{@code fechaHoraFinUtc} siempre están en UTC —
 * la conversión a/desde hora de Costa Rica vive solo en
 * {@link com.nutricionactiva.service.DisponibilidadService}.
 */
@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "servicio_id", nullable = false)
    private String servicioId;

    @Column(name = "duracion_minutos_snapshot", nullable = false)
    private int duracionMinutosSnapshot;

    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;

    @Column(name = "cliente_correo", nullable = false)
    private String clienteCorreo;

    @Column(name = "cliente_telefono", nullable = false)
    private String clienteTelefono;

    @Column(nullable = false, length = 500)
    private String motivo;

    @Column(name = "fecha_hora_inicio_utc", nullable = false)
    private Instant fechaHoraInicioUtc;

    @Column(name = "fecha_hora_fin_utc", nullable = false)
    private Instant fechaHoraFinUtc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCita estado;

    @Column(name = "creado_en", nullable = false)
    private Instant creadoEn;

    protected Cita() {
        // JPA
    }

    public Cita(
            String servicioId,
            int duracionMinutosSnapshot,
            String clienteNombre,
            String clienteCorreo,
            String clienteTelefono,
            String motivo,
            Instant fechaHoraInicioUtc,
            Instant fechaHoraFinUtc) {
        this.servicioId = servicioId;
        this.duracionMinutosSnapshot = duracionMinutosSnapshot;
        this.clienteNombre = clienteNombre;
        this.clienteCorreo = clienteCorreo;
        this.clienteTelefono = clienteTelefono;
        this.motivo = motivo;
        this.fechaHoraInicioUtc = fechaHoraInicioUtc;
        this.fechaHoraFinUtc = fechaHoraFinUtc;
        this.estado = EstadoCita.CONFIRMADA;
        this.creadoEn = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getServicioId() {
        return servicioId;
    }

    public int getDuracionMinutosSnapshot() {
        return duracionMinutosSnapshot;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getClienteCorreo() {
        return clienteCorreo;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public String getMotivo() {
        return motivo;
    }

    public Instant getFechaHoraInicioUtc() {
        return fechaHoraInicioUtc;
    }

    public Instant getFechaHoraFinUtc() {
        return fechaHoraFinUtc;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public Instant getCreadoEn() {
        return creadoEn;
    }
}

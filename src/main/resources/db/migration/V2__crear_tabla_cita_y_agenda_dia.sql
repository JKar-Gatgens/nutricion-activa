-- Fila de sincronizacion por dia: no representa negocio, solo existe para que
-- ReservaCitaService pueda tomar un candado (SELECT ... FOR UPDATE) sobre un
-- dia concreto y serializar reservas concurrentes de ese dia (ver
-- docs/arquitectura-agendamiento.md, seccion 3).
CREATE TABLE agenda_dia (
    fecha DATE NOT NULL,
    PRIMARY KEY (fecha)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- fecha_hora_*_utc y creado_en usan DATETIME (no TIMESTAMP): TIMESTAMP en
-- MySQL convierte automaticamente segun la zona horaria de la sesion, lo que
-- puede introducir una segunda conversion de zona horaria fuera del control
-- de la app. La conversion CR <-> UTC se hace explicitamente en Java (ver
-- docs/arquitectura-agendamiento.md, seccion 4); estas columnas solo deben
-- guardar el instante UTC ya calculado, sin que MySQL lo reinterprete.
CREATE TABLE cita (
    id                         BIGINT       NOT NULL AUTO_INCREMENT,
    servicio_id                VARCHAR(50)  NOT NULL,
    duracion_minutos_snapshot  INT          NOT NULL,
    cliente_nombre             VARCHAR(150) NOT NULL,
    cliente_correo             VARCHAR(150) NOT NULL,
    cliente_telefono           VARCHAR(30)  NOT NULL,
    motivo                     VARCHAR(500) NOT NULL,
    fecha_hora_inicio_utc      DATETIME     NOT NULL,
    fecha_hora_fin_utc         DATETIME     NOT NULL,
    estado                     VARCHAR(20)  NOT NULL,
    creado_en                  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_cita_servicio FOREIGN KEY (servicio_id) REFERENCES servicio (id),
    -- Soporta la consulta de solape del servicio de disponibilidad
    -- (fecha_hora_inicio_utc < :fin AND fecha_hora_fin_utc > :inicio).
    INDEX idx_cita_rango_utc (fecha_hora_inicio_utc, fecha_hora_fin_utc)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

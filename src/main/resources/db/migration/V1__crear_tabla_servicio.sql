CREATE TABLE servicio (
    id                VARCHAR(50)  NOT NULL,
    nombre            VARCHAR(100) NOT NULL,
    descripcion       VARCHAR(500) NOT NULL,
    precio_colones    INT          NULL,
    duracion_minutos  INT          NULL,
    modalidad         VARCHAR(50)  NOT NULL,
    destacado         BOOLEAN      NOT NULL DEFAULT FALSE,
    orden             INT          NOT NULL,
    agendable         BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Catalogo definitivo confirmado por el PO (2026-07-12), migrado desde
-- CatalogoServicios (memoria) preservando id, orden, destacado y agendable.
INSERT INTO servicio
    (id, nombre, descripcion, precio_colones, duracion_minutos, modalidad, destacado, orden, agendable)
VALUES
    ('consulta-nutricion',
     'Consulta de nutrición',
     'Evaluación inicial completa: composición corporal, hábitos y objetivos para construir tu plan personalizado.',
     30000, 90, 'Virtual o a domicilio', TRUE, 1, TRUE),

    ('consulta-seguimiento',
     'Consulta de seguimiento',
     'Ajustes del plan según tu progreso: mediciones, revisión de adherencia y nuevas metas.',
     30000, 40, 'Virtual o a domicilio', FALSE, 2, TRUE),

    ('protocolo-competencias',
     'Protocolo de competencia',
     'Plan de 3 días para llegar a tu competencia en el punto ideal: carga de energía, hidratación y timing de comidas.',
     22000, NULL, 'Virtual', FALSE, 3, FALSE),

    ('rutina-entrenamiento',
     'Rutina de entrenamiento',
     'Rutina de fuerza o acondicionamiento alineada con tu plan nutricional y tus objetivos.',
     15000, NULL, 'Virtual', FALSE, 4, FALSE);

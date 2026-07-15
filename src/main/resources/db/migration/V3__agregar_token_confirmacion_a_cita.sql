-- Token opaco (UUID) para la URL publica de confirmacion de una cita
-- (/agendar/confirmacion/{token}, paso 6 de HU-04). Antes esa ruta usaba
-- directamente el id autoincremental de MySQL, secuencial y enumerable sin
-- autenticacion: cualquiera podia iterar /agendar/confirmacion/1, /2, /3...
-- y ver nombre, fecha/hora y servicio de citas ajenas (hallazgo de code
-- review, 2026-07-14).
ALTER TABLE cita
    ADD COLUMN token_confirmacion CHAR(36) NULL;

-- Backfill de filas existentes (solo datos de desarrollo: el proyecto
-- todavia no esta desplegado en produccion, ver
-- docs/arquitectura-agendamiento.md). Deliberadamente NO usa UUID(): esa
-- funcion no es deterministica y MySQL rechaza sentencias que la usan
-- cuando el binlog replica en formato STATEMENT ("Statement is unsafe
-- because it uses a system function that may return a different value on
-- the replica", error 1674). El valor derivado del id solo necesita ser
-- unico para estas filas viejas, no impredecible -- ninguna recibe trafico
-- publico nuevo. Toda fila creada de aqui en adelante obtiene su token real
-- (UUID.randomUUID()) en Java, ver Cita.java.
UPDATE cita
SET token_confirmacion = CONCAT(LPAD(HEX(id), 8, '0'), '-0000-4000-8000-000000000000')
WHERE token_confirmacion IS NULL;

ALTER TABLE cita
    MODIFY COLUMN token_confirmacion CHAR(36) NOT NULL,
    ADD UNIQUE INDEX idx_cita_token_confirmacion (token_confirmacion);

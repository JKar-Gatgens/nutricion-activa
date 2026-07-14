# Arquitectura de agendamiento (HU-04)

> **Estado:** Aprobado por el PO/dev el 14 de julio de 2026 · **Sprint:** 2
> Diseño elaborado por el agente `spring-boot-engineer` antes de tocar código, revisado y aprobado en conversación. Este documento es la referencia del diseño para el resto del sprint — cualquier desviación durante la implementación debe reflejarse aquí.

## Contexto

HU-04 (`docs/product-backlog.md`) agrega agendamiento de citas sin registro de usuario. Es el primer momento del proyecto en que hay datos reales de clientes persistidos en producción (Aiven), y en que dos visitantes pueden competir por el mismo recurso (un horario). El diseño prioriza soluciones simples y mantenibles para un equipo de un solo dev junior, no arquitectura sofisticada.

---

## 1. Modelo de entidades

### `Servicio` (catálogo — ya existe, gana un campo)

Describe **qué se puede reservar**, no una reserva en sí. Hoy es una lista fija en código (`CatalogoServicios`); en el diseño pasa a vivir en una tabla, pero se ve igual desde afuera (mismo contrato para `HomeController` e `index.html`).

```
Servicio
├── id             "consulta-nutricion"
├── nombre         "Consulta de nutrición"
├── descripcion    "Evaluación inicial completa..."
├── precioColones  30000
├── duracionMinutos 90
├── modalidad      "Virtual o a domicilio"
├── destacado      true
├── orden          1
└── agendable      true   ← campo NUEVO
```

`agendable` reemplaza la inferencia implícita actual (duracionMinutos != null ⇒ agendable). Hoy esa suposición funciona porque coincide, pero mezcla dos preguntas distintas ("¿cuánto dura?" vs. "¿se puede agendar?"). Con el campo explícito: `consulta-nutricion` y `consulta-seguimiento` → `true`; `protocolo-competencias` y `rutina-entrenamiento` → `false` (se piden por WhatsApp).

Migración de catálogo estático → tabla `servicio`: se separa el record `Servicio` (contrato público, usado por la vista) de una `ServicioEntity` JPA (persistencia). Un record no es gestionable como entidad JPA estándar. `CatalogoServicios` mapea `ServicioEntity → Servicio` internamente; nada cambia para `HomeController` ni la plantilla. El repositorio expone `findAllByOrderByOrdenAsc()` para preservar el `ORDER BY orden` que el contrato exige.

### `Cita` (entidad nueva)

Es **una reserva concreta**: una persona, un servicio, un horario.

```
Cita
├── id                      1024                    ← autogenerado, sin significado
├── servicioId              "consulta-nutricion"    ← columna simple + FK a nivel de BD
├── duracionMinutosSnapshot 90                       ← "foto" de la duración al reservar
├── clienteNombre           "María Pérez"
├── clienteCorreo           "maria@correo.com"
├── clienteTelefono         "8888-8888"
├── motivo                  "Quiero bajar de grasa corporal"
├── fechaHoraInicioUtc      2026-07-20 14:00 UTC     ← siempre en UTC
├── fechaHoraFinUtc         2026-07-20 15:30 UTC
├── estado                  CONFIRMADA               ← enum, no boolean
└── creadoEn                2026-07-14 10:32 UTC
```

Decisiones puntuales:

- **`servicioId` como columna simple + FK de BD, sin `@ManyToOne`.** Evita lazy-loading y gestión de sesión extra para un dev junior; la integridad la garantiza el `FOREIGN KEY` de la migración SQL, no la relación Java.
- **`duracionMinutosSnapshot`**: se copia la duración del servicio al reservar, no se lee en vivo del catálogo. Si el nutricionista cambia la duración de un servicio en el futuro, las citas ya reservadas conservan la duración con la que el cliente aceptó — no se reescriben retroactivamente.
- **`fechaHoraFinUtc` persistido**, no calculado al vuelo — permite que el chequeo de solape sea una comparación simple de columnas indexadas.
- **`estado` como enum** (`CONFIRMADA` hoy). El backlog anticipa más estados en Sprint 3 (D-04 a D-07: confirmación/reprogramación con tokens). Un `boolean` obligaría a cambiar el tipo de columna entonces; un enum solo agrega literales nuevos.
- No se agregan columnas de token/reprogramación ahora — llegan como migración Flyway nueva en Sprint 3, con el detalle real de esas historias.

### `AgendaDia` — qué es y para qué sirve

No representa nada de negocio ni se le muestra al usuario. Es una tabla técnica con un solo propósito: darle a la base de datos algo concreto que "agarrar y bloquear" cuando dos personas intentan reservar el mismo día al mismo tiempo.

```
AgendaDia
└── fecha   2026-07-20    ← única columna, es la clave
```

Su función se explica en la sección 3 (concurrencia).

---

## 2. Servicio de disponibilidad (algoritmo)

```
calcularSlotsDisponibles(servicioId, fechaCR):
    servicio = catalogo.buscarPorId(servicioId).filter(Servicio::agendable)
               .orElseThrow(ServicioNoAgendableException)

    hoyCR = LocalDate.now(ZONA_NEGOCIO)
    si fechaCR.isBefore(hoyCR.plusDays(1)): retornar []      // anticipación mínima 1 día
    si fechaCR.getDayOfWeek() == DOMINGO: retornar []

    duracion = Duration.ofMinutes(servicio.duracionMinutos())
    apertura = fechaCR.atTime(8, 0)
    cierre   = fechaCR.atTime(18, 0)
    candidatos = grid(apertura, cierre, paso = 30 min)        // 8:00, 8:30, 9:00...

    ocupadas = citaRepository.findConfirmadasEntre(inicioVentanaUtc, finVentanaUtc)
               // TODAS las citas del día, sin filtrar por servicio: un solo
               // profesional, una sola agenda compartida entre servicios

    libres = []
    para cada inicioCandidato en candidatos:
        finCandidato = inicioCandidato + duracion
        si finCandidato > cierre: continuar                   // debe TERMINAR dentro del horario
        si seSolapaConAlguna(inicioCandidato, finCandidato, ocupadas): continuar
        libres.add(FranjaHoraria(inicioCandidato, finCandidato))
    retornar libres

seSolapa(aInicio, aFin, bInicio, bFin) = aInicio < bFin && bInicio < aFin
    // desigualdades estrictas: una cita que termina a las 10:00 y otra
    // que empieza a las 10:00 NO se solapan
```

Notas: grid fijo de 30 min (constante única, fácil de cambiar); todas las citas del día se traen una sola vez y el solape se calcula en memoria (más simple que una query por candidato); el endpoint de reserva vuelve a llamar este cálculo server-side antes de persistir, como primera verificación barata — la garantía real contra condiciones de carrera vive en la sección 3.

---

## 3. Concurrencia, paso a paso

**Escenario**: Ana y Beto intentan reservar el mismo horario (20 de julio, 14:00 CR) casi al mismo segundo.

Sin ningún mecanismo pasaría esto: ambos consultan disponibilidad antes de que el otro guarde nada, ambos ven "libre", ambos guardan → **doble reserva**.

Cómo el diseño lo evita:

1. **Ana llega primero (por una fracción de segundo).** Su solicitud abre una transacción que: (a) se asegura de que exista una fila en `AgendaDia` para el 20 de julio (la crea si no existe), y (b) le pide a la base de datos el candado exclusivo de esa fila (`SELECT ... FOR UPDATE`) — como agarrar la única llave de una puerta.
2. **Beto llega justo después.** Su solicitud también intenta agarrar el candado de esa misma fila. Ana ya lo tiene, así que la base de datos hace **esperar** a Beto — su transacción se congela hasta que Ana termine.
3. **Con el candado en mano, Ana recalcula los horarios libres del 20 de julio.** Todavía no hay ninguna cita ese día, las 14:00 sigue libre. Guarda su cita; su transacción termina y libera el candado.
4. **Beto obtiene el candado.** Recalcula los horarios libres — ahora sí ve la cita de Ana ya guardada. Las 14:00 ya no aparece libre. El sistema le informa que ese horario acaba de ocuparse, sin dejarlo reservar.

**Resultado**: exactamente una cita se guarda; el segundo recibe un rechazo claro, nunca un doble booking ni un error genérico.

Detalle clave: el candado es **sobre el día completo**, no sobre el horario específico. Si Ana reserva a las 14:00 y Beto a las 9:00 el mismo día, Beto también espera a que Ana termine, aunque no haya conflicto real entre sus horarios — una espera de milisegundos, precio aceptado por mantener el mecanismo simple y verificable en vez de bloquear "solo el rango que se solapa" (mucho más difícil de razonar con garantías).

Se descartaron: optimistic locking (no resuelve inserts nuevos que se solapan entre sí), check-then-insert sin lock (riesgo real de doble-booking con tráfico bajo pero no nulo), y confiar en gap locks de InnoDB sobre rangos vacíos (frágil, difícil de verificar con confianza).

El test de concurrencia debe correr contra MySQL real (Testcontainers, no H2) porque el upsert de `AgendaDia` usa sintaxis nativa de MySQL.

---

## 4. Zona horaria, en la práctica

Regla de oro: la base de datos **siempre** guarda en UTC. Las reglas de negocio (8:00–18:00, lunes a sábado) **siempre** se piensan en hora de Costa Rica. La conversión pasa en un solo lugar del código.

Clases de Java:
- `Instant` → un punto exacto en el tiempo, sin zona horaria. Así se guarda `fechaHoraInicioUtc`/`fechaHoraFinUtc`.
- `LocalDateTime` → fecha y hora "de pared", sin decidir a qué instante UTC corresponde. Así piensa el negocio (horario CR).
- `ZonedDateTime` → el puente entre ambas, usando una constante fija: `ZoneId.of("America/Costa_Rica")`.

Ejemplo concreto (14:00 CR del 20 de julio):

```java
LocalDateTime horaCR = LocalDateTime.of(2026, 7, 20, 14, 0);
ZonedDateTime horaConZona = ZonedDateTime.of(horaCR, ZONA_NEGOCIO);
Instant instanteUtc = horaConZona.toInstant();
// resultado: 2026-07-20T20:00:00Z (14:00 CR = 20:00 UTC, CR es UTC-6)
```

Y a la inversa, para calcular "¿qué horarios libres hay hoy?":

```java
Instant ahoraUtc = Instant.now();
LocalDate hoyCR = ahoraUtc.atZone(ZONA_NEGOCIO).toLocalDate();
```

**Por qué evita el bug de medianoche**: si se sacara la fecha directamente de un timestamp UTC sin pasar por la zona CR, cerca de la medianoche de Costa Rica se asignarían horas a la fecha equivocada (CR es UTC-6, así que la noche de un día en CR cae en la madrugada UTC del día siguiente). La regla "construir primero en `LocalDate`/`LocalTime` de CR, convertir a UTC al final" elimina esa clase de error. Como Costa Rica no tiene horario de verano, no existen los casos de "esta hora local ocurre dos veces" o "no existe" que sí complican zonas con DST — la conversión es aritmética simple y determinística.

**Nota de implementación (paso 4, 14 de julio de 2026):** la conversión Java es solo la mitad del problema — el driver JDBC (MySQL Connector/J) también decide una zona horaria al traducir `Instant` a las columnas `DATETIME` de `cita`. Por defecto (`connectionTimeZone=LOCAL`) usa la zona por defecto de la JVM, que en las máquinas de este equipo es `America/Costa_Rica`, no UTC — se verificó explícitamente. Sin corregirlo, cada hora guardada habría quedado corrida 6 horas de forma silenciosa (ningún error, ningún test lo detecta a simple vista). Se agregó `connectionTimeZone=UTC` a la URL JDBC en `application.properties` y `application-dev.properties(.example)`, y se agregó `CitaRepositoryTest` (`src/test/java/com/nutricionactiva/repository/`), que guarda una `Cita` con un `Instant` conocido, limpia el contexto de persistencia (`entityManager.clear()`) para forzar una lectura real desde MySQL, y confirma que el `Instant` releído es idéntico al original.

---

## 5. Decisión de esquema: Flyway (no `ddl-auto`)

**Decisión**: Flyway con migraciones versionadas + `spring.jpa.hibernate.ddl-auto=validate` (nunca `update`/`create`/`create-drop`).

Justificación: HU-04 es el momento en que el proyecto empieza a tener datos reales de clientes en producción (Aiven) desde el día uno del despliegue. `ddl-auto=update` decide el DDL implícitamente en cada arranque, sin diff revisable ni historial, y puede variar entre versiones de Hibernate. Con datos reales de por medio, eso es un riesgo que un equipo de un solo dev junior sin red de DBA no debería asumir. El costo de adopción es bajo: dos migraciones iniciales (`V1` tabla `servicio` + seed de los 4 servicios, `V2` tabla `cita` + `agenda_dia`), y corre igual en MySQL local y Aiven.

`ddl-auto=validate` se mantiene como red de seguridad barata: valida que las entidades coincidan con el esquema real sin emitir DDL — atrapa errores de mapeo a costo cero.

**Nota de implementación (paso 2, 14 de julio de 2026):** el `spring-boot-autoconfigure` de esta versión de Spring Boot (4.1.0) no trae autoconfiguración de Flyway (se verificó que no hay ninguna clase de Flyway en el jar). Se agregó `DatabaseConfig` (`src/main/java/com/nutricionactiva/config/DatabaseConfig.java`), un `@Bean` explícito que construye el `DataSource` y corre `Flyway.migrate()` sobre él antes de devolverlo. Como Hibernate depende de ese `DataSource` para construir el `entityManagerFactory`, el orden (migrar antes de validar el esquema) queda garantizado sin necesidad de `@DependsOn` ni post-processors adicionales. Verificado contra MySQL local: las migraciones `V1`/`V2` corren y quedan registradas en `flyway_schema_history`.

---

## 6. Orden de implementación

1. Flag `agendable` explícito en el catálogo, sin BD (riesgo cero sobre `HomeControllerTest`).
2. Dependencias de persistencia + migraciones Flyway, aditivo, nada las usa todavía.
3. `CatalogoServicios` migra a `ServicioRepository` (único paso que toca `HomeControllerTest`: se agrega `@MockBean`, ninguna aserción cambia).
4. Entidades `Cita`/`AgendaDia` + `DisponibilidadService` puro, con los 8 tests de contrato (sección siguiente).
5. `ReservaCitaService` con el candado de `agenda_dia` + test de concurrencia (Testcontainers).
6. Capa web: `CitaController`, plantilla Thymeleaf, validación + honeypot anti-bot.
7. Coordinar el CTA "Agendar mi cita" del hero con HU-11 (reorden en paralelo).
8. Regresión completa, despliegue, y una reserva real de extremo a extremo en producción antes de dar la historia por terminada.

---

## 8 casos del test de contrato de disponibilidad

Test todavía por escribir (paso 4). Cada caso responde a una regla de negocio concreta:

1. **Servicio no agendable** (protocolo, rutina) → nunca ofrece horarios ni se muestra como reservable.
2. **Un domingo** → lista de horarios vacía.
3. **Hoy o una fecha pasada** → lista vacía (anticipación mínima de 1 día).
4. **Límite de las 18:00** → un horario que terminaría después se descarta; uno que termina exactamente a las 18:00 se ofrece (límite inclusivo).
5. **Solapamiento entre servicios de distinta duración** → una cita de seguimiento (40 min) a las 10:00 debe hacer desaparecer un horario de consulta inicial (90 min) que chocaría con ella, aunque las horas de inicio no coincidan.
6. **Conversión CR→UTC exacta** → 8:00 AM CR debe guardarse como 14:00 UTC del mismo día (caso fijo, pinea la conversión).
7. **Reserva simultánea del mismo horario** → dos hilos/requests concurrentes contra el mismo slot; exactamente una cita persistida, la otra rechazada con excepción de negocio clara (no error 500 genérico).
8. **Datos del formulario inválidos** (correo mal formado, motivo vacío, honeypot relleno) → se rechaza, ninguna cita se guarda aunque el horario estuviera libre.

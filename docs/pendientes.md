# Pendientes

Tareas anotadas durante el desarrollo que no bloquean el sprint actual.

## ⚠️ Bloqueantes de deploy

- [ ] **BLOQUEANTE — `connectionTimeZone=UTC` debe llegar a la variable `APP_DB_URL`
      de Render, no solo al default local**: `application.properties` solo aplica
      `connectionTimeZone=UTC` en su valor por defecto (`jdbc:mysql://localhost:...`);
      en Render la variable de entorno `APP_DB_URL` sobreescribe la URL COMPLETA hacia
      Aiven, no la extiende — así que el fix del paso 4 (ver sección 4 de
      `docs/arquitectura-agendamiento.md`) no llega a producción a menos que se
      actualice esa variable manualmente, fuera del repo. **Antes de desplegar con
      Aiven en la FASE 4, la variable `APP_DB_URL` en Render DEBE incluir
      `connectionTimeZone=UTC`, o el bug de corrupción de zona horaria (6h) resucita
      en producción** — cada `DATETIME` guardado en `cita` quedaría corrido 6 horas de
      forma silenciosa, sin ningún error visible. Verificar con un round-trip real
      contra Aiven (mismo principio que `CitaRepositoryTest`, pero apuntando a
      producción) al menos una vez antes de dar HU-04 por desplegada.
      *(Origen: code review de `feature/hu04-disponibilidad` paso 4, 2026-07-14)*

## Seguridad

- [ ] **`POST /agendar` no tiene rate limiting ni CAPTCHA — el honeypot solo
      frena bots ingenuos**: `AgendarController` valida en servidor (campos
      obligatorios, correo, horario realmente libre) y descarta envíos con
      el campo honeypot (`paginaWeb`) relleno, pero eso solo detecta bots que
      completan cualquier campo presente en el DOM. Un atacante que arma el
      POST a mano (sin pasar por el HTML) simplemente omite ese campo — Spring
      lo deja en `null`, indistinguible de un envío legítimo — y nada más lo
      frena: no hay límite por IP, ni CAPTCHA, ni verificación de
      correo/teléfono en ningún punto del proyecto. Con la disponibilidad
      recalculándose correctamente en servidor (no hay forma de reservar un
      horario inválido), el riesgo real es de **negación de servicio por
      reserva masiva**: un script podría recorrer servicio × fecha × horario
      y llenar la agenda completa con datos inventados, bloqueando a clientes
      reales. Abordarlo en Sprint 3 cuando entra el agente `security-auditor`
      a endurecer toda la superficie del formulario (coincide con los tokens
      de confirmación/reprogramación ya planeados para esa etapa — ver
      `docs/arquitectura-agendamiento.md`); una opción liviana es rate
      limiting por IP sobre `POST /agendar` (p. ej. Bucket4j).
      *(Origen: code review de `feature/hu04-agendar-ui` paso 6, 2026-07-14)*

## Marca / assets

- [x] **Logo optimizado**: `static/img/logo.png` pesa 151 KB y no es cuadrado (517×616 px).
      Exportar un recorte cuadrado optimizado en WebP (más un PNG pequeño para favicon)
      y actualizar las referencias en `fragments/layout.html`. *(Origen: code review de
      `feature/base-ui`, 2026-07-11. Resuelto 2026-07-12 en /impeccable polish: emblema
      cuadrado sin wordmark en `logo.webp` (15 KB, 320px) + `favicon.png` (4 KB, 48px);
      la fuente completa sigue en `design-system/brand/logo.png`.)*
- [x] **Foto de "Sobre mí" a WebP**: `static/img/andres.jpg` (98 KB, 720×887) funciona,
      pero MASTER §9 pide WebP para fotografía. Convertirla en el sprint de pulido y
      actualizar la referencia en `index.html` (mantener `width`/`height` declarados).
      *(Origen: HU-01/HU-02, foto real incorporada el 2026-07-12. Resuelto 2026-07-12:
      `andres.webp` de 36 KB, misma resolución; el JPG original queda en el historial git.)*

## UI / próximas HU

- [ ] **Unificar iconos en un solo set (Lucide, stroke)**: hoy conviven iconos stroke
      (barra de confianza del index) y Bootstrap Icons fill (redes del footer). MASTER §6
      exige un único set de trazo consistente. Hacerlo cuando se toque el layout en la
      HU-03 o en el sprint de pulido. *(Origen: code review de HU-01, 2026-07-11)*
- [x] **HU-02 — la sección de servicios debe usar exactamente `id="servicios"`**: las
      anclas ya existentes (CTA del navbar, CTA "Agendar mi cita" del hero) apuntan a
      `#servicios`; con otro id quedan muertas. *(Origen: code review de HU-01, 2026-07-11.
      Resuelto en HU-02, 2026-07-12: la sección usa ese id y las anclas conectan.)*

- [x] **Desviación MASTER §7 — "3 cards máximo" vs 5 servicios**: la sección de
      servicios embarca 5 cards porque el catálogo del PO tiene 5 servicios (nutrición
      clínica podría fusionarse con la consulta inicial). Se resuelve cuando el PO
      confirme el catálogo final; si quedan 5, actualizar el MASTER en vez del sitio.
      *(Origen: code review de HU-02, 2026-07-12. Resuelto 2026-07-12: el catálogo
      final del PO quedó en 4 servicios — Nutrición clínica fuera de la oferta — y
      MASTER §7 se actualizó de "3 máx." a 4.)*
- [ ] **Restaurar el ítem de colegiatura en la trustbar**: se retiró "Nutricionista
      colegiado [CODIGO_CPN]" (reemplazado por "Hábitos sostenibles, no dietas de
      moda") porque esa afirmación no se publica hasta que el PO entregue el código
      CPN real. Restaurarlo con el código cuando exista. *(Origen: datos finales del
      PO, 2026-07-12)*
- [ ] **`.gitattributes` para normalizar finales de línea (LF/CRLF)**: git avisa
      conversión en cada commit. Agregarlo en el cierre del sprint.
      *(Origen: code review de HU-02, 2026-07-12)*

- [ ] **Pulse del FAB de WhatsApp (MASTER §8)**: el spec pide un pulse sutil del anillo
      cada ~6s (solo opacity/transform) que se detiene tras la primera interacción.
      Quedó fuera del alcance de la HU-03; implementarlo en el sprint de pulido.
      *(Origen: code review de HU-03, 2026-07-12)*
- [ ] **Deduplicar icono y enlace de WhatsApp**: el path SVG está copiado 2 veces en
      `fragments/layout.html` y la expresión wa.me ya va 7 veces (FAB, footer ×2,
      hero del index, y ×3 en `programa.html` — hero, card de precio y cierre de
      HU-12, siempre con el mismo mensaje literal del programa repetido). Extraer
      un fragmento parametrizado (p. ej. `icono-whatsapp(size)`) y/o exponer la URL
      ya construida desde `GlobalModelAttributes`; el mensaje precargado del
      programa pertenece conceptualmente a los datos (`ProgramaService` o
      properties), no a la plantilla. *(Origen: code review de HU-03, 2026-07-12;
      conteo actualizado en code review de HU-12, 2026-07-18)*
- [ ] **Renombrar el fragmento `footer` de `layout.html`**: ahora devuelve footer + FAB,
      el nombre ya no describe el contenido (p. ej. `footer-y-fab`). Coordinar con las
      páginas que lo consumen. *(Origen: code review de HU-03, 2026-07-12)*

- [ ] **Configurar `APP_SITIO_BASEURL` en Render** con el dominio público real cuando
      exista. Los meta tags Open Graph (og:url, og:image del preview de WhatsApp)
      exigen URL absoluta; en local cae al default `http://localhost`, que NO sirve
      para compartir en producción. *(Origen: /impeccable harden, 2026-07-13)*

## Backend / base de datos

- [ ] **`mvn test` ya no es autosuficiente**: `NutricionActivaApplicationTests`
      (`@SpringBootTest` + `@ActiveProfiles("dev")`) requiere MySQL local corriendo y
      `application-dev.properties` con credenciales reales (gitignorado). Un clon nuevo
      del repo, o una futura CI, fallarían este test sin esa configuración. El paso 5 del
      plan de HU-04 (`docs/arquitectura-agendamiento.md`) ya prevé Testcontainers para el
      test de concurrencia — evaluar en ese momento si conviene migrar también este smoke
      test a Testcontainers, o documentar el requisito de MySQL local si se agrega CI antes.
      *(Origen: code review de `feature/db-fundacion`, 2026-07-14)*
- [ ] **Mejoras a `DatabaseConfig`**: (1) inyectar `DataSourceProperties` de Spring Boot
      en vez de tres `@Value` sueltos para `url`/`username`/`password` — hoy se pierde el
      soporte estándar de `spring.datasource.hikari.*`, JMX y métricas de pool que trae la
      autoconfiguración nativa de `DataSource` (confirmado que sí existe en esta versión,
      solo Flyway carece de autoconfiguración); (2) quitar el `@Primary` del bean
      `dataSource()` — no hay otro `DataSource` compitiendo en el contexto, así que sugiere
      una ambigüedad que no existe; (3) envolver `Flyway...migrate()` en un `try/catch` que
      loguee una línea clara antes de relanzar — hoy, si Flyway falla, el error real queda
      enterrado en varias capas de `BeanCreationException`, más difícil de depurar rápido
      en un incidente de producción (Aiven). *(Origen: code review de `feature/db-fundacion`,
      2026-07-14)*
- [ ] **Comentario de advertencia en `DatabaseConfig` sobre el riesgo de un segundo
      `DataSource`**: la garantía de que Flyway migra antes de que Hibernate toque el
      esquema depende de que este sea el ÚNICO bean capaz de producir un `DataSource` en el
      contexto (verificado, hoy lo es). Si en el futuro se agrega otro `@Bean DataSource`
      (p. ej. una réplica de lectura) sin pasar por este mismo mecanismo, la garantía de
      orden se rompe silenciosamente para ese segundo datasource. Agregar un comentario en
      la clase advirtiendo esto antes de que alguien tropiece con el bug.
      *(Origen: code review de `feature/db-fundacion`, 2026-07-14)*
- [ ] **Bajar `DisponibilidadService.aInstante` a visibilidad de paquete**: hoy es
      `private`, así que `DisponibilidadServiceTest` reimplementa la misma fórmula
      (`ZonedDateTime.of(fecha, hora, ZONA_NEGOCIO).toInstant()`) a mano en vez de
      llamar al método real. No es una divergencia hoy (es exactamente la misma
      expresión), pero si `aInstante` cambia más adelante, los tests no lo reflejarían
      automáticamente. Como el test está en el mismo paquete, bajarlo a visibilidad de
      paquete permite que lo llamen directamente y elimina el riesgo.
      *(Origen: code review de `feature/hu04-disponibilidad` paso 4, 2026-07-14)*
- [ ] **`creado_en DEFAULT CURRENT_TIMESTAMP` en `V2__crear_tabla_cita_y_agenda_dia.sql`
      es un artefacto muerto**: el constructor de `Cita` siempre setea
      `creadoEn = Instant.now()` en Java antes de persistir (correcto y consistente con
      la decisión de que toda conversión de tiempo pasa por Java, no por MySQL — confiar
      en el DEFAULT del servidor usaría su zona de sesión, no necesariamente UTC). Sin
      riesgo real, pero el DEFAULT en la migración sugiere una garantía que la app no
      usa. Agregar un comentario aclaratorio en la migración.
      *(Origen: code review de `feature/hu04-disponibilidad` paso 4, 2026-07-14)*
- [ ] **Pool de `@Async` (correos HU-05) con cola no acotada**: `AsyncConfig` solo
      trae `@EnableAsync`, sin `@Bean Executor` propio, así que Spring Boot resuelve
      el executor con sus defaults (`core-size=8`, `queue-capacity=Integer.MAX_VALUE`,
      verificado en el jar de `spring-boot-autoconfigure-4.1.0`). Eso evita una
      explosión de hilos (nunca pasan de 8 concurrentes enviando correo), pero la
      cola ilimitada significa que si el SMTP de Gmail se degrada de forma sostenida,
      las tareas de envío se acumulan en memoria sin límite ni rechazo — nadie
      decidió esto explícitamente, no hay ninguna línea `spring.task.execution.pool.*`
      en `application.properties`. Acotar `queue-capacity` (con una política de
      rechazo tipo `CallerRunsPolicy`) cuando el volumen de reservas lo justifique;
      para el tráfico actual del MVP no es urgente, mismo tratamiento que el rate
      limiting de `POST /agendar` de la sección Seguridad. *(Origen: code review de
      HU-05, 2026-07-17)*

## Contenido / datos del PO

- [x] **URL real de Facebook**: el enlace del footer apunta al placeholder `https://www.facebook.com/`.
      Pedir la URL de la página al PO y actualizarla en `fragments/layout.html`.
      *(Origen: code review de `feature/base-ui`, 2026-07-11. Resuelto 2026-07-12: la URL
      nunca llegó y no se publican enlaces muertos — el icono se retiró del footer.)*
- [ ] **Restaurar el icono de Facebook en el footer** si el PO confirma que tiene página,
      con su URL real. El SVG retirado era el de Bootstrap Icons (`facebook`, viewBox 0 0 16 16).
      *(Origen: correcciones de contenido pre-producción, 2026-07-12)*
- [ ] **Correo de marca en el footer**: hoy publica `andresht0502@gmail.com` (personal,
      autorizado por el PO como interino). Reemplazarlo cuando el PO confirme el correo
      de marca definitivo. *(Origen: correcciones de contenido pre-producción, 2026-07-12)*
- [ ] **Validar con el PO el emoji 💪 del mensaje precargado del programa**: los 3 CTA
      de `/programa` precargan "Hola Andrés, quiero información del programa Fuerte y
      Definido 💪", mientras el resto de los mensajes wa.me del sitio (hero, cards de
      servicios, footer, FAB) no llevan emoji. No viola la regla de "no emojis como
      iconos" (es contenido conversacional del mensaje, nunca se renderiza en la
      página), pero es una inconsistencia de voz: validar en la demo del sprint y
      unificar (quitarlo o agregarlo en todos). *(Origen: code review de HU-12,
      2026-07-18)*
- [x] **Apellido de Andrés en "Sobre mí"**: el h2 de la sección pasó de "Andrés — Nutrición
      Activa" a "Andrés Herrera" en el paso 7 (HU-11, `feature/hu11-reorden`). El
      code-reviewer marcó el cambio por no tener nota de trazabilidad, a diferencia del
      resto del texto de la sección (ya anotado como "basado en las palabras del PO;
      validación final en la demo"). El apellido "Herrera" fue pedido y confirmado
      directamente por el PO. *(Origen: code review del paso 7, 2026-07-15. Confirmado
      por el PO el mismo día.)*
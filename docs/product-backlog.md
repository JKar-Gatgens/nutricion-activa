# Product Backlog — Nutrición Activa

> **Versión 3.0** · Actualizado el 16 de julio de 2026
> **Product Owner:** Andrés Herrera (Nutricionista) · **Desarrollo:** Johan Gätgens
> **Metodología:** Scrum, sprints de 2 semanas
> **Stack:** Spring Boot 3 · Thymeleaf · Bootstrap 5 (WebJars) · MySQL (Aiven) · Render · Flyway
> **Producción:** https://nutricion-activa.onrender.com — **con agendamiento en línea funcional**

Este documento es la fuente de verdad del proyecto. La v3.0 incorpora el programa "Fuerte y Definido" (nueva propuesta del PO) y reestructura el mapa de sprints.

---

## 1. Visión del producto

Un sitio web profesional para Nutrición Activa que permita a cualquier persona de Latinoamérica conocer los servicios del nutricionista, descubrir su producto estrella (el programa "Fuerte y Definido" de 90 días), y agendar una cita en línea sin fricción, mientras el nutricionista administra su agenda y hace crecer su oferta a medida que su negocio se desarrolla.

---

## 2. Decisiones registradas

### Del levantamiento inicial (9-10 de julio)

- **D-01** — Sin formulario de admisión con datos de salud: solo motivo de consulta al agendar.
- **D-02** — MVP sin pago en línea; el cobro se realiza en consulta.
- **D-03** — Notificaciones al nutricionista por correo y panel; sin WhatsApp automático (costo de API).
- **D-04** — El recordatorio 24h incluirá acciones de confirmación y reprogramación.
- **D-05** — Remitente de correo: Gmail personal del PO durante el MVP, vía contraseña de aplicación en variables de entorno. Cambiable al correo de marca en minutos (solo variables).
- **D-06** — Cuentas de cliente: Fase 2, prioridad baja.
- **D-07** — Agendar sin registro: nombre, correo, teléfono y motivo.

### Del Sprint 1 (10-13 de julio)

- **D-08** — Catálogo definitivo: 4 servicios (se descartó "Nutrición clínica").
- **D-09** — Precios confirmados: consulta inicial 90 min ₡30.000; seguimiento 40 min ₡30.000; protocolo de competencia ₡22.000; rutina de entrenamiento ₡15.000. Protocolo y rutina son planes (no agendables; se solicitan por WhatsApp).
- **D-10** — Precios públicos en la página.
- **D-11** — "Sin prueba, sin promesa": nada se publica sin respaldo real (código CPN retirado hasta que exista; testimonios ocultos hasta tener casos reales con permiso).
- **D-12** — Facebook retirado (sin URL); Instagram publicado (nutricion_activa_cr).
- **D-13** — Identidad visual propia (verde/negro del logo, Barlow, "Barra de Impulso") en design-system/MASTER.md.

### Del Sprint Review 1 y Sprint 2 (13-16 de julio)

- **D-14** — Orden de secciones estilo referencia del PO (pia-nutricion.com): implementado en HU-11.
- **D-15** — Testimonios: sección data-ready construida y oculta; el PO gestiona testimonios reales con plantilla de permiso entregada.

### De la propuesta del programa "Fuerte y Definido" (16 de julio)

- **D-16** — **Se incorpora el programa "Fuerte y Definido" como producto estrella de la página:** 3 módulos × 30 días (90 días), nutrición + entrenamiento, ₡105.000 el programa completo (precio corregido por el PO), población 16+ que entrena 3+ veces/semana, online o presencial (CR y extranjero). Incluye: sesiones de seguimiento, guía de suplementación deportiva, listas de intercambio de alimentos, plan nutricional personalizado, guía de entrenamiento estructurado y atención en tiempo real. Mensaje de venta: "cambio físico en 90 días sin dietas restrictivas, con planes ajustados a tu rutina diaria".
- **D-17** — **El pago en línea del programa se pospone hasta tener una base de clientes suficiente** (decisión del PO, refuerza D-02). Mientras tanto, la venta se cierra por WhatsApp: el CTA del programa es "Quiero más información" → chat directo con el PO.
- **D-18** — **Temática visual del programa: GYM** (aclarado por el PO). Se aplica en el sprint de identidad visual (Sprint 4), donde el programa recibe presencia protagónica y estética diferenciada.
- **D-19** — **Construcción por fases acompañando la producción de contenido del PO:** el programa está ~30% desarrollado por su lado (avanza ~3h/semana). La página se construye data-ready: la estructura existe desde ya y el detalle de los módulos se agrega como datos a medida que el PO los diseñe, sin tocar código. Solo se publica el nivel de detalle que ya existe (principio D-11).
- **D-20** — **Los correos de notificación (HU-05) se priorizan de inmediato:** el agendamiento está vivo en producción y el PO debe enterarse de cada reserva sin depender de revisión manual de la base de datos.

### Del Sprint 4 — dirección visual (21 de julio)

- **D-21** — Dirección visual elegida: "Neón de Madrugada" (la dirección A), aprobada por el PO. Negro dominante, verde como única luz encendida, temática gym cinematográfica. Codificada en design-system/MASTER.md v2. Se incorpora el tablero de números de la dirección B en /programa. **Actualizado el 23 de julio — el PO revisa a mitad del rollout, tras ver el sitio completo migrado (Bloques 1 a 4): la dirección pasa a ser HÍBRIDA por página, no global.** La landing (`index.html`) vuelve a fondo claro, como estaba antes de que arrancara el rollout oscuro — con una sola pieza nueva: la foto real de Andrés dentro del aro del hero (reemplaza al logo), tratada en color natural, no en duotono. `/programa` se queda en negro, Dirección A completa tal como está implementada — al PO le gustó especialmente esa página, en particular con el fondo `gym-ambiente.jpg` ya corregido a un oscurecido direccional (antes ocultaba a la persona entrenando). Dentro de `/programa`, el "90" deja de vivir dentro del aro degradado y pasa a presentarse como un contador/tablero tipo calendario — el lenguaje de tablero de datos que ya había explorado la dirección B, pero con los colores y superficies de la dirección A. Codificado en design-system/MASTER.md v2.1; la implementación en código queda pendiente para el próximo bloque.

---

## 3. Historias de usuario

Estado: ✅ hecha · 🔨 en curso · 📋 pendiente · 💤 preparada/inactiva

| ID | Historia | Prioridad | Sprint | Estado |
|---|---|---|---|---|
| HU-01 | Conocer la propuesta de valor | Must | 1 | ✅ |
| HU-02 | Ver servicios y precios | Must | 1 | ✅ |
| HU-03 | Contactar por WhatsApp | Must | 1 | ✅ |
| HU-04 | Agendar una cita en línea (con BD, concurrencia y token) | Must | 2 | ✅ |
| HU-11 | Reorden de secciones estilo referencia del PO | Should | 2 | ✅ |
| HU-05 | Confirmación por correo (cliente y nutricionista) | Must | 3 | 📋 |
| HU-12 | Vitrina del programa "Fuerte y Definido" (landing + página dedicada) | Must | 3 | 📋 |
| HU-09a | Identidad visual audaz (temática gym, rediseño coherente) | Should | 4 | 📋 |
| HU-13 | Detalle progresivo de módulos del programa (data-driven) | Should | 4+ | 📋 |
| HU-08 | Panel de administración de citas (Spring Security) | Must | 5 | 📋 |
| HU-06 | Recordatorio 24h con confirmar/reprogramar (tokens) | Must | 5 | 📋 |
| HU-07 | Reprogramar desde el enlace del recordatorio | Should | 5 | 📋 |
| HU-09b | Auditoría de seguridad completa (security-auditor, rate limiting) | Must | 5 | 📋 |
| HU-10 | Testimonios (activación al tener material real) | Could | — | 💤 |
| HU-14 | Inscripción y pago en línea del programa | Won't (por ahora) | Fase C | 📋 |

### Detalle de las historias próximas

**HU-05 — Confirmación por correo** (Sprint 3)
Como cliente y como nutricionista, queremos recibir un correo automático al crearse una cita, para tener constancia inmediata sin revisar la base de datos.
- El cliente recibe correo con: servicio, fecha y hora (en hora de Costa Rica, GMT-6), modalidad y contacto del nutricionista.
- El nutricionista recibe aviso con los datos del cliente y el motivo.
- El envío NO bloquea la reserva: si el correo falla, la cita igual queda guardada (el correo es secundario, se registra el fallo).
- Remitente y credenciales SMTP por variables de entorno (D-05); ninguna credencial en el repo.
- El aviso "te contactaremos" de la página de confirmación se actualiza para reflejar que ahora sí llega correo.

**HU-12 — Vitrina del programa "Fuerte y Definido"** (Sprint 3)
Como visitante, quiero descubrir el programa estrella desde la primera pantalla y conocer todo lo que incluye, para decidir si pedir información.
- Sección destacada en la landing (posición protagónica cerca del hero) que presenta el programa y enlaza a su página.
- Página dedicada /programa con: qué es (90 días, 3 módulos), qué incluye (los 6 elementos de D-16), el precio (₡105.000), la población objetivo, el mensaje "sin dietas restrictivas", y CTA "Quiero más información" → WhatsApp con mensaje precargado del programa (D-17).
- Estructura data-ready (ProgramaService): los módulos se muestran a nivel de estructura (Módulo 1/2/3 · 30 días); el detalle se agrega como datos cuando el PO lo produzca (D-19). No se publica detalle inexistente.
- Enlace en el navbar. Tests de contrato para /programa.
- El diseño de esta fase es estructural y dentro del MASTER actual; la temática gym llega en el Sprint 4 (D-18).

**HU-09a — Identidad visual audaz** (Sprint 4 — el "sprint de diseño")
Con la página completa (agendamiento + programa + reorden), se reemplaza la dirección estética conservadora del Sprint 0 por una audaz con temática gym/fitness, conservando colores del logo, eslogan, estructura y funcionalidad. Se generan variaciones para que el PO elija. Incluye la presencia visual diferenciada del programa (D-18).

**HU-13 — Detalle progresivo de módulos** (Sprint 4 en adelante, según produzca el PO)
A medida que el PO diseñe cada módulo (contenido, enfoque, entregables), su detalle se agrega a la página como datos. Activación sin cambios de código.

---

## 4. Mapa de sprints (reestructurado en v3.0)

| Sprint | Meta | Historias | Estado |
|---|---|---|---|
| **Sprint 0** | Fundaciones: proyecto, repo, CI/CD, skills, sistema de diseño | — | ✅ |
| **Sprint 1** | La vitrina: propuesta de valor, servicios, WhatsApp (19/20 en auditoría) | HU-01/02/03 | ✅ |
| **Sprint 2** | El corazón: agendamiento con BD (Aiven), concurrencia probada, deploy en producción | HU-04, HU-11 | ✅ |
| **Sprint 3** | **Avisos y vitrina del programa:** correos de confirmación + programa Fuerte y Definido (fase vitrina) | HU-05, HU-12 | 📋 Próximo |
| **Sprint 4** | **Identidad visual:** rediseño audaz con temática gym, página completa, variaciones para el PO | HU-09a (+HU-13 si hay contenido) | 📋 |
| **Sprint 5** | **Administración y blindaje:** panel admin con login, recordatorios con tokens, reprogramación, auditoría de seguridad completa | HU-08, HU-06, HU-07, HU-09b | 📋 |
| **Fase C** | Inscripción y pago en línea del programa (cuando exista base de clientes y el programa esté completo) | HU-14 | 📋 Futuro |

---

## 5. Fuera del alcance actual (Fase 2 / futuro)

- Pago en línea (pasarela Tilopay/ONVO local, PayPal internacional) — D-02, D-17.
- Cuentas de cliente con historial y descarga de PDFs — D-06.
- WhatsApp automático (API Meta/Twilio) — D-03.
- Blog de tips y recetas (requiere contenido del PO).
- Detección de zona horaria del visitante.
- Dominio propio (GitHub Student Pack) y correo de marca definitivo.

---

## 6. Definición de Terminado (DoD)

- Criterios de aceptación cumplidos y verificados.
- Código revisado por **code-reviewer**; hallazgos críticos resueltos.
- Lógica de negocio con pruebas en verde (**test-automator** donde aplique).
- Cambios en develop, desplegados desde main en Render, verificados en producción.
- El PO validó en la demo del sprint.
- Ninguna credencial versionada en el repositorio.

---

## 7. Consideraciones registradas

- **Menores de edad (16-17):** la web informativa no requiere nada especial; cuando exista inscripción formal (Fase C), el proceso debe contemplar consentimiento de padres/tutores. Es además práctica profesional del PO en su atención.
- **Zona horaria verificada en producción:** la reserva de las 8:00 AM CR se persiste como 14:00 UTC — el diseño UTC/CR y el bloqueante connectionTimeZone=UTC funcionan correctamente (verificado 16 de julio).
- **Deuda técnica activa:** ver docs/pendientes.md (rate limiting → Sprint 5; server.port=80 → revisar; hardening de certificado CA de Aiven → futuro; DatabaseConfig mejoras → menor).

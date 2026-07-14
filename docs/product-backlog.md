# Product Backlog — Nutrición Activa

> **Versión 2.0** · Actualizado el 14 de julio de 2026
> **Product Owner:** Andrés (Nutricionista) · **Desarrollo:** Johan Gätgens
> **Metodología:** Scrum, sprints de 2 semanas
> **Stack:** Spring Boot 3 · Thymeleaf · Bootstrap 5 (WebJars) · MySQL (Aiven) · Render
> **Producción:** https://nutricion-activa.onrender.com

Este documento es la fuente de verdad del proyecto. Se actualiza al cierre de cada sprint con lo aprendido y lo decidido por el Product Owner.

---

## 1. Visión del producto

Un sitio web profesional para Nutrición Activa que permita a cualquier persona de Latinoamérica conocer los servicios del nutricionista, entender su propuesta de valor ("Alimenta tu potencial": perder grasa y ganar músculo con acompañamiento profesional y hábitos sostenibles), y agendar una cita en línea sin fricción, mientras el nutricionista administra su agenda desde un panel privado.

---

## 2. Decisiones registradas

Decisiones del Product Owner y del equipo, con su fecha. Cada una tiene un ID trazable (`D-##`) que se referencia en las historias.

### Del levantamiento inicial (9-10 de julio)

- **D-01** — El formulario de admisión con datos de salud se descarta: los datos clínicos se toman en consulta. Solo se solicita el motivo de consulta al agendar.
- **D-02** — El MVP se lanza sin pago en línea. El cobro se realiza en consulta. El adelanto con pasarela de pagos se pospone a Fase 2.
- **D-03** — Las notificaciones al nutricionista serán por correo y panel de administración. Se descarta WhatsApp automático por su costo de API.
- **D-04** — El recordatorio 24h antes incluirá acciones de confirmación y reprogramación por parte del cliente.
- **D-05** — Correo del remitente: Gmail personal del PO (andresht0502@gmail.com) durante el MVP, vía contraseña de aplicación en variables de entorno. Se cambiará al correo de marca cuando esté disponible.
- **D-06** — Las cuentas de cliente (historial y descarga de planes PDF) se aprueban para Fase 2 con prioridad baja: el flujo actual de entrega personal por WhatsApp funciona para el PO.
- **D-07** — Agendar cita no requiere registro: solo nombre, correo, teléfono y motivo de consulta.

### Del Sprint 1 y sus datos finales (10-13 de julio)

- **D-08** — **El catálogo definitivo son 4 servicios, no 5.** El PO listó su oferta final sin incluir "Nutrición clínica" tras consultársele explícitamente dos veces; se descarta como servicio independiente (queda integrada en la consulta normal).
- **D-09** — **Precios y duraciones confirmados:** Consulta de nutrición inicial (90 min, ₡30.000) y de seguimiento (40 min, ₡30.000) — mismo precio por decisión del PO. Protocolo para competencias deportivas (plan de 3 días, ₡22.000). Rutina de entrenamiento (₡15.000). Protocolo y rutina son planes, no sesiones con horario fijo.
- **D-10** — **Los precios van públicos** en la página (confirmado por el PO al aportarlos).
- **D-11** — **Integridad de credenciales ("sin prueba, sin promesa"):** no se publica ninguna afirmación sin respaldo. El código de colegiado se retiró de la barra de confianza porque el PO aún no lo tiene; se restaurará cuando lo aporte. La sección de testimonios permanece oculta hasta tener casos reales con permiso.
- **D-12** — **Facebook retirado del footer:** el PO no aportó URL. Se restaura solo si confirma que tiene página. Instagram sí está publicado (nutricion_activa_cr).
- **D-13** — Identidad visual: paleta verde/negro del logo real, tipografía Barlow, eslogan "Alimenta tu potencial". Elemento propio del sistema ("Barra de Impulso", eco del aro del logo) en lugar de patrones genéricos. Documentado en `design-system/MASTER.md` y `DESIGN.md`.

### Del feedback de Sprint Review (13 de julio)

- **D-14** — El PO pidió que el ORDEN y las secciones sigan el modelo de su referencia (pia-nutricion.com). Se adopta la *lógica* de orden, NO una copia literal (inapropiado legalmente y contraproducente sin el contenido que la sostiene). Genera la HU-11.
- **D-15** — El PO ya tiene clientes con resultados, pero solo fotos de progreso en Instagram, sin testimonios escritos ni permiso para uso web. Se le entregó una plantilla para solicitar testimonios + permiso explícito. La sección se activará cuando lleguen.

---

## 3. Dirección de diseño

Referencias aprobadas por el PO: pia-nutricion.com (favorita), larisawellness.com, y directorios médicos (Hospital CIMA, Medismart). Patrones adoptados: el profesional como rostro de la marca, servicios en tarjetas con CTA, WhatsApp como canal principal (botón flotante), propuesta de valor prominente en el hero, estética limpia con espacio en blanco, y flujo de secciones hero → sobre mí → servicios → prueba social → agendar.

La identidad NO se copia de las referencias: es propia (verde eléctrico sobre negro, energía fitness), lo que diferencia a Nutrición Activa en el nicho. La credibilidad se construye con el enfoque de hábitos sostenibles y — cuando existan — testimonios y credencial de colegiado reales.

---

## 4. Historias de usuario

Estado: ✅ hecha · 🔨 en curso · 📋 pendiente · 💤 preparada pero inactiva

### MVP

| ID | Historia | Prioridad | Sprint | Estado |
|---|---|---|---|---|
| HU-01 | Conocer la propuesta de valor (hero, confianza, sobre mí) | Must | 1 | ✅ |
| HU-02 | Ver servicios y precios (4 tarjetas) | Must | 1 | ✅ |
| HU-03 | Contactar por WhatsApp (botón flotante) | Must | 1 | ✅ |
| HU-04 | Agendar una cita en línea sin registro | Must | 2 | 🔨 |
| HU-05 | Recibir confirmación de la cita por correo | Must | 3 | 📋 |
| HU-06 | Recordatorio 24h con confirmar/reprogramar | Must | 3 | 📋 |
| HU-07 | Reprogramar desde el enlace del recordatorio | Should | 3 | 📋 |
| HU-08 | Panel de administración de citas (con login) | Must | 3 | 📋 |
| HU-09 | Pulido visual y auditoría de seguridad | Should | 3-4 | 📋 |
| HU-10 | Sección de testimonios | Could | 2-3 | 💤 |
| HU-11 | Reorden de secciones al flujo estilo Pía | Should | 2 | 📋 |

### Detalle de las historias activas y próximas

**HU-04 — Agendar una cita en línea** (Sprint 2, en curso)
Como cliente, quiero seleccionar un servicio, ver los horarios disponibles y reservar con mis datos básicos, para asegurar mi espacio sin crear cuenta.
- Solo se muestran servicios agendables (las consultas); protocolo y rutina se solicitan por WhatsApp (pendiente de confirmación del PO en FASE 0 del Sprint 2).
- Solo horarios realmente libres, según agenda y duración del servicio.
- Reglas: L-S 8:00-18:00 GMT-6; la cita termina dentro del horario; anticipación mínima 1 día; sin solapamiento de rangos.
- Formulario: nombre, correo, teléfono, motivo (D-01, D-07). Validación en servidor + anti-bots.
- El horario se bloquea al confirmar (control de concurrencia). Zona horaria de CR indicada explícitamente.
- Estado inicial de la cita: CONFIRMADA. La cita persiste en base de datos.

**HU-11 — Reorden estilo Pía** (Sprint 2, nueva — D-14)
Como visitante, quiero que la página fluya en un orden natural que me lleve de conocer al profesional hasta agendar, para tener una experiencia clara.
- Orden: hero → sobre mí → servicios → testimonios (oculta) → CTA de cierre → footer.
- Navbar y scroll-padding ajustados al nuevo orden.
- No rompe los tests de contrato existentes.

**HU-10 — Sección de testimonios** (preparada, inactiva — D-11, D-15)
Como visitante, quiero leer experiencias de otros clientes, para reforzar mi confianza.
- Estructura "data-ready": se renderiza solo si hay testimonios cargados; con lista vacía no aparece en el HTML.
- Solo casos reales con permiso explícito del cliente para uso web. Foto de progreso solo con permiso separado y acompañando texto + nombre/inicial.
- Activar = agregar datos, cero cambios de código.

**HU-05 — Confirmación por correo** (Sprint 3)
Cliente y nutricionista reciben correo al crearse la cita (D-03, D-05). Remitente configurable por variable de entorno; ninguna credencial en el repo.

**HU-06 — Recordatorio con acciones** (Sprint 3 — D-04)
Proceso programado envía recordatorio 24h antes, una vez por cita, con botones "Confirmo" / "Necesito reprogramar" vía enlace con token único sin login.

**HU-07 — Reprogramar desde el enlace** (Sprint 3)
El enlace de reprogramación reutiliza las reglas de disponibilidad de la HU-04; libera el horario viejo, bloquea el nuevo, notifica a ambas partes; el token se invalida tras usarse.

**HU-08 — Panel de administración** (Sprint 3)
Acceso con login (Spring Security), solo rol administrador en el MVP. Lista citas por día/semana con datos, estado y motivo. El nutricionista puede cancelar (notifica al cliente). Es donde entra el security-auditor.

**HU-09 — Pulido y seguridad** (Sprint 3-4)
Auditoría de diseño (impeccable) y de seguridad (security-auditor) sobre la superficie completa; hallazgos críticos resueltos antes de considerar el producto listo.

---

## 5. Mapa de sprints

| Sprint | Meta | Historias | Estado |
|---|---|---|---|
| **Sprint 0** | Fundaciones: proyecto, repo, deploy continuo, skills, sistema de diseño | — | ✅ Completado |
| **Sprint 1** | La vitrina: propuesta de valor, servicios, WhatsApp. Cerró con 19/20 en auditoría | HU-01, HU-02, HU-03 | ✅ Completado |
| **Sprint 2** | El corazón: agendamiento con BD real + reorden estilo Pía | HU-04, HU-11, HU-10 (prep) | 🔨 En curso |
| **Sprint 3** | Comunicación y administración: correos, recordatorios con tokens, panel privado, auditoría de seguridad | HU-05, HU-06, HU-07, HU-08, HU-09 | 📋 Pendiente |
| **Sprint 4** | Pulido final, testimonios activos (si hay material), rendimiento | HU-09, HU-10 | 📋 Pendiente |

---

## 6. Fase 2 — Fuera del alcance del MVP

Clasificación *Won't have* por ahora (aprobado o discutido con el PO, pospuesto):

- Pago de adelanto mediante pasarela (Tilopay/ONVO local; PayPal internacional) — D-02.
- Cuentas de cliente con historial y descarga de planes PDF (Firebase Storage) — D-06, prioridad baja.
- Notificaciones automáticas por WhatsApp (API de Meta/Twilio) — D-03.
- Blog de contenido (tips y recetas) — presente en las referencias del PO, requiere que él genere contenido.
- Detección de zona horaria del visitante.
- Dominio propio (GitHub Student Pack) y correo de marca definitivo.
- Migración de fotos/logo a WebP optimizado (parcialmente resuelto en Sprint 1).

---

## 7. Definición de Terminado (DoD)

Una historia está terminada cuando:

- Todos sus criterios de aceptación se cumplen y fueron verificados.
- El código pasó por el agente **code-reviewer** y los hallazgos críticos están resueltos.
- La lógica de negocio tiene pruebas unitarias en verde (agente **test-automator**, desde Sprint 2).
- Los cambios están en `develop`, desplegados desde `main` en Render y verificados en producción.
- El Product Owner validó la funcionalidad en la demo del sprint.
- No se versionó ninguna credencial ni secreto en el repositorio.

---

## 8. Deuda técnica y pendientes conocidos

Trackeados en `docs/pendientes.md` dentro del repo:
- Restaurar credencial de colegiado con el código real (depende del PO — D-11).
- Restaurar Facebook si el PO aporta URL (D-12).
- Cambiar al correo de marca cuando exista (D-05).
- Unificar todo el sitio en un solo set de íconos (Lucide) — en curso.
- Optimización WebP de assets pendientes.
- Recorte de peso 700 de Barlow si no se usa.

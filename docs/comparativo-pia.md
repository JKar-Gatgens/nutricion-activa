# Análisis comparativo y hoja de ruta — Nutrición Activa vs. referencia del PO

> **Documento de Sprint Review** · 13 de julio de 2026
> Referencia del Product Owner: pia-nutricion.com

Este documento traduce el feedback del PO ("me gusta cómo quedó, pero el orden y las secciones los quiero como Pía") en un plan concreto: compara ambas páginas sección por sección, distingue lo que Nutrición Activa ya tiene de lo que falta, y ubica cada pieza pendiente en el sprint o fase donde corresponde.

---

## Aclaración importante sobre "copiar" una página

**No se hará una copia literal de Pía Nutrición.** Dos razones:

1. **Legal/ético:** copiar el diseño y contenido de otra marca es inapropiado.
2. **Estratégico:** Pía se ve robusta porque es un negocio con años de operación — acumula testimonios reales, un blog con decenas de entradas, equipo médico especializado y ubicación física. Replicar su estructura completa hoy dejaría secciones vacías que transmitirían lo *contrario* a profesionalismo.

La estrategia correcta es adoptar su **lógica de orden** y crecer hacia esa robustez a medida que el negocio de Andrés genere el contenido real que la sostiene (testimonios, contenido de blog).

---

## La buena noticia: la estructura ya coincide

Nutrición Activa ya comparte la columna vertebral de Pía. El orden que el PO admira no requiere reconstruir lo hecho, sino continuar el plan y reordenar ligeramente lo que ya existe.

### Comparación sección por sección

| Sección en Pía | Estado en Nutrición Activa | Cuándo se resuelve |
|---|---|---|
| Hero / portada con mensaje | ✅ Ya existe, con identidad propia | Completado (Sprint 1) |
| "Soy María Pía" — sobre mí con foto | ✅ Ya existe: Sobre mí con foto real y marco de marca | Completado (Sprint 1) |
| Servicios en tarjetas | ✅ Ya existe: 4 servicios con precio, duración, modalidad | Completado (Sprint 1) |
| Sección "Agendar" (central en Pía) | ◐ Botones preparados, agendamiento aún no funcional | Sprint 2 (el corazón del proyecto) |
| Testimonios de clientes | ◐ Estructura diseñada pero oculta ("sin prueba, sin promesa") | Se activa cuando el PO aporte casos reales con permiso |
| Blog: Tips y Recetas | ✘ Aún no existe | Fase 2 (requiere que el PO produzca contenido) |
| Footer: contacto, redes, ubicación | ✅ Ya existe (correo, WhatsApp, Instagram, ubicación) | Completado (Sprint 1) |
| Servicio corporativo destacado (NutriWell) | ✘ No aplica a la oferta de Andrés | Fuera de alcance salvo que el PO lo pida |

Leyenda: ✅ hecho · ◐ parcial/planificado · ✘ no existe aún

---

## Qué es "ajuste" y qué es "continuar"

El deseo del PO se resuelve por dos vías distintas:

### Se resuelve solo continuando el plan (no es trabajo extra)

- **Agendar:** la sección "Agendar" que en Pía es protagonista es todo el Sprint 2. Ya estaba planificado.
- **Testimonios:** la sección ya está diseñada en el código, simplemente oculta hasta tener material real. Activarla es trivial cuando llegue el contenido.
- **Blog:** ya figuraba en la Fase 2 del backlog original.

### Sí es un ajuste real (una historia nueva y pequeña — HU-11)

- **Reordenamiento:** reordenar las secciones existentes para que el flujo de scroll siga la cadencia de Pía (hero → sobre mí → servicios → prueba social → agendar). Cambio de maquetación relativamente barato que entra como historia chica del Sprint 2.

---

## Lo que depende del Product Owner

Varias piezas no dependen del desarrollo sino de material que solo Andrés puede aportar:

- **Testimonios reales:** el PO ya tiene clientes con resultados, pero solo fotos de progreso en Instagram (sin texto ni permiso para uso web). Se le entregó una plantilla para pedir testimonios escritos + permiso explícito. Sin material real con permiso no se publica — decisión de integridad, no limitación técnica.
- **Código de colegiado:** para restaurar la credencial "Nutricionista colegiado" en la barra de confianza (hoy retirada por no tener respaldo).
- **Datos de marca:** correo definitivo (hoy Gmail personal autorizado) y URL de Facebook (ícono retirado por no tenerla).
- **Contenido del blog:** si en algún momento lo quiere, requiere que él genere el contenido; el desarrollo solo construye el contenedor.

---

## Propuesta para el Sprint 2

Con el feedback del PO incorporado:

- **Prioridad 1:** construir el agendamiento funcional (la pieza central que Pía tiene y Nutrición Activa aún no): selección de servicio, disponibilidad real, reserva sin registro. Incluye la base de datos.
- **Prioridad 2:** reordenar las secciones al flujo estilo Pía (HU-11).
- **Prioridad 3:** dejar lista la activación de testimonios para cuando Andrés aporte los primeros casos reales con permiso.

> **Próximo paso:** este documento y el feedback del PO alimentan el Product Backlog v2.0 (`docs/product-backlog.md`), del cual sale el plan del Sprint 2.

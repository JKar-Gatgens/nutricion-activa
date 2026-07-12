# Pendientes

Tareas anotadas durante el desarrollo que no bloquean el sprint actual.

## Marca / assets

- [ ] **Logo optimizado**: `static/img/logo.png` pesa 151 KB y no es cuadrado (517×616 px).
      Exportar un recorte cuadrado optimizado en WebP (más un PNG pequeño para favicon)
      y actualizar las referencias en `fragments/layout.html`. *(Origen: code review de `feature/base-ui`, 2026-07-11)*

## UI / próximas HU

- [ ] **Unificar iconos en un solo set (Lucide, stroke)**: hoy conviven iconos stroke
      (barra de confianza del index) y Bootstrap Icons fill (redes del footer). MASTER §6
      exige un único set de trazo consistente. Hacerlo cuando se toque el layout en la
      HU-03 o en el sprint de pulido. *(Origen: code review de HU-01, 2026-07-11)*
- [x] **HU-02 — la sección de servicios debe usar exactamente `id="servicios"`**: las
      anclas ya existentes (CTA del navbar, CTA "Agendar mi cita" del hero) apuntan a
      `#servicios`; con otro id quedan muertas. *(Origen: code review de HU-01, 2026-07-11.
      Resuelto en HU-02, 2026-07-12: la sección usa ese id y las anclas conectan.)*

- [ ] **Desviación MASTER §7 — "3 cards máximo" vs 5 servicios**: la sección de
      servicios embarca 5 cards porque el catálogo del PO tiene 5 servicios (nutrición
      clínica podría fusionarse con la consulta inicial). Se resuelve cuando el PO
      confirme el catálogo final; si quedan 5, actualizar el MASTER en vez del sitio.
      *(Origen: code review de HU-02, 2026-07-12)*
- [ ] **`.gitattributes` para normalizar finales de línea (LF/CRLF)**: git avisa
      conversión en cada commit. Agregarlo en el cierre del sprint.
      *(Origen: code review de HU-02, 2026-07-12)*

## Contenido / datos del PO

- [ ] **URL real de Facebook**: el enlace del footer apunta al placeholder `https://www.facebook.com/`.
      Pedir la URL de la página al PO y actualizarla en `fragments/layout.html`. *(Origen: code review de `feature/base-ui`, 2026-07-11)*
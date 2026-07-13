# Nutrición Activa

Landing de nutrición deportiva online (Spring Boot + Thymeleaf + Bootstrap 5 vía WebJars, Java 21). Prohibido Tailwind; sin CDNs externos salvo Google Fonts.

## Design Context

Antes de cualquier trabajo de UI, leer en este orden:

1. **`PRODUCT.md`** — estrategia de marca: registro (brand), usuarios, posicionamiento ("Hábitos sostenibles, no dietas de moda"), escalera de conversión, anti-referencias y el principio "Sin prueba, sin promesa" (nada se publica sin material real del PO).
2. **`DESIGN.md`** — sistema visual normativo: tokens (frontmatter YAML), reglas con nombre (Verde Legible, Botón Negro, El Aro, Cifra Protagonista, Tinte al Tacto) y Do's/Don'ts.
3. **`design-system/MASTER.md`** — especificación operativa original (paleta, tipografía Barlow, espaciado 8px, micro-interacciones, checklist de accesibilidad AA). Overrides por página en `design-system/pages/<pagina>.md` si existen.

Los tokens viven como variables CSS `--na-*` en `src/main/resources/static/css/styles.css` (cargado después de Bootstrap; los overrides van ahí, nunca en el WebJar). Deuda y datos pendientes del PO se registran en `docs/pendientes.md`.

---
target: sitio completo (index, agendar, programa)
total_score: 33
max_score: 40
na_heuristics: 
p0_count: 0
p1_count: 1
timestamp: 2026-07-24T13-11-24Z
slug: sitio-completo-index-agendar-programa
---
Method: dual-agent (A: design-review subagent · B: detector subagent)

## Design Health Score

| # | Heuristic | Score | Key Issue |
|---|-----------|-------|-----------|
| 1 | Visibility of System Status | 3 | Confirmation page states the email was sent as settled fact; DESIGN.md itself calls that send best-effort |
| 2 | Match System / Real World | 4 | CR hours stated 3x, colones, voseo, WhatsApp as real regional channel |
| 3 | User Control and Freedom | 3 | `/agendar` steps stay visible for backtracking, but no explicit "cambiar" affordance once slots show |
| 4 | Consistency and Standards | 3 | Only emoji in the whole codebase sits in `programa.html`'s 3 WhatsApp links; DESIGN.md bans emoji-as-icon |
| 5 | Error Prevention | 4 | Honeypot, required/min date, live Sunday check, submit self-disable |
| 6 | Recognition Rather Than Recall | 4 | Labels always visible, stepper labels persist, timezone restated |
| 7 | Flexibility and Efficiency | 3 | `autocomplete` wired on all `/agendar` inputs |
| 8 | Aesthetic and Minimalist Design | 4 | Tight token discipline, one accent shape per section |
| 9 | Error Recovery | 3 | `role="alert"` present, but `errorGeneral` is one generic string with no differentiated messaging |
| 10 | Help and Documentation | 2 | No FAQ/inline help beyond the WhatsApp FAB as implicit escape hatch |
| **Total** | | **33/40** | **Good** |

## Design Specificity Verdict

**LLM assessment (Specific):** The system is grounded in real product specifics well past what a review candidate usually shows: a named real person (Andrés) with a bespoke duotono photo treatment, colones pricing, GMT-6 hours stated three times, voseo throughout, a two-territory system with a named polymorphic signature component ("El Aro"), and disciplined "Sin prueba, sin promesa" handling via `.na-pending` rather than fabricated proof. The one generic slip is the landing hero H1 ("Perdé grasa y ganá músculo con un plan hecho para vos") — voseo-flavored but otherwise swappable fitness copy; it's rescued by the eyebrow above it, PRODUCT.md's actual differentiator line ("Alimenta tu potencial"). Closing-band CTAs ("Empezá tu cambio hoy") are similarly generic-adjacent but function fine as closers, not differentiators.

**Deterministic scan:** `detect.mjs --json` against `src/main/resources/templates` returned **zero findings** in every configuration tested (directory scan, explicit file list, `--no-design-system`, `--no-config` with all suppression stripped, and plain-text mode) — exit code 0 across the board, confirmed as a genuine clean result rather than silent misconfiguration. This corroborates the "Specific" verdict at the structural/pattern level: no generic-template anti-patterns tripped the detector. It's a partial corroboration, though — the detector is a static markup/inline-style scanner, so it has no way to catch the two most substantive issues Assessment A found (a CSS mask producing a ring ~5x its documented thickness, and a copy claim that overstates a best-effort email send as fact). No false positives to adjudicate since there were no findings.

**Visual overlays:** Not available this run. The Spring Boot dev server requires a local MySQL database that isn't running in this environment (`Access denied for user 'root'@'localhost'`); starting one was out of scope for a critique. Fallback signal: *"browser visualization unavailable — dev server requires a local MySQL DB not present in this environment."* Both assessments worked from source (Thymeleaf templates + `styles.css`) instead.

## Overall Impression

This is a disciplined, well-documented system that mostly lives up to its own rules — the accessibility-rule comments left inline in the CSS, the data-derived stepper state, and the honestly-handled pending-price marker all show a team holding itself to DESIGN.md rather than just writing it down. The gap isn't in taste, it's in a few places where implementation quietly drifted from the spec it was built against: the confirmation page's email copy asserts something DESIGN.md explicitly calls uncertain, and the site's single most identity-defining shape (the Aro) is built to spec everywhere except the one hero moment that matters most (`/programa`'s "90"). Neither is a redesign-level problem; both are the kind of thing that slips through when nobody re-checks implementation against the written rule after the fact.

## What's Working

1. **The pending-price marker is executed exactly right.** `.na-pending` deliberately drops out of the heading font into body-weight amber text — it reads as an honest "not yet set" state, not a broken layout, which is precisely what "Sin prueba, sin promesa" demands.
2. **La Regla del Verde Legible is enforced with self-documenting rigor.** Dark-territory selectors that could tempt reuse of Gris Secundario instead use `--na-dark-ink`, with inline comments explaining why. A design system holding its own accessibility rule under pressure across many components, consistently, is rare to see actually maintained in CSS rather than just stated in the doc.
3. **The 3-step stepper derives state from data, not scroll/JS heuristics**, and exposes it via `aria-current="step"` in addition to color — exactly what DESIGN.md's Do's list demands, and it survives reload/back-navigation because nothing depends on client memory.

## Priority Issues

**[P1] Confirmation page copy overstates a best-effort action as fact**
- **Why it matters:** DESIGN.md's own spec for this exact element calls the email send "best-effort, no bloquea la reserva" — but `agendar-confirmacion.html` states "Te enviamos un correo a [correo] con el detalle de tu cita" as settled fact. A user whose email never arrives has been told something false at the highest-stakes moment of the flow, with no fallback offered beyond a generic WhatsApp mention that doesn't address a missing confirmation.
- **Fix:** Soften to "Te enviaremos un correo…" or add a next-step for non-delivery ("si no llega en unos minutos, revisá spam o escribinos por WhatsApp"), matching the best-effort framing DESIGN.md already specifies.
- **Suggested command:** `/impeccable clarify`

**[P2] "El Aro" ring thickness diverges from spec between territories**
- **Why it matters:** DESIGN.md states the Aro is "el mismo anillo de 4px" everywhere it appears, and frames the whole point of the signature component as the ring staying constant while only its content changes. The landing hero and "Sobre mí" frame match (4px padding), but `/programa`'s hero cifra ring is built via a radial mask (`transparent calc(100% - 20px)` → opaque at `calc(100% - 19px)`), producing a band roughly 19-20px thick — about 5x spec, on the single most prominent hero moment on the site.
- **Fix:** Either tighten the mask math to a true ~4-6px ring, or update DESIGN.md to document a heavier ring weight as an intentional oscuro-territory variant. Right now code and doc disagree and neither side is "wrong" on its own terms.
- **Suggested command:** `/impeccable polish`

**[P2] Unbounded time-slot grid violates the site's own cognitive-load ceiling**
- **Why it matters:** `.na-slots` renders every available slot as equal-weight pills with no grouping, cap, or filter. An 8am-6pm day of hourly slots can produce 8-10+ simultaneous choices with no structure — the single most decision-dense screen on the site, well past the ≤4-visible-options guidance the rest of the site respects.
- **Fix:** Group by mañana/tarde with sub-headers, or cap initial visible slots behind a "ver más horarios" expansion. No new visual language needed — same `.na-slot` pill component.
- **Suggested command:** `/impeccable layout`

**[P3] Open Graph metadata is not parameterized per page**
- **Why it matters:** `fragments/layout.html`'s `og:title`/`og:description` hardcode homepage copy for every page, even though the head fragment's own comment calls out the WhatsApp link preview as the primary conversion channel per PRODUCT.md. Sharing `/programa` — a real, promoted URL with its own nav entry and homepage banner — still previews generic homepage copy instead of "El programa Fuerte y Definido, 90 días…".
- **Fix:** Extend the `head(pageTitle)` fragment to accept `pageDescription`/`pageOgTitle` params, with per-page overrides.
- **Suggested command:** `/impeccable harden`

**[P3] Hardcoded emoji breaks brand-voice consistency**
- **Why it matters:** `programa.html`'s three WhatsApp links hardcode a 💪 emoji in the message text — the only emoji anywhere in the codebase. PRODUCT.md's anti-references reject "coach genérico de Instagram" tone, and while this is message copy rather than a UI icon, it reads as a one-off inconsistency in an otherwise disciplined system.
- **Fix:** Drop the emoji, or document it as a deliberate `/programa`-specific voice exception if the PO wants that page pitched higher-energy.
- **Suggested command:** `/impeccable clarify`

## Persona Red Flags

**Jordan (First-Timer):** On the landing services grid, some cards say "Agendar" and others say "Solicitar por WhatsApp" with zero copy anywhere explaining why. Jordan has no way to know in advance that protocols/routines require a WhatsApp conversation while consultations book instantly — the distinction is a backend concept (an "agendable" flag) that never gets translated into user-facing explanation.

**Riley (Stress Tester):** The generic `errorGeneral` alert on `/agendar` gives no differentiated failure messaging — if a slot gets taken between page-load and submit (a real race condition for a booking form), there's no visible mechanism to say "ese horario ya no está disponible" versus any other failure.

**Casey (Distracted Mobile User):** FAB safe-area handling and the 88px bottom-padding reservation are genuinely well thought through for thumb use. Less well thought through: scrolling the landing quickly hits hero → programa banner → trust bar → sobre mí → servicios before any proof exists (testimonios is empty by design per "Sin prueba, sin promesa") — Casey never gets a "this works" beat before being asked to pick a paid service, a real risk for someone evaluating in seconds.

**Project-specific — LATAM athlete evaluating trust in seconds (per PRODUCT.md Users):** Prices are colones-only with currency context stated once in a section subtitle and nowhere else. A first-time visitor from Colombia or Mexico sees a currency code they may not immediately convert, undercutting the "puedo pagarlo" rung of the belief ladder for exactly the non-CR audience PRODUCT.md says the site targets ("toda Latinoamérica").

## Minor Observations

- `agendar-confirmacion.html`'s `<h1>` doesn't carry the `.na-section-title` class that `agendar.html`'s does, losing the Barra de Impulso underline treatment — a small visible inconsistency between two pages of the same flow.
- The landing trustbar mixes one positioning claim ("Hábitos sostenibles, no dietas de moda") with two factual data points (LATAM coverage, hours) under an identical checkmark icon — visually they read as equally "verified," slightly blurring assertion from fact.
- `.na-servicio-opcion` cards on `/agendar` step 1 have no `focus-visible`-specific styling beyond the global 3px outline; a more contained focus treatment matching `.na-slot`'s pattern would feel more polished, though nothing is broken.

## Questions to Consider

- If email delivery is genuinely best-effort and can fail, why does the confirmation page never mention what to do if it doesn't arrive — is that planned, or is WhatsApp meant to silently absorb 100% of that failure mode?
- Was the Aro's 5x thickness difference on `/programa`'s hero a deliberate territory-specific call the PO signed off on, or did it drift during implementation without a re-check against the 4px spec?
- The belief ladder puts "esto funciona" first, but the site has nothing to put there yet — is there an interim plan (even a modest one, like the pending-price pattern applied to a "resultados próximamente" marker) to avoid asking visitors to trust before showing anything?

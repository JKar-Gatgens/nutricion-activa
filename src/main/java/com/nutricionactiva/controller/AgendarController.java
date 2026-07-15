package com.nutricionactiva.controller;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nutricionactiva.model.Cita;
import com.nutricionactiva.model.Servicio;
import com.nutricionactiva.repository.CitaRepository;
import com.nutricionactiva.service.CatalogoServicios;
import com.nutricionactiva.service.DisponibilidadService;
import com.nutricionactiva.service.ReservaCitaService;
import com.nutricionactiva.service.SlotNoDisponibleException;

import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;

/**
 * Flujo público de agendamiento (HU-04, paso 6): selección de servicio
 * agendable → fecha → horario → datos de contacto, y la confirmación tras
 * reservar. Sin autenticación — cualquier visitante puede reservar, por eso
 * el honeypot ({@link ReservaCitaForm#getPaginaWeb()}) y la validación de
 * servidor no son opcionales aunque el HTML ya restrinja lo que un
 * navegador normal puede enviar.
 *
 * <p>El GET y el re-render del POST (validación fallida, spam, o
 * {@link SlotNoDisponibleException}) comparten {@link #poblarPasos}, para
 * que el visitante nunca pierda el servicio/fecha ya elegidos ni los datos
 * ya escritos.
 */
@Controller
public class AgendarController {

    private static final DateTimeFormatter FORMATO_FECHA_LARGA =
            DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es"));

    private final CatalogoServicios catalogoServicios;
    private final DisponibilidadService disponibilidadService;
    private final ReservaCitaService reservaCitaService;
    private final CitaRepository citaRepository;

    public AgendarController(
            CatalogoServicios catalogoServicios,
            DisponibilidadService disponibilidadService,
            ReservaCitaService reservaCitaService,
            CitaRepository citaRepository) {
        this.catalogoServicios = catalogoServicios;
        this.disponibilidadService = disponibilidadService;
        this.reservaCitaService = reservaCitaService;
        this.citaRepository = citaRepository;
    }

    @GetMapping("/agendar")
    public String mostrarFormulario(
            @RequestParam(required = false) String servicio,
            @RequestParam(required = false) LocalDate fecha,
            Model model) {
        Servicio servicioSeleccionado = buscarAgendable(servicio);
        LocalDate fechaSeleccionada = servicioSeleccionado == null ? null : fecha;

        poblarPasos(model, servicioSeleccionado, fechaSeleccionada);

        ReservaCitaForm form = new ReservaCitaForm();
        form.setServicioId(servicioSeleccionado == null ? null : servicioSeleccionado.id());
        form.setFecha(fechaSeleccionada);
        model.addAttribute("reservaCitaForm", form);

        return "agendar";
    }

    @PostMapping("/agendar")
    public String reservar(
            @Valid @ModelAttribute("reservaCitaForm") ReservaCitaForm form,
            BindingResult bindingResult,
            Model model) {
        Servicio servicioSeleccionado = buscarAgendable(form.getServicioId());
        if (servicioSeleccionado == null) {
            // servicioId ausente o inválido: no viene de un envío normal del
            // formulario (el campo es un hidden fijado por el paso 1), así que
            // no hay horarios que recalcular ni datos que preservar.
            return "redirect:/agendar";
        }

        boolean esSpam = form.getPaginaWeb() != null && !form.getPaginaWeb().isBlank();
        if (esSpam) {
            poblarPasos(model, servicioSeleccionado, form.getFecha());
            model.addAttribute("errorGeneral", "Hubo un problema con el envío. Revisá los datos e intentá de nuevo.");
            return "agendar";
        }

        if (bindingResult.hasErrors()) {
            poblarPasos(model, servicioSeleccionado, form.getFecha());
            return "agendar";
        }

        try {
            Cita cita = reservaCitaService.reservar(
                    servicioSeleccionado.id(),
                    form.getFecha(),
                    form.getHoraInicio(),
                    form.getNombre().strip(),
                    form.getCorreo().strip(),
                    form.getTelefono().strip(),
                    form.getMotivo().strip());
            return "redirect:/agendar/confirmacion/" + cita.getTokenConfirmacion();
        } catch (SlotNoDisponibleException e) {
            poblarPasos(model, servicioSeleccionado, form.getFecha());
            model.addAttribute("errorGeneral",
                    "Ese horario se acaba de ocupar. Elegí otro de los horarios disponibles abajo.");
            return "agendar";
        }
    }

    @GetMapping("/agendar/confirmacion/{token}")
    public String confirmacion(@PathVariable String token, Model model) {
        return citaRepository.findByTokenConfirmacion(token)
                .map(cita -> {
                    ZonedDateTime inicioCR = cita.getFechaHoraInicioUtc().atZone(DisponibilidadService.ZONA_NEGOCIO);
                    ZonedDateTime finCR = cita.getFechaHoraFinUtc().atZone(DisponibilidadService.ZONA_NEGOCIO);

                    model.addAttribute("cita", cita);
                    model.addAttribute("servicio", catalogoServicios.buscarPorId(cita.getServicioId()).orElse(null));
                    model.addAttribute("fechaFormateada", FORMATO_FECHA_LARGA.format(inicioCR));
                    model.addAttribute("horaInicio", inicioCR.toLocalTime());
                    model.addAttribute("horaFin", finCR.toLocalTime());
                    return "agendar-confirmacion";
                })
                .orElse("redirect:/agendar");
    }

    /** Servicio del catálogo solo si existe Y es agendable; {@code null} en cualquier otro caso. */
    private Servicio buscarAgendable(String servicioId) {
        if (servicioId == null) {
            return null;
        }
        return catalogoServicios.buscarPorId(servicioId).filter(Servicio::agendable).orElse(null);
    }

    /**
     * Puebla los atributos comunes a los pasos (a)-(c) del flujo: el catálogo
     * agendable completo (paso a), el servicio/fecha ya elegidos, la fecha
     * mínima reservable (para el atributo {@code min} del selector) y, si ya
     * hay servicio y fecha, los horarios libres (paso c). Usado tanto por el
     * GET inicial como por cualquier re-render del POST.
     */
    private void poblarPasos(Model model, Servicio servicioSeleccionado, LocalDate fechaSeleccionada) {
        model.addAttribute("serviciosAgendables", catalogoServicios.obtenerAgendables());
        model.addAttribute("servicioSeleccionado", servicioSeleccionado);
        model.addAttribute("fechaSeleccionada", fechaSeleccionada);
        model.addAttribute("fechaMinima", disponibilidadService.fechaMinimaReservable());

        if (servicioSeleccionado != null && fechaSeleccionada != null) {
            model.addAttribute("slotsDisponibles",
                    disponibilidadService.calcularSlotsDisponibles(servicioSeleccionado.id(), fechaSeleccionada));
        }
    }
}

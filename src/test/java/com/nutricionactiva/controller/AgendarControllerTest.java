package com.nutricionactiva.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nutricionactiva.model.Cita;
import com.nutricionactiva.model.FranjaHoraria;
import com.nutricionactiva.model.Servicio;
import com.nutricionactiva.repository.CitaRepository;
import com.nutricionactiva.service.CatalogoServicios;
import com.nutricionactiva.service.DisponibilidadService;
import com.nutricionactiva.service.ReservaCitaService;
import com.nutricionactiva.service.SlotNoDisponibleException;

/**
 * Test de contrato del flujo público de agendamiento (HU-04, paso 6):
 * {@link AgendarController} orquesta {@link CatalogoServicios} (paso a),
 * {@link DisponibilidadService} (pasos b/c) y {@link ReservaCitaService}
 * (paso d) — las tres se mockean porque su propia lógica ya está probada en
 * sus tests unitarios; acá solo se fija el contrato HTTP/vista/modelo del
 * controller.
 */
@WebMvcTest(AgendarController.class)
class AgendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatalogoServicios catalogoServicios;

    @MockitoBean
    private DisponibilidadService disponibilidadService;

    @MockitoBean
    private ReservaCitaService reservaCitaService;

    @MockitoBean
    private CitaRepository citaRepository;

    private static final Servicio CONSULTA = new Servicio(
            "consulta-nutricion", "Consulta de nutrición", "desc", 30_000, 90,
            "Virtual o a domicilio", true, 1, true);

    private static final Servicio NO_AGENDABLE = new Servicio(
            "protocolo-competencias", "Protocolo de competencia", "desc", 22_000, null,
            "Virtual", false, 3, false);

    @BeforeEach
    void configurarCatalogo() {
        when(catalogoServicios.obtenerAgendables()).thenReturn(List.of(CONSULTA));
        when(catalogoServicios.buscarPorId("consulta-nutricion")).thenReturn(Optional.of(CONSULTA));
        when(catalogoServicios.buscarPorId("protocolo-competencias")).thenReturn(Optional.of(NO_AGENDABLE));
        when(disponibilidadService.fechaMinimaReservable()).thenReturn(LocalDate.of(2026, 7, 15));
    }

    @Test
    void pasoUnoMuestraSoloElCatalogoAgendableSinPasosSiguientes() throws Exception {
        MvcResult result = mockMvc.perform(get("/agendar"))
                .andExpect(status().isOk())
                .andExpect(view().name("agendar"))
                .andExpect(model().attribute("servicioSeleccionado", nullValue()))
                .andExpect(model().attributeDoesNotExist("slotsDisponibles"))
                .andReturn();

        String html = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(html).contains("Consulta de nutrición");
        assertThat(html).doesNotContain("Ver horarios");
        assertThat(html).doesNotContain("Confirmar cita");
    }

    @Test
    void servicioNoAgendableEnLaQueryStringSeIgnora() throws Exception {
        mockMvc.perform(get("/agendar").param("servicio", "protocolo-competencias"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("servicioSeleccionado", nullValue()));
    }

    @Test
    void conServicioValidoMuestraElPasoDeFecha() throws Exception {
        MvcResult result = mockMvc.perform(get("/agendar").param("servicio", "consulta-nutricion"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("servicioSeleccionado", CONSULTA))
                .andReturn();

        String html = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(html).contains("Ver horarios");
        assertThat(html).doesNotContain("Confirmar cita");
    }

    @Test
    void conServicioYFechaMuestraLosHorariosLibres() throws Exception {
        LocalDate fecha = LocalDate.of(2026, 7, 20);
        when(disponibilidadService.calcularSlotsDisponibles("consulta-nutricion", fecha))
                .thenReturn(List.of(new FranjaHoraria(LocalTime.of(8, 0), LocalTime.of(9, 30))));

        MvcResult result = mockMvc.perform(get("/agendar")
                        .param("servicio", "consulta-nutricion")
                        .param("fecha", "2026-07-20"))
                .andExpect(status().isOk())
                .andReturn();

        String html = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(html).contains("Confirmar cita");
        assertThat(html).contains("08:00");
    }

    @Test
    void reservaExitosaRedirigeALaConfirmacion() throws Exception {
        Cita cita = new Cita("consulta-nutricion", 90, "Ana Pérez", "ana@correo.com", "8888-0000",
                "Quiero bajar de grasa", java.time.Instant.parse("2026-07-20T14:00:00Z"),
                java.time.Instant.parse("2026-07-20T15:30:00Z"));

        when(reservaCitaService.reservar(
                "consulta-nutricion", LocalDate.of(2026, 7, 20), LocalTime.of(8, 0),
                "Ana Pérez", "ana@correo.com", "8888-0000", "Quiero bajar de grasa"))
                .thenReturn(cita);

        mockMvc.perform(post("/agendar")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("servicioId", "consulta-nutricion")
                        .param("fecha", "2026-07-20")
                        .param("horaInicio", "08:00")
                        .param("nombre", "Ana Pérez")
                        .param("correo", "ana@correo.com")
                        .param("telefono", "8888-0000")
                        .param("motivo", "Quiero bajar de grasa")
                        .param("paginaWeb", ""))
                .andExpect(status().is3xxRedirection())
                // El token es un UUID generado por el propio constructor de Cita
                // (no el id autoincremental, ver AgendarController#confirmacion):
                // no hay un valor fijo que hardcodear, así que se compara contra
                // el que la propia Cita mockeada generó.
                .andExpect(redirectedUrl("/agendar/confirmacion/" + cita.getTokenConfirmacion()));
    }

    @Test
    void slotOcupadoVuelveAMostrarElFormularioConElErrorYLosDatosEscritos() throws Exception {
        LocalDate fecha = LocalDate.of(2026, 7, 20);
        when(disponibilidadService.calcularSlotsDisponibles("consulta-nutricion", fecha))
                .thenReturn(List.of(new FranjaHoraria(LocalTime.of(9, 0), LocalTime.of(10, 30))));
        when(reservaCitaService.reservar(
                "consulta-nutricion", fecha, LocalTime.of(8, 0),
                "Ana Torres", "ana@correo.com", "8888-0000", "Quiero bajar de grasa"))
                .thenThrow(new SlotNoDisponibleException(fecha, LocalTime.of(8, 0)));

        MvcResult result = mockMvc.perform(post("/agendar")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("servicioId", "consulta-nutricion")
                        .param("fecha", "2026-07-20")
                        .param("horaInicio", "08:00")
                        .param("nombre", "Ana Torres")
                        .param("correo", "ana@correo.com")
                        .param("telefono", "8888-0000")
                        .param("motivo", "Quiero bajar de grasa")
                        .param("paginaWeb", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("agendar"))
                .andExpect(model().attributeExists("errorGeneral"))
                .andReturn();

        verify(reservaCitaService).reservar(any(), any(), any(), any(), any(), any(), any());
        String html = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(html).contains("Ese horario se acaba de ocupar");
        // Los datos escritos no se pierden: siguen en los campos del formulario re-renderizado.
        assertThat(html).contains("Ana Torres");
        assertThat(html).contains("Quiero bajar de grasa");
    }

    @Test
    void datosInvalidosNoLlamanAReservaYMuestranErroresDeCampo() throws Exception {
        LocalDate fecha = LocalDate.of(2026, 7, 20);
        when(disponibilidadService.calcularSlotsDisponibles("consulta-nutricion", fecha))
                .thenReturn(List.of(new FranjaHoraria(LocalTime.of(8, 0), LocalTime.of(9, 30))));

        mockMvc.perform(post("/agendar")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .param("servicioId", "consulta-nutricion")
                        .param("fecha", "2026-07-20")
                        .param("horaInicio", "08:00")
                        .param("nombre", "Ana Pérez")
                        .param("correo", "esto-no-es-un-correo")
                        .param("telefono", "8888-0000")
                        .param("motivo", "Quiero bajar de grasa")
                        .param("paginaWeb", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("agendar"))
                .andExpect(model().attributeHasFieldErrors("reservaCitaForm", "correo"));

        verify(reservaCitaService, never()).reservar(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void honeypotRellenoSeDescartaSinReservarNiRevelarQueEsAntiSpam() throws Exception {
        LocalDate fecha = LocalDate.of(2026, 7, 20);
        when(disponibilidadService.calcularSlotsDisponibles("consulta-nutricion", fecha))
                .thenReturn(List.of(new FranjaHoraria(LocalTime.of(8, 0), LocalTime.of(9, 30))));

        mockMvc.perform(post("/agendar")
                        .param("servicioId", "consulta-nutricion")
                        .param("fecha", "2026-07-20")
                        .param("horaInicio", "08:00")
                        .param("nombre", "Bot Spammer")
                        .param("correo", "bot@spam.com")
                        .param("telefono", "8888-0000")
                        .param("motivo", "relleno")
                        .param("paginaWeb", "http://spam.example"))
                .andExpect(status().isOk())
                .andExpect(view().name("agendar"))
                .andExpect(model().attributeExists("errorGeneral"));

        verify(reservaCitaService, never()).reservar(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void confirmacionMuestraElResumenDeLaCitaExistente() throws Exception {
        Cita cita = new Cita("consulta-nutricion", 90, "Ana Pérez", "ana@correo.com", "8888-0000",
                "Quiero bajar de grasa", java.time.Instant.parse("2026-07-20T14:00:00Z"),
                java.time.Instant.parse("2026-07-20T15:30:00Z"));
        when(citaRepository.findByTokenConfirmacion(cita.getTokenConfirmacion())).thenReturn(Optional.of(cita));

        mockMvc.perform(get("/agendar/confirmacion/" + cita.getTokenConfirmacion()))
                .andExpect(status().isOk())
                .andExpect(view().name("agendar-confirmacion"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("¡Cita confirmada!")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Ana Pérez")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Consulta de nutrición")));
    }

    @Test
    void confirmacionDeCitaInexistenteRedirigeAAgendar() throws Exception {
        when(citaRepository.findByTokenConfirmacion("token-no-existe")).thenReturn(Optional.empty());

        mockMvc.perform(get("/agendar/confirmacion/token-no-existe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agendar"));
    }
}

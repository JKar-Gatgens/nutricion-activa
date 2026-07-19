package com.nutricionactiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nutricionactiva.service.ProgramaService;

/**
 * Página dedicada del programa "Fuerte y Definido" (HU-12). Solo lectura: no
 * hay inscripción ni pago en línea (D-17) — la conversión cierra por WhatsApp
 * con el CTA "Quiero más información".
 */
@Controller
public class ProgramaController {

    private final ProgramaService programaService;

    public ProgramaController(ProgramaService programaService) {
        this.programaService = programaService;
    }

    @GetMapping("/programa")
    public String programa(Model model) {
        model.addAttribute("programa", programaService.obtenerPrograma());
        return "programa";
    }
}

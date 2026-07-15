package com.nutricionactiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nutricionactiva.service.CatalogoServicios;
import com.nutricionactiva.service.TestimonioService;

@Controller
public class HomeController {

    private final CatalogoServicios catalogoServicios;
    private final TestimonioService testimonioService;

    public HomeController(CatalogoServicios catalogoServicios, TestimonioService testimonioService) {
        this.catalogoServicios = catalogoServicios;
        this.testimonioService = testimonioService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("servicios", catalogoServicios.obtenerTodos());
        model.addAttribute("testimonios", testimonioService.obtenerTodos());
        return "index";
    }
}

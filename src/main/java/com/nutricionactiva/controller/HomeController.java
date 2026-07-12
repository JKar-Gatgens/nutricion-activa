package com.nutricionactiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nutricionactiva.service.CatalogoServicios;

@Controller
public class HomeController {

    private final CatalogoServicios catalogoServicios;

    public HomeController(CatalogoServicios catalogoServicios) {
        this.catalogoServicios = catalogoServicios;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("servicios", catalogoServicios.obtenerTodos());
        return "index";
    }
}

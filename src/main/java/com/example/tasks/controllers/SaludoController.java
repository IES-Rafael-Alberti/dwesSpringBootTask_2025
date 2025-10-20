package com.example.tasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SaludoController {

    @GetMapping("/saludo")
    public String mostrarSaludo(Model model) {
        model.addAttribute("nombre", "Juan");
        model.addAttribute("edad", 25);
        return "saludo"; // Nombre de la plantilla Thymeleaf
    }
}

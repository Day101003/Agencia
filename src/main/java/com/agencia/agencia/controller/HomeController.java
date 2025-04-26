package com.agencia.agencia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * GET  /  y  /home
     * Ambas rutas sirven la misma vista home.html.
     */
    @GetMapping("/home")
    public String home() {
        return "home";  // Thymeleaf: src/main/resources/templates/home.html
    }
}
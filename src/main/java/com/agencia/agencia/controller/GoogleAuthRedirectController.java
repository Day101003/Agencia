package com.agencia.agencia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleAuthRedirectController {

    @GetMapping("/auth/google")
    public String redirectToLogin() {
        // Redirige al formulario de login donde está tu botón de Google
        return "redirect:/index";
    }
}
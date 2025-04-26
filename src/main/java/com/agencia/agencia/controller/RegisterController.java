package com.agencia.agencia.controller;

import com.agencia.agencia.dto.RegisterRequest;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.service.UsuarioService;
import com.agencia.agencia.security.JwtTokenProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

@Controller
public class RegisterController {

    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtProvider;

    public RegisterController(UsuarioService usuarioService,
                              JwtTokenProvider jwtProvider) {
        this.usuarioService = usuarioService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/register")
    public String showForm(Model m) {
        m.addAttribute("registerDto", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute RegisterRequest dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setCorreo(dto.getCorreo());
        u.setContrasena(dto.getContrasena());
        u.setTelefono(dto.getTelefono());
        u.setTipo_usuario(2); // Usuario normal
        u.setFecha_registro(LocalDate.now());
        u.setRuta_imagen_usuario("/assets/img/FotoPerfil/default.png"); // Imagen por defecto

        usuarioService.registrarUsuario(u);

        Authentication auth = new UsernamePasswordAuthenticationToken(
            u.getCorreo(), u.getContrasena()
        );
        String token = jwtProvider.generarToken(auth);
        return "redirect:/index?token=" + token;
    }
}
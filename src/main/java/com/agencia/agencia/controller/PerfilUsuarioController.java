package com.agencia.agencia.controller;

import com.agencia.agencia.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class PerfilUsuarioController {

    private final JwtTokenProvider jwtTokenProvider;

    public PerfilUsuarioController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token no encontrado");
        }

        String token = authHeader.substring(7);

        if (!jwtTokenProvider.validarToken(token)) {
            return ResponseEntity.status(401).body("Token inválido o expirado");
        }

        Claims claims = jwtTokenProvider.getAllClaims(token);
        Map<String, Object> data = new HashMap<>();
        data.put("correo", claims.getSubject());
        data.put("nombre", claims.get("nombre"));
        data.put("ruta_imagen_usuario", claims.get("imagen"));

        return ResponseEntity.ok(data);
    }
}
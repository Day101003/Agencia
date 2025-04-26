package com.agencia.agencia.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class JwtTokenProvider {

    private static final Logger log = Logger.getLogger(JwtTokenProvider.class.getName());

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-millis}")
    private long jwtExpirationMillis;

    public String generarToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        String email;
        String nombre = "";
        String imagen = "";
        int tipoUsuario = 2; // Default a usuario normal

        if (principal instanceof com.agencia.agencia.model.Usuario usuario) {
            email = usuario.getCorreo();
            nombre = usuario.getNombre();
            imagen = usuario.getRuta_imagen_usuario();
            tipoUsuario = usuario.getTipo_usuario();
        } else if (principal instanceof org.springframework.security.core.userdetails.User userDetails) {
            email = userDetails.getUsername();
        } else {
            email = authentication.getName();
        }

        return buildToken(email, nombre, imagen, tipoUsuario);
    }

    public String generateToken(String email, String nombre, String rutaImagen, int tipoUsuario) {
        return buildToken(email, nombre, rutaImagen, tipoUsuario);
    }

    private String buildToken(String email, String nombre, String imagen, int tipoUsuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMillis);
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Map<String, Object> claims = new HashMap<>();
        claims.put("nombre", nombre);
        claims.put("imagen", imagen);
        claims.put("tipo_usuario", tipoUsuario);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        log.info("Token JWT generado para: " + email);
        return token;
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.severe("Token inválido: " + e.getMessage());
            return false;
        }
    }

    public String getUsernameDelJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
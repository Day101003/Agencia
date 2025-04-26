package com.agencia.agencia.security;

import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = Logger.getLogger(OAuth2LoginSuccessHandler.class.getName());
    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

    public OAuth2LoginSuccessHandler(UsuarioService usuarioService, JwtTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {
        try {
            log.info("Procesando autenticación exitosa con OAuth2");

            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

            // Extraer atributos con manejo de valores nulos
            String email = oauthUser.getAttribute("email");
            if (email == null) {
                // Intentar con otros nombres de atributos comunes
                email = oauthUser.getAttribute("emailAddress");
                if (email == null) {
                    log.severe("Email no encontrado en los atributos de OAuth2User");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email no proporcionado por el proveedor OAuth2");
                    return;
                }
            }

            String name = oauthUser.getAttribute("name");
            if (name == null) {
                name = oauthUser.getAttribute("given_name") != null 
                    ? oauthUser.getAttribute("given_name") + " " + (oauthUser.getAttribute("family_name") != null ? oauthUser.getAttribute("family_name") : "")
                    : "Usuario";
            }

            String picture = oauthUser.getAttribute("picture");
            if (picture == null) {
                picture = "default.png";
            }

            log.info("Autenticando usuario con email: " + email);

            // Login o registro
            Usuario usuario = usuarioService.loginOrRegisterOAuth(oauthUser);

            // Generar el token incluyendo tipo_usuario
            String token = jwtTokenProvider.generateToken(
                    usuario.getCorreo(),
                    usuario.getNombre(),
                    usuario.getRuta_imagen_usuario(),
                    usuario.getTipo_usuario()); // Agregamos tipo_usuario

            log.info("Token JWT generado: " + token);

            // Enviar el token en una cookie
            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(false); // Necesario para el frontend
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(3600); // 1 hora
            jwtCookie.setSecure(false); // Cambia a true en producción
            jwtCookie.setAttribute("SameSite", "Lax");
            response.addCookie(jwtCookie);
            log.info("Cookie JWT_TOKEN establecida con valor: " + token);

            // Redirigir al frontend
            String redirectUrl = "http://localhost:8080/index.html";
            log.info("Redirigiendo a: " + redirectUrl);
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            log.severe("Error en OAuth2LoginSuccessHandler: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar autenticación con Google");
        }
    }
}
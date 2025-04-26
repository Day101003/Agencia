// UsuarioService.java
package com.agencia.agencia.service;

import com.agencia.agencia.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.util.List;
import java.util.Optional;

public interface UsuarioService extends UserDetailsService {
    Usuario registrarUsuario(Usuario u);
    Usuario loginOrRegister(GoogleIdToken.Payload payload);
    Usuario loginOrRegisterOAuth(OAuth2User oauth2User);
    List<Usuario> listarUsuarios();
    Optional<Usuario> findByCorreo(String correo);
    Usuario consultar(int id);
    Usuario add(Usuario u);
    Usuario actualizarUsuario(Usuario u);
    void eliminar(int id);
}
package com.agencia.agencia.service;

import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.repository.UsuarioRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger log = Logger.getLogger(UsuarioServiceImpl.class.getName());
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario loginOrRegisterOAuth(OAuth2User oauth2User) {
        try {
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            String picture = oauth2User.getAttribute("picture");

            log.info("Procesando login/registro para email: " + email);

            Optional<Usuario> existente = usuarioRepository.findByCorreo(email);

            if (existente.isPresent()) {
                log.info("Usuario encontrado: " + email);
                return existente.get();
            }

            log.info("Creando nuevo usuario: " + email);
            Usuario nuevo = new Usuario();
            nuevo.setCorreo(email);
            nuevo.setNombre(name != null ? name : "Usuario");
            nuevo.setRuta_imagen_usuario(picture != null ? picture : "default.png");
            nuevo.setTipo_usuario(2); // Usuario normal
            nuevo.setContrasena(passwordEncoder.encode(UUID.randomUUID().toString()));
            nuevo.setFecha_registro(LocalDate.now());
            nuevo.setTelefono("00000000");

            Usuario guardado = usuarioRepository.save(nuevo);
            log.info("Usuario creado: " + guardado.getCorreo());
            return guardado;

        } catch (Exception e) {
            log.severe("Error al procesar login/registro: " + e.getMessage());
            throw new RuntimeException("Error al procesar autenticación OAuth2", e);
        }
    }

    @Override
    public Usuario loginOrRegister(GoogleIdToken.Payload payload) {
    try {
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String picture = (String) payload.get("picture");

        log.info("Procesando login/registro para email: " + email);

        // Buscar si el usuario ya existe
        Optional<Usuario> existente = usuarioRepository.findByCorreo(email);

        if (existente.isPresent()) {
            log.info("Usuario encontrado: " + email);
            return existente.get();
        }

        log.info("Creando nuevo usuario: " + email);
        Usuario nuevo = new Usuario();
        nuevo.setCorreo(email);
        nuevo.setNombre(name != null ? name : "Usuario");
        nuevo.setRuta_imagen_usuario(picture != null ? picture : "default.png");
        nuevo.setTipo_usuario(2); // Usuario normal
        nuevo.setContrasena(passwordEncoder.encode(UUID.randomUUID().toString()));
        nuevo.setFecha_registro(LocalDate.now());
        nuevo.setTelefono("00000000"); // Valor por defecto

        Usuario guardado = usuarioRepository.save(nuevo);
        log.info("Usuario creado: " + guardado.getCorreo());
        return guardado;

    } catch (Exception e) {
        log.severe("Error al procesar login/registro con Google: " + e.getMessage());
        throw new RuntimeException("Error al procesar autenticación con Google", e);
    }
}

    // Eliminar loginOrRegister(Payload) ya que no se usa
    // Otros métodos permanecen igual
    @Override
    public Usuario registrarUsuario(Usuario u) {
        u.setFecha_registro(LocalDate.now());
        u.setContrasena(passwordEncoder.encode(u.getContrasena()));
        return usuarioRepository.save(u);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    public Usuario consultar(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario actualizarUsuario(Usuario u) {
        return usuarioRepository.save(u);
    }

    @Override
    public void eliminar(int id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario add(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String role = usuario.getTipo_usuario() == 1 ? "ROLE_ADMIN" : "ROLE_USER";
        return User.withUsername(usuario.getCorreo())
                .password(usuario.getContrasena())
                .authorities(role)
                .build();
    }

    
}
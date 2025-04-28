package com.agencia.agencia.controller;

import com.agencia.agencia.dto.FavoritoDTO;
import com.agencia.agencia.model.Favorito;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.service.FavoritoService;
import com.agencia.agencia.service.UsuarioService;
import com.agencia.agencia.service.CarrosServiceLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarrosServiceLong carrosServiceLong; // Correcto servicio para los carros

    // ✅ Agregar carro a favoritos
    @PostMapping("/agregar/{carroId}")
    @ResponseBody
    public ResponseEntity<String> agregarAFavoritos(@PathVariable long carroId, Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
            }

            Optional<Usuario> usuarioOpt = usuarioService.findByCorreo(principal.getName());
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                favoritoService.agregarFavorito(usuario, carroId);
                return ResponseEntity.ok("Carro agregado a favoritos!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + ex.getMessage());
        }
    }

    // ✅ Eliminar carro de favoritos
    @PostMapping("/eliminar/{carroId}")
    @ResponseBody
    public ResponseEntity<String> eliminarDeFavoritos(@PathVariable long carroId, Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
            }

            Optional<Usuario> usuarioOpt = usuarioService.findByCorreo(principal.getName());
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                favoritoService.eliminarFavorito(usuario, carroId);
                return ResponseEntity.ok("Carro eliminado de favoritos!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + ex.getMessage());
        }
    }

    // ✅ Listar favoritos para el modal
    @GetMapping
@ResponseBody
public ResponseEntity<?> listarFavoritos(Principal principal) {
    try {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
        }

        Optional<Usuario> usuarioOpt = usuarioService.findByCorreo(principal.getName());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            List<Favorito> favoritos = favoritoService.listarFavoritos(usuario);

            // Convertimos cada Favorito a FavoritoDTO
            List<FavoritoDTO> favoritoDTOs = favoritos.stream().map(fav -> {
                var carro = fav.getCarro();
                return new FavoritoDTO(
                    carro.getId_carro(),
                    carro.getMarca() != null ? carro.getMarca().getNombre_marca() : "Marca desconocida",
                    carro.getModelo() != null ? carro.getModelo().getNombre_modelo() : "Modelo desconocido",
                    carro.getAno(),
                    carro.getPrecio_carro()
                );
            }).toList();

            return ResponseEntity.ok(favoritoDTOs);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno: " + ex.getMessage());
    }
}
}

package com.agencia.agencia.controller;

import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({ "", "/" })
    public String lista(Model model) {
        model.addAttribute("listaUsuarios", usuarioService.listarUsuarios());
        return "usuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("telefono") String telefono,
            @RequestParam("fechaRegistro") String fechaRegistro,
            @RequestParam("correo") String correo,
            @RequestParam("tipoUsuario") Integer tipoUsuario,
            @RequestParam("ruta_imagen_usuario") MultipartFile imagen,
            RedirectAttributes redirectAttributes) {

        try {
            Optional<Usuario> usuarioExistente = usuarioService.findByCorreo(correo);
            if (usuarioExistente.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "El correo ya está registrado.");
                redirectAttributes.addFlashAttribute("nombre", nombre);
                redirectAttributes.addFlashAttribute("telefono", telefono);
                redirectAttributes.addFlashAttribute("fechaRegistro", fechaRegistro);
                redirectAttributes.addFlashAttribute("correo", correo);
                return "redirect:/admin";
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setContrasena(passwordEncoder.encode(contrasena)); // 🔥 Encriptar contraseña
            usuario.setTelefono(telefono);
            usuario.setFecha_registro(LocalDate.parse(fechaRegistro));
            usuario.setCorreo(correo);
            usuario.setTipo_usuario(tipoUsuario);

            if (!imagen.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/imagenes/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String originalNombre = imagen.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + originalNombre;
                String rutaImagen = "imagenes/" + uniqueFileName;

                File imagenFile = new File(uploadDir + uniqueFileName);
                imagen.transferTo(imagenFile);

                usuario.setRuta_imagen_usuario(rutaImagen);
            } else {
                usuario.setRuta_imagen_usuario("/assets/img/FotoPerfil/default.png"); // Imagen por defecto
            }

            usuarioService.add(usuario);
            return "redirect:/admin";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la imagen del usuario: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable int id, Model model) {
        Usuario usuario = usuarioService.consultar(id);
        if (usuario == null) {
            model.addAttribute("error", "Usuario no encontrado.");
            return "redirect:/admin";
        }
        model.addAttribute("usuario", usuario);
        return "editarUsuario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(
            @PathVariable int id,
            @RequestParam("nombre") String nombre,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("telefono") String telefono,
            @RequestParam("fechaRegistro") String fechaRegistro,
            @RequestParam("correo") String correo,
            @RequestParam("tipoUsuario") Integer tipoUsuario,
            @RequestParam(value = "ruta_imagen_usuario", required = false) MultipartFile imagen,
            RedirectAttributes redirectAttributes) {

        try {
            Usuario usuarioExistente = usuarioService.consultar(id);
            if (usuarioExistente == null) {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
                return "redirect:/admin";
            }

            Optional<Usuario> usuarioConCorreoExistente = usuarioService.findByCorreo(correo);
            if (usuarioConCorreoExistente.isPresent() && !usuarioConCorreoExistente.get().getId_usuario().equals(id)) {
                redirectAttributes.addFlashAttribute("error", "El correo ya está registrado en otra cuenta.");
                return "redirect:/admin";
            }

            usuarioExistente.setNombre(nombre);
            usuarioExistente.setContrasena(passwordEncoder.encode(contrasena)); // 🔥 Encriptar contraseña nueva
            usuarioExistente.setTelefono(telefono);
            usuarioExistente.setFecha_registro(LocalDate.parse(fechaRegistro));
            usuarioExistente.setCorreo(correo);
            usuarioExistente.setTipo_usuario(tipoUsuario);

            if (imagen != null && !imagen.isEmpty()) {
                String contentType = imagen.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    redirectAttributes.addFlashAttribute("error", "El archivo cargado no es una imagen.");
                    return "redirect:/admin";
                }

                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/imagenes/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String originalNombre = imagen.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + originalNombre;
                String rutaImagen = "imagenes/" + uniqueFileName;

                File imagenFile = new File(uploadDir + uniqueFileName);
                imagen.transferTo(imagenFile);

                usuarioExistente.setRuta_imagen_usuario(rutaImagen);
            }

            usuarioService.actualizarUsuario(usuarioExistente);
            return "redirect:/admin";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al actualizar la imagen del usuario: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable int id) {
        usuarioService.eliminar(id);
        return "redirect:/admin";
    }
}
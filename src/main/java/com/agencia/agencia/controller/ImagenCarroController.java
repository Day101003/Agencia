package com.agencia.agencia.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agencia.agencia.model.ImagenCarros;
import com.agencia.agencia.service.ImagenCarrosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/imagenes")
public class ImagenCarroController {

    private final ImagenCarrosService imagenCarrosService;

    public ImagenCarroController(ImagenCarrosService imagenCarrosService) {
        this.imagenCarrosService = imagenCarrosService;
    }

    @GetMapping("/imagen")
    public String mostrarImagenes(Model model) {
        List<ImagenCarros> imagenes = imagenCarrosService.listarImagenCarros();
        model.addAttribute("listaImagen", imagenes);

        return "listaImagen";
    }

    @GetMapping("/nuevo")
    public String crearNuevaImagen(Model model) {
        List<ImagenCarros> imagenes = imagenCarrosService.listarImagenCarros();
        model.addAttribute("listaImagen", imagenes);
        model.addAttribute("opcionesImagen", new ImagenCarros());
        return "opcionesImagen";
    }

    @PostMapping("/guardar")
    public String guardarImagen(
            @RequestParam(name = "id_imagen", required = false) Integer id_imagen,
            @RequestParam("tipo") int tipo,
            @RequestParam("imagen") MultipartFile imagen,
            Model model) {
        try {
            String rutaImagen = null;

            if (!imagen.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/imagenes/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String originalNombre = imagen.getOriginalFilename();
                String imagenNombre = originalNombre;
                rutaImagen = "imagenes/" + imagenNombre;

                File imagenFile = new File(uploadDir + imagenNombre);
                imagen.transferTo(imagenFile);
            }

            ImagenCarros imagenCarros;
            if (id_imagen != null) {
                imagenCarros = imagenCarrosService.consultar(id_imagen);
                if (imagenCarros == null) {
                    model.addAttribute("error", "La imagen no fue encontrada para editar.");
                    return "error";
                }
            } else {
                imagenCarros = new ImagenCarros();
            }

            imagenCarros.setTipo(tipo);
            if (rutaImagen != null) {
                imagenCarros.setRuta_imagen(rutaImagen);
            }

            imagenCarrosService.add(imagenCarros);

            return "redirect:/imagenes/imagen";

        } catch (IOException e) {
            model.addAttribute("error", "Error al guardar la imagen: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        ImagenCarros imagenCarros = imagenCarrosService.consultar(id);
        model.addAttribute("imagen", imagenCarros);
        return "editarImagen";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarImagen(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            imagenCarrosService.eliminar(id);
            } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "¡No se puede eliminar esta imagen! Está asociada a un carro.");
        }
            return "redirect:/imagenes/imagen";
    }
}
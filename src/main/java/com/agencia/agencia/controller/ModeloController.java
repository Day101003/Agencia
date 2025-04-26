package com.agencia.agencia.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.agencia.agencia.model.Modelo;
import com.agencia.agencia.model.Tipo;
import com.agencia.agencia.service.ModeloService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/modelos")
public class ModeloController {
    
    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService){
        this.modeloService = modeloService;
    }

  @GetMapping("/modelo")
    public String listaModelo(Model model) {
        model.addAttribute("listaModelo", modeloService.listarModelo());
        return "listaModelo";
    }

    @GetMapping("/nuevo")
    public String crearNuevoModelo(Model model) {
        model.addAttribute("opcionesCarro", new Modelo());
        return "opcionesCarro";
    }

    @PostMapping("/guardar")
    public String guardaModelo(@ModelAttribute Modelo modelo) {
        modeloService.add(modelo);
        return "redirect:/modelos/modelo";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        Modelo modelo = modeloService.consultar(id);
        model.addAttribute("modelos", modelo);
        return "editarModelo";
    }

    @PostMapping("/eliminar/{id}")
public String eliminar(@PathVariable int id) {
    modeloService.eliminar(id);
    return "redirect:/modelos/modelo"; 
}
}

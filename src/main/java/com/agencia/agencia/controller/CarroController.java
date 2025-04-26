package com.agencia.agencia.controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.agencia.agencia.model.Carro;
import com.agencia.agencia.model.ImagenCarros;
import com.agencia.agencia.model.Marca;
import com.agencia.agencia.model.Modelo;
import com.agencia.agencia.model.Tipo;
import com.agencia.agencia.service.CarrosService;
import com.agencia.agencia.service.ImagenCarrosService;
import com.agencia.agencia.service.MarcaService;
import com.agencia.agencia.service.ModeloService;
import com.agencia.agencia.service.TipoService; 
@Controller
@RequestMapping("/controller_carro")
public class CarroController {

    private final CarrosService carrosService;
    private final TipoService tipoService; 
    private final MarcaService marcaService;
    private final ModeloService modeloService;
    private final ImagenCarrosService imagenCarrosService;

    public CarroController(CarrosService carrosService, TipoService tipoService, MarcaService marcaService, ModeloService modeloService, ImagenCarrosService imagenCarrosService) {
        this.carrosService = carrosService;
        this.tipoService = tipoService;
        this.marcaService = marcaService;
        this.modeloService = modeloService;
        this.imagenCarrosService = imagenCarrosService;
    }

    @GetMapping("/gestionar")
    public String gestionarCarros(Model model) {
        model.addAttribute("carros", carrosService.listarCarros());
        model.addAttribute("marcas", marcaService.listarMarca()); 
        model.addAttribute("modelos", modeloService.listarModelo()); 
        model.addAttribute("tipos", tipoService.listarTipo()); 
        model.addAttribute("imagen", imagenCarrosService.listarImagenCarros());
        return "gestionarCarro"; 
    }

    @GetMapping("/opciones")
    public String opcionesCarro(Model model) {
        model.addAttribute("opcionesCarro", carrosService.listarCarros());
        return "opcionesCarro";
    }

    @GetMapping("/listaCar")
    public String listaCarro(Model model) {
        model.addAttribute("carros", carrosService.listarCarros());
        return "listaCarro"; 
    }


    @PostMapping("/guardar")
    public String guardarCarro(@ModelAttribute Carro carro, @RequestParam("imagen") int imagenId) {
    ImagenCarros imagenSeleccionada = imagenCarrosService.consultar(imagenId);
    carro.setImagenCarros(imagenSeleccionada);
    carrosService.add(carro);
    return "redirect:/controller_carro/listaCar";
}
@GetMapping("/editar/{id}")
public String editar(@PathVariable int id, Model model) {
    Carro carro = carrosService.consultar(id);
    if (carro == null) {
        return "redirect:/controller_carro/listaCar"; // Manejo de error
    }
    
    // Cargar las listas de marcas, modelos y tipos
    List<Marca> marcas = marcaService.listarMarca();
    List<Modelo> modelos = modeloService.listarModelo();
    List<Tipo> tipos = tipoService.listarTipo();
    List<ImagenCarros> imagen = imagenCarrosService.listarImagenCarros();
    model.addAttribute("carros", carro);
    model.addAttribute("marcas", marcas);
    model.addAttribute("modelos", modelos);
    model.addAttribute("tipos", tipos);
    model.addAttribute("imagen", imagen);
    
    
    return "editarCarro"; // Asegúrate de que este nombre sea correcto
}


    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        carrosService.eliminar(id);
        return "redirect:/controller_carro/listaCar"; 
    }


    @PostMapping("/cambiarEstado/{id}")
public String cambiarEstado(@PathVariable int id) {
    Carro carro = carrosService.consultar(id);
    if (carro != null) {
        carro.setEstado(carro.getEstado() == 1 ? 0 : 1); // Cambia de activo a inactivo o viceversa
        carrosService.actualizaCarro(carro); // Asegúrate de tener un método en tu servicio para actualizar el carro
    }
    return "redirect:/controller_carro/listaCar";
}

    

}
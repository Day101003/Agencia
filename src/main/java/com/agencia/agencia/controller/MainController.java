package com.agencia.agencia.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.agencia.agencia.service.CarrosService;
import com.agencia.agencia.service.MarcaService;
import org.springframework.web.bind.annotation.RequestParam;
import com.agencia.agencia.model.Carro;
import com.agencia.agencia.model.Marca;

@Controller
public class MainController {

    private final CarrosService carrosService;
    private final MarcaService marcaService;

    public MainController(CarrosService carrosService, MarcaService marcaService) {
        this.carrosService = carrosService;
        this.marcaService = marcaService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("carros", carrosService.listarCarros());
        model.addAttribute("marcas", marcaService.listarMarca());
        return "index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("carros", carrosService.listarCarros());
        model.addAttribute("marcas", marcaService.listarMarca());
        return "index";
    }

    @GetMapping("/marcasTipos")
    public String obtenerMarcasTipos(@RequestParam("id_marca") Long idMarca, Model model) {
        try {
            Marca marca = marcaService.consultar(idMarca);
            List<Carro> carros = carrosService.listarCarrosPorMarca(marca);
            model.addAttribute("carros", carros);
            model.addAttribute("marca", marca);
            return "marcasTipos";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un problema al obtener los carros.");
            return "index";
        }
    }

    @GetMapping("/detalleCarro")
    public String obtenerDetallesCarro(@RequestParam("id") int idCarro, Model model) {
        try {
            Carro carro = carrosService.consultar(idCarro);
            if (carro == null) {
                model.addAttribute("error", "El carro no fue encontrado.");
                return "index";
            }
            model.addAttribute("carro", carro);
            return "detalleCarro";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un problema al obtener los detalles del carro.");
            return "index";
        }
    }
}
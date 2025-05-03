package com.agencia.agencia.controller;

import com.agencia.agencia.dto.*;
import com.agencia.agencia.model.*;
import com.agencia.agencia.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CarrosService carrosService;
    private final OrdenService ordenService;
    private final DetalleOrdenesService detalleOrdenesService;
    private final UsuarioService usuarioService;

    public CartController(CarrosService carrosService, OrdenService ordenService,
                          DetalleOrdenesService detalleOrdenesService, UsuarioService usuarioService) {
        this.carrosService = carrosService;
        this.ordenService = ordenService;
        this.detalleOrdenesService = detalleOrdenesService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/view")
    public ResponseEntity<OrdenDTO> viewCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        String email = auth.getName();
        Usuario usuario = usuarioService.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Orden orden = ordenService.findOrCreateOpenOrder(usuario);
        OrdenDTO ordenDTO = mapToOrdenDTO(orden);
        return ResponseEntity.ok(ordenDTO);
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<String> addToCart(@RequestParam("carroId") int carroId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("Unauthorized"); // Unauthorized
        }

        String email = auth.getName();
        Usuario usuario = usuarioService.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            // Obtener la orden abierta del usuario
            Orden orden = ordenService.findOrCreateOpenOrder(usuario);
            System.out.println("Orden antes de añadir detalle: " + orden);

            // Verificar si el carro ya está en el carrito
            boolean carroExists = orden.getDetalles().stream()
                    .anyMatch(detalle -> detalle.getCarro().getId_carro() == carroId);
            if (carroExists) {
                return ResponseEntity.status(400).body("El vehículo ya se encuentra en el carrito de compras.");
            }

            // Consultar el carro
            Carro carro = carrosService.consultar(carroId);
            if (carro == null || !carro.isDisponibilidad()) {
                return ResponseEntity.status(400).body("Carro no disponible o no encontrado"); 
            }

            
            DetalleOrdenes detalle = detalleOrdenesService.addCarToOrder(orden, carro);
            System.out.println("Detalle añadido: " + detalle);

           
            orden = ordenService.findOrCreateOpenOrder(usuario);
            System.out.println("Orden después de añadir detalle: " + orden);

            
            int totalPrice = orden.getDetalles().stream()
                    .mapToInt(d -> (int) (d.getCantidad() * d.getPrecio_unitario()))
                    .sum();
            orden.setPrecio(totalPrice);

         
            ordenService.updateOrder(orden);
            System.out.println("Precio total actualizado: " + totalPrice);

            return ResponseEntity.ok("Carro añadido al carrito exitosamente");
        } catch (RuntimeException e) {
            System.err.println("Error al añadir al carrito: " + e.getMessage());
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        String email = auth.getName();
        Usuario usuario = usuarioService.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Orden orden = ordenService.findOrCreateOpenOrder(usuario);
        if (orden.getPrecio() == 0) {
            return ResponseEntity.status(400).build(); // Bad Request
        }

        for (DetalleOrdenes detalle : orden.getDetalles()) {
            Carro carro = detalle.getCarro();
            carrosService.markAsSold(carro);
        }

        ordenService.completeOrder(orden);
        ordenService.createOrder(usuario);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    @Transactional
    public ResponseEntity<String> removeFromCart(@RequestParam("detalleId") Long detalleId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("Unauthorized"); // Unauthorized
        }

        String email = auth.getName();
        Usuario usuario = usuarioService.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            System.out.println("Intentando eliminar detalle con ID: " + detalleId);

            // Obtener la orden abierta del usuario
            Orden orden = ordenService.findOrCreateOpenOrder(usuario);
            System.out.println("Orden antes de eliminar: " + orden);

            // Verificar que el detalle pertenece a la orden del usuario
            DetalleOrdenes detalle = orden.getDetalles().stream()
                    .filter(d -> d.getId_detalle() == detalleId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Detalle not found in order"));

            // Eliminar el detalle
            orden.getDetalles().remove(detalle); // Remover de la lista en memoria
            detalleOrdenesService.removeCarFromOrder(detalleId);
            System.out.println("Detalle eliminado de la base de datos");

            
            orden = ordenService.findOrCreateOpenOrder(usuario);
            System.out.println("Orden después de eliminar: " + orden);

            
            int totalPrice = orden.getDetalles().stream()
                    .mapToInt(d -> (int) (d.getCantidad() * d.getPrecio_unitario()))
                    .sum();
            orden.setPrecio(totalPrice);

           
            ordenService.updateOrder(orden);
            System.out.println("Precio total actualizado después de eliminar: " + totalPrice);

            return ResponseEntity.ok("Carro eliminado del carrito exitosamente");
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar detalle: " + e.getMessage());
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Métodos de mapeo
    private OrdenDTO mapToOrdenDTO(Orden orden) {
        OrdenDTO ordenDTO = new OrdenDTO();
        ordenDTO.setIdOrden(orden.getId_orden());
        ordenDTO.setFechaOrden(orden.getFecha_orden());
        ordenDTO.setPrecio((double) orden.getPrecio()); // Convertimos int a double
        ordenDTO.setEstadoPago(orden.isEstado_pago());
        if (orden.getDetalles() != null) {
            ordenDTO.setDetalles(orden.getDetalles().stream()
                    .map(this::mapToDetalleOrdenDTO)
                    .collect(Collectors.toList()));
        }
        return ordenDTO;
    }

    private DetalleOrdenDTO mapToDetalleOrdenDTO(DetalleOrdenes detalle) {
        DetalleOrdenDTO detalleDTO = new DetalleOrdenDTO();
        detalleDTO.setIdDetalle(detalle.getId_detalle());
        detalleDTO.setCantidad(detalle.getCantidad());
        detalleDTO.setPrecioUnitario(detalle.getPrecio_unitario());
        if (detalle.getCarro() != null) {
            detalleDTO.setCarro(mapToCarroDTO(detalle.getCarro()));
        }
        return detalleDTO;
    }

    private CarroDTO mapToCarroDTO(Carro carro) {
        CarroDTO carroDTO = new CarroDTO();
        carroDTO.setIdCarro(carro.getId_carro());
        carroDTO.setPrecioCarro(carro.getPrecio_carro());
        carroDTO.setAno(carro.getAno());
        if (carro.getModelo() != null) {
            carroDTO.setModelo(mapToModeloDTO(carro.getModelo()));
        }
        if (carro.getMarca() != null) {
            carroDTO.setMarca(mapToMarcaDTO(carro.getMarca()));
        }
        if (carro.getImagenCarros() != null) {
            carroDTO.setRutaImagen(carro.getImagenCarros().getRuta_imagen());
        } else {
            carroDTO.setRutaImagen("assets/img/default-car.jpg");
        }
        return carroDTO;
    }

    private ModeloDTO mapToModeloDTO(Modelo modelo) {
        ModeloDTO modeloDTO = new ModeloDTO();
        modeloDTO.setIdModelo(modelo.getId_modelo());
        modeloDTO.setNombreModelo(modelo.getNombre_modelo());
        return modeloDTO;
    }

    private MarcaDTO mapToMarcaDTO(Marca marca) {
        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setIdMarca(marca.getId_marca());
        marcaDTO.setNombreMarca(marca.getNombre_marca());
        return marcaDTO;
    }
}
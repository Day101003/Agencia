package com.agencia.agencia.service;

import com.agencia.agencia.model.Orden;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.repository.OrdenRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;

    public OrdenService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    @Transactional
    public Orden findOrCreateOpenOrder(Usuario usuario) {
        Optional<Orden> openOrder = ordenRepository.findTopByUsuarioAndEstadoPagoFalseOrderByFechaOrdenDesc(usuario);
        Orden orden = openOrder.orElseGet(() -> createOrder(usuario));
        Hibernate.initialize(orden.getDetalles());
        if (orden.getDetalles() != null) {
            orden.getDetalles().forEach(detalle -> {
                Hibernate.initialize(detalle.getCarro());
                if (detalle.getCarro() != null) {
                    Hibernate.initialize(detalle.getCarro().getModelo());
                    Hibernate.initialize(detalle.getCarro().getMarca());
                }
            });
        }
        System.out.println("Orden cargada con detalles: " + orden);
        return orden;
    }

    @Transactional
    public Orden createOrder(Usuario usuario) {
        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setFecha_orden(LocalDate.now());
        orden.setEstado_pago(false);
        orden.setPrecio(0);
        Orden savedOrden = ordenRepository.save(orden);
        System.out.println("Nueva orden creada: " + savedOrden);
        return savedOrden;
    }

    @Transactional
    public void updateOrder(Orden orden) {
        // Recalcular el precio total antes de guardar
        int totalPrice = orden.getDetalles().stream()
                .mapToInt(d -> (int) (d.getCantidad() * d.getPrecio_unitario()))
                .sum();
        orden.setPrecio(totalPrice);
        ordenRepository.save(orden);
        System.out.println("Orden actualizada con precio total: " + totalPrice);
    }

    @Transactional
    public void completeOrder(Orden orden) {
        orden.setEstado_pago(true);
        ordenRepository.save(orden);
        System.out.println("Orden completada: " + orden);
    }
}
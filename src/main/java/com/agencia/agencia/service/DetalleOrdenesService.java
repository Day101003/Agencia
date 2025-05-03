package com.agencia.agencia.service;

import com.agencia.agencia.model.Carro;
import com.agencia.agencia.model.DetalleOrdenes;
import com.agencia.agencia.model.Orden;
import com.agencia.agencia.repository.DetalleOrdenesRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class DetalleOrdenesService {

    private final DetalleOrdenesRepository detalleOrdenesRepository;

    public DetalleOrdenesService(DetalleOrdenesRepository detalleOrdenesRepository) {
        this.detalleOrdenesRepository = detalleOrdenesRepository;
    }

    @Transactional
    public DetalleOrdenes addCarToOrder(Orden orden, Carro carro) {
        DetalleOrdenes detalle = new DetalleOrdenes();
        detalle.setOrden(orden);
        detalle.setCarro(carro);
        detalle.setCantidad(1); // Assuming 1 car per entry
        detalle.setPrecio_unitario(carro.getPrecio_carro());
        
        // Añadir el detalle a la lista de detalles de la orden (sincronización bidireccional)
        orden.getDetalles().add(detalle);
        
        DetalleOrdenes savedDetalle = detalleOrdenesRepository.save(detalle);
        System.out.println("Detalle guardado en la base de datos: " + savedDetalle);
        return savedDetalle;
    }

    @Transactional
    public void removeCarFromOrder(Long detalleId) {
        System.out.println("Eliminando detalle con ID: " + detalleId);
        Optional<DetalleOrdenes> detalleOpt = detalleOrdenesRepository.findById(detalleId);
        if (detalleOpt.isPresent()) {
            detalleOrdenesRepository.deleteAll(Collections.singletonList(detalleOpt.get()));
            System.out.println("Detalle con ID " + detalleId + " eliminado correctamente");
        } else {
            System.out.println("No se encontró detalle con ID: " + detalleId);
            throw new RuntimeException("Detalle no encontrado");
        }
    }
}
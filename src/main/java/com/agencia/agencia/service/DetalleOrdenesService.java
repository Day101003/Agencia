package com.agencia.agencia.service;

import com.agencia.agencia.model.Carro;
import com.agencia.agencia.model.DetalleOrdenes;
import com.agencia.agencia.model.Orden;
import com.agencia.agencia.repository.DetalleOrdenesRepository;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenesService {

    private final DetalleOrdenesRepository detalleOrdenesRepository;

    public DetalleOrdenesService(DetalleOrdenesRepository detalleOrdenesRepository) {
        this.detalleOrdenesRepository = detalleOrdenesRepository;
    }

    // Add a car to an order
    public DetalleOrdenes addCarToOrder(Orden orden, Carro carro) {
        DetalleOrdenes detalle = new DetalleOrdenes();
        detalle.setOrden(orden);
        detalle.setCarro(carro);
        detalle.setCantidad(1); // Assuming 1 car per entry
        detalle.setPrecio_unitario(carro.getPrecio_carro());
        return detalleOrdenesRepository.save(detalle);
    }
}
package com.agencia.agencia.repository;

import com.agencia.agencia.model.DetalleOrdenes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenesRepository extends JpaRepository<DetalleOrdenes, Long> {
}
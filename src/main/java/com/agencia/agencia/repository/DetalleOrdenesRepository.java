package com.agencia.agencia.repository;

import com.agencia.agencia.model.DetalleOrdenes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DetalleOrdenesRepository extends JpaRepository<DetalleOrdenes, Long> {
    Optional<DetalleOrdenes> findById(Long id);
}
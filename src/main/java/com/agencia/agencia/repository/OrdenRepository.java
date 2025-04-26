package com.agencia.agencia.repository;

import com.agencia.agencia.model.Orden;
import com.agencia.agencia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    @Query("SELECT o FROM Orden o WHERE o.usuario = :usuario AND o.estado_pago = false ORDER BY o.fecha_orden DESC LIMIT 1")
    Optional<Orden> findTopByUsuarioAndEstadoPagoFalseOrderByFechaOrdenDesc(@Param("usuario") Usuario usuario);
}
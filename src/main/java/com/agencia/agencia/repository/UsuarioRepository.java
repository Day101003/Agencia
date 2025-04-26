package com.agencia.agencia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agencia.agencia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
}
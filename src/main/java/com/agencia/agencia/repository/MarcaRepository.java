package com.agencia.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agencia.agencia.model.Marca;

public interface MarcaRepository  extends JpaRepository<Marca, Long>{
}
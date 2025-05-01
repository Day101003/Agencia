package com.agencia.agencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agencia.agencia.model.Carro;
import com.agencia.agencia.model.Marca;

public interface CarroRepository extends JpaRepository<Carro, Integer>{
    List<Carro> findByEstado(int estado);
    List<Carro> findByMarca(Marca marca); 

}
package com.agencia.agencia.repository;

import com.agencia.agencia.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepositoryLong extends JpaRepository<Carro, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}

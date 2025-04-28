package com.agencia.agencia.service;

import com.agencia.agencia.model.Carro;
import com.agencia.agencia.repository.CarroRepositoryLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrosServiceLongImpl implements CarrosServiceLong {

    @Autowired
    private CarroRepositoryLong carroRepositoryLong;

    @Override
    public Carro consultar(long id) {
        return carroRepositoryLong.findById(id).orElse(null);  // Usamos el repositorio con el id tipo long
    }
}
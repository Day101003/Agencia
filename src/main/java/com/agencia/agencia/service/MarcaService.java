package com.agencia.agencia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.model.Marca;
import com.agencia.agencia.repository.MarcaRepository;

@Service
public class MarcaService {
    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

public Marca add(Marca marca) {
        return marcaRepository.save(marca);
    }

    public List<Marca> listarMarca(){
        return marcaRepository.findAll();
    }

    public Marca consultar(long id_marca){
        return marcaRepository.findById(id_marca).orElse(null);
    }

    public void eliminar(long id_marca){
        marcaRepository.deleteById(id_marca);
    }

    public  Marca actualizaModelo(Marca marca){
        return marcaRepository.save(marca);
    }
}





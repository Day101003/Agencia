package com.agencia.agencia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.model.Tipo;
import com.agencia.agencia.repository.TipoRepository;

@Service
public class TipoService {
    private final TipoRepository tipoRepository;

    public TipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }
    
    public Tipo add(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    public List<Tipo> listarTipo(){
        return tipoRepository.findAll();
    }

    public Tipo consultar(int id_tipo){
        return tipoRepository.findById(id_tipo).orElse(null);
    }

    public void eliminar(int id_tipo){
        tipoRepository.deleteById(id_tipo);
    }

    public  Tipo actualizaTipo(Tipo tipo){
        return tipoRepository.save(tipo);
    }

}

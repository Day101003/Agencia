package com.agencia.agencia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.model.Modelo;
import com.agencia.agencia.repository.ModeloRepository;
@Service
public class ModeloService {
   private final ModeloRepository modeloRepository;

   public ModeloService(ModeloRepository modeloRepository) {
    this.modeloRepository = modeloRepository;
   } 

    
    public Modelo add(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    public List<Modelo> listarModelo(){
        return modeloRepository.findAll();
    }

    public Modelo consultar(int id_modelo){
        return modeloRepository.findById(id_modelo).orElse(null);
    }

    public void eliminar(int id_modelo){
        modeloRepository.deleteById(id_modelo);
    }

    public  Modelo actualizaModelo(Modelo modelo){
        return modeloRepository.save(modelo);
    }
}

package com.agencia.agencia.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.model.ImagenCarros;
import com.agencia.agencia.repository.ImagenCarrosRepository;

@Service
public class ImagenCarrosService {
    private final ImagenCarrosRepository imagenCarrosRepository;

    public ImagenCarrosService(ImagenCarrosRepository imagenCarrosRepository) {
        this.imagenCarrosRepository = imagenCarrosRepository;
    }


    
    public ImagenCarros add(ImagenCarros imagenCarros) {
        return imagenCarrosRepository.save(imagenCarros);
    }

    public List<ImagenCarros> listarImagenCarros(){
        return imagenCarrosRepository.findAll();
    }

    public ImagenCarros consultar(int id_imagen){
        return imagenCarrosRepository.findById(id_imagen).orElse(null);
    }

    public void eliminar(int id_imagen){
        imagenCarrosRepository.deleteById(id_imagen);
    }

    public  ImagenCarros actualizaUsuario(ImagenCarros imagenCarros){
        return imagenCarrosRepository.save(imagenCarros);
    }
}


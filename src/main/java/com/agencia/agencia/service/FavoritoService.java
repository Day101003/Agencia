package com.agencia.agencia.service;

import com.agencia.agencia.model.Favorito;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.model.Carro;
import com.agencia.agencia.repository.FavoritoRepository;
import com.agencia.agencia.repository.CarroRepositoryLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private CarroRepositoryLong carroRepositoryLong;

    // Método para agregar un carro a los favoritos (evitando duplicados)
    public void agregarFavorito(Usuario usuario, long carroId) {
        // Buscar el carro
        Carro carro = carroRepositoryLong.findById(carroId).orElse(null);

        if (carro != null) {
            // Verificar si ya existe el favorito
            Optional<Favorito> favoritoExistente = favoritoRepository.findByUsuarioAndCarro(usuario, carro);

            if (favoritoExistente.isPresent()) {
                // Ya existe, no lo volvemos a agregar
                throw new RuntimeException("Este carro ya está en tus favoritos.");
            }

            // Crear y guardar el nuevo favorito
            Favorito favorito = new Favorito();
            favorito.setUsuario(usuario);
            favorito.setCarro(carro);
            favoritoRepository.save(favorito);
        } else {
            throw new RuntimeException("Carro no encontrado para agregar a favoritos.");
        }
    }

    // Método para eliminar un carro de los favoritos
    public void eliminarFavorito(Usuario usuario, long carroId) {
        List<Favorito> favoritos = favoritoRepository.findAllByUsuarioAndCarroIdCarro(usuario, carroId);

        if (!favoritos.isEmpty()) {
            favoritoRepository.deleteAll(favoritos);
        } else {
            throw new RuntimeException("No se encontró favorito para el usuario y carro indicado.");
        }
    }

    // Método para listar todos los favoritos de un usuario
    public List<Favorito> listarFavoritos(Usuario usuario) {
        return favoritoRepository.findByUsuario(usuario);
    }
}
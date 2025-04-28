package com.agencia.agencia.repository;

import com.agencia.agencia.model.Favorito;
import com.agencia.agencia.model.Usuario;
import com.agencia.agencia.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    
    List<Favorito> findByUsuario(Usuario usuario);

    // Para verificar si ya existe ese favorito
    Optional<Favorito> findByUsuarioAndCarro(Usuario usuario, Carro carro);

    // Para eliminar todos los favoritos del mismo carro-usuario
    @Query("SELECT f FROM Favorito f WHERE f.usuario = :usuario AND f.carro.id_carro = :carroId")
    List<Favorito> findAllByUsuarioAndCarroIdCarro(@Param("usuario") Usuario usuario, @Param("carroId") Long carroId);
}
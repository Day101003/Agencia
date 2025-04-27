package com.agencia.agencia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "favoritos")
public class Favorito {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_favorito", nullable= false)
    private long id_favorito;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "carros", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "modelo", "marca", "tipo", "imagenCarros"})
    private Carro carro;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "favoritos"})
    private Usuario usuario;

    public Favorito() {}

    public Favorito(Carro carro, Usuario usuario) {
        this.carro = carro;
        this.usuario = usuario;
    }

    public long getId_favorito() {
        return id_favorito;
    }

    public void setId_favorito(long id_favorito) {
        this.id_favorito = id_favorito;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

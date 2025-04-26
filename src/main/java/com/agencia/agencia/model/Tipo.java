package com.agencia.agencia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos")
public class Tipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name= "id_tipo", nullable= false)
    private long id_tipo;

    @Column(name= "nombre", nullable= false)
    private String nombre;
    
    public Tipo() {
    }

    public Tipo(String nombre) { 
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(long id_tipo) {
        this.id_tipo = id_tipo;
    }
}

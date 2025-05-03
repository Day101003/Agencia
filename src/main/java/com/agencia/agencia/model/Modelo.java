package com.agencia.agencia.model;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "modelos")
public class Modelo {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name= "id_modelo", nullable= false)
    private long id_modelo;
    @NotNull
    @Column(name= "nombre_modelo", nullable= false)
    private String nombre_modelo;


    public Modelo() {
    }
    public Modelo( String nombre_modelo) {
        this.nombre_modelo = nombre_modelo;
    }
    public long getId_modelo() {
        return id_modelo;
    }
    public void setId_modelo(long id_modelo) {
        this.id_modelo = id_modelo;
    }
    public String getNombre_modelo() {
        return nombre_modelo;
    }
    public void setNombre_modelo(String nombre_modelo) {
        this.nombre_modelo = nombre_modelo;
    }
    


    
}

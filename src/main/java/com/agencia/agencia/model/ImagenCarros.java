package com.agencia.agencia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "imagenCarros")
public class ImagenCarros {
    
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name= "id_imagen", nullable= false)
    private long id_imagen;

    @Column(name= "tipo", nullable= false)
    private int tipo;
    @Column(name= "ruta_imagen", nullable= false)
    private String ruta_imagen;

    
    public ImagenCarros() {
    }


    public ImagenCarros(int tipo, String ruta_imagen) {
     
        this.tipo = tipo;
        this.ruta_imagen = ruta_imagen;
    }


    public long getId_imagen() {
        return id_imagen;
    }


    public void setId_imagen(long id_imagen) {
        this.id_imagen = id_imagen;
    }


    public int getTipo() {
        return tipo;
    }


    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


    public String getRuta_imagen() {
        return ruta_imagen;
    }


    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }


    
}

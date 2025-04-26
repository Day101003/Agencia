package com.agencia.agencia.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;


    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private LocalDate fecha_registro;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ruta_imagen_usuario;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Integer tipo_usuario;

    // === Getters y Setters ===

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta_imagen_usuario() {
        return ruta_imagen_usuario;
    }

    public void setRuta_imagen_usuario(String ruta_imagen_usuario) {
        this.ruta_imagen_usuario = ruta_imagen_usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(Integer tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
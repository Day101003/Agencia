package com.agencia.agencia.dto;

public class UserProfileDto {

    private Integer id;
    private String nombre;
    private String correo;
    private String ruta_imagen_usuario;
    private Integer tipo_usuario;

    // Constructor vacío (necesario para frameworks tipo Spring)
    public UserProfileDto() {
    }

    // Constructor completo
    public UserProfileDto(Integer id, String nombre, String correo, String ruta_imagen_usuario, Integer tipo_usuario) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.ruta_imagen_usuario = ruta_imagen_usuario;
        this.tipo_usuario = tipo_usuario;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRuta_imagen_usuario() {
        return ruta_imagen_usuario;
    }

    public Integer getTipo_usuario() {
        return tipo_usuario;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setRuta_imagen_usuario(String ruta_imagen_usuario) {
        this.ruta_imagen_usuario = ruta_imagen_usuario;
    }

    public void setTipo_usuario(Integer tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
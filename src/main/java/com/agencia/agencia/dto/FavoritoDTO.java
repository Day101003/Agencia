package com.agencia.agencia.dto;

public class FavoritoDTO {

    private Long idCarro;
    private String marca;
    private String modelo;
    private Integer ano;
    private Integer precio;

    public FavoritoDTO(Long idCarro, String marca, String modelo, Integer ano, Integer precio) {
        this.idCarro = idCarro;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.precio = precio;
    }

    public Long getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(Long idCarro) {
        this.idCarro = idCarro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}
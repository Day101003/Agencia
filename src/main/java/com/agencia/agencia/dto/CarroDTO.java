// CarroDTO.java
package com.agencia.agencia.dto;

public class CarroDTO {
    private Long idCarro;
    private Integer precioCarro; // Ajustado para coincidir con lo que espera el frontend
    private Integer ano;
    private ModeloDTO modelo;
    private MarcaDTO marca;
    private String rutaImagen;

    // Getters y setters
    public Long getIdCarro() { return idCarro; }
    public void setIdCarro(Long idCarro) { this.idCarro = idCarro; }
    public Integer getPrecioCarro() { return precioCarro; }
    public void setPrecioCarro(Integer precioCarro) { this.precioCarro = precioCarro; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public ModeloDTO getModelo() { return modelo; }
    public void setModelo(ModeloDTO modelo) { this.modelo = modelo; }
    public MarcaDTO getMarca() { return marca; }
    public void setMarca(MarcaDTO marca) { this.marca = marca; }
    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
}
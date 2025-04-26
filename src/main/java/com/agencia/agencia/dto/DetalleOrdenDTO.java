// DetalleOrdenDTO.java
package com.agencia.agencia.dto;

public class DetalleOrdenDTO {
    private Long idDetalle;
    private Integer cantidad;
    private Double precioUnitario;
    private CarroDTO carro;

    // Getters y setters
    public Long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Long idDetalle) { this.idDetalle = idDetalle; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public CarroDTO getCarro() { return carro; }
    public void setCarro(CarroDTO carro) { this.carro = carro; }
}
// OrdenDTO.java
package com.agencia.agencia.dto;

import java.time.LocalDate;
import java.util.List;

public class OrdenDTO {
    private Long idOrden;
    private LocalDate fechaOrden;
    private Double precio;
    private Boolean estadoPago;
    private List<DetalleOrdenDTO> detalles;

    // Getters y setters
    public Long getIdOrden() { return idOrden; }
    public void setIdOrden(Long idOrden) { this.idOrden = idOrden; }
    public LocalDate getFechaOrden() { return fechaOrden; }
    public void setFechaOrden(LocalDate fechaOrden) { this.fechaOrden = fechaOrden; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Boolean getEstadoPago() { return estadoPago; }
    public void setEstadoPago(Boolean estadoPago) { this.estadoPago = estadoPago; }
    public List<DetalleOrdenDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrdenDTO> detalles) { this.detalles = detalles; }
}
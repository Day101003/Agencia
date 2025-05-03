package com.agencia.agencia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_ordenes")
public class DetalleOrdenes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_detalle", nullable = false)
    private long id_detalle;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private double precio_unitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carros", nullable = false)
    private Carro carro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordenes", nullable = false)
    private Orden orden;

    public DetalleOrdenes() {
    }

    public DetalleOrdenes(int cantidad, double precio_unitario, Carro carro, Orden orden) {
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.carro = carro;
        this.orden = orden;
    }

    public long getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(long id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "DetalleOrdenes{id_detalle=" + id_detalle + ", cantidad=" + cantidad +
               ", precio_unitario=" + precio_unitario + ", carro=" + (carro != null ? carro.getId_carro() : null) + "}";
    }
}
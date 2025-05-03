package com.agencia.agencia.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden", nullable = false)
    private long id_orden;

    @Column(name = "fecha_orden", nullable = false)
    private LocalDate fecha_orden;

    @Column(name = "precio", nullable = false)
    private int precio;

    @Column(name = "estado_pago", nullable = false)
    private boolean estado_pago;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios", nullable = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleOrdenes> detalles = new ArrayList<>();

    public Orden() {
    }

    public Orden(LocalDate fecha_orden, int precio, boolean estado_pago, Usuario usuario) {
        this.fecha_orden = fecha_orden;
        this.precio = precio;
        this.estado_pago = estado_pago;
        this.usuario = usuario;
    }
    public long getId_orden() {
        return id_orden;
    }

    public void setId_orden(long id_orden) {
        this.id_orden = id_orden;
    }

    public LocalDate getFecha_orden() {
        return fecha_orden;
    }

    public void setFecha_orden(LocalDate fecha_orden) {
        this.fecha_orden = fecha_orden;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(boolean estado_pago) {
        this.estado_pago = estado_pago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetalleOrdenes> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenes> detalles) {
        this.detalles = detalles;
    }

    
}

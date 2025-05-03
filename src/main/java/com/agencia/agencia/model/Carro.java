package com.agencia.agencia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "Carro")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_carro", nullable= false)
    private long id_carro;

    @Column(name = "precio_carro", nullable = false)
    private int precio_carro;

    @Column(name = "color_carro", nullable = false)
    private String color_carro;

    @Column(name = "estado", nullable = false)
    private int estado;

    @Column(name = "ano", nullable = false)
    private int ano;

    @Column(name = "combustible", nullable = false)
    private String combustible;

    @Column(name = "numero_puertas", nullable = false)
    private int numero_puertas;

    @Column(name = "observaciones", nullable = false)
    private String observaciones;

    @Column(name = "transmision", nullable = false)
    private String transmision;

    @Column(name = "kilometraje", nullable = false)
    private int kilometraje;

    @Column(name = "numero_placa", nullable = false)
    private String numero_placa;

    @Column(name = "numero_vin", nullable = false)
    private String numero_vin;

    @Column(name = "disponibilidad", nullable = false)
    private boolean disponibilidad;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "modelos", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Modelo modelo;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "marcas", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Marca marca;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipos", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Tipo tipo;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "imagen_Carros", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ImagenCarros imagenCarros;

    public Carro() {}

    public Carro(int id_carro, int precio_carro, String color_carro, int estado, int ano, String combustible,
            int numero_puertas, String observaciones, String transmision, int kilometraje, String numero_placa,
            String numero_vin, boolean disponibilidad, Modelo modelo, Marca marca, Tipo tipo, ImagenCarros imagenCarros) {
        this.id_carro = id_carro;
        this.precio_carro = precio_carro;
        this.color_carro = color_carro;
        this.estado = estado;
        this.ano = ano;
        this.combustible = combustible;
        this.numero_puertas = numero_puertas;
        this.observaciones = observaciones;
        this.transmision = transmision;
        this.kilometraje = kilometraje;
        this.numero_placa = numero_placa;
        this.numero_vin = numero_vin;
        this.disponibilidad = disponibilidad;
        this.modelo = modelo;
        this.marca = marca;
        this.tipo = tipo;
        this.imagenCarros = imagenCarros;
    }

    public long getId_carro() {
        return id_carro;
    }

    public void setId_carro(int id_carro) {
        this.id_carro = id_carro;
    }

    public int getPrecio_carro() {
        return precio_carro;
    }

    public void setPrecio_carro(int precio_carro) {
        this.precio_carro = precio_carro;
    }

    public String getColor_carro() {
        return color_carro;
    }

    public void setColor_carro(String color_carro) {
        this.color_carro = color_carro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public int getNumero_puertas() {
        return numero_puertas;
    }

    public void setNumero_puertas(int numero_puertas) {
        this.numero_puertas = numero_puertas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getNumero_placa() {
        return numero_placa;
    }

    public void setNumero_placa(String numero_placa) {
        this.numero_placa = numero_placa;
    }

    public String getNumero_vin() {
        return numero_vin;
    }

    public void setNumero_vin(String numero_vin) {
        this.numero_vin = numero_vin;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public ImagenCarros getImagenCarros() {
        return imagenCarros;
    }

    public void setImagenCarros(ImagenCarros imagenCarros) {
        this.imagenCarros = imagenCarros;
    }
}
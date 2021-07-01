package com.example.agenciaviajes.modelo;

import java.io.Serializable;

public class Estancia implements Serializable {

    private int codigoEstancia;
    private String pension;
    private String fechaEntrada;
    private String fechaSalida;
    private int codigoHotel;

    public Estancia() {
    }

    public Estancia(String pension, String fechaEntrada, String fechaSalida, int codigoHotel) {
        this.pension = pension;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.codigoHotel = codigoHotel;
    }

    public int getCodigoEstancia() {
        return codigoEstancia;
    }

    public void setCodigoEstancia(int codigoEstancia) {
        this.codigoEstancia = codigoEstancia;
    }

    public String getPension() {
        return pension;
    }

    public void setPension(String pension) {
        this.pension = pension;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getCodigoHotel() {
        return codigoHotel;
    }

    public void setCodigoHotel(int codigoHotel) {
        this.codigoHotel = codigoHotel;
    }
}

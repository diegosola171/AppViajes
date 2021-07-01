package com.example.agenciaviajes.modelo;

import java.io.Serializable;

public class ViajeContratado implements Serializable {

    private int codigoViaje;
    private int codigoTurista;
    private int codigoSucursal;
    private int codigoEstancia;

    public ViajeContratado() {
    }

    public ViajeContratado(int codigoTurista, int codigoSucursal, int codigoEstancia) {
        this.codigoTurista = codigoTurista;
        this.codigoSucursal = codigoSucursal;
        this.codigoEstancia = codigoEstancia;
    }

    public int getCodigoViaje() {
        return codigoViaje;
    }

    public void setCodigoViaje(int codigoViaje) {
        this.codigoViaje = codigoViaje;
    }

    public int getCodigoTurista() {
        return codigoTurista;
    }

    public void setCodigoTurista(int codigoTurista) {
        this.codigoTurista = codigoTurista;
    }

    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(int codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public int getCodigoEstancia() {
        return codigoEstancia;
    }

    public void setCodigoEstancia(int codigoEstancia) {
        this.codigoEstancia = codigoEstancia;
    }

    @Override
    public String toString() {
        return "codigoViaje=" + codigoViaje +
                ", codigoTurista=" + codigoTurista +
                ", codigoSucursal=" + codigoSucursal +
                ", codigoEstancia=" + codigoEstancia;
    }
}

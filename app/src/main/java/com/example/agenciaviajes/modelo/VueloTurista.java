package com.example.agenciaviajes.modelo;

import java.io.Serializable;

public class VueloTurista implements Serializable {

    private int codigoVuelo;
    private String clase;
    private int numeroVuelo;
    private int codigoViaje;

    public VueloTurista() {
    }

    public VueloTurista(String clase, int numeroVuelo, int codigoViaje) {
        this.clase = clase;
        this.numeroVuelo = numeroVuelo;
        this.codigoViaje = codigoViaje;
    }

    public int getCodigoVuelo() {
        return codigoVuelo;
    }

    public void setCodigoVuelo(int codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public int getNumeroVuelo() {
        return numeroVuelo;
    }

    public void setNumeroVuelo(int numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
    }

    public int getCodigoViaje() {
        return codigoViaje;
    }

    public void setCodigoViaje(int codigoViaje) {
        this.codigoViaje = codigoViaje;
    }

    @Override
    public String toString() {
        return "Clase " + clase + " - Número de Vuelo: " + numeroVuelo +
                " - Código de Viaje: " + codigoViaje;
    }
}

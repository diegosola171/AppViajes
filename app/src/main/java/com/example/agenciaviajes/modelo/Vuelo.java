package com.example.agenciaviajes.modelo;

import java.io.Serializable;

public class Vuelo implements Serializable {

    private int NumeroVuelo;
    private String fechaVuelo;
    private String horaVuelo;
    private String origenVuelo;
    private String destinoVuelo;
    private int plazasTotales;
    private int plazasTurista;

    public Vuelo() {
    }

    public Vuelo(String fechaVuelo, String horaVuelo, String origenVuelo, String destinoVuelo, int plazasTotales, int plazasTurista) {
        this.fechaVuelo = fechaVuelo;
        this.horaVuelo = horaVuelo;
        this.origenVuelo = origenVuelo;
        this.destinoVuelo = destinoVuelo;
        this.plazasTotales = plazasTotales;
        this.plazasTurista = plazasTurista;
    }

    public int getNumeroVuelo() {
        return NumeroVuelo;
    }

    public void setNumeroVuelo(int numeroVuelo) {
        NumeroVuelo = numeroVuelo;
    }

    public String getFechaVuelo() {
        return fechaVuelo;
    }

    public void setFechaVuelo(String fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public String getHoraVuelo() {
        return horaVuelo;
    }

    public void setHoraVuelo(String horaVuelo) {
        this.horaVuelo = horaVuelo;
    }

    public String getOrigenVuelo() {
        return origenVuelo;
    }

    public void setOrigenVuelo(String origenVuelo) {
        this.origenVuelo = origenVuelo;
    }

    public String getDestinoVuelo() {
        return destinoVuelo;
    }

    public void setDestinoVuelo(String destinoVuelo) {
        this.destinoVuelo = destinoVuelo;
    }

    public int getPlazasTotales() {
        return plazasTotales;
    }

    public void setPlazasTotales(int plazasTotales) {
        this.plazasTotales = plazasTotales;
    }

    public int getPlazasTurista() {
        return plazasTurista;
    }

    public void setPlazasTurista(int plazasTurista) {
        this.plazasTurista = plazasTurista;
    }

    @Override
    public String toString() {
        return fechaVuelo + " ( " + horaVuelo + " ) : De " + origenVuelo + " a " + destinoVuelo;
    }
}

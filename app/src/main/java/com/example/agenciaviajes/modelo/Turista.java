package com.example.agenciaviajes.modelo;

import java.io.Serializable;

public class Turista implements Serializable {
    private int codigoTurista;
    private String nombreTurista;
    private String apellidosTurista;
    private String direccionTurista;
    private String telefonoTurista;

    public Turista() {
    }

    public Turista(int codigoTurista, String nombreTurista, String apellidosTurista, String direccionTurista, String telefonoTurista) {
        this.codigoTurista = codigoTurista;
        this.nombreTurista = nombreTurista;
        this.apellidosTurista = apellidosTurista;
        this.direccionTurista = direccionTurista;
        this.telefonoTurista = telefonoTurista;
    }

    public int getCodigoTurista() {
        return codigoTurista;
    }

    public void setCodigoTurista(int codigoTurista) {
        this.codigoTurista = codigoTurista;
    }

    public String getNombreTurista() {
        return nombreTurista;
    }

    public void setNombreTurista(String nombreTurista) {
        this.nombreTurista = nombreTurista;
    }

    public String getApellidosTurista() {
        return apellidosTurista;
    }

    public void setApellidosTurista(String apellidosTurista) {
        this.apellidosTurista = apellidosTurista;
    }

    public String getDireccionTurista() {
        return direccionTurista;
    }

    public void setDireccionTurista(String direccionTurista) {
        this.direccionTurista = direccionTurista;
    }

    public String getTelefonoTurista() {
        return telefonoTurista;
    }

    public void setTelefonoTurista(String telefonoTurista) {
        this.telefonoTurista = telefonoTurista;
    }
}
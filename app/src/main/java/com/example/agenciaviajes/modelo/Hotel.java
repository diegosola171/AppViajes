package com.example.agenciaviajes.modelo;

import java.io.Serializable;

public class Hotel implements Serializable {

    private int codigoHotel;
    private String nombreHotel;
    private String direccionHotel;
    private String ciudadHotel;
    private String telefonoHotel;
    private int plazasHotel;

    public Hotel() {
    }

    public Hotel(String nombreHotel, String direccionHotel, String ciudadHotel, String telefonoHotel, int plazasHotel) {
        this.nombreHotel = nombreHotel;
        this.direccionHotel = direccionHotel;
        this.ciudadHotel = ciudadHotel;
        this.telefonoHotel = telefonoHotel;
        this.plazasHotel = plazasHotel;
    }

    public int getCodigoHotel() {
        return codigoHotel;
    }

    public void setCodigoHotel(int codigoHotel) {
        this.codigoHotel = codigoHotel;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }

    public String getDireccionHotel() {
        return direccionHotel;
    }

    public void setDireccionHotel(String direccionHotel) {
        this.direccionHotel = direccionHotel;
    }

    public String getCiudadHotel() {
        return ciudadHotel;
    }

    public void setCiudadHotel(String ciudadHotel) {
        this.ciudadHotel = ciudadHotel;
    }

    public String getTelefonoHotel() {
        return telefonoHotel;
    }

    public void setTelefonoHotel(String telefonoHotel) {
        this.telefonoHotel = telefonoHotel;
    }

    public int getPlazasHotel() {
        return plazasHotel;
    }

    public void setPlazasHotel(int plazasHotel) {
        this.plazasHotel = plazasHotel;
    }

    @Override
    public String toString() {
        return nombreHotel;
    }
}

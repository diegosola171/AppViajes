package com.example.agenciaviajes.modelo;

import java.io.Serializable;

public class Sucursal implements Serializable {

    private int codigoSucursal;
    private String direccionSucursal;
    private String telefonoSucursal;

    public Sucursal() {
    }

    public Sucursal(String direccionSucursal, String telefonoSucursal) {
        this.direccionSucursal = direccionSucursal;
        this.telefonoSucursal = telefonoSucursal;
    }

    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(int codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    public String getTelefonoSucursal() {
        return telefonoSucursal;
    }

    public void setTelefonoSucursal(String telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }

    @Override
    public String toString() {
        return direccionSucursal;
    }
}

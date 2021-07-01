package com.example.agenciaviajes.db;

import android.content.Context;

import com.example.agenciaviajes.modelo.Estancia;
import com.example.agenciaviajes.modelo.Hotel;
import com.example.agenciaviajes.modelo.Sucursal;
import com.example.agenciaviajes.modelo.Turista;
import com.example.agenciaviajes.modelo.ViajeContratado;
import com.example.agenciaviajes.modelo.Vuelo;
import com.example.agenciaviajes.modelo.VueloTurista;

import java.util.ArrayList;

public class DBHelper {

    private DBAdapter dbAdapter;

    public DBHelper(Context context){
        dbAdapter = new DBAdapter(context);
    }

    public boolean isOpen(){
        return dbAdapter.isOpen();
    }

    public ArrayList<Hotel> obtenerHoteles(){
        dbAdapter.open();
        ArrayList<Hotel> hoteles = dbAdapter.obtenerHoteles();
        dbAdapter.close();
        return hoteles;
    }

    public ArrayList<Sucursal> obtenerSucursales(){
        dbAdapter.open();
        ArrayList<Sucursal> sucursales = dbAdapter.obtenerSucursales();
        dbAdapter.close();
        return sucursales;
    }

    public ArrayList<Vuelo> obtenerVuelos(){
        dbAdapter.open();
        ArrayList<Vuelo> vuelos = dbAdapter.obtenerVuelos();
        dbAdapter.close();
        return vuelos;
    }

    public void actualizarTurista(Turista turista){
        dbAdapter.open();
        dbAdapter.actualizarTurista(turista);
        dbAdapter.close();
    }

    public Turista obtenerTurista(){
        dbAdapter.open();
        Turista turista = dbAdapter.obtenerTurista();
        dbAdapter.close();
        return turista;
    }

    public boolean actualizarHotel(int codigoHotelAntes, int codigoHotelNueva){
        dbAdapter.open();
        Boolean result = dbAdapter.actualizarHotel(codigoHotelAntes, codigoHotelNueva);
        dbAdapter.close();
        return result;
    }

    /*public Hotel obtenerHotel(int codigoHotel){
        dbAdapter.open();
        Hotel hotel = dbAdapter.obtenerHotel(codigoHotel);
        dbAdapter.close();
        return hotel;
    }*/

    public boolean actualizarVuelo(int codigoVueloAntes, int codigoVueloNueva){
        dbAdapter.open();
        Boolean result = dbAdapter.actualizarVuelo(codigoVueloAntes, codigoVueloNueva);
        dbAdapter.close();
        return result;
    }

    /*public Vuelo obtenerVuelo(int codigoVuelo){
        dbAdapter.open();
        Vuelo vuelo = dbAdapter.obtenerVuelo(codigoVuelo);
        dbAdapter.close();
        return vuelo;
    }*/

    public int insertarEstancia(Estancia estancia){
        dbAdapter.open();
        int codigo = dbAdapter.insertarEstancia(estancia);
        dbAdapter.close();
        return codigo;
    }

    public void actualizarEstancia(Estancia estancia){
        dbAdapter.open();
        dbAdapter.actualizarEstancia(estancia);
        dbAdapter.close();
    }

    public void eliminarEstancia(Estancia estancia){
        dbAdapter.open();
        dbAdapter.eliminarEstancia(estancia);
        dbAdapter.close();
    }

    public Estancia obtenerEstancia(int codigo){
        dbAdapter.open();
        Estancia estancia = dbAdapter.obtenerEstancia(codigo);
        dbAdapter.close();
        return estancia;
    }

    public ArrayList<Estancia> obtenerEstancias(){
        dbAdapter.open();
        ArrayList<Estancia> estancias = dbAdapter.obtenerEstancias();
        dbAdapter.close();
        return estancias;
    }

    public void insertarViajeContratado(ViajeContratado viajeContratado){
        dbAdapter.open();
        dbAdapter.insertarViajeContratado(viajeContratado);
        dbAdapter.close();
    }

    public void actualizarViajeContratado(ViajeContratado viajeContratado){
        dbAdapter.open();
        dbAdapter.actualizarViajeContratado(viajeContratado);
        dbAdapter.close();
    }

    public void eliminarViajeContratado(ViajeContratado viajeContratado){
        dbAdapter.open();
        dbAdapter.eliminarViajeContratado(viajeContratado);
        dbAdapter.close();
    }

    public void eliminarViajeContratados(){
        dbAdapter.open();
        dbAdapter.eliminarViajeContratados();
        dbAdapter.close();
    }

    public ViajeContratado obtenerViajeContratado(int codigo){
        dbAdapter.open();
        ViajeContratado viajeContratado = dbAdapter.obtenerViajeContratado(codigo);
        dbAdapter.close();
        return viajeContratado;
    }

    public ArrayList<ViajeContratado> obtenerViajeContratados(){
        dbAdapter.open();
        ArrayList<ViajeContratado> viajeContratados = dbAdapter.obtenerViajeContratados();
        dbAdapter.close();
        return viajeContratados;
    }

    public void insertarVueloTurista(VueloTurista vueloTurista){
        dbAdapter.open();
        dbAdapter.insertarVueloTurista(vueloTurista);
        dbAdapter.close();
    }

    public void actualizarVueloTurista(VueloTurista vueloTurista){
        dbAdapter.open();
        dbAdapter.actualizarVueloTurista(vueloTurista);
        dbAdapter.close();
    }

    public void eliminarVueloTurista(VueloTurista vueloTurista){
        dbAdapter.open();
        dbAdapter.eliminarVueloTurista(vueloTurista);
        dbAdapter.close();
    }

    public void eliminarVueloTuristas(){
        dbAdapter.open();
        dbAdapter.eliminarVueloTuristas();
        dbAdapter.close();
    }

    public VueloTurista obtenerVueloTurista(int codigo){
        dbAdapter.open();
        VueloTurista vueloTurista = dbAdapter.obtenerVueloTurista(codigo);
        dbAdapter.close();
        return vueloTurista;
    }

    public ArrayList<VueloTurista> obtenerVueloTuristas(){
        dbAdapter.open();
        ArrayList<VueloTurista> vueloTuristas = dbAdapter.obtenerVueloTuristas();
        dbAdapter.close();
        return vueloTuristas;
    }
}

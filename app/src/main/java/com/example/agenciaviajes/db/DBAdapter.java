package com.example.agenciaviajes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.agenciaviajes.modelo.Hotel;
import com.example.agenciaviajes.modelo.Sucursal;
import com.example.agenciaviajes.modelo.Turista;
import com.example.agenciaviajes.modelo.Vuelo;
import com.example.agenciaviajes.modelo.VueloTurista;
import com.example.agenciaviajes.modelo.Estancia;
import com.example.agenciaviajes.modelo.ViajeContratado;

import java.util.ArrayList;

public class DBAdapter {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB_AgenciaViajes";

    //    Nombre de las Tablas
    private static final String TABLE_TURISTA = "Turista";
    private static final String TABLE_ESTANCIA = "Estancia";
    private static final String TABLE_VIAJECONTRATADO = "ViajeContratado";
    private static final String TABLE_VUELOTURISTA = "VueloTurista";
    private static final String TABLE_HOTEL = "Hotel";
    private static final String TABLE_SUCURSAL = "Sucursal";
    private static final String TABLE_VUELO = "Vuelo";

    //    Nombres de los campos por cada tabla
    private static final String KEY_CODTURISTA = "codigoTurista";
    private static final String KEY_NOMTURISTA = "nombreTurista";
    private static final String KEY_APETURISTA = "apellidosTurista";
    private static final String KEY_DIRTURISTA = "direccionTurista";
    private static final String KEY_TELTURISTA = "telefonoTurista";

    private static final String KEY_CODESTANCIA = "codigoEstancia";
    private static final String KEY_PENESTANCIA = "pension";
    private static final String KEY_ENTESTANCIA = "fechaEntrada";
    private static final String KEY_SALESTANCIA = "fechaSalida";
    private static final String KEY_CODHOTEL = "codigoHotel";

    private static final String KEY_CODVIAJE = "codigoViaje";
    private static final String KEY_CODSUCURSAL = "codigoSucursal";

    private static final String KEY_CODVUELO = "codigoVuelo";
    private static final String KEY_CLASE = "clase";
    private static final String KEY_NUMVUELO = "numeroVuelo";

    private static final String KEY_NOMHOTEL = "nombreHotel";
    private static final String KEY_DIRHOTEL = "direccionHotel";
    private static final String KEY_CIUHOTEL = "ciudadHotel";
    private static final String KEY_TELHOTEL = "telefonoHotel";
    private static final String KEY_PLAHOTEL = "plazasHotel";

    private static final String KEY_DIRSUCURSAL = "direccionSucursal";
    private static final String KEY_TELSUCURSAL = "telefonoSucursal";

    private static final String KEY_FECVUELO = "fechaVuelo";
    private static final String KEY_HORVUELO = "horaVuelo";
    private static final String KEY_ORIVUELO = "origenVuelo";
    private static final String KEY_DESVUELO = "destinoVuelo";
    private static final String KEY_PTOVUELO = "plazasTotales";
    private static final String KEY_PTUVUELO = "plazasTurista";

    private static final String TABLE_CREATE_VUELO = "CREATE TABLE " + TABLE_VUELO +
            "(" +
            KEY_NUMVUELO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_FECVUELO + " TEXT NOT NULL, " +
            KEY_HORVUELO + " TEXT NOT NULL, " +
            KEY_ORIVUELO + " TEXT NOT NULL, " +
            KEY_DESVUELO + " TEXT NOT NULL, " +
            KEY_PTOVUELO + " INTEGER NOT NULL, " +
            KEY_PTUVUELO + " INTEGER NOT NULL " +
            ")";

    private static final String TABLE_CREATE_SUCURSAL = "CREATE TABLE " + TABLE_SUCURSAL +
            "(" +
            KEY_CODSUCURSAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_DIRSUCURSAL + " TEXT NOT NULL, " +
            KEY_TELSUCURSAL + " TEXT NOT NULL " +
            ")";

    private static final String TABLE_CREATE_HOTEL = "CREATE TABLE " + TABLE_HOTEL +
            "(" +
            KEY_CODHOTEL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NOMHOTEL + " TEXT NOT NULL, " +
            KEY_DIRHOTEL + " TEXT NOT NULL, " +
            KEY_CIUHOTEL + " TEXT NOT NULL, " +
            KEY_TELHOTEL + " TEXT NOT NULL, " +
            KEY_PLAHOTEL + " INTEGER NOT NULL " +
            ")";

    private static final String TABLE_CREATE_TURISTA = "CREATE TABLE " + TABLE_TURISTA +
            "(" +
            KEY_CODTURISTA + " INTEGER PRIMARY KEY, " +
            KEY_NOMTURISTA + " TEXT NOT NULL, " +
            KEY_APETURISTA + " TEXT NOT NULL, " +
            KEY_DIRTURISTA + " TEXT NOT NULL, " +
            KEY_TELTURISTA + " TEXT NOT NULL " +
            ")";

    private static final String TABLE_CREATE_ESTANCIA = "CREATE TABLE " + TABLE_ESTANCIA +
            "(" +
            KEY_CODESTANCIA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_PENESTANCIA + " TEXT NOT NULL, " +
            KEY_ENTESTANCIA + " TEXT NOT NULL, " +
            KEY_SALESTANCIA + " TEXT NOT NULL, " +
            KEY_CODHOTEL + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + KEY_CODHOTEL + ") REFERENCES " + TABLE_HOTEL + "(" + KEY_CODHOTEL + ")" +
            ")";

    private static final String TABLE_CREATE_VIAJECONTRATADO = "CREATE TABLE " + TABLE_VIAJECONTRATADO +
            "(" +
            KEY_CODVIAJE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_CODTURISTA + " INTEGER NOT NULL, " +
            KEY_CODSUCURSAL + " INTEGER NOT NULL, " +
            KEY_CODESTANCIA + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + KEY_CODTURISTA + ") REFERENCES " + TABLE_TURISTA + "(" + KEY_CODTURISTA + ")," +
            "FOREIGN KEY(" + KEY_CODSUCURSAL + ") REFERENCES " + TABLE_SUCURSAL + "(" + KEY_CODSUCURSAL + ")," +
            "FOREIGN KEY(" + KEY_CODESTANCIA + ") REFERENCES " + TABLE_ESTANCIA + "(" + KEY_CODESTANCIA + ") ON DELETE CASCADE" +
            ")";

    private static final String TABLE_CREATE_VUELOTURISTA = "CREATE TABLE " + TABLE_VUELOTURISTA +
            "(" +
            KEY_CODVUELO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_CLASE + " TEXT NOT NULL, " +
            KEY_NUMVUELO + " INTEGER NOT NULL, " +
            KEY_CODVIAJE + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + KEY_NUMVUELO + ") REFERENCES " + TABLE_VUELO + "(" + KEY_NUMVUELO + ")," +
            "FOREIGN KEY(" + KEY_CODVIAJE + ") REFERENCES " + TABLE_VIAJECONTRATADO + "(" + KEY_CODVIAJE + ")" +
            ")";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private static Context context;

    public DBAdapter(Context context){
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE_HOTEL);
            db.execSQL(TABLE_CREATE_VUELO);
            db.execSQL(TABLE_CREATE_SUCURSAL);
            db.execSQL(TABLE_CREATE_TURISTA);
            db.execSQL(TABLE_CREATE_ESTANCIA);
            db.execSQL(TABLE_CREATE_VIAJECONTRATADO);
            db.execSQL(TABLE_CREATE_VUELOTURISTA);

            Turista turista = new Turista(0,"", "", "", "");
            ContentValues values = new ContentValues();
            values.put(KEY_CODTURISTA, turista.getCodigoTurista());
            values.put(KEY_NOMTURISTA, turista.getNombreTurista());
            values.put(KEY_APETURISTA, turista.getApellidosTurista());
            values.put(KEY_DIRTURISTA, turista.getDireccionTurista());
            values.put(KEY_TELTURISTA, turista.getTelefonoTurista());
            db.insert(TABLE_TURISTA, null, values);

            Hotel hotel1 = new Hotel("Hotel Realeza", "Av. Las Flores 391", "Trujillo", "975448456", 50);
            ContentValues valuesh1 = new ContentValues();
            valuesh1.put(KEY_NOMHOTEL, hotel1.getNombreHotel());
            valuesh1.put(KEY_DIRHOTEL, hotel1.getDireccionHotel());
            valuesh1.put(KEY_CIUHOTEL, hotel1.getCiudadHotel());
            valuesh1.put(KEY_TELHOTEL, hotel1.getTelefonoHotel());
            valuesh1.put(KEY_PLAHOTEL, hotel1.getPlazasHotel());
            db.insert(TABLE_HOTEL, null, valuesh1);

            Hotel hotel2 = new Hotel("Glamho Hotel", "Av. Los Geranios 110", "Chimbote", "986557423", 50);
            ContentValues valuesh2 = new ContentValues();
            valuesh2.put(KEY_NOMHOTEL, hotel2.getNombreHotel());
            valuesh2.put(KEY_DIRHOTEL, hotel2.getDireccionHotel());
            valuesh2.put(KEY_CIUHOTEL, hotel2.getCiudadHotel());
            valuesh2.put(KEY_TELHOTEL, hotel2.getTelefonoHotel());
            valuesh2.put(KEY_PLAHOTEL, hotel2.getPlazasHotel());
            db.insert(TABLE_HOTEL, null, valuesh2);

            Hotel hotel3 = new Hotel("Passarela Hotel", "Av. Las Flores 115", "Lima", "998787510", 50);
            ContentValues valuesh3 = new ContentValues();
            valuesh3.put(KEY_NOMHOTEL, hotel3.getNombreHotel());
            valuesh3.put(KEY_DIRHOTEL, hotel3.getDireccionHotel());
            valuesh3.put(KEY_CIUHOTEL, hotel3.getCiudadHotel());
            valuesh3.put(KEY_TELHOTEL, hotel3.getTelefonoHotel());
            valuesh3.put(KEY_PLAHOTEL, hotel3.getPlazasHotel());
            db.insert(TABLE_HOTEL, null, valuesh3);

            Hotel hotel4 = new Hotel("Oz Resort", "Av. El Colrrobarrutia 20", "Ica", "975411580", 50);
            ContentValues valuesh4 = new ContentValues();
            valuesh4.put(KEY_NOMHOTEL, hotel4.getNombreHotel());
            valuesh4.put(KEY_DIRHOTEL, hotel4.getDireccionHotel());
            valuesh4.put(KEY_CIUHOTEL, hotel4.getCiudadHotel());
            valuesh4.put(KEY_TELHOTEL, hotel4.getTelefonoHotel());
            valuesh4.put(KEY_PLAHOTEL, hotel4.getPlazasHotel());
            db.insert(TABLE_HOTEL, null, valuesh4);

            Hotel hotel5 = new Hotel("Queen Hotel", "Av. Los ángeles 510", "Trujillo", "915228797", 50);
            ContentValues valuesh5 = new ContentValues();
            valuesh5.put(KEY_NOMHOTEL, hotel5.getNombreHotel());
            valuesh5.put(KEY_DIRHOTEL, hotel5.getDireccionHotel());
            valuesh5.put(KEY_CIUHOTEL, hotel5.getCiudadHotel());
            valuesh5.put(KEY_TELHOTEL, hotel5.getTelefonoHotel());
            valuesh5.put(KEY_PLAHOTEL, hotel5.getPlazasHotel());
            db.insert(TABLE_HOTEL, null, valuesh5);

            Hotel hotel6 = new Hotel("Hotel El Bosque", "Av. Los Palacios 220", "Chimbote", "911254898", 50);
            ContentValues valuesh6 = new ContentValues();
            valuesh6.put(KEY_NOMHOTEL, hotel6.getNombreHotel());
            valuesh6.put(KEY_DIRHOTEL, hotel6.getDireccionHotel());
            valuesh6.put(KEY_CIUHOTEL, hotel6.getCiudadHotel());
            valuesh6.put(KEY_TELHOTEL, hotel6.getTelefonoHotel());
            valuesh6.put(KEY_PLAHOTEL, hotel6.getPlazasHotel());
            db.insert(TABLE_HOTEL, null, valuesh6);

            Hotel hotel7 = new Hotel("Tea Tree Hotel", "Av. 03 de Octubre 130", "Lima", "999785454", 50);
            ContentValues valuesh7 = new ContentValues();
            valuesh7.put(KEY_NOMHOTEL, hotel7.getNombreHotel());
            valuesh7.put(KEY_DIRHOTEL, hotel7.getDireccionHotel());
            valuesh7.put(KEY_CIUHOTEL, hotel7.getCiudadHotel());
            valuesh7.put(KEY_TELHOTEL, hotel7.getTelefonoHotel());
            valuesh7.put(KEY_PLAHOTEL, hotel7.getPlazasHotel());
            db.insert(TABLE_HOTEL, null, valuesh7);

            Hotel hotel8 = new Hotel("Candel Resort", "Av. Los Girasoles312", "Ica", "974889951", 50);
            ContentValues valuesh8 = new ContentValues();
            valuesh8.put(KEY_NOMHOTEL, hotel8.getNombreHotel());
            valuesh8.put(KEY_DIRHOTEL, hotel8.getDireccionHotel());
            valuesh8.put(KEY_CIUHOTEL, hotel8.getCiudadHotel());
            valuesh8.put(KEY_TELHOTEL, hotel8.getTelefonoHotel());
            valuesh8.put(KEY_PLAHOTEL, hotel8.getPlazasHotel());
            db.insert(TABLE_HOTEL, null, valuesh8);

            Sucursal sucursal1 = new Sucursal("Av. San Felipe 120", "985460218");
            ContentValues valuess1 = new ContentValues();
            valuess1.put(KEY_DIRSUCURSAL, sucursal1.getDireccionSucursal());
            valuess1.put(KEY_TELSUCURSAL, sucursal1.getTelefonoSucursal());
            db.insert(TABLE_SUCURSAL, null, valuess1);

            Sucursal sucursal2 = new Sucursal("Av. Mariscal Cáceres 50", "977458622");
            ContentValues valuess2 = new ContentValues();
            valuess2.put(KEY_DIRSUCURSAL, sucursal2.getDireccionSucursal());
            valuess2.put(KEY_TELSUCURSAL, sucursal2.getTelefonoSucursal());
            db.insert(TABLE_SUCURSAL, null, valuess2);

            Sucursal sucursal3 = new Sucursal("Av. 28 de Julio 113", "948756664");
            ContentValues valuess3 = new ContentValues();
            valuess3.put(KEY_DIRSUCURSAL, sucursal3.getDireccionSucursal());
            valuess3.put(KEY_TELSUCURSAL, sucursal3.getTelefonoSucursal());
            db.insert(TABLE_SUCURSAL, null, valuess3);

            Vuelo vuelo1 = new Vuelo("2021-06-26", "10:00:00", "Lima", "Trujillo", 100, 75);
            ContentValues valuesv1 = new ContentValues();
            valuesv1.put(KEY_FECVUELO, vuelo1.getFechaVuelo());
            valuesv1.put(KEY_HORVUELO, vuelo1.getHoraVuelo());
            valuesv1.put(KEY_ORIVUELO, vuelo1.getOrigenVuelo());
            valuesv1.put(KEY_DESVUELO, vuelo1.getDestinoVuelo());
            valuesv1.put(KEY_PTOVUELO, vuelo1.getPlazasTotales());
            valuesv1.put(KEY_PTUVUELO, vuelo1.getPlazasTurista());
            db.insert(TABLE_VUELO, null, valuesv1);

            Vuelo vuelo2 = new Vuelo("2021-06-26", "15:00:00", "Trujillo", "Ica", 100, 75);
            ContentValues valuesv2 = new ContentValues();
            valuesv2.put(KEY_FECVUELO, vuelo2.getFechaVuelo());
            valuesv2.put(KEY_HORVUELO, vuelo2.getHoraVuelo());
            valuesv2.put(KEY_ORIVUELO, vuelo2.getOrigenVuelo());
            valuesv2.put(KEY_DESVUELO, vuelo2.getDestinoVuelo());
            valuesv2.put(KEY_PTOVUELO, vuelo2.getPlazasTotales());
            valuesv2.put(KEY_PTUVUELO, vuelo2.getPlazasTurista());
            db.insert(TABLE_VUELO, null, valuesv2);

            Vuelo vuelo3 = new Vuelo("2021-06-26", "20:00:00", "Ica", "Lima", 100, 75);
            ContentValues valuesv3 = new ContentValues();
            valuesv3.put(KEY_FECVUELO, vuelo3.getFechaVuelo());
            valuesv3.put(KEY_HORVUELO, vuelo3.getHoraVuelo());
            valuesv3.put(KEY_ORIVUELO, vuelo3.getOrigenVuelo());
            valuesv3.put(KEY_DESVUELO, vuelo3.getDestinoVuelo());
            valuesv3.put(KEY_PTOVUELO, vuelo3.getPlazasTotales());
            valuesv3.put(KEY_PTUVUELO, vuelo3.getPlazasTurista());
            db.insert(TABLE_VUELO, null, valuesv3);

            Vuelo vuelo4 = new Vuelo("2021-06-27", "10:00:00", "Lima", "Chimbote", 100, 75);
            ContentValues valuesv4 = new ContentValues();
            valuesv4.put(KEY_FECVUELO, vuelo4.getFechaVuelo());
            valuesv4.put(KEY_HORVUELO, vuelo4.getHoraVuelo());
            valuesv4.put(KEY_ORIVUELO, vuelo4.getOrigenVuelo());
            valuesv4.put(KEY_DESVUELO, vuelo4.getDestinoVuelo());
            valuesv4.put(KEY_PTOVUELO, vuelo4.getPlazasTotales());
            valuesv4.put(KEY_PTUVUELO, vuelo4.getPlazasTurista());
            db.insert(TABLE_VUELO, null, valuesv4);

            Vuelo vuelo5 = new Vuelo("2021-06-27", "15:00:00", "Chimbote", "Ica", 100, 75);
            ContentValues valuesv5 = new ContentValues();
            valuesv5.put(KEY_FECVUELO, vuelo5.getFechaVuelo());
            valuesv5.put(KEY_HORVUELO, vuelo5.getHoraVuelo());
            valuesv5.put(KEY_ORIVUELO, vuelo5.getOrigenVuelo());
            valuesv5.put(KEY_DESVUELO, vuelo5.getDestinoVuelo());
            valuesv5.put(KEY_PTOVUELO, vuelo5.getPlazasTotales());
            valuesv5.put(KEY_PTUVUELO, vuelo5.getPlazasTurista());
            db.insert(TABLE_VUELO, null, valuesv5);

            Vuelo vuelo6 = new Vuelo("2021-06-27", "20:00:00", "Ica", "Trujillo", 100, 75);
            ContentValues valuesv6 = new ContentValues();
            valuesv6.put(KEY_FECVUELO, vuelo6.getFechaVuelo());
            valuesv6.put(KEY_HORVUELO, vuelo6.getHoraVuelo());
            valuesv6.put(KEY_ORIVUELO, vuelo6.getOrigenVuelo());
            valuesv6.put(KEY_DESVUELO, vuelo6.getDestinoVuelo());
            valuesv6.put(KEY_PTOVUELO, vuelo6.getPlazasTotales());
            valuesv6.put(KEY_PTUVUELO, vuelo6.getPlazasTurista());
            db.insert(TABLE_VUELO, null, valuesv6);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TURISTA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTANCIA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIAJECONTRATADO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VUELOTURISTA);
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLiteException {
        try {
            db = databaseHelper.getWritableDatabase();
        } catch (SQLiteException e){
            Toast.makeText(context, "Error al Abrir la Base de Datos", Toast.LENGTH_LONG).show();
        }
        return this;
    }

    public boolean isCreated(){
        if (db != null){
            return db.isOpen();
        }
        return false;
    }

    public boolean isOpen(){
        if (db == null){
            return false;
        }
        return db.isOpen();
    }

    public void close(){
        databaseHelper.close();
        db.close();
    }

    public ArrayList<Hotel> obtenerHoteles(){
        ArrayList<Hotel> hoteles = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_HOTEL;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Hotel hotel = new Hotel();
                    hotel.setCodigoHotel(cursor.getInt(0));
                    hotel.setNombreHotel(cursor.getString(1));
                    hotel.setDireccionHotel(cursor.getString(2));
                    hotel.setCiudadHotel(cursor.getString(3));
                    hotel.setTelefonoHotel(cursor.getString(4));
                    hotel.setPlazasHotel(cursor.getInt(5));
                    hoteles.add(hotel);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return hoteles;
        }catch (Exception e){
            return null;
        }
    }

    public ArrayList<Sucursal> obtenerSucursales(){
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_SUCURSAL;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Sucursal sucursal = new Sucursal();
                    sucursal.setCodigoSucursal(cursor.getInt(0));
                    sucursal.setDireccionSucursal(cursor.getString(1));
                    sucursal.setTelefonoSucursal(cursor.getString(2));
                    sucursales.add(sucursal);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return sucursales;
        }catch (Exception e){
            return null;
        }
    }

    public ArrayList<Vuelo> obtenerVuelos(){
        ArrayList<Vuelo> vuelos = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_VUELO;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Vuelo vuelo = new Vuelo();
                    vuelo.setNumeroVuelo(cursor.getInt(0));
                    vuelo.setFechaVuelo(cursor.getString(1));
                    vuelo.setHoraVuelo(cursor.getString(2));
                    vuelo.setOrigenVuelo(cursor.getString(3));
                    vuelo.setDestinoVuelo(cursor.getString(4));
                    vuelo.setPlazasTotales(cursor.getInt(5));
                    vuelo.setPlazasTurista(cursor.getInt(6));
                    vuelos.add(vuelo);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return vuelos;
        }catch (Exception e){
            return null;
        }
    }

    public void actualizarTurista(Turista turista){
        ContentValues values = new ContentValues();
        values.put(KEY_CODTURISTA, turista.getCodigoTurista());
        values.put(KEY_NOMTURISTA, turista.getNombreTurista());
        values.put(KEY_APETURISTA, turista.getApellidosTurista());
        values.put(KEY_DIRTURISTA, turista.getDireccionTurista());
        values.put(KEY_TELTURISTA, turista.getTelefonoTurista());
        db.update(TABLE_TURISTA, values, null, null);
    }

    public Turista obtenerTurista(){
//        String query = "SELECT * FROM " + TABLE_TURISTA + " WHERE " + KEY_CODTURISTA + " = " + codigo;
        String query = "SELECT * FROM " + TABLE_TURISTA;
        Turista turista = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                turista = new Turista();
                turista.setCodigoTurista(cursor.getInt(0));
                turista.setNombreTurista(cursor.getString(1));
                turista.setApellidosTurista(cursor.getString(2));
                turista.setDireccionTurista(cursor.getString(3));
                turista.setTelefonoTurista(cursor.getString(4));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return turista;
    }

    public boolean actualizarHotel(int codigoHotelAntes, int codigoHotelNueva){
        String where2 = KEY_CODHOTEL + "=" + codigoHotelNueva;
        ContentValues values2 = new ContentValues();
        Hotel hotel = obtenerHotel(codigoHotelNueva);
        int plazas = hotel.getPlazasHotel();
        if (plazas > 0){
            if (codigoHotelAntes != 0){
                ContentValues values1 = new ContentValues();
                String where1 = KEY_CODHOTEL + "=" + codigoHotelAntes;
                values1.put(KEY_PLAHOTEL, plazas+1);
                db.update(TABLE_HOTEL, values1, where1, null);
            }
            values2.put(KEY_PLAHOTEL, plazas-1);
            return 1==db.update(TABLE_HOTEL, values2, where2, null);
        }
        return false;
    }

    public Hotel obtenerHotel(int codigo){
        String query = "SELECT * FROM " + TABLE_HOTEL + " WHERE " + KEY_CODHOTEL + " = " + codigo;
        Hotel hotel = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                hotel = new Hotel();
                hotel.setCodigoHotel(cursor.getInt(0));
                hotel.setNombreHotel(cursor.getString(1));
                hotel.setDireccionHotel(cursor.getString(2));
                hotel.setCiudadHotel(cursor.getString(3));
                hotel.setTelefonoHotel(cursor.getString(4));
                hotel.setPlazasHotel(cursor.getInt(5));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return hotel;
    }

    public boolean actualizarVuelo(int codigoVueloAntes, int codigoVueloNueva){
        String where2 = KEY_CODVUELO + "=" + codigoVueloNueva;
        ContentValues values2 = new ContentValues();
        Vuelo vuelo = obtenerVuelo(codigoVueloNueva);
        int plazas = vuelo.getPlazasTurista();
        if (plazas > 0){
            if (codigoVueloAntes != 0){
                ContentValues values1 = new ContentValues();
                String where1 = KEY_CODHOTEL + "=" + codigoVueloAntes;
                values1.put(KEY_PTOVUELO, plazas+1);
                values1.put(KEY_PTUVUELO, plazas+1);
                db.update(TABLE_VUELO, values1, where1, null);
            }
            values2.put(KEY_PTOVUELO, plazas-1);
            values2.put(KEY_PTUVUELO, plazas-1);
            return 1==db.update(TABLE_VUELO, values2, where2, null);
        }
        return false;
    }

    public Vuelo obtenerVuelo(int codigo){
        String query = "SELECT * FROM " + TABLE_VUELO + " WHERE " + KEY_CODVUELO + " = " + codigo;
        Vuelo vuelo = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                vuelo = new Vuelo();
                vuelo.setNumeroVuelo(cursor.getInt(0));
                vuelo.setFechaVuelo(cursor.getString(1));
                vuelo.setHoraVuelo(cursor.getString(2));
                vuelo.setOrigenVuelo(cursor.getString(3));
                vuelo.setDestinoVuelo(cursor.getString(4));
                vuelo.setPlazasTotales(cursor.getInt(5));
                vuelo.setPlazasTurista(cursor.getInt(6));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return vuelo;
    }

    public int insertarEstancia(Estancia estancia){
        ContentValues values = new ContentValues();
//        values.put(KEY_CODESTANCIA, estancia.getCodigoEstancia());
        values.put(KEY_PENESTANCIA, estancia.getPension());
        values.put(KEY_ENTESTANCIA, estancia.getFechaEntrada());
        values.put(KEY_SALESTANCIA, estancia.getFechaSalida());
        values.put(KEY_CODHOTEL, estancia.getCodigoHotel());
        int codigo = (int) db.insert(TABLE_ESTANCIA, null, values);
        return codigo;
    }

    public void actualizarEstancia(Estancia estancia){
        String where = KEY_CODESTANCIA + "=" + estancia.getCodigoEstancia();
        ContentValues values = new ContentValues();
        values.put(KEY_PENESTANCIA, estancia.getPension());
        values.put(KEY_ENTESTANCIA, estancia.getFechaEntrada());
        values.put(KEY_SALESTANCIA, estancia.getFechaSalida());
        values.put(KEY_CODHOTEL, estancia.getCodigoHotel());
        db.update(TABLE_ESTANCIA, values, where, null);
    }

    public void eliminarEstancia(Estancia estancia){
        String where = KEY_CODESTANCIA + "=" + estancia.getCodigoEstancia();
        db.delete(TABLE_ESTANCIA, where, null);
    }

    public Estancia obtenerEstancia(int codigo){
        String query = "SELECT * FROM " + TABLE_ESTANCIA + " WHERE " + KEY_CODESTANCIA + " = " + codigo;
        Estancia estancia = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                estancia = new Estancia();
                estancia.setCodigoEstancia(cursor.getInt(0));
                estancia.setPension(cursor.getString(1));
                estancia.setFechaEntrada(cursor.getString(2));
                estancia.setFechaSalida(cursor.getString(3));
                estancia.setCodigoHotel(cursor.getInt(4));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return estancia;
    }

    public ArrayList<Estancia> obtenerEstancias(){
        ArrayList<Estancia> estancias = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_ESTANCIA;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Estancia estancia = new Estancia();
                    estancia.setCodigoEstancia(cursor.getInt(0));
                    estancia.setPension(cursor.getString(1));
                    estancia.setFechaEntrada(cursor.getString(2));
                    estancia.setFechaSalida(cursor.getString(3));
                    estancia.setCodigoHotel(cursor.getInt(4));
                    estancias.add(estancia);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return estancias;
        }catch (Exception e){
            return null;
        }
    }

    public void insertarViajeContratado(ViajeContratado viajeContratado){
        ContentValues values = new ContentValues();
//        values.put(KEY_CODVIAJE, viajeContratado.getCodigoViaje());
        values.put(KEY_CODTURISTA, viajeContratado.getCodigoTurista());
        values.put(KEY_CODSUCURSAL, viajeContratado.getCodigoSucursal());
        values.put(KEY_CODESTANCIA, viajeContratado.getCodigoEstancia());
        db.insert(TABLE_VIAJECONTRATADO, null, values);
    }

    public void actualizarViajeContratado(ViajeContratado viajeContratado){
        String where = KEY_CODVIAJE + "=" + viajeContratado.getCodigoViaje();
        ContentValues values = new ContentValues();
        values.put(KEY_CODTURISTA, viajeContratado.getCodigoTurista());
        values.put(KEY_CODSUCURSAL, viajeContratado.getCodigoSucursal());
        values.put(KEY_CODESTANCIA, viajeContratado.getCodigoEstancia());
        db.update(TABLE_VIAJECONTRATADO, values, where, null);
    }

    public void eliminarViajeContratado(ViajeContratado viajeContratado){
        String where = KEY_CODVIAJE + "=" + viajeContratado.getCodigoViaje();
        db.delete(TABLE_VIAJECONTRATADO, where, null);
    }

    public void eliminarViajeContratados(){
        db.delete(TABLE_VIAJECONTRATADO, null, null);
    }

    public ViajeContratado obtenerViajeContratado(int codigo){
        String query = "SELECT * FROM " + TABLE_VIAJECONTRATADO + " WHERE " + KEY_CODVIAJE + " = " + codigo;
        ViajeContratado viajeContratado = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                viajeContratado = new ViajeContratado();
                viajeContratado.setCodigoViaje(cursor.getInt(0));
                viajeContratado.setCodigoTurista(cursor.getInt(1));
                viajeContratado.setCodigoSucursal(cursor.getInt(2));
                viajeContratado.setCodigoEstancia(cursor.getInt(3));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return viajeContratado;
    }

    public ArrayList<ViajeContratado> obtenerViajeContratados(){
        ArrayList<ViajeContratado> viajeContratados = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_VIAJECONTRATADO;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    ViajeContratado viajeContratado = new ViajeContratado();
                    viajeContratado.setCodigoViaje(cursor.getInt(0));
                    viajeContratado.setCodigoTurista(cursor.getInt(1));
                    viajeContratado.setCodigoSucursal(cursor.getInt(2));
                    viajeContratado.setCodigoEstancia(cursor.getInt(3));
                    viajeContratados.add(viajeContratado);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return viajeContratados;
        }catch (Exception e){
            return null;
        }
    }

    public void insertarVueloTurista(VueloTurista vueloTurista){
        ContentValues values = new ContentValues();
//        values.put(KEY_CODVUELO, vueloTurista.getCodigoVuelo());
        values.put(KEY_CLASE, vueloTurista.getClase());
        values.put(KEY_NUMVUELO, vueloTurista.getNumeroVuelo());
        values.put(KEY_CODVIAJE, vueloTurista.getCodigoViaje());
        db.insert(TABLE_VUELOTURISTA, null, values);
    }

    public void actualizarVueloTurista(VueloTurista vueloTurista){
        String where = KEY_CODVUELO + "=" + vueloTurista.getCodigoVuelo();
        ContentValues values = new ContentValues();
        values.put(KEY_CLASE, vueloTurista.getClase());
        values.put(KEY_NUMVUELO, vueloTurista.getNumeroVuelo());
        values.put(KEY_CODVIAJE, vueloTurista.getCodigoViaje());
        db.update(TABLE_VUELOTURISTA, values, where, null);
    }

    public void eliminarVueloTurista(VueloTurista vueloTurista){
        String where = KEY_CODVUELO + "=" + vueloTurista.getCodigoVuelo();
        db.delete(TABLE_VUELOTURISTA, where, null);
    }

    public void eliminarVueloTuristas(){
        db.delete(TABLE_VUELOTURISTA, null, null);
    }

    public VueloTurista obtenerVueloTurista(int codigo){
        String query = "SELECT * FROM " + TABLE_VUELOTURISTA + " WHERE " + KEY_CODVUELO + "=" + codigo;
        VueloTurista vueloTurista = null;
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                vueloTurista = new VueloTurista();
                vueloTurista.setCodigoVuelo(cursor.getInt(0));
                vueloTurista.setClase(cursor.getString(1));
                vueloTurista.setNumeroVuelo(cursor.getInt(2));
                vueloTurista.setCodigoViaje(cursor.getInt(3));
            }
            cursor.close();
        } catch (Exception ex){

        }
        return vueloTurista;
    }

    public ArrayList<VueloTurista> obtenerVueloTuristas(){
        ArrayList<VueloTurista> vueloTuristas = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + TABLE_VUELOTURISTA;
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    VueloTurista vueloTurista = new VueloTurista();
                    vueloTurista.setCodigoVuelo(cursor.getInt(0));
                    vueloTurista.setClase(cursor.getString(1));
                    vueloTurista.setNumeroVuelo(cursor.getInt(2));
                    vueloTurista.setCodigoViaje(cursor.getInt(3));
                    vueloTuristas.add(vueloTurista);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return vueloTuristas;
        }catch (Exception e){
            return null;
        }
    }
}

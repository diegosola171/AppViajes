package com.example.agenciaviajes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenciaviajes.db.DBHelper;
import com.example.agenciaviajes.modelo.Estancia;
import com.example.agenciaviajes.modelo.Hotel;
import com.example.agenciaviajes.modelo.Sucursal;
import com.example.agenciaviajes.modelo.ViajeContratado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class CuadroRegEdiViaje {

    final Dialog dialogo;
    private Spinner sp_hotel, sp_sucursal;
    private int dia1, dia2, mes1, mes2, anio1, anio2;
    private EditText et_fentrada, et_fsalida;
    private TextView tv_pension;
    private DBHelper dbHelper;
    private final int pensionxdia = 50;
    private ViajeContratado viajeContratado;
    private Estancia estancia;
    private int codigoHotelPasado;

    ArrayList<Hotel> listaHoteles;
    ArrayList<Sucursal> listaSucursales;

    ProgressDialog progress;
    JORequest jsonObjectRequest;

    public interface FinalizoCuadroViaje{
        void ResultadoCuadroViaje(int codigoTurista, int codigoSucursal, int codigoEstancia);
    }
    private FinalizoCuadroViaje interfaz;

    public CuadroRegEdiViaje(Context contexto, FinalizoCuadroViaje actividad, ViajeContratado viajeContratado) {
        interfaz = actividad;

        dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_regediviaje);
        dbHelper = new DBHelper(contexto);

        tv_pension = dialogo.findViewById(R.id.tv_pension);
        et_fentrada = dialogo.findViewById(R.id.et_fentrada);
        et_fsalida = dialogo.findViewById(R.id.et_fsalida);
        sp_hotel = dialogo.findViewById(R.id.sp_hotel);
        sp_sucursal = dialogo.findViewById(R.id.sp_sucursal);

        listaHoteles = new ArrayList<>();
        listaSucursales = new ArrayList<>();

        llenarSpinnerHotel();
        llenarSpinnerSucursal();
//        cargarWebServiceHoteles();
//        cargarWebServiceSucursales();

        if (viajeContratado != null){
            estancia = dbHelper.obtenerEstancia(viajeContratado.getCodigoEstancia());
            tv_pension.setText(estancia.getPension()+"");
            et_fentrada.setText(estancia.getFechaEntrada());
            et_fsalida.setText(estancia.getFechaSalida());
            sp_hotel.setSelection(estancia.getCodigoHotel()-1);
            sp_sucursal.setSelection(viajeContratado.getCodigoSucursal()-1);
            codigoHotelPasado = estancia.getCodigoHotel();
        }

        dialogo.findViewById(R.id.btn_fentrada).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                dia1= c.get(Calendar.DAY_OF_MONTH);
                mes1 = c.get(Calendar.MONTH);
                anio1 = c.get(Calendar.YEAR);

                final Calendar calendar = Calendar.getInstance();
                calendar.set(anio1, mes1, dia1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(y,m, d);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendar.getTime());
                        et_fentrada.setText(strDate);
                        Date fechaSalida = null;
                        long diff = 0;
                        try {
                            fechaSalida = format.parse(et_fsalida.getText().toString());
                            diff = fechaSalida.getTime() - calendar.getTimeInMillis();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        TimeUnit time = TimeUnit.DAYS;
                        long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                        tv_pension.setText((diffrence+1)*pensionxdia + "");
                    }
                } ,anio1,mes1,dia1);
                datePickerDialog.show();
            }
        });

        dialogo.findViewById(R.id.btn_fsalida).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                dia2= c.get(Calendar.DAY_OF_MONTH);
                mes2 = c.get(Calendar.MONTH);
                anio2 = c.get(Calendar.YEAR);

                final Calendar calendar = Calendar.getInstance();
                calendar.set(anio2, mes2, dia2);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(y,m, d);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendar.getTime());
                        et_fsalida.setText(strDate);
                        Date fechaEntrada = null;
                        long diff = 0;
                        try {
                            fechaEntrada = format.parse(et_fentrada.getText().toString());
                            diff = calendar.getTimeInMillis() - fechaEntrada.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        TimeUnit time = TimeUnit.DAYS;
                        long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
                        tv_pension.setText(diffrence*pensionxdia + "");
                    }
                } ,anio2,mes2,dia2);
                datePickerDialog.show();
            }
        });

        dialogo.findViewById(R.id.btn_aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int codigoTurista = dbHelper.obtenerTurista().getCodigoTurista();
                if (codigoTurista == 0){
                    Toast.makeText(contexto, "Por favor, primero registre los datos del turista", Toast.LENGTH_SHORT).show();
                    return;
                }
                int codigoSucursal = sp_sucursal.getSelectedItemPosition()+1;
                int codigoHotel = sp_hotel.getSelectedItemPosition()+1;
                if (estancia==null){
                    estancia = new Estancia(tv_pension.getText().toString(), et_fentrada.getText().toString(), et_fsalida.getText().toString(), codigoHotel);
                    int codigoEstancia = dbHelper.insertarEstancia(estancia);
                    if (!dbHelper.actualizarHotel(0, codigoHotel)){
                        Toast.makeText(contexto, "Ya no existen plazas disponibles en tal Hotel", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    interfaz.ResultadoCuadroViaje(codigoTurista, codigoSucursal, codigoEstancia);
                } else {
                    estancia.setPension(tv_pension.getText().toString());
                    estancia.setFechaEntrada(et_fentrada.getText().toString());
                    estancia.setFechaSalida(et_fsalida.getText().toString());
                    estancia.setCodigoHotel(codigoHotel);
                    dbHelper.actualizarEstancia(estancia);
                    if (!dbHelper.actualizarHotel(codigoHotelPasado, codigoHotel)) {
                        Toast.makeText(contexto, "Ya no existen plazas disponibles en tal Hotel", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    interfaz.ResultadoCuadroViaje(codigoTurista, codigoSucursal, estancia.getCodigoEstancia());
                }
                dialogo.dismiss();
            }
        });
        dialogo.findViewById(R.id.btn_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }

    private void llenarSpinnerHotel() {
        listaHoteles = dbHelper.obtenerHoteles();
        ArrayAdapter<Hotel> adapter = new ArrayAdapter<Hotel>(dialogo.getContext(), android.R.layout.simple_spinner_item, listaHoteles);
        sp_hotel.setAdapter(adapter);
    }

    private void llenarSpinnerSucursal() {
        listaSucursales = dbHelper.obtenerSucursales();
        ArrayAdapter<Sucursal> adapter = new ArrayAdapter<Sucursal>(dialogo.getContext(), android.R.layout.simple_spinner_item, listaSucursales);
        sp_sucursal.setAdapter(adapter);
    }

    private void cargarWebServiceHoteles() {

        progress=new ProgressDialog(dialogo.getContext());
        progress.setMessage("Consultando...");
        progress.show();

        String ip=dialogo.getContext().getString(R.string.ip);

        String url=ip+"/WebServiceAgencia/hotel_read.php";

        jsonObjectRequest=new JORequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Hotel hotel=null;
                JSONArray json=response.optJSONArray("hotel");

                try {
                    for (int i=0;i<json.length();i++){
                        hotel=new Hotel();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);

                        hotel.setCodigoHotel(jsonObject.optInt("CodigoHotel"));
                        hotel.setNombreHotel(jsonObject.optString("NombreHotel"));
                        hotel.setDireccionHotel(jsonObject.optString("DireccionHotel"));
                        hotel.setCiudadHotel(jsonObject.optString("CiudadHotel"));
                        hotel.setTelefonoHotel(jsonObject.optString("TelefonoHotel"));
                        hotel.setPlazasHotel(jsonObject.optInt("PlazasHotel"));
                        listaHoteles.add(hotel);
                    }
                    progress.hide();
                    ArrayAdapter<Hotel> adapterHotel = new ArrayAdapter<Hotel>(dialogo.getContext(), android.R.layout.simple_spinner_dropdown_item, listaHoteles);
                    sp_hotel.setAdapter(adapterHotel);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(dialogo.getContext(), "No se ha podido establecer conexión con el servidor" +
                            " "+response, Toast.LENGTH_LONG).show();
                    progress.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(dialogo.getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString());
                progress.hide();
            }
        });
        VolleySingleton.getIntanciaVolley(dialogo.getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarWebServiceSucursales() {

        progress=new ProgressDialog(dialogo.getContext());
        progress.setMessage("Consultando...");
        progress.show();

        String ip=dialogo.getContext().getString(R.string.ip);

        String url=ip+"/WebServiceAgencia/sucursal_read.php";

        jsonObjectRequest=new JORequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Sucursal sucursal=null;
                JSONArray json=response.optJSONArray("sucursal");

                try {
                    for (int i=0;i<json.length();i++){
                        sucursal=new Sucursal();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);

                        sucursal.setCodigoSucursal(jsonObject.optInt("CodigoSucursal"));
                        sucursal.setDireccionSucursal(jsonObject.optString("DireccionSucursal"));
                        sucursal.setTelefonoSucursal(jsonObject.optString("TelefonoSucursal"));
                        listaSucursales.add(sucursal);
                    }
                    progress.hide();
                    ArrayAdapter<Sucursal> adapterSucursal = new ArrayAdapter<Sucursal>(dialogo.getContext(), android.R.layout.simple_spinner_dropdown_item, listaSucursales);
                    sp_sucursal.setAdapter(adapterSucursal);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(dialogo.getContext(), "No se ha podido establecer conexión con el servidor" +
                            " "+response, Toast.LENGTH_LONG).show();
                    progress.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(dialogo.getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString());
                progress.hide();
            }
        });
        VolleySingleton.getIntanciaVolley(dialogo.getContext()).addToRequestQueue(jsonObjectRequest);
    }

    public class JORequest extends JsonObjectRequest{

        public JORequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                JSONObject result = null;

                if (jsonString != null && jsonString.length() > 0)
                    result = new JSONObject(jsonString);

                return Response.success(result,
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }
    }
}

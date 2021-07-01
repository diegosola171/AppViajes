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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.agenciaviajes.db.DBHelper;
import com.example.agenciaviajes.modelo.Estancia;
import com.example.agenciaviajes.modelo.Vuelo;
import com.example.agenciaviajes.modelo.ViajeContratado;
import com.example.agenciaviajes.modelo.Vuelo;
import com.example.agenciaviajes.modelo.VueloTurista;
import java.util.ArrayList;

public class CuadroRegEdiVueloTurista {

    final Dialog dialogo;
    private Spinner sp_vuelo, sp_viaje;
    private TextView tv_clase;
    private DBHelper dbHelper;
    private VueloTurista vueloTurista1;
    private Estancia estancia;
    private int codigoVueloAntes;

    ArrayList<Vuelo> listaVuelos;
    ArrayList<ViajeContratado> listaViajes;

    public interface FinalizoCuadroVueloTurista{
        void ResultadoCuadroVueloTurista(VueloTurista vueloTurista);
    }
    private FinalizoCuadroVueloTurista interfaz;

    public CuadroRegEdiVueloTurista(Context contexto, FinalizoCuadroVueloTurista actividad, VueloTurista vueloTurista) {
        interfaz = actividad;

        dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_regedivueloturista);
        dbHelper = new DBHelper(contexto);

        tv_clase = dialogo.findViewById(R.id.tv_clase);
        sp_vuelo = dialogo.findViewById(R.id.sp_vuelo);
        sp_viaje = dialogo.findViewById(R.id.sp_viaje);

        listaVuelos = new ArrayList<>();
        listaViajes = new ArrayList<>();

        llenarSpinnerVuelo();
        llenarSpinnerViajeContratado();

        if (vueloTurista != null){
            vueloTurista1 = vueloTurista;
            tv_clase.setText(vueloTurista1.getClase());
            sp_vuelo.setSelection(vueloTurista1.getNumeroVuelo()-1);
            sp_viaje.setSelection(vueloTurista1.getCodigoViaje()-1);
            codigoVueloAntes = vueloTurista1.getCodigoVuelo();
        }

        dialogo.findViewById(R.id.btn_aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int codigoTurista = dbHelper.obtenerTurista().getCodigoTurista();
                if (codigoTurista == 0){
                    Toast.makeText(contexto, "Por favor, primero registre los datos del turista", Toast.LENGTH_SHORT).show();
                    return;
                }
                int codigoViajeContratado = sp_viaje.getSelectedItemPosition()+1;
                int codigoVuelo = sp_vuelo.getSelectedItemPosition()+1;
                if (vueloTurista1==null){
                    vueloTurista1 = new VueloTurista(tv_clase.getText().toString(), codigoVuelo, codigoViajeContratado);
                    if (!dbHelper.actualizarVuelo(0, codigoVuelo)){
                        Toast.makeText(contexto, "Ya no existen plazas disponibles en tal Vuelo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    vueloTurista1.setNumeroVuelo(codigoVuelo);
                    vueloTurista1.setCodigoViaje(codigoViajeContratado);
                    if (!dbHelper.actualizarVuelo(codigoVueloAntes, codigoVuelo)){
                        Toast.makeText(contexto, "Ya no existen plazas disponibles en tal Vuelo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                interfaz.ResultadoCuadroVueloTurista(vueloTurista1);
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

    private void llenarSpinnerVuelo() {
        listaVuelos = dbHelper.obtenerVuelos();
        ArrayAdapter<Vuelo> adapter = new ArrayAdapter<Vuelo>(dialogo.getContext(), android.R.layout.simple_spinner_item, listaVuelos);
        sp_vuelo.setAdapter(adapter);
    }

    private void llenarSpinnerViajeContratado() {
        listaViajes = dbHelper.obtenerViajeContratados();
        ArrayAdapter<ViajeContratado> adapter = new ArrayAdapter<ViajeContratado>(dialogo.getContext(), android.R.layout.simple_spinner_item, listaViajes);
        sp_viaje.setAdapter(adapter);
    }
}

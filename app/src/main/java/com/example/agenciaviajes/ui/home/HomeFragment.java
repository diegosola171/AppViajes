package com.example.agenciaviajes.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.agenciaviajes.R;
import com.example.agenciaviajes.VolleySingleton;
import com.example.agenciaviajes.databinding.FragmentHomeBinding;
import com.example.agenciaviajes.db.DBHelper;
import com.example.agenciaviajes.modelo.Turista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    EditText et_nombre, et_apellido, et_direccion, et_telefono;
    private DBHelper dbHelper;
    private Turista turista;
    ProgressDialog progreso;

    JsonObjectRequest jsonObjectRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DBHelper(getActivity().getBaseContext());
        turista = dbHelper.obtenerTurista();
        et_nombre= root.findViewById(R.id.et_nombre);
        et_apellido= root.findViewById(R.id.et_apellido);
        et_direccion= root.findViewById(R.id.et_direccion);
        et_telefono= root.findViewById(R.id.et_telefono);

        if (turista.getCodigoTurista() > 0){
            et_nombre.setText(turista.getNombreTurista());
            et_apellido.setText(turista.getApellidosTurista());
            et_direccion.setText(turista.getDireccionTurista());
            et_telefono.setText(turista.getTelefonoTurista());
        }

        root.findViewById(R.id.btn_registrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });

        return root;
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip=getString(R.string.ip);
        String url = null;

        if (turista.getCodigoTurista() == 0){
            url = ip+"/WebServiceAgencia/turista_create.php?nombre_turista="+et_nombre.getText().toString()+
                    "&apellidos_turista="+et_apellido.getText().toString()+"&direccion_turista="+et_direccion.getText().toString()+
                    "&telefono_turista="+et_telefono.getText().toString();
        } else {
            url = ip+"/WebServiceAgencia/turista_update.php?codigo_turista="+turista.getCodigoTurista()+"&nombre_turista="+et_nombre.getText().toString()+
                    "&apellidos_turista="+et_apellido.getText().toString()+"&direccion_turista="+et_direccion.getText().toString()+
                    "&telefono_turista="+et_telefono.getText().toString();

            turista.setNombreTurista(et_nombre.getText().toString());
            turista.setApellidosTurista(et_apellido.getText().toString());
            turista.setDireccionTurista(et_direccion.getText().toString());
            turista.setTelefonoTurista(et_telefono.getText().toString());

            dbHelper.actualizarTurista(turista);
            Toast.makeText(getContext(), "Se ha actualizado exitosamente", Toast.LENGTH_SHORT).show();
        }

        url = url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();

        if (turista.getCodigoTurista() == 0) {
            JSONArray json = response.optJSONArray("turista");
            JSONObject jsonObject = null;

            try {
                jsonObject = json.getJSONObject(0);
                turista.setCodigoTurista(jsonObject.optInt("CodigoTurista"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            turista.setNombreTurista(et_nombre.getText().toString());
            turista.setApellidosTurista(et_apellido.getText().toString());
            turista.setDireccionTurista(et_direccion.getText().toString());
            turista.setTelefonoTurista(et_telefono.getText().toString());

            dbHelper.actualizarTurista(turista);
            Toast.makeText(getContext(), "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();
        }
    }
}
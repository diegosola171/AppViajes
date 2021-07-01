package com.example.agenciaviajes.ui.viajeContratado;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.agenciaviajes.CuadroRegEdiViaje;
import com.example.agenciaviajes.R;
import com.example.agenciaviajes.databinding.FragmentViajecontratadoBinding;
import com.example.agenciaviajes.db.DBHelper;
import com.example.agenciaviajes.modelo.ViajeContratado;

import java.util.ArrayList;

public class ViajeContratadoFragment extends Fragment implements CuadroRegEdiViaje.FinalizoCuadroViaje {

    private ViajeContratadoViewModel viajeContratadoViewModel;
    private FragmentViajecontratadoBinding binding;
    private ListView lv_viajecontratados;
    private ArrayList<ViajeContratado> viajeContratados;
    private DBHelper dbHelper;
    private ViajeContratado viajeContratado;
    boolean registro;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viajeContratadoViewModel =
                new ViewModelProvider(this).get(ViajeContratadoViewModel.class);

        binding = FragmentViajecontratadoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);

        dbHelper = new DBHelper(getContext());
        lv_viajecontratados = root.findViewById(R.id.lv_viajecontratados);

        listarViajeContratados();
        registerForContextMenu(lv_viajecontratados);
        return root;
    }

    private void listarViajeContratados(){
        viajeContratados = dbHelper.obtenerViajeContratados();
        ArrayAdapter<ViajeContratado> adapter = new ArrayAdapter<ViajeContratado>(getContext(), android.R.layout.simple_list_item_1, viajeContratados);
        lv_viajecontratados.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        viajeContratado = (ViajeContratado) lv_viajecontratados.getAdapter().getItem(info.position);
        switch (item.getItemId()) {
            case R.id.eliminar:
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estás seguro?")
                        .setMessage("¿Quieres eliminar este registro?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.eliminarViajeContratado(viajeContratado);
                                listarViajeContratados();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            case R.id.editar:
                new CuadroRegEdiViaje(getContext(), ViajeContratadoFragment.this, viajeContratado);
                registro = false;
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.add:
                new CuadroRegEdiViaje(getContext(), ViajeContratadoFragment.this, null);
                registro = true;
                break;
            case R.id.delete:
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estás seguro?")
                        .setMessage("¿Quieres eliminar todos los registros?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.eliminarViajeContratados();
                                listarViajeContratados();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void ResultadoCuadroViaje(int codigoTurista, int codigoSucursal, int codigoEstancia) {
        if (registro) {
            viajeContratado = new ViajeContratado(codigoTurista, codigoSucursal, codigoEstancia);
            dbHelper.insertarViajeContratado(viajeContratado);
        } else {
            viajeContratado.setCodigoSucursal(codigoSucursal);
            dbHelper.actualizarViajeContratado(viajeContratado);
        }
        listarViajeContratados();
    }
}
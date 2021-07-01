package com.example.agenciaviajes.ui.vueloTurista;

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

import com.example.agenciaviajes.CuadroRegEdiVueloTurista;
import com.example.agenciaviajes.R;
import com.example.agenciaviajes.databinding.FragmentVueloturistaBinding;
import com.example.agenciaviajes.db.DBHelper;
import com.example.agenciaviajes.modelo.VueloTurista;

import java.util.ArrayList;

public class VueloTuristaFragment extends Fragment implements CuadroRegEdiVueloTurista.FinalizoCuadroVueloTurista {

    private VueloTuristaViewModel vueloTuristaViewModel;
    private FragmentVueloturistaBinding binding;
    private DBHelper dbHelper;
    private VueloTurista vueloTurista;
    private ListView lv_vueloTuristas;
    ArrayList<VueloTurista> vueloTuristas;
    boolean registro;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vueloTuristaViewModel =
                new ViewModelProvider(this).get(VueloTuristaViewModel.class);

        binding = FragmentVueloturistaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);

        dbHelper = new DBHelper(getContext());
        lv_vueloTuristas = root.findViewById(R.id.lv_vueloturistas);

        listarVueloTuristas();
        registerForContextMenu(lv_vueloTuristas);
        return root;
    }

    private void listarVueloTuristas(){
        vueloTuristas = dbHelper.obtenerVueloTuristas();
        ArrayAdapter<VueloTurista> adapter = new ArrayAdapter<VueloTurista>(getContext(), android.R.layout.simple_list_item_1, vueloTuristas);
        lv_vueloTuristas.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        vueloTurista = (VueloTurista) lv_vueloTuristas.getAdapter().getItem(info.position);
        switch (item.getItemId()) {
            case R.id.eliminar:
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estás seguro?")
                        .setMessage("¿Quieres eliminar este registro?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.eliminarVueloTurista(vueloTurista);
                                listarVueloTuristas();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            case R.id.editar:
                new CuadroRegEdiVueloTurista(getContext(), VueloTuristaFragment.this, vueloTurista);
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
                new CuadroRegEdiVueloTurista(getContext(), VueloTuristaFragment.this, null);
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
                                dbHelper.eliminarVueloTuristas();
                                listarVueloTuristas();
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
    public void ResultadoCuadroVueloTurista(VueloTurista vueloTurista) {
        if (registro) {
            dbHelper.insertarVueloTurista(vueloTurista);
        } else {
            dbHelper.actualizarVueloTurista(vueloTurista);
        }
        listarVueloTuristas();
    }
}
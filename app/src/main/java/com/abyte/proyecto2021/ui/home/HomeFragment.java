package com.abyte.proyecto2021.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.proyecto2021.Adapter.AdapterMascotas;
import com.abyte.proyecto2021.BD.dboMascotas;
import com.abyte.proyecto2021.Class.Mascota;
import com.abyte.proyecto2021.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Mascota> lista;
    RecyclerView recyclermascotas;
    dboMascotas dbo;
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         root = inflater.inflate(R.layout.fragment_home, container, false);

        try{
        lista = new ArrayList<>();
        recyclermascotas= (RecyclerView) root.findViewById(R.id.RecyclerId);
        recyclermascotas.setLayoutManager(new LinearLayoutManager(root.getContext()));

        ConsulaMascotas();
        AdapterMascotas adapter = new AdapterMascotas(lista);
        recyclermascotas.setAdapter(adapter);

        return root;
        }
        catch (Exception e){
            Toast.makeText(root.getContext(), "Error, comuniquese con el administrador", Toast.LENGTH_LONG).show();
        }
        return root;
    }

    private void ConsulaMascotas() {
        try{
        dbo = dboMascotas.getInstance(root.getContext());
        lista=dbo.selectMascota();
        }
        catch (Exception e){
            Toast.makeText(root.getContext(), "Error, comuniquese con el administrador", Toast.LENGTH_LONG).show();
        }
    }
}
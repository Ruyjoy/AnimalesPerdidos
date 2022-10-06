package com.abyte.proyecto2021.ui.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.proyecto2021.Adapter.AdapterMascotas;
import com.abyte.proyecto2021.Adapter.AdapterRefugio;
import com.abyte.proyecto2021.BD.dboMascotas;
import com.abyte.proyecto2021.Class.Mascota;
import com.abyte.proyecto2021.Class.Refugio;
import com.abyte.proyecto2021.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    ArrayList<Refugio> lista;
    RecyclerView recyclerRefugio;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        try{
            lista = new ArrayList<>();
            recyclerRefugio= (RecyclerView) root.findViewById(R.id.RecyclerId1);
            recyclerRefugio.setLayoutManager(new LinearLayoutManager(root.getContext()));

            obtenerDatos();
            AdapterRefugio adapter = new AdapterRefugio(lista);
            recyclerRefugio.setAdapter(adapter);

            return root;
        }
        catch (Exception e){
            Toast.makeText(root.getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return root;
    }



    public String getStringImagen(Bitmap bmp){

        ByteArrayOutputStream bArry = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, bArry);
        byte[] imageBytes = bArry.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void obtenerDatos(){
        //Refugio_N°1
        Refugio refugio_1 = new Refugio();
        refugio_1.setNombreRefugio("Refugio Ecológico MI ZOO S.C.");
        refugio_1.setDescription("Contacto: 46232959" +
                " - Se aceptan todo tipo de animales");
        Bitmap bitmR_1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.refugio_n1);
        refugio_1.setFoto(getStringImagen(bitmR_1));
        lista.add(refugio_1);

        //Refugio_N°2
        Refugio refugio_2 = new Refugio();
        refugio_2.setNombreRefugio("Refugio Canino “Juan Lacaze”");
        refugio_2.setDescription("Contacto: 098311905" +
                " - Se aceptan Perros y Gatos");
        Bitmap bitmR_2 = BitmapFactory.decodeResource(getResources(),
                R.drawable.refugio_n2);
        refugio_2.setFoto(getStringImagen(bitmR_2));
        lista.add(refugio_2);

        //Refugio_N°3
        Refugio refugio_3 = new Refugio();
        refugio_3.setNombreRefugio("Refugio Municipal de Maldonado para Animales");
        refugio_3.setDescription("Contacto: 42223333" +
                " - Se aceptan Perros y Gatos");
        Bitmap bitmR_3 = BitmapFactory.decodeResource(getResources(),
                R.drawable.refugio_n3);
        refugio_3.setFoto(getStringImagen(bitmR_3));
        lista.add(refugio_3);

        //Refugio_N°4
        Refugio refugio_4 = new Refugio();
        refugio_4.setNombreRefugio("Animal Help");
        refugio_4.setDescription("Contacto: 094676181" +
                " - Se aceptan Perros y Gatos");
        Bitmap bitmR_4 = BitmapFactory.decodeResource(getResources(),
                R.drawable.refugio_n4);
        refugio_4.setFoto(getStringImagen(bitmR_4));
        lista.add(refugio_4);

        //Refugio_N°5
        Refugio refugio_5 = new Refugio();
        refugio_5.setNombreRefugio("Espacio ASH");
        refugio_5.setDescription("Contacto: 091318644" +
                " - Se aceptan Perros, Gatos, entre otros");
        Bitmap bitmR_5 = BitmapFactory.decodeResource(getResources(),
                R.drawable.refugio_sin_logo);
        refugio_5.setFoto(getStringImagen(bitmR_5));
        lista.add(refugio_5);

        //Refugio_N°6
        Refugio refugio_6 = new Refugio();
        refugio_6.setNombreRefugio("La Costa Bichera");
        refugio_6.setDescription("Contacto: 094636115" +
                " - Se aceptan Perros y Gatos");
        Bitmap bitmR_6 = BitmapFactory.decodeResource(getResources(),
                R.drawable.refugio_n6);
        refugio_6.setFoto(getStringImagen(bitmR_6));
        lista.add(refugio_6);


    }

}
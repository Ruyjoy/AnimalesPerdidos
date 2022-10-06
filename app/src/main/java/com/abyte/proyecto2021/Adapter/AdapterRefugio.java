package com.abyte.proyecto2021.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abyte.proyecto2021.Class.Mascota;
import com.abyte.proyecto2021.Class.Refugio;
import com.abyte.proyecto2021.R;

import java.util.ArrayList;


public class AdapterRefugio extends RecyclerView.Adapter<AdapterRefugio.ViewHolder> {

    ArrayList<Refugio> Lista;



    public AdapterRefugio(ArrayList<Refugio> lista) {
        Lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewerefugio,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Refugio refugio = Lista.get(position);

        holder.nombreTitulo.setText(refugio.getNombreRefugio());
        holder.CvDescipcion.setText(refugio.getDescription());

        if (refugio.getFoto()!=null){
            holder.foto.setImageBitmap(StringToBitMap(refugio.getFoto()));

        }else{
            holder.foto.setImageResource(R.drawable.img_base);

        }

    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombreTitulo,CvDescipcion;
        ImageView foto;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreTitulo = (TextView) itemView.findViewById(R.id.nametext1);
            CvDescipcion = (TextView) itemView.findViewById(R.id.Des);
            foto = (ImageView) itemView.findViewById(R.id.iconoImagen1);


        }
    }


    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}

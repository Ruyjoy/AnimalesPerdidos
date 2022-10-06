package com.abyte.proyecto2021.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.abyte.proyecto2021.R;

import java.util.ArrayList;

public class AdapterMascotas extends RecyclerView.Adapter<AdapterMascotas.ViewHolderMascotas>{

    static ArrayList<Mascota> mascotas;



    public AdapterMascotas(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    @NonNull
    @Override
    public ViewHolderMascotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewmascotas,parent,false);
        return new ViewHolderMascotas(v);

    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolderMascotas holder, int position) {
        final Mascota mascota = mascotas.get(position);

        holder.nombreTitulo.setText(mascota.getTipoMascota());
        holder.barrio.setText(mascota.getBarrioDeparta());
        holder.CvDescipcion.setText(mascota.getDescription());

        if (mascota.getFoto()!=null){
            holder.foto.setImageBitmap(StringToBitMap(mascota.getFoto()));
            holder.fotoCompleta.setImageBitmap(StringToBitMap(mascota.getFoto()));
        }else{
            holder.foto.setImageResource(R.drawable.img_base);
            holder.fotoCompleta.setImageResource(R.drawable.img_base);
        }

        boolean isExpandido = mascotas.get(position).isExpandido();
        holder.detalles.setVisibility(isExpandido ? View.VISIBLE : View.GONE);
        holder.ficha.setVisibility(isExpandido ? View.GONE : View.VISIBLE);



    }



    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public class ViewHolderMascotas extends RecyclerView.ViewHolder {

        TextView nombreTitulo,CvDescipcion,barrio;
        ImageView foto, fotoCompleta;

        CardView fichaTotal;
        RelativeLayout ficha;
        LinearLayout detalles;


        public ViewHolderMascotas(@NonNull View itemView) {
            super(itemView);
            nombreTitulo = (TextView) itemView.findViewById(R.id.nametext);
            CvDescipcion = (TextView) itemView.findViewById(R.id.CvDescipcion);
            barrio = (TextView) itemView.findViewById(R.id.barrio_zona);
            foto = (ImageView) itemView.findViewById(R.id.iconoImagen);

            fotoCompleta= (ImageView) itemView.findViewById(R.id.fotoCompleta);

            fichaTotal= (CardView) itemView.findViewById(R.id.cardviewmascota);
            ficha= (RelativeLayout)itemView.findViewById(R.id.ficha);
            detalles = (LinearLayout)itemView.findViewById(R.id.detalles);

            fichaTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Mascota mascota = mascotas.get(getAdapterPosition());
                    mascota.setExpandido(!mascota.isExpandido());
                    notifyItemChanged(getAdapterPosition());


                }
            });
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

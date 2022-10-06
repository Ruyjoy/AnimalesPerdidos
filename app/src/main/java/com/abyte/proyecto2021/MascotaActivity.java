package com.abyte.proyecto2021;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.abyte.proyecto2021.BD.dboMascotas;
import com.abyte.proyecto2021.Class.Mascota;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MascotaActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imagen;
    EditText tipomascota, departamento, description;
    Button  btnAcerptarImagen, btnEliminar, btnModificar, btnCancelar;
    Bitmap bitmap;
        dboMascotas dbo;
    Mascota mas = new Mascota();
    String datos;
    Uri miPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota);


        try {

        getSupportActionBar().hide();

        imagen = (ImageView) findViewById(R.id.imageView5);
        btnAcerptarImagen = (Button) findViewById(R.id.AceptarImagen);
        btnModificar = (Button) findViewById(R.id.Modificar);
        btnEliminar = (Button) findViewById(R.id.EliminarMas);
        btnCancelar = (Button) findViewById(R.id.cancelaMas);

        tipomascota =(EditText)findViewById(R.id.tipomascota);
        departamento=(EditText)findViewById(R.id.nombreusuario3);
        description=(EditText)findViewById(R.id.Description);

        btnCancelar.setOnClickListener(this);
        btnAcerptarImagen.setOnClickListener(this);
        btnModificar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        dbo= dboMascotas.getInstance(this);
        imagen.setOnClickListener(this);


        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
             datos = parametros.getString("Usuario");
        }
        //Toast.makeText(this, datos, Toast.LENGTH_LONG).show();


            llenardatos();
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imageView5:
                cargarImagen();
                break;

            case R.id.AceptarImagen:
                RegsistarMascota(mas);
                break;

            case R.id.Modificar:
                modificarDatos(datos);
                break;

            case R.id.cancelaMas:

                try {
                    Intent i = new Intent(this, Principal.class);
                    i.putExtra("Usuario", datos);
                    startActivity(i);
                    finish();
                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.EliminarMas:
                eliminarPublicacion(datos);
                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            miPath = data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), miPath);
                imagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "La foto esta vacia", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
    }

    public String getStringImagen(Bitmap bmp){

        ByteArrayOutputStream bArry = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, bArry);
        byte[] imageBytes = bArry.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void RegsistarMascota(Mascota mas){
        try {
            if(dbo.validarMascota(datos)){
                Toast.makeText(this, "Error: Usuario Ya tiene Una Publicacion.\",", Toast.LENGTH_LONG).show();
                return;
            }else {
            mas.setUser(datos);
            mas.setTipoMascota(tipomascota.getText().toString());
            mas.setBarrioDeparta(departamento.getText().toString());
            mas.setDescription(description.getText().toString());
            mas.setFoto(getStringImagen(bitmap));
            }

            if (tipomascota.getText().toString().isEmpty()){
                tipomascota.setError("No puede estar vacio");
                return;
            }
            else if(departamento.getText().toString().isEmpty()){
                departamento.setError("No puede estar vacio");
                return;

            } else if (description.getText().toString().isEmpty()){
                description.setError("No puede estar vacio");
                return;
            }





            else {
                dbo.insertarMascota(mas);
                Intent i =new Intent(this, Principal.class);
                i.putExtra("Usuario",datos);
                startActivity(i);
                finish();
                Toast.makeText(this, "Publicacion Correcta.\",", Toast.LENGTH_LONG).show();

            }
        }
        catch (Exception e){
            Toast.makeText(this, "La foto o uno de los campos esta vacio", Toast.LENGTH_LONG).show();
        }

    }

    public void llenardatos(){

        try {
            if (dbo.validarMascota(datos)) {
                ArrayList<Mascota> Mascotalista;
                Mascotalista = dbo.selectMascota();
                for (Mascota us : Mascotalista) {
                    if (us.getUser().equals(datos)) {
                        tipomascota.setText(us.getTipoMascota());
                        departamento.setText(us.getBarrioDeparta());
                        description.setText(us.getDescription());
                        imagen.setImageBitmap(StringToBitMap(us.getFoto()));
                    }
                }

            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public void modificarDatos(String u){
        try {

            if(dbo.validarMascota(datos)){

            if (tipomascota.getText().toString().isEmpty()){
                tipomascota.setError("No puede estar vacio");
                return;
            }
            else if(departamento.getText().toString().isEmpty()){
                departamento.setError("No puede estar vacio");
                return;

            } else if (description.getText().toString().isEmpty()){
                description.setError("No puede estar vacio");
                return;
            }

            Mascota masco = new Mascota();
            masco.setUser(datos);
            masco.setTipoMascota(tipomascota.getText().toString());
            masco.setBarrioDeparta(departamento.getText().toString());
            masco.setDescription(description.getText().toString());
            //Aca ba un if para preguntar cual es cua
            masco.setFoto(getStringImagen(bitmap));

            dbo.updataMascota(masco);

            Intent i =new Intent(this, Principal.class);
            i.putExtra("Usuario",datos);
            startActivity(i);
            finish();
            Toast.makeText(this, "Modificacion Correcta.\",", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Error: Usted no tiene una Publicacion.\",", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Uno de los datos estan vacios", Toast.LENGTH_LONG).show();
        }

    }

    public void eliminarPublicacion(String u){

        try {
            if(dbo.validarMascota(datos)){

            AlertDialog.Builder b =new AlertDialog.Builder(this);
            b.setMessage("Esta Seguro de Eliminar esta Publicacion ???");
            b.setCancelable(false);
            b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dbo.EliminarMascota(u);
                    Intent j = new Intent(MascotaActivity.this, Principal.class);
                    j.putExtra("Usuario",datos);
                    startActivity(j);
                    finish();
                    Toast.makeText(MascotaActivity.this, "Se a Eliminado la Publicacion", Toast.LENGTH_LONG).show();
                }
            });
            b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            b.show();

            }else {
                Toast.makeText(this, "Error: Usted no tiene una Publicacion.\",", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }



}
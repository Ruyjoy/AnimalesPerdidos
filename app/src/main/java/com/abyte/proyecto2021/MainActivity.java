package com.abyte.proyecto2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abyte.proyecto2021.BD.dboMascotas;
import com.abyte.proyecto2021.BD.dboUsuarios;
import com.abyte.proyecto2021.Class.Mascota;
import com.abyte.proyecto2021.Class.Usuarios;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText user, pass;
    Button btnEntrar, btnRegistrar;
    dboUsuarios dbo;
    dboMascotas dbomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        dbo= dboUsuarios.getInstance(this);
        dbomas= dboMascotas.getInstance(this);

        user=(EditText)findViewById(R.id.user);
        pass=(EditText)findViewById(R.id.password);

        btnEntrar=(Button)findViewById(R.id.login);
        btnRegistrar=(Button)findViewById(R.id.registrar);

        btnEntrar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

        iniciarListaMascotas();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login:
                try {
                    String u = user.getText().toString();
                    String p = pass.getText().toString();

                    if(u.equals("") || p.equals("")){
                        Toast.makeText(this, "Error: Uno de los Campos esta Vacio", Toast.LENGTH_LONG).show();
                        user.setError("El campo no puede estar Vacio");
                        pass.setError("El campo no puede estar Vacio");

                    }else if(dbo.validar(u,p) == false){
                        Toast.makeText(this, "Error: Usuario o contraseña incorrecta.\",", Toast.LENGTH_LONG).show();
                    }else {
                        Usuarios usu = dbo.Login(u,p);
                        Intent i =new Intent(this, Principal.class);
                        i.putExtra("Usuario",usu.getNombreUsuario().toString());
                        startActivity(i);
                        finish();
                       // Toast.makeText(this, "Usuario Correcto.\",", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.registrar:
                Intent i =new Intent(this, Registros.class);
                startActivity(i);
                break;
        }


    }

    public String getStringImagen(Bitmap bmp){

        ByteArrayOutputStream bArry = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, bArry);
        byte[] imageBytes = bArry.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void iniciarListaMascotas(){

        try {
            Usuarios u1 = new Usuarios();
            u1.setNombreUsuario("Jose");
            u1.setPassword("1234");
            u1.setNombre("jose");
            u1.setTelefono("091777644");
            u1.setMail("Ruyjoy@hotmail.com");

            dbo.registrosdatos(u1);

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        Mascota mas =new Mascota();
        mas.setUser("Jose");
        mas.setTipoMascota("Gato");
        mas.setBarrioDeparta("Canelones");
        mas.setDescription("Perdido cerca del Pinar");

        Bitmap bitm = BitmapFactory.decodeResource(getResources(),
                R.drawable.mia);

        mas.setFoto(getStringImagen(bitm));
        dbomas.insertarMascota(mas);
        

    }
}
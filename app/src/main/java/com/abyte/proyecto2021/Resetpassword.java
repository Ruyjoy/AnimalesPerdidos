package com.abyte.proyecto2021;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abyte.proyecto2021.BD.dboMascotas;
import com.abyte.proyecto2021.BD.dboUsuarios;
import com.abyte.proyecto2021.Class.Usuarios;

import java.util.ArrayList;

public class Resetpassword extends AppCompatActivity implements View.OnClickListener{

    Button btnCancelar, btnCambiopass;
    EditText password, newpass, repitpass;
    String datos,nombre, tel, mail;
    dboUsuarios dbo;
    ArrayList<Usuarios> usuList= new ArrayList<Usuarios>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        getSupportActionBar().hide();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            datos = parametros.getString("Usuario");
        }

        dbo= dboUsuarios.getInstance(this);

        password =(EditText)findViewById(R.id.passUsuario);
        newpass =(EditText)findViewById(R.id.passUsu1);
        repitpass =(EditText)findViewById(R.id.passUsu2);

        llenardatos();

        repitpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strPass1 = newpass.getText().toString();
                String strPass2 = repitpass.getText().toString();
                if (!strPass1.equals(strPass2)) {
                    repitpass.setError("El pass no coinciden");
                }

            }
        });


        btnCambiopass = (Button) findViewById(R.id.AceptarPass);
        btnCancelar = (Button) findViewById(R.id.Cancelarpass);

        btnCambiopass.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.Cancelarpass:
                Intent i = new Intent(this, Settings.class);
                i.putExtra("Usuario",datos);
                startActivity(i);
                finish();
                break;


            case R.id.AceptarPass:
               modificarDatos(datos);
                break;


        }

    }


    public void modificarDatos(String u){

        try {

            if (password.getText().toString().isEmpty() || newpass.getText().toString().isEmpty() || repitpass.getText().toString().isEmpty()) {
                Toast.makeText(this, "Error: Uno de los Campos esta Vacio", Toast.LENGTH_LONG).show();
                newpass.setError("El Campo esta vacio");
                repitpass.setError("El Campo esta vacio");

                return;
            }

            String strPass1 = newpass.getText().toString();
            String strPass2 = repitpass.getText().toString();

            AlertDialog.Builder b =new AlertDialog.Builder(this);
            b.setMessage("Esta Seguro de cambiar el Pass ???");
            b.setCancelable(false);
            b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if(strPass1.equals(strPass2)) {

                        Usuarios usu = new Usuarios();
                        usu.setNombreUsuario(u);
                        usu.setPassword(newpass.getText().toString());
                        usu.setNombre(nombre);
                        usu.setTelefono(tel);
                        usu.setMail(mail);


                        dbo.updataUsuario(usu);

                        Intent p = new Intent(Resetpassword.this, MainActivity.class);
                        p.putExtra("Usuario", datos);
                        startActivity(p);
                        finish();
                        Toast.makeText(Resetpassword.this, "Modificacion Correcta.\",", Toast.LENGTH_LONG).show();
                    }

                    else {
                        Toast.makeText(Resetpassword.this, "La Contaseña no Coinciden.\",", Toast.LENGTH_LONG).show();
                    }

                }
            });
            b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            b.show();



        }catch (Exception e){
            Toast.makeText(this, "Error, comuniquese con el administrador", Toast.LENGTH_LONG).show();
        }

    }


    public void llenardatos(){

        try {
            usuList = dbo.selectUsuarios();

            for (Usuarios us : usuList) {
                if (us.getNombreUsuario().equals(datos)) {
                    password.setText(us.getPassword());
                    nombre = us.getNombre();
                    tel = us.getTelefono();
                    mail = us.getMail();
                }

            }
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
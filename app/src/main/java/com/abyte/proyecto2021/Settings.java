package com.abyte.proyecto2021;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abyte.proyecto2021.BD.dboMascotas;
import com.abyte.proyecto2021.BD.dboUsuarios;
import com.abyte.proyecto2021.Class.Mascota;
import com.abyte.proyecto2021.Class.Usuarios;

import java.util.ArrayList;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    Button btnEliminar, btnModificar,btnCancelar, btnCambiopass;
    EditText usuarioNombre, nombre, tel, mail;
    String datos, pass;
    dboUsuarios dbo;
    dboMascotas dbomas;
    ArrayList<Usuarios> usuList= new ArrayList<Usuarios>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().hide();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            datos = parametros.getString("Usuario");
        }

        dbo= dboUsuarios.getInstance(this);
        dbomas=dboMascotas.getInstance(this);


        usuarioNombre =(EditText)findViewById(R.id.nombreusuariousu);
        usuarioNombre.setText(datos);

        nombre =(EditText)findViewById(R.id.nombreUsu);
        tel =(EditText)findViewById(R.id.telUsu);
        mail =(EditText)findViewById(R.id.mailUsu);


        btnCambiopass = (Button) findViewById(R.id.CambioPass);
        btnModificar = (Button) findViewById(R.id.ModificarUsu);
        btnEliminar = (Button) findViewById(R.id.EliminarUsu);
        btnCancelar = (Button) findViewById(R.id.cancelarusu);


        btnCambiopass.setOnClickListener(this);
        btnModificar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        llenardatos();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cancelarusu:
                Intent i = new Intent(this, Principal.class);
                i.putExtra("Usuario",datos);
                startActivity(i);
                finish();
                break;

            case R.id.ModificarUsu:
                modificarDatos(datos);
                break;

            case R.id.CambioPass:
                Intent j = new Intent(this, Resetpassword.class);
                j.putExtra("Usuario",datos);
                startActivity(j);
                finish();
                break;

            case R.id.EliminarUsu:
                eliminarUsuario(datos);
                break;


        }
    }

    public void modificarDatos(String u){

        try {

            if (nombre.getText().toString().isEmpty() || tel.getText().toString().isEmpty() || mail.getText().toString().isEmpty()){
                Toast.makeText(this, "Error: Uno de los Campos esta Vacio", Toast.LENGTH_LONG).show();
                nombre.setError("El campo no puede estar Vacio");
                tel.setError("El campo no puede estar Vacio");
                mail.setError("El campo no puede estar Vacio");
                return;
            }

            Usuarios usu = new Usuarios();
            usu.setNombreUsuario(datos);
            usu.setPassword(pass);
            usu.setNombre(nombre.getText().toString());
            usu.setTelefono(tel.getText().toString());
            usu.setMail(mail.getText().toString());

            dbo.updataUsuario(usu);

            Intent i = new Intent(this, Principal.class);
            i.putExtra("Usuario", datos);
            startActivity(i);
            finish();
            Toast.makeText(this, "Modificacion Correcta.\",", Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
            Toast.makeText(this, "Error, comuniquese con el administrador", Toast.LENGTH_LONG).show();
        }

    }

    public void eliminarUsuario(String u){

        try {

            AlertDialog.Builder b =new AlertDialog.Builder(this);
            b.setMessage("Esta Seguro de Eliminar esta Cuenta ???");
            b.setCancelable(false);
            b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    AlertDialog.Builder c =new AlertDialog.Builder( Settings.this);
                    b.setMessage("Este usuario tiene una Publicacion. Desea eliminarla ?");
                    b.setCancelable(false);
                    b.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(dbomas.validarMascota(datos)){
                                dbomas.EliminarMascota(datos);
                            }
                            dbo.EliminarUsuario(u);
                            Intent j = new Intent(Settings.this, MainActivity.class);
                            j.putExtra("Usuario",datos);
                            startActivity(j);
                            finish();
                            Toast.makeText(Settings.this, "Se a Eliminado la cuenta", Toast.LENGTH_LONG).show();
                        }
                    });
                    b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbo.EliminarUsuario(u);
                            Intent j = new Intent(Settings.this, MainActivity.class);
                            j.putExtra("Usuario",datos);
                            startActivity(j);
                            finish();
                            Toast.makeText(Settings.this, "Se a Eliminado la cuenta", Toast.LENGTH_LONG).show();
                        }
                    });
                    b.show();
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
                        nombre.setText(us.getNombre());
                        tel.setText(us.getTelefono());
                        mail.setText(us.getMail());
                        pass = us.getPassword();
                    }

            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }



}
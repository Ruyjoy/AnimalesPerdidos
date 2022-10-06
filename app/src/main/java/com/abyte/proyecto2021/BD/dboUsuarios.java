package com.abyte.proyecto2021.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.abyte.proyecto2021.Class.Mascota;
import com.abyte.proyecto2021.Class.Usuarios;

import java.util.ArrayList;

public class dboUsuarios {

    ArrayList<Usuarios> lista;
    Context c;


    private static dboUsuarios instacia;

    private dboUsuarios(Context c) {
        this.c = c;
    }

    public static dboUsuarios getInstance(Context c){
        if(instacia == null){
            instacia = new dboUsuarios(c);
        }
        return instacia;
    }

    public boolean registrosdatos(Usuarios u){

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this.c,"BDusuarios",null,1);

        if(buscar(u.getNombreUsuario())== 0){
                SQLiteDatabase db = conn.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("nombreUsuario", u.getNombreUsuario());
                cv.put("pasword", u.getPassword());
                cv.put("nombre", u.getNombre());
                cv.put("telefono", u.getTelefono());
                cv.put("mail", u.getMail());
                Long res = db.insert("usuario", null, cv);
                db.close();
                return res >0;

        }else{ return false; }

    }

    public ArrayList<Usuarios> selectUsuarios(){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this.c,"BDusuarios",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        lista=new ArrayList<Usuarios>();
        lista.clear();
        Cursor cr=db.rawQuery("select * from usuario", null);
        if(cr!=null&&cr.moveToFirst()){
            do {
                Usuarios u=new Usuarios();
                u.setNombreUsuario(cr.getString(0));
                u.setPassword(cr.getString(1));
                u.setNombre(cr.getString(2));
                u.setTelefono(cr.getString(3));
                u.setMail(cr.getString(4));
                lista.add(u);

            }while (cr.moveToNext());
        }
        db.close();
        return lista;
    }

    public int buscar (String u){
        int x = 0;
        lista=selectUsuarios();
        for (Usuarios us:lista) {
            if(us.getNombreUsuario().equals(u)){
                x++;
            }
        }
        return x;
    }

    public boolean validar(String u, String p){
        lista=selectUsuarios();
        for (Usuarios us:lista) {
            if(us.getNombreUsuario().equals(u) && us.getPassword().equals(p)){
                return true;
            }
        }return false;
    }

    public Usuarios Login(String u, String p){
        lista=selectUsuarios();
        for (Usuarios us:lista) {
            if(us.getNombreUsuario().equals(u) && us.getPassword().equals(p)){
                return us;
            }
        }return null;
    }

    public void updataUsuario (Usuarios u){

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this.c,"BDusuarios",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] Parametros ={u.getNombreUsuario()};
        ContentValues cv = new ContentValues();
        cv.put("nombreUsuario", u.getNombreUsuario());
        cv.put("pasword", u.getPassword());
        cv.put("nombre", u.getNombre());
        cv.put( "telefono", u.getTelefono());
        cv.put("mail", u.getMail());
        db.update("usuario", cv,"nombreUsuario"+"=?",  Parametros);
        db.close();

    }

    public void EliminarUsuario(String u){

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this.c,"BDusuarios",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] Parametros ={u};


        db.delete("usuario", "nombreUsuario"+"=?",  Parametros );
        db.close();

    }
}

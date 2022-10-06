package com.abyte.proyecto2021.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.abyte.proyecto2021.Class.Mascota;

import java.util.ArrayList;

public class dboMascotas {

    Context c;
    ArrayList<Mascota> Mascotalista;


    private static dboMascotas instacia;

    private dboMascotas(Context c) {
        this.c = c;
    }

    public static dboMascotas getInstance(Context c){
        if(instacia == null){
            instacia = new dboMascotas(c);
        }
        return instacia;
    }


    public boolean insertarMascota (Mascota m){

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this.c,"BDusuarios",null,1);

        if (buscarMascota(m.getUser())== 0){
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("user", m.getUser());
            cv.put("tipoMascota", m.getTipoMascota());
            cv.put("barrioDeparta", m.getBarrioDeparta());
            cv.put( "description", m.getDescription());
            cv.put("foto", m.getFoto());
            boolean res = (db.insert("mascota",null,cv)>0);
            db.close();
            return res;

        }else{ return false; }
    }

    public ArrayList<Mascota> selectMascota(){

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this.c,"BDusuarios",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        Mascotalista=new ArrayList<Mascota>();
        Mascotalista.clear();

        Cursor cr=db.rawQuery("select * from mascota", null);
        if(cr!=null&&cr.moveToFirst()){
            do {
                Mascota ma =new Mascota();
                ma.setUser(cr.getString(0));
                ma.setTipoMascota(cr.getString(1));
                ma.setBarrioDeparta(cr.getString(2));
                ma.setDescription(cr.getString(3));
                ma.setFoto(cr.getString(4));
                Mascotalista.add(ma);

            }while (cr.moveToNext());
        }
        db.close();
        return Mascotalista;
    }

    public boolean validarMascota(String u){
        Mascotalista=selectMascota();
        for (Mascota us:Mascotalista) {
            if(us.getUser().equals(u)){
                return true;
            }
        }return false;
    }

    public int buscarMascota (String u){
        int x = 0;
        Mascotalista=selectMascota();
        for (Mascota us:Mascotalista) {
            if(us.getUser().equals(u)){
                x++;
            }
        }
        return x;
    }

    public void updataMascota (Mascota m){

            ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this.c,"BDusuarios",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();

            String[] Parametros ={m.getUser().toString()};
            ContentValues cv = new ContentValues();
            cv.put("user", m.getUser());
            cv.put("tipoMascota", m.getTipoMascota());
            cv.put("barrioDeparta", m.getBarrioDeparta());
            cv.put( "description", m.getDescription());
            cv.put("foto", m.getFoto());
            db.update("mascota", cv,"user"+"=?",  Parametros);

    }

    public void EliminarMascota(String u){

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this.c,"BDusuarios",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] Parametros ={u};


        db.delete("mascota", "user"+"=?",  Parametros );
        db.close();

    }


}

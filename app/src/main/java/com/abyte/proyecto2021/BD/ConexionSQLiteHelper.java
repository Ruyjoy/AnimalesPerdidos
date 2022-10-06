package com.abyte.proyecto2021.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    final String CREAR_TABLA_USIARIO= "Create Table if Not Exists usuario (nombreUsuario text primary key , pasword text, nombre text, telefono text, mail text)";
    final String CREAR_TABLA_MASCOTAS= "Create Table if Not Exists mascota(user text primary key , tipoMascota text, barrioDeparta text, description text, foto blob,FOREIGN KEY(user) REFERENCES usuario(nombreUsuario))";

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAR_TABLA_USIARIO);
        db.execSQL( CREAR_TABLA_MASCOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {

        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS mascota");
        this.onCreate(db);

    }
}

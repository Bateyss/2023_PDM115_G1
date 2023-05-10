package com.ues.sv.proyecto.controladministrativoapp.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ues.sv.proyecto.controladministrativoapp.dao.PersonaDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.UsuarioDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;

@Database(entities = {Persona.class, Usuario.class}, version = 1)
public abstract class DatabaseHandler extends RoomDatabase {

    private static volatile DatabaseHandler INSTANCE;

    public static DatabaseHandler getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseHandler.class, "CONTROLADMIN.s3db").build();
                }
            }
        }
        return INSTANCE;
    }

    // TODO agregar clases con  @Dao para instanciarlas con la db
    public abstract PersonaDao personaDao();

    public abstract UsuarioDao usuarioDao();

}

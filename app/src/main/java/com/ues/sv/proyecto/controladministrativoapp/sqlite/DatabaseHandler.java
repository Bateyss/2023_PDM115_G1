package com.ues.sv.proyecto.controladministrativoapp.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ues.sv.proyecto.controladministrativoapp.dao.CicloDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.CoordinadorDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.CursoDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.DocenteDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.EvaluacionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.MateriaDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.PersonaDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.TipoEvaluacionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.UsuarioDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;
import com.ues.sv.proyecto.controladministrativoapp.models.Curso;
import com.ues.sv.proyecto.controladministrativoapp.models.Docente;
import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;

@Database(entities = {Persona.class, Usuario.class, Ciclo.class, Coordinador.class, Curso.class, Docente.class, Evaluacion.class, Materia.class, TipoEvaluacion.class}, version = 1)
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

    public abstract CicloDao cicloDao();

    public abstract CoordinadorDao coordinadorDao();

    public abstract CursoDao cursoDao();

    public abstract DocenteDao docenteDao();

    public abstract EvaluacionDao evaluacionDao();

    public abstract MateriaDao materiaDao();

    public abstract TipoEvaluacionDao tipoEvaluacionDao();

}

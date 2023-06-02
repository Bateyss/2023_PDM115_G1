package com.ues.sv.proyecto.controladministrativoapp.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ues.sv.proyecto.controladministrativoapp.dao.AlumnoDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.CicloDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.CoordinadorDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.CursoDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.DetalleRevisionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.DocenteDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.EncargadoImpresionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.EvaluacionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.ImpresionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.InscripcionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.MateriaDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.MotivoErrorImpresionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.ParametrosDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.ParticipanteRevisionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.PersonaDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.RevisionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.SolicitudRevisionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.TipoEvaluacionDao;
import com.ues.sv.proyecto.controladministrativoapp.dao.UsuarioDao;
import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;
import com.ues.sv.proyecto.controladministrativoapp.models.Curso;
import com.ues.sv.proyecto.controladministrativoapp.models.DetalleRevision;
import com.ues.sv.proyecto.controladministrativoapp.models.Docente;
import com.ues.sv.proyecto.controladministrativoapp.models.EncargadoImpresion;
import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.models.Inscripcion;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.models.MotivoErrorImpresion;
import com.ues.sv.proyecto.controladministrativoapp.models.Parametros;
import com.ues.sv.proyecto.controladministrativoapp.models.ParticipanteRevision;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.models.Revision;
import com.ues.sv.proyecto.controladministrativoapp.models.SolicitudRevision;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;

@Database(entities = {Persona.class,
        Usuario.class, Ciclo.class, Coordinador.class, Curso.class,
        Docente.class, Evaluacion.class, Materia.class, TipoEvaluacion.class,
        Alumno.class, DetalleRevision.class, EncargadoImpresion.class, Impresion.class,
        Inscripcion.class, MotivoErrorImpresion.class, ParticipanteRevision.class, Revision.class,
        SolicitudRevision.class, Parametros.class}, version = 1)
public abstract class DatabaseHandler extends RoomDatabase {

    private static volatile DatabaseHandler INSTANCE;

    public static DatabaseHandler getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseHandler.class, "CONTROLADMIN.s3db")
                            .fallbackToDestructiveMigration()
                            .fallbackToDestructiveMigrationOnDowngrade()
                            .build();
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

    public abstract AlumnoDao alumnoDao();

    public abstract DetalleRevisionDao detalleRevisionDao();

    public abstract EncargadoImpresionDao encargadoImpresionDao();

    public abstract ImpresionDao impresionDao();

    public abstract InscripcionDao inscripcionDao();

    public abstract MotivoErrorImpresionDao motivoErrorImpresionDao();

    public abstract ParticipanteRevisionDao participanteRevisionDao();

    public abstract RevisionDao revisionDao();

    public abstract SolicitudRevisionDao solicitudRevisionDao();

    public abstract ParametrosDao parametrosDao();
}

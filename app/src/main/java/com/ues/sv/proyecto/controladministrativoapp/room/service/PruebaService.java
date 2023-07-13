package com.ues.sv.proyecto.controladministrativoapp.room.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;
import com.ues.sv.proyecto.controladministrativoapp.models.Curso;
import com.ues.sv.proyecto.controladministrativoapp.models.Docente;
import com.ues.sv.proyecto.controladministrativoapp.models.EncargadoImpresion;
import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.Inscripcion;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.models.Parametros;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.models.SolicitudRevision;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.room.conf.DatabaseHandler;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.AlumnoDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.CicloDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.CoordinadorDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.CursoDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.DocenteDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.EncargadoImpresionDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.EvaluacionDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.InscripcionDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.MateriaDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.ParametrosDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.PersonaDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.SolicitudRevisionDao;
import com.ues.sv.proyecto.controladministrativoapp.room.dao.TipoEvaluacionDao;
import com.ues.sv.proyecto.controladministrativoapp.utils.DisposableUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PruebaService {

    SingleQueue singleQueue = null;

    private AlumnoDao alumnoDao;
    private ParametrosDao parametrosDao;
    private PersonaDao personaDao;
    private DocenteDao docenteDao;
    private EncargadoImpresionDao encargadoImpresionDao;
    private CoordinadorDao coordinadorDao;
    private CicloDao cicloDao;
    private MateriaDao materiaDao;
    private CursoDao cursoDao;
    private InscripcionDao inscripcionDao;
    private TipoEvaluacionDao tipoEvaluacionDao;
    private EvaluacionDao evaluacionDao;
    private SolicitudRevisionDao solicitudRevisionDao;
    private Context context;

    public PruebaService(Context context) {
        DatabaseHandler handler = DatabaseHandler.getInstance(context);
        this.context = context;
        alumnoDao = handler.alumnoDao();
        parametrosDao = handler.parametrosDao();
        personaDao = handler.personaDao();
        docenteDao = handler.docenteDao();
        encargadoImpresionDao = handler.encargadoImpresionDao();
        coordinadorDao = handler.coordinadorDao();
        cicloDao = handler.cicloDao();
        materiaDao = handler.materiaDao();
        cursoDao = handler.cursoDao();
        inscripcionDao = handler.inscripcionDao();
        tipoEvaluacionDao = handler.tipoEvaluacionDao();
        evaluacionDao = handler.evaluacionDao();
        solicitudRevisionDao = handler.solicitudRevisionDao();
    }

    public void cargarDatosDePrueba() {
        List<TipoEvaluacion> tipoEvaluacions = new ArrayList<>();
        tipoEvaluacions.add(new TipoEvaluacion(1L, "ORDINARIO"));
        tipoEvaluacions.add(new TipoEvaluacion(2L, "REPETIDO"));
        tipoEvaluacions.add(new TipoEvaluacion(3L, "DIFERIDO"));
        tipoEvaluacions.forEach(tipoEvaluacion -> {
            addSingleQueue(tipoEvaluacionDao.insertTipoEvaluacion(tipoEvaluacion));
        });

        List<Materia> materias = new ArrayList<>();
        materias.add(new Materia(1L, "Materia 1"));
        materias.add(new Materia(2L, "Materia 2"));
        materias.add(new Materia(3L, "Materia 3"));
        materias.add(new Materia(4L, "Materia 4"));
        materias.forEach(materia -> {
            addSingleQueue(materiaDao.insertMateria(materia));
        });

        List<Ciclo> ciclos = new ArrayList<>();
        ciclos.add(new Ciclo(1L, "01", "2023"));
        ciclos.add(new Ciclo(2L, "02", "2023"));
        ciclos.add(new Ciclo(3L, "01", "2022"));
        ciclos.add(new Ciclo(4L, "02", "2022"));
        ciclos.forEach(ciclo -> addSingleQueue(cicloDao.insertCiclo(ciclo)));

        List<Parametros> parametros = new ArrayList<>();
        parametros.add(new Parametros(1L, "MAX_DAY_SOL_REVISION", "10", 1));
        parametros.add(new Parametros(2L, "MAX_DAY_SOL_DIFERIR", "10", 2));
        parametros.add(new Parametros(3L, "MAX_DAY_SOL_REPETIR", "10", 3));
        parametros.forEach(parametro -> {
            addSingleQueue(parametrosDao.insertParametros(parametro));
        });

        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1L, "PERSONA", "PRUEBA 1", "012345", "M"));
        personas.add(new Persona(2L, "PERSONA", "PRUEBA 2", "056789", "M"));
        personas.add(new Persona(3L, "PERSONA", "PRUEBA 3", "098765", "M"));
        personas.forEach(persona -> addSingleQueue(personaDao.insertPersona(persona)));

        List<Alumno> alumnos = new ArrayList<>();
        alumnos.add(new Alumno(1L, personas.get(0), "AA" + personas.get(0).getIdentificacion()));
        alumnos.add(new Alumno(2L, personas.get(1), "AA" + personas.get(1).getIdentificacion()));
        alumnos.add(new Alumno(3L, personas.get(2), "AA" + personas.get(2).getIdentificacion()));
        alumnos.forEach(alumno -> addSingleQueue(alumnoDao.insertAlumno(alumno)));

        List<Docente> docentes = new ArrayList<>();
        docentes.add(new Docente(1L, personas.get(0), new Date()));
        docentes.add(new Docente(2L, personas.get(1), new Date()));
        docentes.add(new Docente(3L, personas.get(2), new Date()));
        docentes.forEach(docente -> addSingleQueue(docenteDao.insertDocente(docente)));

        List<EncargadoImpresion> encargadoImpresions = new ArrayList<>();
        encargadoImpresions.add(new EncargadoImpresion(1L, personas.get(0), "Area x"));
        encargadoImpresions.add(new EncargadoImpresion(2L, personas.get(1), "Area x"));
        encargadoImpresions.add(new EncargadoImpresion(3L, personas.get(2), "Area x"));
        encargadoImpresions.forEach(encargadoImpresion -> addSingleQueue(encargadoImpresionDao.insertEncargadoImpresion(encargadoImpresion)));

        List<Coordinador> coordinadors = new ArrayList<>();
        coordinadors.add(new Coordinador(1L, personas.get(0), new Date()));
        coordinadors.add(new Coordinador(2L, personas.get(1), new Date()));
        coordinadors.add(new Coordinador(3L, personas.get(2), new Date()));
        coordinadors.forEach(coordinador -> addSingleQueue(coordinadorDao.insertCoordinador(coordinador)));

        List<Curso> cursos = new ArrayList<>();
        cursos.add(new Curso(1L, ciclos.get(0), materias.get(0), docentes.get(0), coordinadors.get(0)));
        cursos.add(new Curso(2L, ciclos.get(1), materias.get(1), docentes.get(1), coordinadors.get(1)));
        cursos.add(new Curso(3L, ciclos.get(2), materias.get(2), docentes.get(2), coordinadors.get(2)));
        cursos.forEach(curso -> addSingleQueue(cursoDao.insertCurso(curso)));

        List<Inscripcion> inscripciones = new ArrayList<>();
        inscripciones.add(new Inscripcion(1L, alumnos.get(0), cursos.get(0)));
        inscripciones.add(new Inscripcion(2L, alumnos.get(1), cursos.get(1)));
        inscripciones.add(new Inscripcion(3L, alumnos.get(2), cursos.get(2)));
        inscripciones.forEach(inscripcion -> addSingleQueue(inscripcionDao.insertInscripcion(inscripcion)));

        List<Evaluacion> evaluaciones = new ArrayList<>();
        evaluaciones.add(new Evaluacion(1L, cursos.get(0), tipoEvaluacions.get(0), 1));
        evaluaciones.add(new Evaluacion(2L, cursos.get(0), tipoEvaluacions.get(1), 1));
        evaluaciones.add(new Evaluacion(3L, cursos.get(0), tipoEvaluacions.get(0), 2));
        evaluaciones.forEach(evaluacion -> addSingleQueue(evaluacionDao.insertEvaluacion(evaluacion)));

        List<SolicitudRevision> solicitudRevisiones = new ArrayList<>();
        solicitudRevisiones.add(new SolicitudRevision(1L, inscripciones.get(0), evaluaciones.get(1), "asd", new Date(), 1));
        solicitudRevisiones.add(new SolicitudRevision(2L, inscripciones.get(1), evaluaciones.get(1), "asd", new Date(), 1));
        solicitudRevisiones.add(new SolicitudRevision(3L, inscripciones.get(2), evaluaciones.get(2), "asd", new Date(), 1));
        solicitudRevisiones.forEach(solicitudRevision -> addSingleQueue(solicitudRevisionDao.insertSolicitudRevision(solicitudRevision)));

        excecuteQueue();
    }

    public void excecuteQueue() {
        //volcar cola
        if (singleQueue != null) {
            SingleQueue nuevaCola = null;

            while (singleQueue.hasNext()) {
                singleQueue = singleQueue.getNext();

                SingleQueue singleQueue1 = new SingleQueue();
                singleQueue1.setSingle(singleQueue.getSingle());
                if (nuevaCola != null) singleQueue1.setNext(nuevaCola);

                nuevaCola = singleQueue1;
            }

            // utilizar nueva cola
            if (nuevaCola != null) {
                Toast.makeText(context, "Ejecutando cola", Toast.LENGTH_SHORT).show();
                siguiente(nuevaCola);
            }

        }else{
            Toast.makeText(context, " cola vacia", Toast.LENGTH_SHORT).show();
        }
    }

    // metodo con recursividad
    public void siguiente(SingleQueue cola) {
        DisposableUtils.addDisposable(cola.getSingle().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterSuccess(response -> {
                    Log.w("response", response.toString());
                    if (cola.hasNext()) {
                        siguiente(cola.getNext());
                    }
                })
                .subscribe(response -> {
                }, throwable -> Log.e("ADD_FLOWABLES_COMPOSITE", "Error ejecutar single"
                        , throwable)));
    }

    public void addSingleQueue(Single<?> singleAction) {
        SingleQueue singleQueue1 = new SingleQueue();
        singleQueue1.setSingle(singleAction);
        if (singleQueue != null) singleQueue1.setNext(singleQueue);

        singleQueue = singleQueue1;
    }

    class SingleQueue {
        private Single<?> single;
        private SingleQueue next;

        public SingleQueue() {
            this.next = null;
        }

        public SingleQueue(Disposable disposable, Single<?> single) {
            this.single = single;
            this.next = next;
        }

        public Single<?> getSingle() {
            return single;
        }

        public void setSingle(Single<?> single) {
            this.single = single;
        }

        public SingleQueue getNext() {
            return next;
        }

        public void setNext(SingleQueue next) {
            this.next = next;
        }

        public boolean hasNext() {
            return this.next != null;
        }
    }

}

package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
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
import com.ues.sv.proyecto.controladministrativoapp.rest.service.AlumnoRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.CicloRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.CoordinadorRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.CursoRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.DocenteRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.EncargadoImpresionRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.EvaluacionRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.InscripcionRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.MateriaRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.ParametrosRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.PersonaRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.SolicitudRevisionRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.TipoEvaluacionRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MaterialButton btnParametros, btnEvaluaciones, btnCargarDatos;

    private AlumnoRestService alumnoRestService;
    private ParametrosRestService parametrosRestService;
    private PersonaRestService personaRestService;
    private DocenteRestService docenteRestService;
    private EncargadoImpresionRestService encargadoImpresionRestService;
    private CoordinadorRestService coordinadorRestService;
    private CicloRestService cicloRestService;
    private MateriaRestService materiaRestService;
    private CursoRestService cursoRestService;
    private InscripcionRestService inscripcionRestService;
    private TipoEvaluacionRestService tipoEvaluacionRestService;
    private EvaluacionRestService evaluacionRestService;
    private SolicitudRevisionRestService solicitudRevisionRestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btnParametros = findViewById(R.id.btn_parametros);
        btnEvaluaciones = findViewById(R.id.btn_evaluaciones);
        btnCargarDatos = findViewById(R.id.btn_cargar_datos);
        recyclerView = findViewById(R.id.recyclerList);

        alumnoRestService = new AlumnoRestService();
        parametrosRestService = new ParametrosRestService();
        personaRestService = new PersonaRestService();
        docenteRestService = new DocenteRestService();
        encargadoImpresionRestService = new EncargadoImpresionRestService();
        coordinadorRestService = new CoordinadorRestService();
        cicloRestService = new CicloRestService();
        materiaRestService = new MateriaRestService();
        cursoRestService = new CursoRestService();
        inscripcionRestService = new InscripcionRestService();
        tipoEvaluacionRestService = new TipoEvaluacionRestService();
        evaluacionRestService = new EvaluacionRestService();
        solicitudRevisionRestService = new SolicitudRevisionRestService();

        btnParametros.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ParametrosActivity.class);
            startActivity(intent);
        });

        btnEvaluaciones.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), VerEvaluacionActivity.class);
            startActivity(intent);
        });

        btnCargarDatos.setOnClickListener(v -> comprobarBaseDeDatos());

        cargarRecyclerView();
        onBack();
    }

    private void comprobarBaseDeDatos() {
        evaluacionRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Evaluacion>>() {
            @Override
            public void onCallBack(List<Evaluacion> evaluacions) {
                if (!evaluacions.isEmpty()) {
                    Toast.makeText(InicioActivity.this, "Ya existen datos", Toast.LENGTH_SHORT).show();
                    Toast.makeText(InicioActivity.this, "BORRAR CACHE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onThrow(Throwable throwable) {
                cargarDatosDePrueba();
            }
        });
    }

    private void cargarDatosDePrueba() {
        List<TipoEvaluacion> tipoEvaluacions = new ArrayList<>();
        tipoEvaluacions.add(new TipoEvaluacion(1L, "ORDINARIO"));
        tipoEvaluacions.add(new TipoEvaluacion(2L, "REPETIDO"));
        tipoEvaluacions.add(new TipoEvaluacion(3L, "DIFERIDO"));
        tipoEvaluacions.forEach(tipoEvaluacion -> {
            tipoEvaluacionRestService.registrarEntidad(tipoEvaluacion, new CallBackDisposableInterface() {
                @Override
                public void onCallBack(Object o) {
                    TipoEvaluacion tipoEvaluacionNuevo = (TipoEvaluacion) o;
                    tipoEvaluacion.setIdTipoEvaluacion(tipoEvaluacionNuevo.getIdTipoEvaluacion());
                }

                @Override
                public void onThrow(Throwable throwable) {

                }
            });
        });
        tipoEvaluacions.get(0).setIdTipoEvaluacion(1L);
        tipoEvaluacions.get(1).setIdTipoEvaluacion(2L);
        tipoEvaluacions.get(2).setIdTipoEvaluacion(3L);

        List<Materia> materias = new ArrayList<>();
        materias.add(new Materia(1L, "Materia 1"));
        materias.add(new Materia(2L, "Materia 2"));
        materias.add(new Materia(3L, "Materia 3"));
        materias.add(new Materia(4L, "Materia 4"));
        materias.forEach(materia -> {
            materiaRestService.registrarEntidad(materia, new CallBackDisposableInterface() {
                @Override
                public void onCallBack(Object o) {
                    Materia materia1 = (Materia) o;
                    materia.setIdMateria(materia1.getIdMateria());
                }

                @Override
                public void onThrow(Throwable throwable) {

                }
            });
        });
        materias.get(0).setIdMateria(1L);
        materias.get(1).setIdMateria(2L);
        materias.get(2).setIdMateria(3L);
        materias.get(3).setIdMateria(4L);

        List<Ciclo> ciclos = new ArrayList<>();
        ciclos.add(new Ciclo(1L, "01", "2023"));
        ciclos.add(new Ciclo(2L, "02", "2023"));
        ciclos.add(new Ciclo(3L, "01", "2022"));
        ciclos.add(new Ciclo(4L, "02", "2022"));
        ciclos.forEach(ciclo -> cicloRestService.registrarEntidad(ciclo, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                Ciclo ciclo1 = (Ciclo) o;
                ciclo.setIdCiclo(ciclo1.getIdCiclo());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        ciclos.get(0).setIdCiclo(1L);
        ciclos.get(1).setIdCiclo(2L);
        ciclos.get(2).setIdCiclo(3L);
        ciclos.get(3).setIdCiclo(4L);

        List<Parametros> parametros = new ArrayList<>();
        parametros.add(new Parametros(1L, "MAX_DAY_SOL_REVISION", "10", 1));
        parametros.add(new Parametros(2L, "MAX_DAY_SOL_DIFERIR", "10", 2));
        parametros.add(new Parametros(3L, "MAX_DAY_SOL_REPETIR", "10", 3));
        parametros.forEach(parametro -> {
            parametrosRestService.registrarEntidad(parametro, new CallBackDisposableInterface() {
                @Override
                public void onCallBack(Object o) {
                    Parametros parametros1 = (Parametros) o;
                    parametro.setIdParametro(parametros1.getIdParametro());
                }

                @Override
                public void onThrow(Throwable throwable) {

                }
            });
        });

        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona(1L, "PERSONA", "PRUEBA 1", "012345", "M"));
        personas.add(new Persona(2L, "PERSONA", "PRUEBA 2", "056789", "M"));
        personas.add(new Persona(3L, "PERSONA", "PRUEBA 3", "098765", "M"));
        personas.forEach(persona -> personaRestService.registrarEntidad(persona, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                Persona persona1 = (Persona) o;
                persona.setIdPersona(persona1.getIdPersona());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        personas.get(0).setIdPersona(1L);
        personas.get(1).setIdPersona(2L);
        personas.get(2).setIdPersona(3L);

        List<Alumno> alumnos = new ArrayList<>();
        alumnos.add(new Alumno(1L, personas.get(0), "AA" + personas.get(0).getIdentificacion()));
        alumnos.add(new Alumno(2L, personas.get(1), "AA" + personas.get(1).getIdentificacion()));
        alumnos.add(new Alumno(3L, personas.get(2), "AA" + personas.get(2).getIdentificacion()));
        alumnos.forEach(alumno -> alumnoRestService.registrarEntidad(alumno, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                Alumno alumno1 = (Alumno) o;
                alumno.setIdAlumno(alumno1.getIdAlumno());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        alumnos.get(0).setIdAlumno(1L);
        alumnos.get(1).setIdAlumno(2L);
        alumnos.get(2).setIdAlumno(3L);

        List<Docente> docentes = new ArrayList<>();
        docentes.add(new Docente(1L, personas.get(0), new Date()));
        docentes.add(new Docente(2L, personas.get(1), new Date()));
        docentes.add(new Docente(3L, personas.get(2), new Date()));
        docentes.forEach(docente -> docenteRestService.registrarEntidad(docente, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                Docente docente1 = (Docente) o;
                docente.setIdDocente(docente1.getIdDocente());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        docentes.get(0).setIdDocente(1L);
        docentes.get(1).setIdDocente(2L);
        docentes.get(2).setIdDocente(3L);

        List<EncargadoImpresion> encargadoImpresions = new ArrayList<>();
        encargadoImpresions.add(new EncargadoImpresion(1L, personas.get(0), "Area x"));
        encargadoImpresions.add(new EncargadoImpresion(2L, personas.get(1), "Area x"));
        encargadoImpresions.add(new EncargadoImpresion(3L, personas.get(2), "Area x"));
        encargadoImpresions.forEach(encargadoImpresion -> encargadoImpresionRestService.registrarEntidad(encargadoImpresion, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                EncargadoImpresion encargadoImpresion1 = (EncargadoImpresion) o;
                encargadoImpresion.setIdEncargado((encargadoImpresion1.getIdEncargado()));
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        encargadoImpresions.get(0).setIdEncargado(1L);
        encargadoImpresions.get(1).setIdEncargado(2L);
        encargadoImpresions.get(2).setIdEncargado(3L);

        List<Coordinador> coordinadors = new ArrayList<>();
        coordinadors.add(new Coordinador(1L, personas.get(0), new Date()));
        coordinadors.add(new Coordinador(2L, personas.get(1), new Date()));
        coordinadors.add(new Coordinador(3L, personas.get(2), new Date()));
        coordinadors.forEach(coordinador -> coordinadorRestService.registrarEntidad(coordinador, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                Coordinador coordinador1 = (Coordinador) o;
                coordinador.setIdCoordinador(coordinador1.getIdCoordinador());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        coordinadors.get(0).setIdCoordinador(1L);
        coordinadors.get(1).setIdCoordinador(2L);
        coordinadors.get(2).setIdCoordinador(3L);

        List<Curso> cursos = new ArrayList<>();
        cursos.add(new Curso(1L, ciclos.get(0), materias.get(0), docentes.get(0), coordinadors.get(0)));
        cursos.add(new Curso(2L, ciclos.get(1), materias.get(1), docentes.get(1), coordinadors.get(1)));
        cursos.add(new Curso(3L, ciclos.get(2), materias.get(2), docentes.get(2), coordinadors.get(2)));
        cursos.forEach(curso -> cursoRestService.registrarEntidad(curso, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                Curso curso1 = (Curso) o;
                curso.setIdCurso(curso1.getIdCurso());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        cursos.get(0).setIdCurso(1L);
        cursos.get(1).setIdCurso(2L);
        cursos.get(2).setIdCurso(3L);

        List<Inscripcion> inscripciones = new ArrayList<>();
        inscripciones.add(new Inscripcion(1L, alumnos.get(0), cursos.get(0)));
        inscripciones.add(new Inscripcion(2L, alumnos.get(1), cursos.get(1)));
        inscripciones.add(new Inscripcion(3L, alumnos.get(2), cursos.get(2)));
        inscripciones.forEach(inscripcion -> inscripcionRestService.registrarEntidad(inscripcion, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                Inscripcion inscripcion1 = (Inscripcion) o;
                inscripcion.setIdInscripcion((inscripcion1.getIdInscripcion()));
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        inscripciones.get(0).setIdInscripcion(1L);
        inscripciones.get(1).setIdInscripcion(2L);
        inscripciones.get(2).setIdInscripcion(3L);

        List<Evaluacion> evaluaciones = new ArrayList<>();
        evaluaciones.add(new Evaluacion(1L, cursos.get(0), tipoEvaluacions.get(0), 1));
        evaluaciones.add(new Evaluacion(2L, cursos.get(0), tipoEvaluacions.get(1), 1));
        evaluaciones.add(new Evaluacion(3L, cursos.get(0), tipoEvaluacions.get(0), 2));
        evaluaciones.forEach(evaluacion -> evaluacionRestService.registrarEntidad(evaluacion, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                Evaluacion evaluacion1 = (Evaluacion) o;
                evaluacion.setIdEvaluacion(evaluacion1.getIdEvaluacion());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));
        evaluaciones.get(0).setIdEvaluacion(1L);
        evaluaciones.get(1).setIdEvaluacion(2L);
        evaluaciones.get(2).setIdEvaluacion(3L);

        List<SolicitudRevision> solicitudRevisiones = new ArrayList<>();
        solicitudRevisiones.add(new SolicitudRevision(1L, inscripciones.get(0), evaluaciones.get(1), "asd", new Date(), 1));
        solicitudRevisiones.add(new SolicitudRevision(2L, inscripciones.get(1), evaluaciones.get(1), "asd", new Date(), 1));
        solicitudRevisiones.add(new SolicitudRevision(3L, inscripciones.get(2), evaluaciones.get(2), "asd", new Date(), 1));
        solicitudRevisiones.forEach(solicitudRevision -> solicitudRevisionRestService.registrarEntidad(solicitudRevision, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                SolicitudRevision solicitudRevision1 = (SolicitudRevision) o;
                solicitudRevision.setIdSolicitudRevision(solicitudRevision1.getIdSolicitudRevision());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        }));

    }

    private void cargarRecyclerView() {
        List<Class<?>> objects = new ArrayList<>();
        objects.add(VerCiclosActivity.class);
        objects.add(VerCoordinadoresActivity.class);
        objects.add(VerCursoActivity.class);
        objects.add(VerDocentesActivity.class);
        objects.add(VerEvaluacionActivity.class);
        objects.add(VerMateriaActivity.class);
        objects.add(VerTipoEvaluacionActivity.class);
        objects.add(VerAlumnosActivity.class);

        OnlyTxtRecyclerAdapter<Class<?>> recyclerAdapter = new OnlyTxtRecyclerAdapter<Class<?>>(objects, getBaseContext(), new OnlyTxtInterface<Class<?>>() {
            @Override
            public void imprimirdatos(MaterialTextView textView, Class<?> aClass) {
                textView.setText(aClass.getSimpleName().replace("Ver", "").replace("Activity", ""));
            }

            @Override
            public void onItemClick(ConstraintLayout constraint, Class<?> aClass, int position) {
                try {
                    Intent intent = new Intent(getBaseContext(), aClass);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
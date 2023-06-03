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
import com.ues.sv.proyecto.controladministrativoapp.service.AlumnoService;
import com.ues.sv.proyecto.controladministrativoapp.service.CicloService;
import com.ues.sv.proyecto.controladministrativoapp.service.CoordinadorService;
import com.ues.sv.proyecto.controladministrativoapp.service.CursoService;
import com.ues.sv.proyecto.controladministrativoapp.service.DocenteService;
import com.ues.sv.proyecto.controladministrativoapp.service.EncargadoImpresionService;
import com.ues.sv.proyecto.controladministrativoapp.service.EvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.service.InscripcionService;
import com.ues.sv.proyecto.controladministrativoapp.service.MateriaService;
import com.ues.sv.proyecto.controladministrativoapp.service.ParametrosService;
import com.ues.sv.proyecto.controladministrativoapp.service.PersonaService;
import com.ues.sv.proyecto.controladministrativoapp.service.SolicitudRevisionService;
import com.ues.sv.proyecto.controladministrativoapp.service.TipoEvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MaterialButton btnParametros, btnEvaluaciones, btnCargarDatos;

    private AlumnoService alumnoService;
    private ParametrosService parametrosService;
    private PersonaService personaService;
    private DocenteService docenteService;
    private EncargadoImpresionService encargadoImpresionService;
    private CoordinadorService coordinadorService;
    private CicloService cicloService;
    private MateriaService materiaService;
    private CursoService cursoService;
    private InscripcionService inscripcionService;
    private TipoEvaluacionService tipoEvaluacionService;
    private EvaluacionService evaluacionService;
    private SolicitudRevisionService solicitudRevisionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btnParametros = findViewById(R.id.btn_parametros);
        btnEvaluaciones = findViewById(R.id.btn_evaluaciones);
        btnCargarDatos = findViewById(R.id.btn_cargar_datos);
        recyclerView = findViewById(R.id.recyclerList);

        alumnoService = new AlumnoService(getApplicationContext());
        parametrosService = new ParametrosService(getApplicationContext());
        personaService = new PersonaService(getApplicationContext());
        docenteService = new DocenteService(getApplicationContext());
        encargadoImpresionService = new EncargadoImpresionService(getApplicationContext());
        coordinadorService = new CoordinadorService(getApplicationContext());
        cicloService = new CicloService(getApplicationContext());
        materiaService = new MateriaService(getApplicationContext());
        cursoService = new CursoService(getApplicationContext());
        inscripcionService = new InscripcionService(getApplicationContext());
        tipoEvaluacionService = new TipoEvaluacionService(getApplicationContext());
        evaluacionService = new EvaluacionService(getApplicationContext());
        solicitudRevisionService = new SolicitudRevisionService(getApplicationContext());

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
        evaluacionService.obtenerListaEntidad(new CallBackDisposableInterface<List<Evaluacion>>() {
            @Override
            public void onCallBack(List<Evaluacion> evaluacions) {
                if (evaluacions.isEmpty()) cargarDatosDePrueba();
                else {
                    Toast.makeText(InicioActivity.this, "Ya existen datos", Toast.LENGTH_SHORT).show();
                    Toast.makeText(InicioActivity.this, "BORRAR CACHE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
    }

    private void cargarDatosDePrueba() {
        List<TipoEvaluacion> tipoEvaluacions = new ArrayList<>();
        tipoEvaluacions.add(new TipoEvaluacion(1L, "ORDINARIO"));
        tipoEvaluacions.add(new TipoEvaluacion(2L, "REPETIDO"));
        tipoEvaluacions.add(new TipoEvaluacion(3L, "DIFERIDO"));
        tipoEvaluacions.forEach(tipoEvaluacion -> {
            tipoEvaluacionService.registrarEntidad(tipoEvaluacion, new CallBackDisposableInterface() {
                @Override
                public void onCallBack(Object o) {
                    tipoEvaluacion.setIdTipoEvaluacion((long) o);
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
            materiaService.registrarEntidad(materia, new CallBackDisposableInterface() {
                @Override
                public void onCallBack(Object o) {
                    materia.setIdMateria((long) o);
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
        ciclos.forEach(ciclo -> cicloService.registrarEntidad(ciclo, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                ciclo.setIdCiclo((long) o);
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
            parametrosService.registrarEntidad(parametro, new CallBackDisposableInterface() {
                @Override
                public void onCallBack(Object o) {
                    parametro.setIdParametro((long) o);
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
        personas.forEach(persona -> personaService.registrarEntidad(persona, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                persona.setIdPersona((long) o);
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
        alumnos.forEach(alumno -> alumnoService.registrarEntidad(alumno, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                alumno.setIdAlumno((long) o);
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
        docentes.forEach(docente -> docenteService.registrarEntidad(docente, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                docente.setIdDocente((long) o);
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
        encargadoImpresions.forEach(encargadoImpresion -> encargadoImpresionService.registrarEntidad(encargadoImpresion, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                encargadoImpresion.setIdEncargado((long) o);
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
        coordinadors.forEach(coordinador -> coordinadorService.registrarEntidad(coordinador, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                coordinador.setIdCoordinador((long) o);
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
        cursos.forEach(curso -> cursoService.registrarEntidad(curso, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                curso.setIdCurso((long) o);
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
        inscripciones.forEach(inscripcion -> inscripcionService.registrarEntidad(inscripcion, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                inscripcion.setIdInscripcion((long) o);
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
        evaluaciones.forEach(evaluacion -> evaluacionService.registrarEntidad(evaluacion, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                evaluacion.setIdEvaluacion((long) o);
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
        solicitudRevisiones.forEach(solicitudRevision -> solicitudRevisionService.registrarEntidad(solicitudRevision, new CallBackDisposableInterface() {
            @Override
            public void onCallBack(Object o) {
                solicitudRevision.setIdSolicitudRevision((long) o);
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
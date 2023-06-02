package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;
import com.ues.sv.proyecto.controladministrativoapp.models.Curso;
import com.ues.sv.proyecto.controladministrativoapp.models.Docente;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.service.CicloService;
import com.ues.sv.proyecto.controladministrativoapp.service.CoordinadorService;
import com.ues.sv.proyecto.controladministrativoapp.service.CursoService;
import com.ues.sv.proyecto.controladministrativoapp.service.DocenteService;
import com.ues.sv.proyecto.controladministrativoapp.service.MateriaService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;

import java.util.ArrayList;
import java.util.List;

public class RegistrarCursoActivity extends AppCompatActivity {

    private TextInputLayout layouCiclo, layouMateria, layouDocente, layouCoordinador;
    private MaterialButton btnGuardar;

    private CursoService cursoService;
    private CicloService cicloService;
    private MateriaService materiaService;
    private DocenteService docenteService;
    private CoordinadorService coordinadorService;

    private boolean esEditar = Boolean.FALSE;

    private Curso cursoData = new Curso();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_curso);

        layouCiclo = findViewById(R.id.input_layout_ciclo);
        layouMateria = findViewById(R.id.input_layout_materia);
        layouDocente = findViewById(R.id.input_layout_docente);
        layouCoordinador = findViewById(R.id.input_layout_coordinador);
        btnGuardar = findViewById(R.id.btn_guardar);

        cursoService = new CursoService(getApplicationContext());
        cicloService = new CicloService(getApplicationContext());
        materiaService = new MateriaService(getApplicationContext());
        docenteService = new DocenteService(getApplicationContext());
        coordinadorService = new CoordinadorService(getApplicationContext());

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });

        cargarDatos();
        onBack();
    }

    private boolean validarDatos() {
        boolean valid = true;
        if (cursoData.getCiclo() == null) {
            layouCiclo.setError("Seleccione ciclo");
            valid = false;
        }
        if (cursoData.getMateria() == null) {
            layouMateria.setError("Seleccione Materia");
            valid = false;
        }
        if (cursoData.getDocente() == null) {
            layouDocente.setError("Seleccione Docente");
            valid = false;
        }
        if (cursoData.getCoordinador() == null) {
            layouCoordinador.setError("Seleccione Coordinador");
            valid = false;
        }
        return valid;
    }

    private void guardarRegistro() {
        try {
            try {
                if (esEditar) {
                    cursoService.editarEntidad(cursoData, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "editado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerCursoActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    cursoService.registrarEntidad(cursoData, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerCursoActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                }

            } catch (Exception ex) {
                Log.e("GUARDAR_DATOS", ex.getMessage(), ex.getCause());
                Toast.makeText(this, "Errorcillo", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
    }

    private void cargarDatos() {
        try {
            Bundle bundle = getIntent().getExtras();

            long idCurso = bundle.getLong("IdCurso", 0L);
            if (idCurso > 0)
                cursoService.buscarPorId(idCurso, new CallBackDisposableInterface<Curso>() {
                    @Override
                    public void onCallBack(Curso curso) {
                        cursoData = curso;
                        esEditar = Boolean.TRUE;
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
        cicloService.obtenerListaEntidad(new CallBackDisposableInterface<List<Ciclo>>() {
            @Override
            public void onCallBack(List<Ciclo> ciclos) {
                List<String> cicloStrings = new ArrayList<>();
                ciclos.forEach(cic -> cicloStrings.add(cic.getNumeroCiclo() + "-" + cic.getNumeroAnio()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, cicloStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouCiclo.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cursoData.setCiclo(ciclos.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (cursoData.getCiclo() != null)
                        autoCompleteTextView.setSelection(ciclos.indexOf(cursoData.getCiclo()));
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        materiaService.obtenerListaEntidad(new CallBackDisposableInterface<List<Materia>>() {
            @Override
            public void onCallBack(List<Materia> materias) {
                List<String> materiaStrings = new ArrayList<>();
                materias.forEach(mat -> materiaStrings.add(mat.getNombreMateria()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, materiaStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouMateria.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cursoData.setMateria(materias.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (cursoData.getMateria() != null)
                        autoCompleteTextView.setSelection(materias.indexOf(cursoData.getMateria()));
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        docenteService.obtenerListaEntidad(new CallBackDisposableInterface<List<Docente>>() {
            @Override
            public void onCallBack(List<Docente> docentes) {
                List<String> docenteStrings = new ArrayList<>();
                docentes.forEach(doc -> docenteStrings.add(doc.getPersona().getNombre() + " " + doc.getPersona().getApellido()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, docenteStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouDocente.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cursoData.setDocente(docentes.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (cursoData.getDocente() != null)
                        autoCompleteTextView.setSelection(docentes.indexOf(cursoData.getDocente()));
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        coordinadorService.obtenerListaEntidad(new CallBackDisposableInterface<List<Coordinador>>() {
            @Override
            public void onCallBack(List<Coordinador> coordinadors) {
                List<String> coordinadorStrings = new ArrayList<>();
                coordinadors.forEach(coor -> coordinadorStrings.add(coor.getPersona().getNombre() + " " + coor.getPersona().getApellido()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, coordinadorStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouCoordinador.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cursoData.setCoordinador(coordinadors.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (cursoData.getCoordinador() != null)
                        autoCompleteTextView.setSelection(coordinadors.indexOf(cursoData.getCoordinador()));
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
    }

    public void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(getApplicationContext(), VerCursoActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.ues.sv.proyecto.controladministrativoapp.rest.service.CicloRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.CoordinadorRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.CursoRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.DocenteRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.MateriaRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;

import java.util.ArrayList;
import java.util.List;

public class RegistrarCursoActivity extends AppCompatActivity {

    private TextInputLayout layouCiclo, layouMateria, layouDocente, layouCoordinador;
    private MaterialButton btnGuardar;

    private CursoRestService cursoRestService;
    private CicloRestService cicloRestService;
    private MateriaRestService materiaRestService;
    private DocenteRestService docenteRestService;
    private CoordinadorRestService coordinadorRestService;

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

        cursoRestService = new CursoRestService();
        cicloRestService = new CicloRestService();
        materiaRestService = new MateriaRestService();
        docenteRestService = new DocenteRestService();
        coordinadorRestService = new CoordinadorRestService();

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
                    cursoRestService.editarEntidad(cursoData, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            Toast.makeText(getBaseContext(), "editado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerCursoActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    cursoRestService.registrarEntidad(cursoData, new CallBackDisposableInterface() {
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
                cursoRestService.buscarPorId(idCurso, new CallBackDisposableInterface<Curso>() {
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
        cicloRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Ciclo>>() {
            @Override
            public void onCallBack(List<Ciclo> ciclos) {
                List<String> cicloStrings = new ArrayList<>();
                ciclos.forEach(cic -> cicloStrings.add(cic.getNumeroCiclo() + "-" + cic.getNumeroAnio()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, cicloStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouCiclo.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> cursoData.setCiclo(ciclos.get(position)));
                    if (cursoData.getCiclo() != null) {
                        int index = ciclos.lastIndexOf(cursoData.getCiclo());
                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(index).toString(), false);
                    }
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        materiaRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Materia>>() {
            @Override
            public void onCallBack(List<Materia> materias) {
                List<String> materiaStrings = new ArrayList<>();
                materias.forEach(mat -> materiaStrings.add(mat.getNombreMateria()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, materiaStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouMateria.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> cursoData.setMateria(materias.get(position)));
                    if (cursoData.getMateria() != null) {
                        int index = materias.lastIndexOf(cursoData.getMateria());
                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(index).toString(), false);
                    }
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        docenteRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Docente>>() {
            @Override
            public void onCallBack(List<Docente> docentes) {
                List<String> docenteStrings = new ArrayList<>();
                docentes.forEach(doc -> docenteStrings.add(doc.getPersona().getNombre() + " " + doc.getPersona().getApellido()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, docenteStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouDocente.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> cursoData.setDocente(docentes.get(position)));
                    if (cursoData.getDocente() != null) {
                        int index = docentes.lastIndexOf(cursoData.getDocente());
                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(index).toString(), false);
                    }
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        coordinadorRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Coordinador>>() {
            @Override
            public void onCallBack(List<Coordinador> coordinadors) {
                List<String> coordinadorStrings = new ArrayList<>();
                coordinadors.forEach(coor -> coordinadorStrings.add(coor.getPersona().getNombre() + " " + coor.getPersona().getApellido()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, coordinadorStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouCoordinador.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> cursoData.setCoordinador(coordinadors.get(position)));
                    if (cursoData.getCoordinador() != null) {
                        int index = coordinadors.lastIndexOf(cursoData.getCoordinador());
                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(index).toString(), false);
                    }
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
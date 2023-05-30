package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Curso;
import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.service.CursoService;
import com.ues.sv.proyecto.controladministrativoapp.service.EvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.service.TipoEvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarEvaluacionActivity extends AppCompatActivity {

    private TextInputLayout layouCurso, layouTipo, layouNumero;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;

    private EvaluacionService evaluacionService;
    private CursoService cursoService;
    private TipoEvaluacionService tipoEvaluacionService;

    private Evaluacion evaluacionData = new Evaluacion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_evaluacion);

        layouCurso = findViewById(R.id.input_layout_curso);
        layouTipo = findViewById(R.id.input_layout_tipo);
        layouNumero = findViewById(R.id.input_layout_numero);
        btnGuardar = findViewById(R.id.btn_guardar);

        evaluacionService = new EvaluacionService(getApplicationContext());
        cursoService = new CursoService(getApplicationContext());
        tipoEvaluacionService = new TipoEvaluacionService(getApplicationContext());

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });
        cargarDatos();
    }

    private boolean validarDatos() {
        boolean valid = true;
        if (evaluacionData.getCurso() == null) {
            layouCurso.setError("Seleccione un Curso");
            valid = false;
        }
        if (evaluacionData.getTipoEvaluacion() == null) {
            layouCurso.setError("Seleccione un Tipo de Evaluacion");
            valid = false;
        }

        if (evaluacionData.getNumeroEvaluacion() == null) {
            layouCurso.setError("Seleccione Fecha de Ingreso");
            valid = false;
        }
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("numeroEvaluacion", layouNumero);
        valid = ValidationUtils.validate(Evaluacion.class, map);
        return valid;
    }

    private void guardarRegistro() {
        try {

            try {
                Integer numeroEvaluacion = Integer.parseInt(layouNumero.getEditText().getText().toString());
                evaluacionData.setNumeroEvaluacion(numeroEvaluacion);
                if (esEditar) {
                    evaluacionService.editarEntidad(evaluacionData, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerEvaluacionActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    evaluacionService.registrarEntidad(evaluacionData, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerEvaluacionActivity.class);
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

            evaluacionData.setIdEvaluacion(bundle.getLong("IdEvaluacion", 0L));
            cursoService.buscarPorId(bundle.getLong("IdCurso", 1L), new CallBackDisposableInterface<Curso>() {
                @Override
                public void onCallBack(Curso curso) {
                    evaluacionData.setCurso(curso);
                }

                @Override
                public void onThrow(Throwable throwable) {

                }
            });
            tipoEvaluacionService.buscarPorId(bundle.getLong("IdTipoEvaliacion", 1L), new CallBackDisposableInterface<TipoEvaluacion>() {
                @Override
                public void onCallBack(TipoEvaluacion tipoEvaluacion) {
                    evaluacionData.setTipoEvaluacion(tipoEvaluacion);
                }

                @Override
                public void onThrow(Throwable throwable) {

                }
            });
            evaluacionData.setNumeroEvaluacion(bundle.getInt("NumeroEvaluacion", 0));
            layouNumero.getEditText().setText(evaluacionData.getNumeroEvaluacion());

            if (evaluacionData.getIdEvaluacion() > 0 && evaluacionData.getNumeroEvaluacion() != null) {
                esEditar = Boolean.TRUE;
            }

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
        cursoService.obtenerListaEntidad(new CallBackDisposableInterface<List<Curso>>() {
            @Override
            public void onCallBack(List<Curso> cursos) {
                List<String> cursosStrings = new ArrayList<>();
                cursos.forEach(cur -> cursosStrings.add(cur.getMateria().getNombreMateria() + " " + cur.getCiclo().getNumeroAnio()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, cursosStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouCurso.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            evaluacionData.setCurso(cursos.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (evaluacionData.getCurso() != null)
                        autoCompleteTextView.setSelection(cursos.indexOf(evaluacionData.getCurso()));
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        tipoEvaluacionService.obtenerListaEntidad(new CallBackDisposableInterface<List<TipoEvaluacion>>() {
            @Override
            public void onCallBack(List<TipoEvaluacion> tipoEvaluacions) {
                List<String> tipoEvaluacionesStrings = new ArrayList<>();
                tipoEvaluacions.forEach(tipoev -> tipoEvaluacionesStrings.add(tipoev.getNombreTipoEvaluacion()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, tipoEvaluacionesStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouCurso.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            evaluacionData.setTipoEvaluacion(tipoEvaluacions.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (evaluacionData.getCurso() != null)
                        autoCompleteTextView.setSelection(tipoEvaluacions.indexOf(evaluacionData.getTipoEvaluacion()));
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
    }
}
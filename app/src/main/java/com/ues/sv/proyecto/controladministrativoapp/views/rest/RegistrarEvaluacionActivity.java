package com.ues.sv.proyecto.controladministrativoapp.views.rest;

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
import com.ues.sv.proyecto.controladministrativoapp.models.Curso;
import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.CursoRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.EvaluacionRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.TipoEvaluacionRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarEvaluacionActivity extends AppCompatActivity {

    private TextInputLayout layouCurso, layouTipo, layouNumero;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;

    private EvaluacionRestService evaluacionRestService;
    private CursoRestService cursoRestService;
    private TipoEvaluacionRestService tipoEvaluacionRestService;

    private Evaluacion evaluacionData = new Evaluacion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_evaluacion);

        layouCurso = findViewById(R.id.input_layout_curso);
        layouTipo = findViewById(R.id.input_layout_tipo);
        layouNumero = findViewById(R.id.input_layout_numero);
        btnGuardar = findViewById(R.id.btn_guardar);

        evaluacionRestService = new EvaluacionRestService();
        cursoRestService = new CursoRestService();
        tipoEvaluacionRestService = new TipoEvaluacionRestService();

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });
        cargarDatos();
        onBack();
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
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("numeroEvaluacion", layouNumero);
        if (!ValidationUtils.validate(Evaluacion.class, map))
            valid = false;
        return valid;
    }

    private void guardarRegistro() {
        try {

            try {
                Integer numeroEvaluacion = Integer.parseInt(layouNumero.getEditText().getText().toString());
                evaluacionData.setNumeroEvaluacion(numeroEvaluacion);
                if (esEditar) {
                    evaluacionRestService.editarEntidad(evaluacionData, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerEvaluacionActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    evaluacionRestService.registrarEntidad(evaluacionData, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
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

            long idEvaluacion = bundle.getLong("IdEvaluacion", 0L);
            if (idEvaluacion > 0) {
                evaluacionRestService.buscarPorId(idEvaluacion, new CallBackDisposableInterface<Evaluacion>() {
                    @Override
                    public void onCallBack(Evaluacion evaluacion) {
                        evaluacionData = evaluacion;
                        layouNumero.getEditText().setText(String.valueOf(evaluacion.getNumeroEvaluacion()));
                        esEditar = Boolean.TRUE;
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            }

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
        cursoRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Curso>>() {
            @Override
            public void onCallBack(List<Curso> cursos) {
                List<String> cursosStrings = new ArrayList<>();
                cursos.forEach(cur -> cursosStrings.add(cur.getMateria().getNombreMateria() + " " + cur.getCiclo().getNumeroAnio()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, cursosStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouCurso.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> evaluacionData.setCurso(cursos.get(position)));
                    if (evaluacionData.getCurso() != null) {
                        int index = cursos.lastIndexOf(evaluacionData.getCurso());
                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(index).toString(), false);
                    }
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        tipoEvaluacionRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<TipoEvaluacion>>() {
            @Override
            public void onCallBack(List<TipoEvaluacion> tipoEvaluacions) {
                List<String> tipoEvaluacionesStrings = new ArrayList<>();
                tipoEvaluacions.forEach(tipoev -> tipoEvaluacionesStrings.add(tipoev.getNombreTipoEvaluacion()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, tipoEvaluacionesStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouTipo.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> evaluacionData.setTipoEvaluacion(tipoEvaluacions.get(position)));
                    if (evaluacionData.getTipoEvaluacion() != null) {
                        int index = tipoEvaluacions.lastIndexOf(evaluacionData.getTipoEvaluacion());
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
                Intent intent = new Intent(getApplicationContext(), VerEvaluacionActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
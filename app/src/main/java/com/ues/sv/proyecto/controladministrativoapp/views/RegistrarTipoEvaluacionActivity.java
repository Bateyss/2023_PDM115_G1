package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.service.TipoEvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarTipoEvaluacionActivity extends AppCompatActivity {

    private TextInputLayout layouNombre;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;
    private TipoEvaluacionService tipoEvaluacionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_tipo_evaluacion);

        layouNombre = findViewById(R.id.input_layout_nombre);
        btnGuardar = findViewById(R.id.btn_guardar);

        tipoEvaluacionService = new TipoEvaluacionService(getApplicationContext());

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });

        cargarDatos();
    }

    private boolean validarDatos() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("nombreTipoEvaluacion", layouNombre);
        return ValidationUtils.validate(TipoEvaluacion.class, map);
    }

    private void guardarRegistro() {
        try {
            TipoEvaluacion tipoEvaluacion = new TipoEvaluacion();
            tipoEvaluacion.setNombreTipoEvaluacion(layouNombre.getEditText().getText().toString());

            try {
                if (esEditar) {
                    tipoEvaluacionService.editarEntidad(tipoEvaluacion, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerTipoEvaluacionActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    tipoEvaluacionService.registrarEntidad(tipoEvaluacion, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerTipoEvaluacionActivity.class);
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
            TipoEvaluacion tipoEvaluacion = new TipoEvaluacion();
            tipoEvaluacion.setIdTipoEvaliacion(bundle.getLong("IdTipoEvaliacion", 0L));
            tipoEvaluacion.setNombreTipoEvaluacion(bundle.getString("NombreTipoEvaluacion", null));
            if (tipoEvaluacion.getIdTipoEvaliacion() > 0L && tipoEvaluacion.getNombreTipoEvaluacion() != null) {
                layouNombre.getEditText().setText(tipoEvaluacion.getNombreTipoEvaluacion());
                esEditar = Boolean.TRUE;
            }
        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
    }
}
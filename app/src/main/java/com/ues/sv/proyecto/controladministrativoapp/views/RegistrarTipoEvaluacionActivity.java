package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.room.service.TipoEvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarTipoEvaluacionActivity extends AppCompatActivity {

    private TextInputLayout layouNombre;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;
    private TipoEvaluacionService tipoEvaluacionService;

    private TipoEvaluacion tipoEvaluacionData = new TipoEvaluacion();

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
        onBack();
    }

    private boolean validarDatos() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("nombreTipoEvaluacion", layouNombre);
        return ValidationUtils.validate(TipoEvaluacion.class, map);
    }

    private void guardarRegistro() {
        try {

            tipoEvaluacionData.setNombreTipoEvaluacion(layouNombre.getEditText().getText().toString());

            try {
                if (esEditar) {
                    tipoEvaluacionService.editarEntidad(tipoEvaluacionData, new CallBackVoidInterface() {
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
                    tipoEvaluacionService.registrarEntidad(tipoEvaluacionData, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
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
            long idTipoEvaluacion = bundle.getLong("IdTipoEvaluacion", 0L);
            if (idTipoEvaluacion > 0)
                tipoEvaluacionService.buscarPorId(idTipoEvaluacion, new CallBackDisposableInterface<TipoEvaluacion>() {
                    @Override
                    public void onCallBack(TipoEvaluacion tipoEvaluacion) {
                        tipoEvaluacionData = tipoEvaluacion;
                        layouNombre.getEditText().setText(tipoEvaluacion.getNombreTipoEvaluacion());
                        esEditar = Boolean.TRUE;
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
    }

    public void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(getApplicationContext(), VerTipoEvaluacionActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
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
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.service.CicloService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarCicloActivity extends AppCompatActivity {


    private TextInputLayout layouNumero, layouAnio;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;

    private CicloService cicloService;

    private Ciclo cicloData = new Ciclo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_ciclo);

        layouNumero = findViewById(R.id.input_layout_numero);
        layouAnio = findViewById(R.id.input_layout_anio);
        btnGuardar = findViewById(R.id.btn_guardar);

        cicloService = new CicloService(getApplicationContext());

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });

        cargarDatos();
        onBack();
    }

    private boolean validarDatos() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("numeroCiclo", layouNumero);
        map.put("numeroAnio", layouAnio);
        return ValidationUtils.validate(Ciclo.class, map);
    }

    private void guardarRegistro() {
        try {
            Ciclo ciclo = cicloData;
            ciclo.setNumeroCiclo(layouNumero.getEditText().getText().toString());
            ciclo.setNumeroAnio(layouAnio.getEditText().getText().toString());

            try {
                if (esEditar) {
                    cicloService.editarEntidad(ciclo, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "editado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerCiclosActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    cicloService.registrarEntidad(ciclo, new CallBackDisposableInterface() {
                                @Override
                                public void onCallBack(Object o) {
                                    Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(), VerCiclosActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onThrow(Throwable throwable) {

                                }
                            }
                    );
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

            long idCiclo = bundle.getLong("IdCiclo", 0L);
            if (idCiclo > 0)
                cicloService.buscarPorId(idCiclo, new CallBackDisposableInterface<Ciclo>() {
                    @Override
                    public void onCallBack(Ciclo ciclo) {
                        layouNumero.getEditText().setText(ciclo.getNumeroCiclo());
                        layouAnio.getEditText().setText(ciclo.getNumeroAnio());
                        cicloData = ciclo;
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
                Intent intent = new Intent(getApplicationContext(), VerCiclosActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.service.CicloService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarCicloActivity extends AppCompatActivity {


    private TextInputLayout layouNumero, layouAnio;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;

    private CicloService cicloService;

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
    }

    private boolean validarDatos() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("numeroCiclo", layouNumero);
        map.put("numeroAnio", layouAnio);
        return ValidationUtils.validate(Ciclo.class, map);
    }

    private void guardarRegistro() {
        try {
            Ciclo ciclo = new Ciclo();
            ciclo.setNumeroCiclo(layouNumero.getEditText().getText().toString());
            ciclo.setNumeroAnio(layouAnio.getEditText().getText().toString());

            try {
                if (esEditar) {
                    cicloService.editarEntidad(ciclo, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerCiclosActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    cicloService.registrarEntidad(ciclo, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerCiclosActivity.class);
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
            Ciclo ciclo = new Ciclo();
            ciclo.setIdCiclo(bundle.getLong("IdCiclo", 0L));
            ciclo.setNumeroCiclo(bundle.getString("NumeroCiclo", null));
            ciclo.setNumeroAnio(bundle.getString("NumeroAnio", null));

            if (ciclo.getIdCiclo() > 0L && ciclo.getNumeroCiclo() != null && ciclo.getNumeroAnio() != null) {
                layouNumero.getEditText().setText(ciclo.getNumeroCiclo());
                layouAnio.getEditText().setText(ciclo.getNumeroAnio());
                esEditar = Boolean.TRUE;
            }

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
    }
}
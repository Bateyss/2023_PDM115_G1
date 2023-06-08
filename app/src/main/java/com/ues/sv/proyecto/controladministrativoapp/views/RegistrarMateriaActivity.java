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
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.room.service.MateriaService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarMateriaActivity extends AppCompatActivity {

    private TextInputLayout layouNombre;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;
    private MateriaService materiaService;

    private Materia materiaData = new Materia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_materia);

        layouNombre = findViewById(R.id.input_layout_nombre);
        btnGuardar = findViewById(R.id.btn_guardar);

        materiaService = new MateriaService(getApplicationContext());

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });

        cargarDatos();
        onBack();
    }

    private boolean validarDatos() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("nombreMateria", layouNombre);
        return ValidationUtils.validate(Materia.class, map);
    }

    private void guardarRegistro() {
        try {
            materiaData.setNombreMateria(layouNombre.getEditText().getText().toString());
            try {
                if (esEditar) {
                    materiaService.editarEntidad(materiaData, new CallBackVoidInterface() {
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
                    materiaService.registrarEntidad(materiaData, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerMateriaActivity.class);
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
            long idMateria = bundle.getLong("IdMateria", 0L);
            if (idMateria > 0)
                materiaService.buscarPorId(idMateria, new CallBackDisposableInterface<Materia>() {
                    @Override
                    public void onCallBack(Materia materia) {
                        materiaData = materia;
                        layouNombre.getEditText().setText(materia.getNombreMateria());
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
                Intent intent = new Intent(getApplicationContext(), VerMateriaActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

}
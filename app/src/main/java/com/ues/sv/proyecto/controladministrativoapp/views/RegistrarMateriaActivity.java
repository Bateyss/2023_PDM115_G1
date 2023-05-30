package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.service.MateriaService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarMateriaActivity extends AppCompatActivity {

    private TextInputLayout layouNombre;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;
    private MateriaService materiaService;

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
    }

    private boolean validarDatos() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("nombreMateria", layouNombre);
        return ValidationUtils.validate(Materia.class, map);
    }

    private void guardarRegistro() {
        try {
            Materia materia = new Materia();
            materia.setNombreMateria(layouNombre.getEditText().getText().toString());

            try {
                if (esEditar) {
                    materiaService.editarEntidad(materia, new CallBackVoidInterface() {
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
                    materiaService.registrarEntidad(materia, new CallBackVoidInterface() {
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
            Materia materia = new Materia();
            materia.setIdMateria(bundle.getLong("IdMateria", 0L));
            materia.setNombreMateria(bundle.getString("NombreMateria", null));
            if (materia.getIdMateria() > 0L && materia.getNombreMateria() != null) {
                layouNombre.getEditText().setText(materia.getNombreMateria());
                esEditar = Boolean.TRUE;
            }
        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
    }

}
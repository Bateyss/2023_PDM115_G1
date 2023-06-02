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
import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.service.AlumnoService;
import com.ues.sv.proyecto.controladministrativoapp.service.PersonaService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarAlumnoActivity extends AppCompatActivity {

    private TextInputLayout layouPersona, layouCarnet;
    private MaterialButton btnGuardar;
    private boolean esEditar = Boolean.FALSE;

    private AlumnoService alumnoService;
    private PersonaService personaService;
    private Alumno alumnoData = new Alumno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_alumno);

        layouPersona = findViewById(R.id.input_layout_persona);
        layouCarnet = findViewById(R.id.input_layout_carnet);
        btnGuardar = findViewById(R.id.btn_guardar);

        personaService = new PersonaService(getApplicationContext());
        alumnoService = new AlumnoService(getApplicationContext());

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });
        cargarDatos();
        onBack();
    }

    private boolean validarDatos() {
        boolean valid = true;

        if (alumnoData.getPersona() == null) {
            layouPersona.setError("Seleccione Persona");
            valid = false;
        }
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("carnet", layouCarnet);
        if (!ValidationUtils.validate(Alumno.class, map))
            valid = false;
        return valid;
    }

    private void guardarRegistro() {
        try {
            alumnoData.setCarnet(layouCarnet.getEditText().getText().toString());
            if (esEditar) {
                alumnoService.editarEntidad(alumnoData, new CallBackVoidInterface() {
                    @Override
                    public void onCallBack() {
                        Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), VerDocentesActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            } else {
                alumnoService.registrarEntidad(alumnoData, new CallBackVoidInterface() {
                    @Override
                    public void onCallBack() {
                        Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), VerDocentesActivity.class);
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
    }

    private void cargarDatos() {
        try {
            Bundle bundle = getIntent().getExtras();

            long idAlumno = bundle.getLong("IdAlumno", 0L);
            if (idAlumno > 0)
                alumnoService.buscarPorId(idAlumno, new CallBackDisposableInterface<Alumno>() {
                    @Override
                    public void onCallBack(Alumno alumno) {
                        alumnoData = alumno;
                        layouCarnet.getEditText().setText(alumno.getCarnet());
                        esEditar = true;
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
        personaService.obtenerListaEntidad(new CallBackDisposableInterface<List<Persona>>() {
            @Override
            public void onCallBack(List<Persona> personas) {
                List<String> personasStrings = new ArrayList<>();
                personas.forEach(per -> personasStrings.add(per.getNombre() + " " + per.getApellido()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, personasStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouPersona.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            alumnoData.setPersona(personas.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (alumnoData.getPersona() != null)
                        autoCompleteTextView.setSelection(personas.indexOf(alumnoData.getPersona()));
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
                Intent intent = new Intent(getApplicationContext(), VerAlumnosActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
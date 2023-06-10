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
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.PersonaRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarPersonaActivity extends AppCompatActivity {

    private TextInputLayout layouNombre, layouApellido, layouIdentificacion, layouSexo;
    private MaterialButton btnGuardar;

    private boolean esEditar = Boolean.FALSE;
    private PersonaRestService personaRestService;

    private Persona personaData = new Persona();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_persona);

        layouNombre = findViewById(R.id.input_layout_nombre);
        layouApellido = findViewById(R.id.input_layout_apellido);
        layouIdentificacion = findViewById(R.id.input_layout_identificacion);
        layouSexo = findViewById(R.id.input_layout_sexo);
        btnGuardar = findViewById(R.id.btn_guardar);

        personaRestService = new PersonaRestService();

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });

        cargarDatos();
        onBack();
    }

    private boolean validarDatos() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("nombre", layouNombre);
        map.put("apellido", layouApellido);
        map.put("identificacion", layouIdentificacion);
        map.put("sexo", layouSexo);
        return ValidationUtils.validate(Persona.class, map);
    }

    private void guardarRegistro() {
        try {
            Persona persona = personaData;
            persona.setNombre(layouNombre.getEditText().getText().toString());
            persona.setApellido(layouApellido.getEditText().getText().toString());
            persona.setIdentificacion(layouIdentificacion.getEditText().getText().toString());
            persona.setSexo(layouSexo.getEditText().getText().toString());

            try {
                if (esEditar) {
                    personaRestService.editarEntidad(persona, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            layouNombre.getEditText().setText(null);
                            layouApellido.getEditText().setText(null);
                            layouIdentificacion.getEditText().setText(null);
                            layouSexo.getEditText().setText(null);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    personaRestService.registrarEntidad(persona, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), InicioActivity.class);
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
            long idPersona = bundle.getLong("IdPersona", 0L);
            if (idPersona > 0)
                personaRestService.buscarPorId(idPersona, new CallBackDisposableInterface<Persona>() {
                    @Override
                    public void onCallBack(Persona persona) {
                        personaData = persona;
                        layouNombre.getEditText().setText(persona.getNombre());
                        layouApellido.getEditText().setText(persona.getApellido());
                        layouIdentificacion.getEditText().setText(persona.getIdentificacion());
                        layouSexo.getEditText().setText(persona.getSexo());
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
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
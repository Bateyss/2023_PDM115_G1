package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Docente;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.DocenteRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.PersonaRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrarDocenteActivity extends AppCompatActivity {

    private TextInputLayout layouPersona, layouFecha;
    private MaterialButton btnGuardar;
    private boolean esEditar = Boolean.FALSE;

    private DocenteRestService docenteRestService;
    private PersonaRestService personaRestService;

    private Docente docenteData = new Docente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_docente);

        layouPersona = findViewById(R.id.input_layout_persona);
        layouFecha = findViewById(R.id.input_layout_fecha);
        btnGuardar = findViewById(R.id.btn_guardar);

        docenteRestService = new DocenteRestService();
        personaRestService = new PersonaRestService();

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Seleccionar Fecha").build();

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });

        layouFecha.getEditText().setOnClickListener(v -> {
            datePicker.show(getSupportFragmentManager(), "tag");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                Long timeMilis = (long) selection;
                Date dateSelected = new Date(timeMilis);
                docenteData.setFechaIngreso(dateSelected);
                String dateTxt = DateUtils.formatDate(dateSelected, DateUtils.FORMAT_DD_MM_YYYY);
                layouFecha.getEditText().setText(dateTxt);
            });
        });

        cargarDatos();
        onBack();
    }

    private boolean validarDatos() {
        boolean valid = true;
        if (docenteData.getFechaIngreso() == null) {
            layouFecha.setError("Seleccione Fecha de Ingreso");
            valid = false;
        }
        if (docenteData.getPersona() == null) {
            layouPersona.setError("Seleccione Persona");
            valid = false;
        }
        return valid;
    }

    private void guardarRegistro() {
        try {

            try {
                if (esEditar) {
                    docenteRestService.editarEntidad(docenteData, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerDocentesActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    docenteRestService.registrarEntidad(docenteData, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
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

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
    }

    private void cargarDatos() {
        try {
            Bundle bundle = getIntent().getExtras();

            long idDocente = bundle.getLong("IdDocente", 0L);
            if (idDocente > 0)
                docenteRestService.buscarPorId(idDocente, new CallBackDisposableInterface<Docente>() {
                    @Override
                    public void onCallBack(Docente docente) {
                        docenteData = docente;
                        String dateTxt = DateUtils.formatDate(docente.getFechaIngreso(), DateUtils.FORMAT_DD_MM_YYYY);
                        layouFecha.getEditText().setText(dateTxt);
                        esEditar = Boolean.TRUE;
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
        personaRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Persona>>() {
            @Override
            public void onCallBack(List<Persona> personas) {
                List<String> personasStrings = new ArrayList<>();
                personas.forEach(per -> personasStrings.add(per.getNombre() + " " + per.getApellido()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, personasStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouPersona.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> docenteData.setPersona(personas.get(position)));
                    if (docenteData.getPersona() != null) {
                        int index = personas.lastIndexOf(docenteData.getPersona());
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
                Intent intent = new Intent(getApplicationContext(), VerDocentesActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
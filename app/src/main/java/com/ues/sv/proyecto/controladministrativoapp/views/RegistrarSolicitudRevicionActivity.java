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
import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.models.Inscripcion;
import com.ues.sv.proyecto.controladministrativoapp.models.SolicitudRevision;
import com.ues.sv.proyecto.controladministrativoapp.service.EvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.service.InscripcionService;
import com.ues.sv.proyecto.controladministrativoapp.service.SolicitudRevisionService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DateUtils;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarSolicitudRevicionActivity extends AppCompatActivity {

    private TextInputLayout layouInscripcion, layouEvaluacion, layouMotivo, layouEstado, layouFecha;
    private MaterialButton btnGuardar;
    private boolean esEditar = Boolean.FALSE;

    private SolicitudRevisionService solicitudRevisionService;
    private EvaluacionService evaluacionService;
    private InscripcionService inscripcionService;

    private SolicitudRevision solicitudRevisionSelected = new SolicitudRevision();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_solicitud_revicion);

        layouInscripcion = findViewById(R.id.input_layout_inscripcion);
        layouEvaluacion = findViewById(R.id.input_layout_evaluacion);
        layouMotivo = findViewById(R.id.input_layout_motivo);
        layouEstado = findViewById(R.id.input_layout_estado);
        layouFecha = findViewById(R.id.input_layout_fecha);
        btnGuardar = findViewById(R.id.btn_guardar);

        solicitudRevisionService = new SolicitudRevisionService(getApplicationContext());
        evaluacionService = new EvaluacionService(getApplicationContext());
        inscripcionService = new InscripcionService(getApplicationContext());

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Seleccionar Fecha").build();

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });

        layouFecha.getEditText().setOnClickListener(v -> {
            datePicker.show(getSupportFragmentManager(), "tag");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                Long timeMilis = (long) selection;
                Date dateSelected = new Date(timeMilis);
                solicitudRevisionSelected.setFechaCreacion(dateSelected);
                String dateTxt = DateUtils.formatDate(dateSelected, DateUtils.FORMAT_DD_MM_YYYY);
                layouFecha.getEditText().setText(dateTxt);
            });
        });

        cargarDatos();
        onBack();
    }

    private boolean validarDatos() {
        boolean valid = true;
        if (solicitudRevisionSelected.getFechaCreacion() == null) {
            layouFecha.setError("Seleccione Fecha de Ingreso");
            valid = false;
        }
        if (solicitudRevisionSelected.getEvaluacion() == null) {
            layouEvaluacion.setError("Seleccione Evaluacion");
            valid = false;
        }
        if (solicitudRevisionSelected.getInscripcion() == null) {
            layouEvaluacion.setError("Seleccione Alumno");
            valid = false;
        }
        Map<String, TextInputLayout> param1 = new HashMap<>();
        param1.put("motivo", layouMotivo);
        param1.put("estadoSolicitud", layouEstado);
        if (!ValidationUtils.validate(SolicitudRevision.class, param1)) valid = false;
        return valid;
    }

    private void guardarRegistro() {
        try {

            try {
                if (esEditar) {
                    solicitudRevisionService.editarEntidad(solicitudRevisionSelected, new CallBackVoidInterface() {
                        @Override
                        public void onCallBack() {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerSolicitudEvaluacionActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                } else {
                    solicitudRevisionService.registrarEntidad(solicitudRevisionSelected, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), VerSolicitudEvaluacionActivity.class);
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
            Long idSolicitud = bundle.getLong("IdSolicitudRevision", 0L);
            if (idSolicitud > 0L) {
                solicitudRevisionService.buscarPorId(idSolicitud, new CallBackDisposableInterface<SolicitudRevision>() {
                    @Override
                    public void onCallBack(SolicitudRevision solicitudRevision) {
                        solicitudRevisionSelected = solicitudRevision;
                        layouMotivo.getEditText().setText(solicitudRevision.getMotivo());
                        layouEstado.getEditText().setText(String.valueOf(solicitudRevision.getEstadoSolicitud()));
                        String dateTxt = DateUtils.formatDate(solicitudRevision.getFechaCreacion(), DateUtils.FORMAT_DD_MM_YYYY);
                        layouFecha.getEditText().setText(dateTxt);
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            }


        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
        evaluacionService.obtenerListaEntidad(new CallBackDisposableInterface<List<Evaluacion>>() {
            @Override
            public void onCallBack(List<Evaluacion> evaluacions) {
                List<String> sStrings = new ArrayList<>();
                evaluacions.forEach(evl -> sStrings.add(evl.getCurso().getMateria().getNombreMateria() + " \n " + evl.getTipoEvaluacion().getNombreTipoEvaluacion() + " " + evl.getNumeroEvaluacion() + " " + evl.getCurso().getCiclo().getNumeroAnio()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, sStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouEvaluacion.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> solicitudRevisionSelected.setEvaluacion(evaluacions.get(position)));
                    if (solicitudRevisionSelected.getEvaluacion() != null) {
                        int index = evaluacions.lastIndexOf(solicitudRevisionSelected.getEvaluacion());
                        autoCompleteTextView.setText(autoCompleteTextView.getAdapter().getItem(index).toString(), false);
                    }
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        inscripcionService.obtenerListaEntidad(new CallBackDisposableInterface<List<Inscripcion>>() {
            @Override
            public void onCallBack(List<Inscripcion> inscripcions) {
                List<String> sStrings = new ArrayList<>();
                inscripcions.forEach(insc -> sStrings.add(insc.getAlumno().getCarnet() + " \n " + insc.getAlumno().getPersona().getNombre() + " " + insc.getAlumno().getPersona().getApellido()));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.holder_item_only_text, R.id.only_text_txt_view, sStrings);
                MaterialAutoCompleteTextView autoCompleteTextView = (MaterialAutoCompleteTextView) layouInscripcion.getEditText();
                if (autoCompleteTextView != null) {
                    autoCompleteTextView.setAdapter(adapter);
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> solicitudRevisionSelected.setInscripcion(inscripcions.get(position)));
                    if (solicitudRevisionSelected.getInscripcion() != null) {
                        int index = inscripcions.lastIndexOf(solicitudRevisionSelected.getInscripcion());
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
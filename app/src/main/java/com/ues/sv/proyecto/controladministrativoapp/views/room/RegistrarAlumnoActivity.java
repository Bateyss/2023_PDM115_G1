package com.ues.sv.proyecto.controladministrativoapp.views.room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.service.AlumnoService;
import com.ues.sv.proyecto.controladministrativoapp.room.service.PersonaService;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarAlumnoActivity extends AppCompatActivity {

    private TextInputLayout layouPersona, layouCarnet;
    private MaterialButton btnGuardar;

    private ShapeableImageView imageView;
    private boolean esEditar = Boolean.FALSE;

    private AlumnoService alumnoService;
    private PersonaService personaService;
    private Alumno alumnoData = new Alumno();

    private Uri imageSelectedUri = null;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o != null) {
                Intent iee = o.getData();
                if (iee != null) {
                    Uri result = iee.getData();
                    if (result != null) {
                        Picasso.get().load(result).resize(400, 400).into(imageView);
                        imageSelectedUri = result;
                    } else {
                        imageSelectedUri = null;
                    }
                }
            }
        }
    });

    private ActivityResultLauncher<String> permisoLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), o -> {
        Log.w("PERMISO", o ? "si" : "no");
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_alumno);

        layouPersona = findViewById(R.id.input_layout_persona);
        layouCarnet = findViewById(R.id.input_layout_carnet);
        btnGuardar = findViewById(R.id.btn_guardar);
        imageView = findViewById(R.id.imageview);

        alumnoService = new AlumnoService(getApplicationContext());
        personaService = new PersonaService(getApplicationContext());

        btnGuardar.setOnClickListener(v -> {
            if (validarDatos()) guardarRegistro();
        });
        cargarDatos();
        onBack();


        imageView.setOnClickListener(v -> {
            Intent imgintent = new Intent();
            imgintent.setAction(Intent.ACTION_PICK);
            imgintent.setType("image/*");
            int permission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                launcher.launch(imgintent);
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                permisoLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });
    }

    private boolean validarDatos() {
        boolean valid = true;

        if (alumnoData.getPersona() == null) {
            layouPersona.setError("Seleccione Persona");
            valid = false;
        }
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("carnet", layouCarnet);
        if (!ValidationUtils.validate(Alumno.class, map)) valid = false;
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
                        Intent intent = new Intent(getBaseContext(), VerAlumnosActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            } else {
                alumnoService.registrarEntidad(alumnoData, new CallBackDisposableInterface() {
                    @Override
                    public void onCallBack(Object o) {
                        Toast.makeText(getBaseContext(), "almacenado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), VerAlumnosActivity.class);
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
                    autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
                        Persona persona = personas.get(position);
                        alumnoData.setPersona(persona);
                    });
                    if (alumnoData.getPersona() != null) {
                        int index = personas.lastIndexOf(alumnoData.getPersona());
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
                Intent intent = new Intent(getApplicationContext(), VerAlumnosActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
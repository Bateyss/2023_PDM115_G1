package com.ues.sv.proyecto.controladministrativoapp.views;

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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Docente;
import com.ues.sv.proyecto.controladministrativoapp.models.Imagen;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.DocenteRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.ImagenRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.PersonaRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrarDocenteActivity extends AppCompatActivity {

    private TextInputLayout layouPersona, layouFecha;
    private MaterialButton btnGuardar;
    private ShapeableImageView imageView;
    private boolean esEditar = Boolean.FALSE;

    private DocenteRestService docenteRestService;
    private PersonaRestService personaRestService;
    private ImagenRestService imagenRestService;

    private Docente docenteData = new Docente();

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
        setContentView(R.layout.activity_registrar_docente);

        layouPersona = findViewById(R.id.input_layout_persona);
        layouFecha = findViewById(R.id.input_layout_fecha);
        btnGuardar = findViewById(R.id.btn_guardar);
        imageView = findViewById(R.id.imageview);

        docenteRestService = new DocenteRestService();
        personaRestService = new PersonaRestService();
        imagenRestService = new ImagenRestService(getApplicationContext());

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

    private void guardarImagen() {
        boolean editar = false;
        if (imageSelectedUri != null) {
            if (esEditar && docenteData.getPersona().getIdImagen() != null && docenteData.getPersona().getIdImagen() > 0) {
                editar = true;
            }
            if (editar) {
                imagenRestService.buscarPorId(docenteData.getPersona().getIdImagen().longValue(), new CallBackDisposableInterface<Imagen>() {
                    @Override
                    public void onCallBack(Imagen imagen) {
                        imagenRestService.editarEntidad(imageSelectedUri, imagen, new CallBackDisposableInterface<Imagen>() {
                            @Override
                            public void onCallBack(Imagen imagen) {

                            }

                            @Override
                            public void onThrow(Throwable throwable) {

                            }
                        });
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            } else {
                imagenRestService.registrarEntidad(imageSelectedUri, new CallBackDisposableInterface<Imagen>() {
                    @Override
                    public void onCallBack(Imagen imagen) {
                        docenteData.getPersona().setIdImagen(imagen.getIdImagen().intValue());
                        personaRestService.editarEntidad(docenteData.getPersona(), new CallBackDisposableInterface() {
                            @Override
                            public void onCallBack(Object o) {

                            }

                            @Override
                            public void onThrow(Throwable throwable) {

                            }
                        });
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            }
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

                        if (docente.getPersona().getIdImagen() != null && docente.getPersona().getIdImagen() > 0) {
                            imagenRestService.buscarPorId(docente.getPersona().getIdImagen().longValue(), new CallBackDisposableInterface<Imagen>() {
                                @Override
                                public void onCallBack(Imagen imagen) {
                                    String urlImagen = ApiData.API1_URL.concat("imagen/download/").concat(imagen.getNombre());
                                    Picasso.get().load(urlImagen).resize(400, 400).into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            imageView.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
                                        }
                                    });
                                }

                                @Override
                                public void onThrow(Throwable throwable) {

                                }
                            });
                        }
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
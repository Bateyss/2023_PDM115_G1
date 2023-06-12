package com.ues.sv.proyecto.controladministrativoapp.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Imagen;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.ImagenRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.MateriaRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarMateriaActivity extends AppCompatActivity {

    private TextInputLayout layouNombre;
    private MaterialButton btnGuardar;
    private ShapeableImageView imageView;

    private boolean esEditar = Boolean.FALSE;
    private MateriaRestService materiaRestService;
    private ImagenRestService imagenRestService;

    private Materia materiaData = new Materia();

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
        setContentView(R.layout.activity_registrar_materia);

        layouNombre = findViewById(R.id.input_layout_nombre);
        btnGuardar = findViewById(R.id.btn_guardar);
        imageView = findViewById(R.id.imageview);

        materiaRestService = new MateriaRestService();
        imagenRestService = new ImagenRestService(getApplicationContext());

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
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("nombreMateria", layouNombre);
        return ValidationUtils.validate(Materia.class, map);
    }

    private void guardarRegistro() {
        try {
            materiaData.setNombreMateria(layouNombre.getEditText().getText().toString());
            try {
                if (esEditar) {
                    if (imageSelectedUri != null) {
                        if (materiaData.getIdImagen() != null && materiaData.getIdImagen() > 0) {
                            imagenRestService.buscarPorId(materiaData.getIdImagen().longValue(), new CallBackDisposableInterface<Imagen>() {
                                @Override
                                public void onCallBack(Imagen imagen) {
                                    imagenRestService.editarEntidad(imageSelectedUri, imagen, new CallBackDisposableInterface<Imagen>() {
                                        @Override
                                        public void onCallBack(Imagen imagen) {
                                            editarMateria();
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
                                    materiaData.setIdImagen(imagen.getIdImagen().intValue());
                                    editarMateria();
                                }

                                @Override
                                public void onThrow(Throwable throwable) {

                                }
                            });
                        }
                    } else {
                        editarMateria();
                    }
                } else {
                    if (imageSelectedUri != null) {
                        imagenRestService.registrarEntidad(imageSelectedUri, new CallBackDisposableInterface<Imagen>() {
                            @Override
                            public void onCallBack(Imagen imagen) {
                                registrarMateria();
                            }

                            @Override
                            public void onThrow(Throwable throwable) {

                            }
                        });
                    } else {
                        registrarMateria();
                    }
                }

            } catch (Exception ex) {
                Log.e("GUARDAR_DATOS", ex.getMessage(), ex.getCause());
                Toast.makeText(this, "Errorcillo", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
    }

    private void editarMateria() {
        materiaRestService.editarEntidad(materiaData, new CallBackDisposableInterface() {
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

    private void registrarMateria() {
        materiaRestService.registrarEntidad(materiaData, new CallBackDisposableInterface() {
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

    private void cargarDatos() {
        try {
            Bundle bundle = getIntent().getExtras();
            long idMateria = bundle.getLong("IdMateria", 0L);
            if (idMateria > 0)
                materiaRestService.buscarPorId(idMateria, new CallBackDisposableInterface<Materia>() {
                    @Override
                    public void onCallBack(Materia materia) {
                        materiaData = materia;
                        layouNombre.getEditText().setText(materia.getNombreMateria());
                        esEditar = Boolean.TRUE;

                        if (materia.getIdImagen() != null && materia.getIdImagen() > 0) {
                            imagenRestService.buscarPorId(materia.getIdImagen().longValue(), new CallBackDisposableInterface<Imagen>() {
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
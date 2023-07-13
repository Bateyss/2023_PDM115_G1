package com.ues.sv.proyecto.controladministrativoapp.views.rest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Alumno;
import com.ues.sv.proyecto.controladministrativoapp.models.Imagen;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.AlumnoRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.ImagenRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.TxtAndImageInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.TxtAndImageRecyclerAdapter;

import java.util.List;

public class VerAlumnosActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private AlumnoRestService alumnoRestService;
    private ImagenRestService imagenRestService;
    private Alumno alumnoSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alumnos);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        alumnoRestService = new AlumnoRestService();
        imagenRestService = new ImagenRestService(getApplicationContext());
        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarAlumnoActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
        onBack();
    }

    private void cargarRecyclerList() {
        alumnoRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Alumno>>() {
            @Override
            public void onCallBack(List<Alumno> alumnos) {
                TxtAndImageRecyclerAdapter<Alumno> recyclerImageAdapter = new TxtAndImageRecyclerAdapter<Alumno>(alumnos, getBaseContext(), new TxtAndImageInterface<Alumno>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, ImageView imageView, Alumno alumno) {
                        String txt = alumno.getCarnet() + " - " + alumno.getPersona().getNombre()
                                + " " + alumno.getPersona().getApellido();
                        textView.setText(txt);

                        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.image_person);

                        imageView.setImageDrawable(getDrawable(R.drawable.image_person));

                        if (alumno.getPersona().getIdImagen() != null && alumno.getPersona().getIdImagen() > 0) {
                            imagenRestService.buscarPorId(alumno.getPersona().getIdImagen().longValue(), new CallBackDisposableInterface<Imagen>() {
                                @Override
                                public void onCallBack(Imagen imagen) {
                                    String urlImagen = ApiData.API1_URL.concat("imagen/download/").concat(imagen.getNombre());
                                    Log.w("PICASO_URL", "imagen " + urlImagen);
                                    Picasso.get().load(urlImagen).resize(100, 100).into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Log.w("PICASO", "imagen cargada");
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.e("PICASO", "imagen no cargada", e);
                                            imageView.setImageDrawable(getDrawable(R.drawable.image_person));
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
                    public void onItemClick(ConstraintLayout constraint, Alumno alumno, int position) {
                        alumnoSelected = alumno;
                        if (alumnoSelected != null) {
                            btnEliminar.setEnabled(Boolean.TRUE);
                            btnEditar.setEnabled(Boolean.TRUE);

                            btnEditar.setOnClickListener(v -> {
                                btnEliminar.setEnabled(Boolean.FALSE);
                                btnEditar.setEnabled(Boolean.FALSE);
                                Intent intent = new Intent(getBaseContext(), RegistrarAlumnoActivity.class);
                                intent.putExtra("IdAlumno", alumno.getIdAlumno());
                                startActivity(intent);
                            });

                            btnEliminar.setOnClickListener(v -> {
                                alumnoRestService.eliminarEntidad(alumno, new CallBackVoidInterface() {
                                    @Override
                                    public void onCallBack() {
                                        btnEliminar.setEnabled(Boolean.FALSE);
                                        btnEditar.setEnabled(Boolean.FALSE);
                                        cargarRecyclerList();
                                    }

                                    @Override
                                    public void onThrow(Throwable throwable) {

                                    }
                                });
                            });
                        }
                    }
                });
                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
                recyclerView.setAdapter(recyclerImageAdapter);
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
                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
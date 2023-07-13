package com.ues.sv.proyecto.controladministrativoapp.views.rest;

import android.content.Intent;
import android.os.Bundle;
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
import com.ues.sv.proyecto.controladministrativoapp.models.Imagen;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.rest.conf.ApiData;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.ImagenRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.MateriaRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.TxtAndImageInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.TxtAndImageRecyclerAdapter;

import java.util.List;

public class VerMateriaActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private Materia materiaSelected = null;
    private MateriaRestService materiaRestService;
    private ImagenRestService imagenRestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_materia);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        materiaRestService = new MateriaRestService();
        imagenRestService = new ImagenRestService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarMateriaActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
        onBack();
    }

    private void cargarRecyclerList() {
        materiaRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<Materia>>() {
            @Override
            public void onCallBack(List<Materia> materias) {
                TxtAndImageRecyclerAdapter<Materia> recyclerImageAdapter = new TxtAndImageRecyclerAdapter<>(materias, getBaseContext(), new TxtAndImageInterface<Materia>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, ImageView imageView, Materia materia) {
                        String txt = materia.getNombreMateria();
                        textView.setText(txt);

                        imageView.setImageDrawable(getDrawable(R.drawable.image_materia));

                        if (materia.getIdImagen() != null && materia.getIdImagen() > 0) {
                            imagenRestService.buscarPorId(materia.getIdImagen().longValue(), new CallBackDisposableInterface<Imagen>() {
                                @Override
                                public void onCallBack(Imagen imagen) {
                                    String urlImagen = ApiData.API1_URL.concat("imagen/download/").concat(imagen.getNombre());
                                    Picasso.get().load(urlImagen).resize(100, 100).into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            imageView.setImageDrawable(getDrawable(R.drawable.image_materia));
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
                    public void onItemClick(ConstraintLayout constraint, Materia materia, int position) {
                        materiaSelected = materia;
                        if (materiaSelected != null) {
                            btnEliminar.setEnabled(Boolean.TRUE);
                            btnEditar.setEnabled(Boolean.TRUE);

                            btnEditar.setOnClickListener(v -> {
                                btnEliminar.setEnabled(Boolean.FALSE);
                                btnEditar.setEnabled(Boolean.FALSE);
                                Intent intent = new Intent(getBaseContext(), RegistrarMateriaActivity.class);
                                intent.putExtra("IdMateria", materia.getIdMateria());
                                intent.putExtra("NombreMateria", materia.getNombreMateria());
                                startActivity(intent);
                            });

                            btnEliminar.setOnClickListener(v -> {
                                materiaRestService.eliminarEntidad(materia, new CallBackVoidInterface() {
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
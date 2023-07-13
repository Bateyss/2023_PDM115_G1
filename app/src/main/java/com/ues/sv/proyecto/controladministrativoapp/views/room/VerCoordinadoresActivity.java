package com.ues.sv.proyecto.controladministrativoapp.views.room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.service.CoordinadorService;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.TxtAndImageInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.TxtAndImageRecyclerAdapter;

import java.util.List;

public class VerCoordinadoresActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private Coordinador coordinadorSelected = null;
    private CoordinadorService coordinadorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_coordinadores);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        coordinadorService = new CoordinadorService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarCoordinadorActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
        onBack();
    }

    private void cargarRecyclerList() {
        coordinadorService.obtenerListaEntidad(new CallBackDisposableInterface<List<Coordinador>>() {
            @Override
            public void onCallBack(List<Coordinador> coordinadores) {
                TxtAndImageRecyclerAdapter<Coordinador> recyclerImageAdapter = new TxtAndImageRecyclerAdapter<>(coordinadores, getBaseContext(), new TxtAndImageInterface<Coordinador>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, ImageView imageView, Coordinador coordinador) {
                        String txt = coordinador.getPersona().getNombre() + " " + coordinador.getPersona().getApellido();
                        textView.setText(txt);

                        imageView.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.image_person));
                    }

                    @Override
                    public void onItemClick(ConstraintLayout constraint, Coordinador coordinador, int position) {
                        coordinadorSelected = coordinador;
                        if (coordinadorSelected != null) {
                            btnEliminar.setEnabled(Boolean.TRUE);
                            btnEditar.setEnabled(Boolean.TRUE);

                            btnEditar.setOnClickListener(v -> {
                                btnEliminar.setEnabled(Boolean.FALSE);
                                btnEditar.setEnabled(Boolean.FALSE);
                                Intent intent = new Intent(getBaseContext(), RegistrarCoordinadorActivity.class);
                                intent.putExtra("IdCoordinador", coordinador.getIdCoordinador());
                                startActivity(intent);
                            });

                            btnEliminar.setOnClickListener(v -> {
                                coordinadorService.eliminarEntidad(coordinador, new CallBackVoidInterface() {
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
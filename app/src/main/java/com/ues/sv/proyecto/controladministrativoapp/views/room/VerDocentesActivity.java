package com.ues.sv.proyecto.controladministrativoapp.views.room;

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
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Docente;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.room.service.DocenteService;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.TxtAndImageInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.TxtAndImageRecyclerAdapter;

import java.util.List;

public class VerDocentesActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private Docente docenteSelected = null;
    private DocenteService docenteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_docentes);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        docenteService = new DocenteService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarDocenteActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
        onBack();
    }

    private void cargarRecyclerList() {
        docenteService.obtenerListaEntidad(new CallBackDisposableInterface<List<Docente>>() {
            @Override
            public void onCallBack(List<Docente> docentes) {
                TxtAndImageRecyclerAdapter<Docente> recyclerImageAdapter = new TxtAndImageRecyclerAdapter<>(docentes, getBaseContext(), new TxtAndImageInterface<Docente>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, ImageView imageView, Docente docente) {
                        String txt = docente.getPersona().getNombre() + " - " + docente.getPersona().getApellido();
                        textView.setText(txt);

                        imageView.setImageDrawable(getDrawable(R.drawable.image_person));
                    }

                    @Override
                    public void onItemClick(ConstraintLayout constraint, Docente docente, int position) {
                        docenteSelected = docente;
                        if (docenteSelected != null) {
                            btnEliminar.setEnabled(Boolean.TRUE);
                            btnEditar.setEnabled(Boolean.TRUE);

                            btnEditar.setOnClickListener(v -> {
                                btnEliminar.setEnabled(Boolean.FALSE);
                                btnEditar.setEnabled(Boolean.FALSE);
                                Intent intent = new Intent(getBaseContext(), RegistrarDocenteActivity.class);
                                intent.putExtra("IdDocente", docente.getIdDocente());
                                startActivity(intent);
                            });

                            btnEliminar.setOnClickListener(v -> {
                                docenteService.eliminarEntidad(docente, new CallBackVoidInterface() {
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
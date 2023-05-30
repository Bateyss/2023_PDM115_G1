package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Materia;
import com.ues.sv.proyecto.controladministrativoapp.service.MateriaService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.List;

public class VerMateriaActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private Materia materiaSelected = null;
    private MateriaService materiaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_materia);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        materiaService = new MateriaService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarMateriaActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
    }

    private void cargarRecyclerList() {
        materiaService.obtenerListaEntidad(new CallBackDisposableInterface<List<Materia>>() {
            @Override
            public void onCallBack(List<Materia> materias) {
                OnlyTxtRecyclerAdapter<Materia> recyclerAdapter = new OnlyTxtRecyclerAdapter<Materia>(materias, getBaseContext(), new OnlyTxtInterface<Materia>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, Materia materia) {
                        String txt = materia.getNombreMateria();
                        textView.setText(txt);
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
                                materiaService.eliminarEntidad(materia, new CallBackVoidInterface() {
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
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
    }
}
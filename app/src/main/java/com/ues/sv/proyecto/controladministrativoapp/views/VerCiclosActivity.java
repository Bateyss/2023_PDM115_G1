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
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.service.CicloService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.List;

public class VerCiclosActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private Ciclo cicloSelected = null;
    private CicloService cicloService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ciclos);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        cicloService = new CicloService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarCicloActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
    }

    private void cargarRecyclerList() {
        cicloService.obtenerListaEntidad(new CallBackDisposableInterface<List<Ciclo>>() {
            @Override
            public void onCallBack(List<Ciclo> ciclos) {
                OnlyTxtRecyclerAdapter<Ciclo> recyclerAdapter = new OnlyTxtRecyclerAdapter<Ciclo>(ciclos, getBaseContext(), new OnlyTxtInterface<Ciclo>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, Ciclo ciclo) {
                        String txt = ciclo.getNumeroCiclo() + " - " + ciclo.getNumeroAnio();
                        textView.setText(txt);
                    }

                    @Override
                    public void onItemClick(ConstraintLayout constraint, Ciclo ciclo, int position) {
                        cicloSelected = ciclo;
                        if (cicloSelected != null) {
                            btnEliminar.setEnabled(Boolean.TRUE);
                            btnEditar.setEnabled(Boolean.TRUE);

                            btnEditar.setOnClickListener(v -> {
                                btnEliminar.setEnabled(Boolean.FALSE);
                                btnEditar.setEnabled(Boolean.FALSE);
                                Intent intent = new Intent(getBaseContext(), RegistrarCicloActivity.class);
                                intent.putExtra("IdCiclo", ciclo.getIdCiclo());
                                intent.putExtra("NumeroCiclo", ciclo.getNumeroCiclo());
                                intent.putExtra("NumeroAnio", ciclo.getNumeroAnio());
                                startActivity(intent);
                            });

                            btnEliminar.setOnClickListener(v -> {
                                cicloService.eliminarEntidad(ciclo, new CallBackVoidInterface() {
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
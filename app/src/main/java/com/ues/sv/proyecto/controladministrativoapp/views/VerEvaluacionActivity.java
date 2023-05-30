package com.ues.sv.proyecto.controladministrativoapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.models.Evaluacion;
import com.ues.sv.proyecto.controladministrativoapp.service.CicloService;
import com.ues.sv.proyecto.controladministrativoapp.service.EvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.List;

public class VerEvaluacionActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private Evaluacion evaluacionSelected = null;
    private EvaluacionService evaluacionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_evaluacion);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        evaluacionService = new EvaluacionService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarEvaluacionActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
    }

    private void cargarRecyclerList() {
        evaluacionService.obtenerListaEntidad(new CallBackDisposableInterface<List<Evaluacion>>() {
            @Override
            public void onCallBack(List<Evaluacion> evaluaciones) {
                OnlyTxtRecyclerAdapter<Evaluacion> recyclerAdapter = new OnlyTxtRecyclerAdapter<Evaluacion>(evaluaciones, getBaseContext(), new OnlyTxtInterface<Evaluacion>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, Evaluacion evaluacion) {
                        String txt = evaluacion.getTipoEvaluacion().getNombreTipoEvaluacion() +
                                " " + evaluacion.getNumeroEvaluacion() +
                                " " + evaluacion.getCurso().getMateria().getNombreMateria() +
                                " " + evaluacion.getCurso().getCiclo().getNumeroAnio();
                        textView.setText(txt);
                    }

                    @Override
                    public void onItemClick(ConstraintLayout constraint, Evaluacion evaluacion, int position) {
                        evaluacionSelected = evaluacion;
                        if (evaluacionSelected != null) {
                            btnEliminar.setEnabled(Boolean.TRUE);
                            btnEditar.setEnabled(Boolean.TRUE);

                            btnEditar.setOnClickListener(v -> {
                                btnEliminar.setEnabled(Boolean.FALSE);
                                btnEditar.setEnabled(Boolean.FALSE);
                                Intent intent = new Intent(getBaseContext(), RegistrarEvaluacionActivity.class);
                                intent.putExtra("IdEvaluacion", evaluacion.getIdEvaluacion());
                                intent.putExtra("NumeroEvaluacion", evaluacion.getNumeroEvaluacion());
                                intent.putExtra("IdTipoEvaliacion", evaluacion.getTipoEvaluacion().getIdTipoEvaliacion());
                                intent.putExtra("IdCurso", evaluacion.getCurso().getIdCurso());
                                startActivity(intent);
                            });

                            btnEliminar.setOnClickListener(v -> {
                                evaluacionService.eliminarEntidad(evaluacion, new CallBackVoidInterface() {
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
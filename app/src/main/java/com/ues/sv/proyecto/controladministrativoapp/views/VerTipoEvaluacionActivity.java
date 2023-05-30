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
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.service.TipoEvaluacionService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.List;

public class VerTipoEvaluacionActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private TipoEvaluacion tipoEvaluacionSelected = null;
    private TipoEvaluacionService tipoEvaluacionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tipo_evaluacion);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        tipoEvaluacionService = new TipoEvaluacionService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarTipoEvaluacionActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
    }

    private void cargarRecyclerList() {
        tipoEvaluacionService.obtenerListaEntidad(new CallBackDisposableInterface<List<TipoEvaluacion>>() {
            @Override
            public void onCallBack(List<TipoEvaluacion> tiposEvaluaciones) {
                OnlyTxtRecyclerAdapter<TipoEvaluacion> recyclerAdapter = new OnlyTxtRecyclerAdapter<TipoEvaluacion>(tiposEvaluaciones, getBaseContext(), new OnlyTxtInterface<TipoEvaluacion>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, TipoEvaluacion tipoEvaluacion) {
                        String txt = tipoEvaluacion.getNombreTipoEvaluacion();
                        textView.setText(txt);
                    }

                    @Override
                    public void onItemClick(ConstraintLayout constraint, TipoEvaluacion tipoEvaluacion, int position) {
                        tipoEvaluacionSelected = tipoEvaluacion;
                        if (tipoEvaluacionSelected != null) {
                            btnEliminar.setEnabled(Boolean.TRUE);
                            btnEditar.setEnabled(Boolean.TRUE);

                            btnEditar.setOnClickListener(v -> {
                                btnEliminar.setEnabled(Boolean.FALSE);
                                btnEditar.setEnabled(Boolean.FALSE);
                                Intent intent = new Intent(getBaseContext(), RegistrarTipoEvaluacionActivity.class);
                                intent.putExtra("IdTipoEvaliacion", tipoEvaluacion.getIdTipoEvaliacion());
                                intent.putExtra("NombreTipoEvaluacion", tipoEvaluacion.getNombreTipoEvaluacion());
                                startActivity(intent);
                            });

                            btnEliminar.setOnClickListener(v -> {
                                tipoEvaluacionService.eliminarEntidad(tipoEvaluacion, new CallBackVoidInterface() {
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
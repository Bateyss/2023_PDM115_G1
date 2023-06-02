package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.SolicitudRevision;
import com.ues.sv.proyecto.controladministrativoapp.service.SolicitudRevisionService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.DateUtils;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.List;

public class VerSolicitudEvaluacionActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private SolicitudRevision solicitudRevisionSelected = null;

    private SolicitudRevisionService solicitudRevisionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_solicitud_evaluacion);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        solicitudRevisionService = new SolicitudRevisionService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), RegistrarSolicitudRevicionActivity.class);
            startActivity(intent);
        });

        cargarRecyclerList();
        onBack();
    }

    private void cargarRecyclerList() {
        try {
            Bundle bundle = getIntent().getExtras();
            Long idEvaluacion = bundle.getLong("IdEvaluacion", 0L);
            if (idEvaluacion > 0L) {
                solicitudRevisionService.obtenerListaPorEvaluacionId(idEvaluacion, new CallBackDisposableInterface<List<SolicitudRevision>>() {
                    @Override
                    public void onCallBack(List<SolicitudRevision> solicitudRevisions) {
                        OnlyTxtRecyclerAdapter<SolicitudRevision> recyclerAdapter = new OnlyTxtRecyclerAdapter<SolicitudRevision>(solicitudRevisions, getBaseContext(), new OnlyTxtInterface<SolicitudRevision>() {
                            @Override
                            public void imprimirdatos(MaterialTextView textView, SolicitudRevision solicitudRevision) {
                                String txt = solicitudRevision.getInscripcion().getAlumno().getCarnet()
                                        + " " + solicitudRevision.getEvaluacion().getTipoEvaluacion().getNombreTipoEvaluacion()
                                        + " " + solicitudRevision.getEvaluacion().getNumeroEvaluacion()
                                        + " \n " + solicitudRevision.getEvaluacion().getCurso().getMateria().getNombreMateria()
                                        + " \n " + solicitudRevision.getEvaluacion().getCurso().getCiclo().getNumeroAnio()
                                        + " \n " + DateUtils.formatDate(solicitudRevision.getFechaCreacion(), DateUtils.FORMAT_DD_MM_YYYY);
                                textView.setText(txt);
                            }

                            @Override
                            public void onItemClick(ConstraintLayout constraint, SolicitudRevision solicitudRevision, int position) {
                                solicitudRevisionSelected = solicitudRevision;
                                if (solicitudRevisionSelected != null) {
                                    btnEliminar.setEnabled(Boolean.TRUE);
                                    btnEditar.setEnabled(Boolean.TRUE);

                                    btnEditar.setOnClickListener(v -> {
                                        btnEliminar.setEnabled(Boolean.FALSE);
                                        btnEditar.setEnabled(Boolean.FALSE);
                                        Intent intent = new Intent(getBaseContext(), RegistrarSolicitudRevicionActivity.class);
                                        intent.putExtra("IdSolicitudRevision", solicitudRevision.getIdSolicitudRevision());
                                        startActivity(intent);
                                    });

                                    btnEliminar.setOnClickListener(v -> {
                                        solicitudRevisionService.eliminarEntidad(solicitudRevision, new CallBackVoidInterface() {
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


        } catch (Exception e) {
            Log.e("CARGAR_DATOS", e.getMessage(), e.getCause());
        }
    }
    public void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(getApplicationContext(),VerEvaluacionActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
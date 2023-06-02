package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Impresion;
import com.ues.sv.proyecto.controladministrativoapp.models.MotivoErrorImpresion;
import com.ues.sv.proyecto.controladministrativoapp.service.ImpresionService;
import com.ues.sv.proyecto.controladministrativoapp.service.MotivoErrorImpresionService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.List;

public class VerImpresionesActivity extends AppCompatActivity {

    private TextInputLayout layouMotivo, layouObservacion;
    private MaterialButton btnCrear, btnEditar;
    private RecyclerView recyclerView;

    private ImpresionService impresionService;
    private MotivoErrorImpresionService motivoErrorImpresionService;

    private Impresion impresionData = new Impresion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_impresiones);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        recyclerView = findViewById(R.id.recyclerList);

        layouMotivo = findViewById(R.id.input_layout_motivo);
        layouObservacion = findViewById(R.id.input_layout_descripcion);

        impresionService = new ImpresionService(getApplicationContext());
        motivoErrorImpresionService = new MotivoErrorImpresionService(getApplicationContext());

        btnCrear.setOnClickListener(v -> agregarMotivo());
        btnEditar.setOnClickListener(v -> editarObservacion());

        cargarDatos();
        onBack();
    }

    private void cargarDatos() {
        try {
            Bundle bundle = getIntent().getExtras();
            Long idImpresion = bundle.getLong("IdEvaluacion", 0L);
            if (idImpresion != null) {
                impresionService.buscarPorIdEvaluacion(idImpresion, new CallBackDisposableInterface<Impresion>() {
                    @Override
                    public void onCallBack(Impresion impresion) {
                        impresionData = impresion;
                        motivoErrorImpresionService.obtenerListaPorImpresion(impresion, new CallBackDisposableInterface<List<MotivoErrorImpresion>>() {
                            @Override
                            public void onCallBack(List<MotivoErrorImpresion> motivoErrorImpresions) {
                                OnlyTxtRecyclerAdapter<MotivoErrorImpresion> recyclerAdapter = new OnlyTxtRecyclerAdapter<MotivoErrorImpresion>(motivoErrorImpresions, getBaseContext(), new OnlyTxtInterface<MotivoErrorImpresion>() {

                                    @Override
                                    public void imprimirdatos(MaterialTextView textView, MotivoErrorImpresion motivoErrorImpresion) {
                                        textView.setText(motivoErrorImpresion.getDescripcionMotivo());
                                    }

                                    @Override
                                    public void onItemClick(ConstraintLayout constraint, MotivoErrorImpresion motivoErrorImpresion, int position) {

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

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            } else {
                layouMotivo.setEnabled(false);
                layouObservacion.setEnabled(false);
            }
        } catch (Exception e) {

        }
    }

    private void agregarMotivo() {
        if (this.impresionData.getIdImpresion() != null) {
            String motivoText = layouMotivo.getEditText().getText().toString();
            if (!motivoText.isEmpty() && motivoText.length() <= 300) {
                MotivoErrorImpresion motivoErrorImpresion = new MotivoErrorImpresion();
                motivoErrorImpresion.setIdMotivo(0L);
                motivoErrorImpresion.setDescripcionMotivo(motivoText);
                motivoErrorImpresion.setImpresion(this.impresionData);
                motivoErrorImpresionService.registrarEntidad(motivoErrorImpresion, new CallBackDisposableInterface() {
                    @Override
                    public void onCallBack(Object o) {

                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            } else {
                layouMotivo.setError("Escribe el motivo");
            }
        }
    }

    private void editarObservacion() {
        if (this.impresionData.getIdImpresion() != null) {
            String observacionTxt = layouObservacion.getEditText().getText().toString();
            if (!observacionTxt.isEmpty() && observacionTxt.length() <= 300) {
                this.impresionData.setObservacionesImpresion(observacionTxt);
                impresionService.editarEntidad(impresionData, new CallBackVoidInterface() {
                    @Override
                    public void onCallBack() {
                        cargarDatos();
                    }

                    @Override
                    public void onThrow(Throwable throwable) {

                    }
                });
            } else {
                layouMotivo.setError("Escribe la observacion");
            }
        }
    }

    public void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(getApplicationContext(), VerEvaluacionActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
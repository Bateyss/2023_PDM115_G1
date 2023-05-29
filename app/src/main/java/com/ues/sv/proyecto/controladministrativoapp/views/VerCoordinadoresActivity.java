package com.ues.sv.proyecto.controladministrativoapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Ciclo;
import com.ues.sv.proyecto.controladministrativoapp.models.Coordinador;
import com.ues.sv.proyecto.controladministrativoapp.service.CicloService;
import com.ues.sv.proyecto.controladministrativoapp.service.CoordinadorService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

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
            //Intent intent = new Intent(getBaseContext(), RegistrarAlumnoActivity.class);
            //startActivity(intent);
        });

        cargarRecyclerList();

    }

    private void cargarRecyclerList() {
        coordinadorService.obtenerListaEntidad(new CallBackDisposableInterface<List<Coordinador>>() {
            @Override
            public void onCallBack(List<Coordinador> coordinadores) {
                OnlyTxtRecyclerAdapter<Coordinador> recyclerAdapter = new OnlyTxtRecyclerAdapter<Coordinador>(coordinadores, getBaseContext(), new OnlyTxtInterface<Coordinador>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, Coordinador coordinador) {
                        String txt = coordinador.getPersona().getNombre() + " " + coordinador.getPersona().getApellido();
                        textView.setText(txt);
                    }

                    @Override
                    public void onItemClick(ConstraintLayout constraint, Coordinador coordinador, int position) {
                        coordinadorSelected = coordinador;
                        if (coordinadorSelected != null) {
                            btnEliminar.setEnabled(Boolean.TRUE);
                            btnEditar.setEnabled(Boolean.TRUE);

                            btnEditar.setOnClickListener(v -> {
                                btnEliminar.setEnabled(Boolean.FALSE);
                                /*btnEditar.setEnabled(Boolean.FALSE);
                                Intent intent = new Intent(getBaseContext(), RegistrarAlumnoActivity.class);
                                intent.putExtra("Carnet", alumno.getCarnet());
                                intent.putExtra("Nombre", alumno.getNombre());
                                intent.putExtra("Apellido", alumno.getApellido());
                                intent.putExtra("MatGanadas", alumno.getMatGanadas());
                                intent.putExtra("Sexo", alumno.getSexo());
                                startActivity(intent);*/
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
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
    }
}
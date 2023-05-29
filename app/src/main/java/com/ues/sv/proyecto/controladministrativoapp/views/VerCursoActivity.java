package com.ues.sv.proyecto.controladministrativoapp.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Curso;
import com.ues.sv.proyecto.controladministrativoapp.service.CursoService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.List;

public class VerCursoActivity extends AppCompatActivity {

    private MaterialButton btnCrear, btnEditar, btnEliminar;
    private RecyclerView recyclerView;
    private Curso cursoSelected = null;
    private CursoService cursoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_curso);

        btnCrear = findViewById(R.id.btn_crear);
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        recyclerView = findViewById(R.id.recyclerList);

        cursoService = new CursoService(getApplicationContext());

        btnCrear.setOnClickListener(v -> {
            //Intent intent = new Intent(getBaseContext(), RegistrarAlumnoActivity.class);
            //startActivity(intent);
        });

        cargarRecyclerList();
    }

    private void cargarRecyclerList() {
        cursoService.obtenerListaEntidad(new CallBackDisposableInterface<List<Curso>>() {
            @Override
            public void onCallBack(List<Curso> cursos) {
                OnlyTxtRecyclerAdapter<Curso> recyclerAdapter = new OnlyTxtRecyclerAdapter<Curso>(cursos, getBaseContext(), new OnlyTxtInterface<Curso>() {
                    @Override
                    public void imprimirdatos(MaterialTextView textView, Curso curso) {
                        String txt = curso.getMateria() + " " + curso.getCiclo().getNumeroAnio();
                        textView.setText(txt);
                    }

                    @Override
                    public void onItemClick(ConstraintLayout constraint, Curso curso, int position) {
                        cursoSelected = curso;
                        if (cursoSelected != null) {
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
                                cursoService.eliminarEntidad(curso, new CallBackVoidInterface() {
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
package com.ues.sv.proyecto.controladministrativoapp.views.rest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.TipoEvaluacion;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.PruebaRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.TipoEvaluacionRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.adapters.OnlyTxtRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MaterialButton btnParametros, btnEvaluaciones, btnCargarDatos;

    private TipoEvaluacionRestService tipoEvaluacionRestService;
    private PruebaRestService pruebaRestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btnParametros = findViewById(R.id.btn_parametros);
        btnEvaluaciones = findViewById(R.id.btn_evaluaciones);
        btnCargarDatos = findViewById(R.id.btn_cargar_datos);
        recyclerView = findViewById(R.id.recyclerList);

        tipoEvaluacionRestService = new TipoEvaluacionRestService();
        pruebaRestService = new PruebaRestService();

        btnParametros.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ParametrosActivity.class);
            startActivity(intent);
        });

        btnEvaluaciones.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), VerEvaluacionActivity.class);
            startActivity(intent);
        });

        btnCargarDatos.setOnClickListener(v -> comprobarBaseDeDatos());

        cargarRecyclerView();
        onBack();
    }

    private void comprobarBaseDeDatos() {
        tipoEvaluacionRestService.obtenerListaEntidad(new CallBackDisposableInterface<List<TipoEvaluacion>>() {
            @Override
            public void onCallBack(List<TipoEvaluacion> evaluacions) {
                if (!evaluacions.isEmpty()) {
                    Toast.makeText(InicioActivity.this, "Ya existen datos", Toast.LENGTH_SHORT).show();
                    Toast.makeText(InicioActivity.this, "BORRAR CACHE", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onThrow(Throwable throwable) {
                cargarDatosDePrueba();
            }
        });
    }

    private void cargarDatosDePrueba() {
        pruebaRestService.cargarDatosDePrueba();
    }

    private void cargarRecyclerView() {
        List<Class<?>> objects = new ArrayList<>();
        objects.add(VerCiclosActivity.class);
        objects.add(VerCoordinadoresActivity.class);
        objects.add(VerCursoActivity.class);
        objects.add(VerDocentesActivity.class);
        objects.add(VerEvaluacionActivity.class);
        objects.add(VerMateriaActivity.class);
        objects.add(VerTipoEvaluacionActivity.class);
        objects.add(VerAlumnosActivity.class);

        OnlyTxtRecyclerAdapter<Class<?>> recyclerAdapter = new OnlyTxtRecyclerAdapter<Class<?>>(objects, getBaseContext(), new OnlyTxtInterface<Class<?>>() {
            @Override
            public void imprimirdatos(MaterialTextView textView, Class<?> aClass) {
                textView.setText(aClass.getSimpleName().replace("Ver", "").replace("Activity", ""));
            }

            @Override
            public void onItemClick(ConstraintLayout constraint, Class<?> aClass, int position) {
                try {
                    Intent intent = new Intent(getBaseContext(), aClass);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
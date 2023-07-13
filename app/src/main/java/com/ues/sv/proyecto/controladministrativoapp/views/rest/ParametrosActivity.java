package com.ues.sv.proyecto.controladministrativoapp.views.rest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Parametros;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.ParametrosRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class ParametrosActivity extends AppCompatActivity {

    private TextInputLayout layoutRevision, layoutDiferir, layoutRepetir;
    private MaterialButton btnActualizar;

    private ParametrosRestService parametrosRestService;

    private final int ID_HISTORICO_REVISION = 1;
    private final int ID_HISTORICO_DIFERIR = 2;
    private final int ID_HISTORICO_REPETIR = 3;

    private boolean actualizar1 = false, actualizar2 = false, actualizar3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros);
        layoutRevision = findViewById(R.id.input_layout_revision);
        layoutDiferir = findViewById(R.id.input_layout_diferir);
        layoutRepetir = findViewById(R.id.input_layout_repetir);
        btnActualizar = findViewById(R.id.btn_guardar);

        parametrosRestService = new ParametrosRestService();

        cargarDatos();

        btnActualizar.setOnClickListener(v -> {
            if (validarDatos()) {
                actualizar1 = true;
                actualizar2 = true;
                actualizar3 = true;
                actualizar();
            }
        });
    }

    private void actualizar() {
        parametrosRestService.buscarPorIdHistorico(ID_HISTORICO_REVISION, new CallBackDisposableInterface<Parametros>() {
            @Override
            public void onCallBack(Parametros parametros) {
                if (actualizar1) {
                    parametros.setValor(layoutRevision.getEditText().getText().toString());
                    parametrosRestService.editarEntidad(parametros, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            actualizar1 = false;
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                }

            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        parametrosRestService.buscarPorIdHistorico(ID_HISTORICO_DIFERIR, new CallBackDisposableInterface<Parametros>() {
            @Override
            public void onCallBack(Parametros parametros) {
                if (actualizar2) {
                    parametros.setValor(layoutDiferir.getEditText().getText().toString());
                    parametrosRestService.editarEntidad(parametros, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            actualizar2 = false;
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                }

            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        parametrosRestService.buscarPorIdHistorico(ID_HISTORICO_REPETIR, new CallBackDisposableInterface<Parametros>() {
            @Override
            public void onCallBack(Parametros parametros) {
                if (actualizar3) {
                    parametros.setValor(layoutRepetir.getEditText().getText().toString());
                    parametrosRestService.editarEntidad(parametros, new CallBackDisposableInterface() {
                        @Override
                        public void onCallBack(Object o) {
                            actualizar3 = false;
                        }

                        @Override
                        public void onThrow(Throwable throwable) {

                        }
                    });
                }
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
    }

    private boolean validarDatos() {
        boolean valid = true;
        Map<String, TextInputLayout> param1 = new HashMap<>();
        Map<String, TextInputLayout> param2 = new HashMap<>();
        Map<String, TextInputLayout> param3 = new HashMap<>();
        param1.put("valor", layoutRevision);
        param2.put("valor", layoutDiferir);
        param3.put("valor", layoutRepetir);
        if (!ValidationUtils.validate(Parametros.class, param1)) valid = false;
        if (!ValidationUtils.validate(Parametros.class, param2)) valid = false;
        if (!ValidationUtils.validate(Parametros.class, param3)) valid = false;
        return valid;
    }

    private void cargarDatos() {
        parametrosRestService.buscarPorIdHistorico(ID_HISTORICO_REVISION, new CallBackDisposableInterface<Parametros>() {
            @Override
            public void onCallBack(Parametros parametros) {
                layoutRevision.getEditText().setText(parametros.getValor());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        parametrosRestService.buscarPorIdHistorico(ID_HISTORICO_DIFERIR, new CallBackDisposableInterface<Parametros>() {
            @Override
            public void onCallBack(Parametros parametros) {
                layoutDiferir.getEditText().setText(parametros.getValor());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
        parametrosRestService.buscarPorIdHistorico(ID_HISTORICO_REPETIR, new CallBackDisposableInterface<Parametros>() {
            @Override
            public void onCallBack(Parametros parametros) {
                layoutRepetir.getEditText().setText(parametros.getValor());
            }

            @Override
            public void onThrow(Throwable throwable) {

            }
        });
    }


}
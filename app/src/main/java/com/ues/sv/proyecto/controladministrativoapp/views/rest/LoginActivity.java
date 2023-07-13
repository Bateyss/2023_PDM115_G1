package com.ues.sv.proyecto.controladministrativoapp.views.rest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.UsuarioRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private UsuarioRestService usuarioRestService;

    private TextInputLayout userNameLayout, userPassLayout;
    private MaterialButton btnLogin, brnRegistrar, btnAnonimunLogin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameLayout = findViewById(R.id.input_layout_username);
        userPassLayout = findViewById(R.id.input_layout_userpass);
        btnLogin = findViewById(R.id.btn_login);
        brnRegistrar = findViewById(R.id.btn_login_registrar);
        btnAnonimunLogin = findViewById(R.id.btn_login_autoiniciar);

        usuarioRestService = new UsuarioRestService();

        btnLogin.setOnClickListener(v -> ingresarSesion());

        btnAnonimunLogin.setOnClickListener(v -> {
            v.setEnabled(Boolean.FALSE);
            Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(intent);
        });

        brnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
            startActivity(intent);
        });
    }

    private void ingresarSesion() {
        btnLogin.setEnabled(Boolean.FALSE);
        if (valiadarFormulario()) {
            String username = userNameLayout.getEditText().getText().toString();
            String userpass = userPassLayout.getEditText().getText().toString();
            usuarioRestService.buscarUserNameAndPass(username, userpass, new CallBackDisposableInterface<Usuario>() {
                @Override
                public void onCallBack(Usuario usuario) {
                    // TODO inicio de sesion exitoso, intent para mover a siguiente pantalla
                    btnLogin.setEnabled(Boolean.TRUE);
                    Toast.makeText(LoginActivity.this, "Inicio Correcto", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onThrow(Throwable throwable) {
                    // puede quedar vacio
                    // puede mostra un Toast
                    Toast.makeText(LoginActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(Boolean.TRUE);
                }
            });
        } else {
            btnLogin.setEnabled(Boolean.TRUE);
        }
    }

    private boolean valiadarFormulario() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("userName", userNameLayout);
        map.put("userPass", userPassLayout);
        return ValidationUtils.validate(Usuario.class, map);
    }
}
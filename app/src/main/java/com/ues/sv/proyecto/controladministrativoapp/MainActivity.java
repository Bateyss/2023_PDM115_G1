package com.ues.sv.proyecto.controladministrativoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;
import com.ues.sv.proyecto.controladministrativoapp.service.UsuarioService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;
import com.ues.sv.proyecto.controladministrativoapp.views.InicioActivity;
import com.ues.sv.proyecto.controladministrativoapp.views.RegistrarUsuarioActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private UsuarioService usuarioService;

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

        usuarioService = new UsuarioService(getApplicationContext());

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
            usuarioService.buscarUserNameAndPass(username, userpass, new CallBackDisposableInterface<Usuario>() {
                @Override
                public void onCallBack(Usuario usuario) {
                    // TODO inicio de sesion exitoso, intent para mover a siguiente pantalla
                    Toast.makeText(MainActivity.this, "Inicio Correcto", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onThrow(Throwable throwable) {
                    // puede quedar vacio
                    // puede mostra un Toast
                    Toast.makeText(MainActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
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
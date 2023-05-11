package com.ues.sv.proyecto.controladministrativoapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;
import com.ues.sv.proyecto.controladministrativoapp.service.UsuarioService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private UsuarioService usuarioService;

    private TextInputLayout userNameLayout, userPassLayout;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameLayout = findViewById(R.id.input_layout_username);
        userPassLayout = findViewById(R.id.input_layout_userpass);
        btnLogin = findViewById(R.id.btn_login);

        usuarioService = new UsuarioService(getApplicationContext());

        btnLogin.setOnClickListener(v -> ingresarSesion());
    }

    private void ingresarSesion() {
        if (valiadarFormulario()) {
            String username = userNameLayout.getEditText().getText().toString();
            String userpass = userPassLayout.getEditText().getText().toString();
            usuarioService.buscarUserNameAndPass(username, userpass, new CallBackDisposableInterface<Usuario>() {
                @Override
                public void onCallBack(Usuario usuario) {
                    // TODO inicio de sesion exitoso, intent para mover a siguiente pantalla
                    Toast.makeText(MainActivity.this, "Inicio Correcto", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onThrow(Throwable throwable) {
                    // puede quedar vacio
                    // puede mostra un Toast
                    Toast.makeText(MainActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean valiadarFormulario() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("userName", userNameLayout);
        map.put("userPass", userPassLayout);
        return ValidationUtils.validate(Usuario.class, map);
    }
}
package com.ues.sv.proyecto.controladministrativoapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;
import com.ues.sv.proyecto.controladministrativoapp.service.PersonaService;
import com.ues.sv.proyecto.controladministrativoapp.service.UsuarioService;
import com.ues.sv.proyecto.controladministrativoapp.service.interfaces.CallBackVoidInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private UsuarioService usuarioService;
    private PersonaService personaService;

    private TextInputLayout userNombreLayout, userApellidoLayout, userIdentificacionLayoutv, userSexoLayout, userNameLayout, userPassLayout;
    private MaterialButton brnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        usuarioService = new UsuarioService(getApplicationContext());
        personaService = new PersonaService(getApplicationContext());

        userNombreLayout = findViewById(R.id.input_layout_nombre);
        userApellidoLayout = findViewById(R.id.input_layout_apellido);
        userIdentificacionLayoutv = findViewById(R.id.input_layout_identificacion);
        userSexoLayout = findViewById(R.id.input_layout_sexo);
        userNameLayout = findViewById(R.id.input_layout_username);
        userPassLayout = findViewById(R.id.input_layout_userpass);
        brnRegistrar = findViewById(R.id.btn_registrar);

        brnRegistrar.setOnClickListener(v -> {
            if (valiadarFormularioPersona() && valiadarFormularioUsuario()) {
                Persona persona = new Persona();
                persona.setNombre(userNombreLayout.getEditText().getText().toString());
                persona.setApellido(userApellidoLayout.getEditText().getText().toString());
                persona.setIdentificacion(userIdentificacionLayoutv.getEditText().getText().toString());
                persona.setSexo(userSexoLayout.getEditText().getText().toString());
                personaService.registrarEntidad(persona, new CallBackVoidInterface() {
                    @Override
                    public void onCallBack() {
                        Usuario usuario = new Usuario();
                        usuario.setUserName(userNameLayout.getEditText().getText().toString());
                        usuario.setUserPass(userPassLayout.getEditText().getText().toString());

                        usuarioService.registrarEntidad(usuario, new CallBackVoidInterface() {
                            @Override
                            public void onCallBack() {
                                Toast.makeText(RegistrarUsuarioActivity.this, "usuario Registrado", Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

                                preferences.edit().putString("username", usuario.getUserName()).apply();
                                preferences.edit().putString("usernombre", usuario.getPersona().getNombre()).apply();
                                preferences.edit().putString("userapellido", usuario.getPersona().getApellido()).apply();
                                preferences.edit().putString("useridentificacion", usuario.getPersona().getIdentificacion()).apply();
                                preferences.edit().putString("usersexo", usuario.getPersona().getSexo()).apply();

//                                Intent intent = new Intent();
//                                startActivity(intent);
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


            }
        });
    }

    private boolean valiadarFormularioPersona() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("nombre", userNombreLayout);
        map.put("apellido", userApellidoLayout);
        map.put("identificacion", userIdentificacionLayoutv);
        map.put("sexo", userSexoLayout);
        return ValidationUtils.validate(Persona.class, map);
    }

    private boolean valiadarFormularioUsuario() {
        Map<String, TextInputLayout> map = new HashMap<>();
        map.put("userName", userNameLayout);
        map.put("userPass", userPassLayout);
        return ValidationUtils.validate(Usuario.class, map);
    }
}
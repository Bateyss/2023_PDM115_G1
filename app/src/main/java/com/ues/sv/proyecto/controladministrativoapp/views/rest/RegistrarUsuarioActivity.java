package com.ues.sv.proyecto.controladministrativoapp.views.rest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.ues.sv.proyecto.controladministrativoapp.MainActivity;
import com.ues.sv.proyecto.controladministrativoapp.R;
import com.ues.sv.proyecto.controladministrativoapp.models.Persona;
import com.ues.sv.proyecto.controladministrativoapp.models.Usuario;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.PersonaRestService;
import com.ues.sv.proyecto.controladministrativoapp.rest.service.UsuarioRestService;
import com.ues.sv.proyecto.controladministrativoapp.room.bin.CallBackDisposableInterface;
import com.ues.sv.proyecto.controladministrativoapp.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private UsuarioRestService usuarioRestService;
    private PersonaRestService personaRestService;

    private TextInputLayout userNombreLayout, userApellidoLayout, userIdentificacionLayoutv, userSexoLayout, userNameLayout, userPassLayout;
    private MaterialButton brnRegistrar;

    private Usuario usuarioData = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        usuarioRestService = new UsuarioRestService();
        personaRestService = new PersonaRestService();

        userNombreLayout = findViewById(R.id.input_layout_nombre);
        userApellidoLayout = findViewById(R.id.input_layout_apellido);
        userIdentificacionLayoutv = findViewById(R.id.input_layout_identificacion);
        userSexoLayout = findViewById(R.id.input_layout_sexo);
        userNameLayout = findViewById(R.id.input_layout_username);
        userPassLayout = findViewById(R.id.input_layout_userpass);
        brnRegistrar = findViewById(R.id.btn_registrar);

        brnRegistrar.setOnClickListener(v -> {
            if (valiadarFormularioPersona() && valiadarFormularioUsuario()) {
                final Persona[] persona = {new Persona()};
                persona[0].setNombre(userNombreLayout.getEditText().getText().toString());
                persona[0].setApellido(userApellidoLayout.getEditText().getText().toString());
                persona[0].setIdentificacion(userIdentificacionLayoutv.getEditText().getText().toString());
                persona[0].setSexo(userSexoLayout.getEditText().getText().toString());
                personaRestService.registrarEntidad(persona[0], new CallBackDisposableInterface() {
                    @Override
                    public void onCallBack(Object o) {
                        persona[0] = (Persona) o;
//                        persona.setIdPersona((long) o);
                        usuarioData.setPersona(persona[0]);
                        usuarioData.setUserName(userNameLayout.getEditText().getText().toString());
                        usuarioData.setUserPass(userPassLayout.getEditText().getText().toString());

                        usuarioRestService.registrarEntidad(usuarioData, new CallBackDisposableInterface() {
                            @Override
                            public void onCallBack(Object o) {
                                Toast.makeText(RegistrarUsuarioActivity.this, "usuario Registrado", Toast.LENGTH_SHORT).show();
                                SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

                                preferences.edit().putString("username", usuarioData.getUserName()).apply();
                                preferences.edit().putString("usernombre", usuarioData.getPersona().getNombre()).apply();
                                preferences.edit().putString("userapellido", usuarioData.getPersona().getApellido()).apply();
                                preferences.edit().putString("useridentificacion", usuarioData.getPersona().getIdentificacion()).apply();
                                preferences.edit().putString("usersexo", usuarioData.getPersona().getSexo()).apply();

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
        onBack();
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

    public void onBack() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
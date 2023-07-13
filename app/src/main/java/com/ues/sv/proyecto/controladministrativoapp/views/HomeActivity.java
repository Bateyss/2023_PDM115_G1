package com.ues.sv.proyecto.controladministrativoapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.ues.sv.proyecto.controladministrativoapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MaterialButton buttonRoom = findViewById(R.id.btn_room);
        MaterialButton buttonRest = findViewById(R.id.btn_rest);

        buttonRoom.setOnClickListener(c -> {
            Intent intent = new Intent(getApplicationContext(), com.ues.sv.proyecto.controladministrativoapp.views.room.LoginActivity.class);
            startActivity(intent);
        });

        buttonRest.setOnClickListener(c -> {
            Intent intent = new Intent(getApplicationContext(), com.ues.sv.proyecto.controladministrativoapp.views.rest.LoginActivity.class);
            startActivity(intent);
        });
    }
}
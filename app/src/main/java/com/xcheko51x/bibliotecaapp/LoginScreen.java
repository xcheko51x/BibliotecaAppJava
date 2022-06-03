package com.xcheko51x.bibliotecaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.xcheko51x.bibliotecaapp.databinding.ActivityLoginScreenBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginScreen extends AppCompatActivity {

    private ActivityLoginScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View vista = binding.getRoot();
        setContentView(vista);

        List<String> listaPermisos = new ArrayList<>();
        listaPermisos.add("usuario");
        listaPermisos.add("administrador");

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
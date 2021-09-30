package com.moviles2.universalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Register_Activity extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = findViewById(R.id.spinner);
        String[] opciones = {" ","Usuario","Vendedor"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_rol, opciones);
        spinner.setAdapter(adapter);
    }
}
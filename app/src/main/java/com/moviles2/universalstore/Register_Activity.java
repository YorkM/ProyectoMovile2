package com.moviles2.universalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Register_Activity extends AppCompatActivity {

    Button btnatras;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnatras = findViewById(R.id.btnatras);

        spinner = findViewById(R.id.spinner);
        String[] opciones = {" ","Usuario","Vendedor"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_rol, opciones);
        spinner.setAdapter(adapter);
    }

    public void atras(View view){
        Intent atras = new Intent(this, activity_login.class);
        startActivity(atras);
    }
}
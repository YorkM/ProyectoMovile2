package com.moviles2.universalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Register_Activity extends AppCompatActivity {

    EditText etnombre, etemail, etpais, etciudad, etpass, ettienda;
    Button btnatras, btnregister;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etnombre = findViewById(R.id.etnombre);
        etemail = findViewById(R.id.etemail);
        etpais = findViewById(R.id.etpais);
        etciudad = findViewById(R.id.etciudad);
        etpass = findViewById(R.id.etpassword);
        ettienda = findViewById(R.id.ettienda);
        btnatras = findViewById(R.id.btnatras);
        btnregister = findViewById(R.id.btnregis);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarRegistro(etemail);
            }
        });

        spinner = findViewById(R.id.spinner);
        String[] opciones = {" ","Usuario","Vendedor"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_rol, opciones);
        spinner.setAdapter(adapter);
    }

    public void atras(View view){
        Intent atras = new Intent(this, activity_login.class);
        startActivity(atras);
    }

    private boolean validarRegistro(EditText etemail){
        String inputNombre = etnombre.getText().toString();
        String inputEmail = etemail.getText().toString();
        String inpuPais = etpais.getText().toString();
        String inputCiudad = etciudad.getText().toString();
        String inputPass = etpass.getText().toString();
        String inputTienda = ettienda.getText().toString();

    if  (inputNombre.isEmpty() && inputEmail.isEmpty() && inpuPais.isEmpty() &&
        inputCiudad.isEmpty() && inputPass.isEmpty() && inputTienda.isEmpty()){

            etnombre.setError("Campo Requerido");
            etemail.setError("Campo Requerido");
            etpais.setError("Campo Requerido");
            etciudad.setError("Campo Requerido");
            etpass.setError("Campo Requerido");
            ettienda.setError("Campo Requerido");
            return false;
        }

        else if (inputEmail.isEmpty() && !inputPass.isEmpty() && !inpuPais.isEmpty() && !inputCiudad.isEmpty() && !inputNombre.isEmpty() && !inputTienda.isEmpty()){
            etemail.setError("Campo Requerido");
            return false;
        }
        else  if(!inputEmail.isEmpty() && inputPass.isEmpty() && !inputCiudad.isEmpty() && !inpuPais.isEmpty() && !inputNombre.isEmpty() && !inputTienda.isEmpty()){
            etpass.setError("Campo Requerido");
            return  false;
        }
        else  if(!inputEmail.isEmpty() && !inputPass.isEmpty() && inputCiudad.isEmpty() && !inpuPais.isEmpty() && !inputNombre.isEmpty() && !inputTienda.isEmpty()){
            etpass.setError("Campo Requerido");
            return  false;
        }
        else  if(!inputEmail.isEmpty() && !inputPass.isEmpty() && !inputCiudad.isEmpty() && inpuPais.isEmpty() && !inputNombre.isEmpty() && !inputTienda.isEmpty()){
            etpass.setError("Campo Requerido");
            return  false;
        }
        else  if(!inputEmail.isEmpty() && !inputPass.isEmpty() && !inputCiudad.isEmpty() && !inpuPais.isEmpty() && inputNombre.isEmpty() && !inputTienda.isEmpty()){
            etpass.setError("Campo Requerido");
            return  false;
        }
        else  if(!inputEmail.isEmpty() && !inputPass.isEmpty() && !inputCiudad.isEmpty() && !inpuPais.isEmpty() && !inputNombre.isEmpty() && inputTienda.isEmpty()){
            etpass.setError("Campo Requerido");
            return  false;
        }
        else  if (Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            return true;
        }
        else{
            etemail.setError("Correo no Válido");
            return false;

        }

    }

}
package com.moviles2.universalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_login extends AppCompatActivity {

   EditText etemail, etpassword;
   Button btnlogin, btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarEmail(etemail);
                limpiarCampos();
            }
        });

    }

    private boolean validarEmail(EditText etemail){
        String inputemail = etemail.getText().toString();
        String inputpass = etpassword.getText().toString();

        if (inputemail.isEmpty() && inputpass.isEmpty()){
            etemail.setError("Campo Requerido");
            etpassword.setError("Campo Requerido");
            return false;
        }
        else  if (Patterns.EMAIL_ADDRESS.matcher(inputemail).matches()){
            return true;
        }
        else{
            etemail.setError("correo no valido");
            return false;

        }

    }

    public void irRegister(View view){
        Intent register = new Intent(this, Register_Activity.class);
        startActivity(register);
    }

    public void limpiarCampos(){
        etemail.setText("");
        etpassword.setText("");
        etemail.requestFocus();
    }

    public void irTienda(){
        Intent tienda = new Intent(this, Tienda_Activity.class);
        startActivity(tienda);
    }


}
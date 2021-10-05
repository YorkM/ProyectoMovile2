package com.moviles2.universalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

       /* btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUser(etemail, etpassword);
                //limpiarCampos();
            }
        });*/

    }

    /*private boolean validarUser(EditText etemail, EditText etpassword){
        String inputemail = etemail.getText().toString();
        String inputpass = etpassword.getText().toString();
        Boolean respuesta;

        Pattern c = Pattern.compile("^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher M = c.matcher(inputpass);
        if (respuesta=!M.find()){
            etpassword.setError(respuesta.toString());
        }

        if (inputemail.isEmpty() && inputpass.isEmpty()){

            etemail.setError("Campo Requerido");
            etpassword.setError("Campo Requerido");
            return  false;
        }
        else if (inputemail.isEmpty() && !inputpass.isEmpty()){
            etemail.setError("Campo Requerido");
             return false;
        }
        else  if(!inputemail.isEmpty() && inputpass.isEmpty()){
            etpassword.setError("Campo Requerido");
            return  false;
        }
        else  if (Patterns.EMAIL_ADDRESS.matcher(inputemail).matches()){
            return true;
        }
        else{
            etemail.setError("correo no valido");
            return false;

        }
    }*/

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

    public void  validators (View view){
        Pattern p = Pattern.compile("[a-z0-9!#$%&'+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'+/=?^_`{|}~-]+)@(?:[a-z0-9](?:[a-z0-9-][a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        Pattern c = Pattern.compile("^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$");
        String inputemail = etemail.getText().toString();
        String inputpass = etpassword.getText().toString();
        Matcher m = p.matcher(inputemail);
        Matcher M = c.matcher(inputpass);

        if (inputemail.isEmpty() && inputpass.isEmpty()){
            etemail.setError("Campo Requerido");
            etpassword.setError("Campo Requerido");
        }
        else if( inputemail.isEmpty()){
            etemail.setError("Campo Requerido");

        }else if(!m.find()){

            etemail.setError("Email no Válido");
        }

        else if(inputpass.isEmpty()){

            etpassword.setError("Campo Requerido");

        }
        else if (!M.find()){

            etpassword.setError("Password no Válido");

        }

        else {

            Intent register = new Intent(this, Register_Activity.class);

            startActivity(register);

        }

    }


}
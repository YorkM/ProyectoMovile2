package com.moviles2.universalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.moviles2.universalstore.Entities.SharedEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class activity_login extends AppCompatActivity {


   EditText etemail, etpassword;
   Button btnlogin, btnregister;

    SharedEntity users;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

    public void  login (View view){
        Pattern p = Pattern.compile("[a-z0-9!#$%&'+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'+/=?^_`{|}~-]+)@(?:[a-z0-9](?:[a-z0-9-][a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
       // Pattern c = Pattern.compile("^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$");
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        String inputemail = etemail.getText().toString();
        String inputpass = etpassword.getText().toString();
        Matcher m = p.matcher(inputemail);
       // Matcher M = c.matcher(inputpass);

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
        else if (!inputpass.matches(pattern)){

            etpassword.setError("Password no Válido");

        }

        else {

            mAuth.signInWithEmailAndPassword(inputemail, inputpass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                db.collection("bduniversalstore").whereEqualTo("email", inputemail)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                                        users = documentSnapshot.toObject(SharedEntity.class);
                                                        break;
                                                    }
                                                    Toast.makeText(getApplicationContext(), "Login Exitoso", Toast.LENGTH_LONG).show();
                                                    saveUserPreferences(getApplicationContext());
                                                    Intent inte = new Intent(getApplicationContext(), Tienda_Activity.class);
                                                    startActivity(inte);
                                                    limpiar();
                                                }
                                            }
                                        });


                            } else {
                                try {
                                    throw task.getException();
                                } catch(FirebaseAuthWeakPasswordException e) {
                                    Toast.makeText(getApplicationContext(), "Contraseña debil use mas de 6 caracteres ", Toast.LENGTH_LONG).show();
                                } catch(FirebaseAuthInvalidCredentialsException e) {
                                    Toast.makeText(getApplicationContext(), "Usuario y/o Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                                } catch(FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(getApplicationContext(), "El correo ya se encuentra en uso con otra cuenta", Toast.LENGTH_LONG).show();
                                } catch(Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error de Auth: "+ e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });

        }

    }

    public void saveUserPreferences(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        // permite escribir data en las shared preferences
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("status", true);
        editor.putString("email", users.getEmail());
        editor.putString("rol", users.getRol());
        editor.putString("name", users.getName());
        editor.commit();
    }


    public void limpiar(){
        etemail.setText("");
        etpassword.setText("");
        etemail.requestFocus();
    }


}
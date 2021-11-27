package com.moviles2.universalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register_Activity extends AppCompatActivity {

    EditText etnombre, etemail, etpais, etciudad, etpass, ettienda;
    Button btnatras, btnregister;

    Spinner spinner;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        spinner = findViewById(R.id.spinner);
        String[] opciones = {" ", "Usuario", "Vendedor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_rol, opciones);
        spinner.setAdapter(adapter);
    }

    public void atras(View view) {
        Intent atras = new Intent(this, activity_login.class);
        startActivity(atras);
    }

    public void register(View view){
        String inputNombre = etnombre.getText().toString();
        String inputEmail = etemail.getText().toString();
        String inpuPais = etpais.getText().toString();
        String inputCiudad = etciudad.getText().toString();
        String inputPass = etpass.getText().toString();
        String inputTienda = ettienda.getText().toString();
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

    if (inputNombre.isEmpty() && inputEmail.isEmpty() && inpuPais.isEmpty() &&
                inputCiudad.isEmpty() && inputPass.isEmpty() && inputTienda.isEmpty()) {

            etnombre.setError("Campo Requerido");
            etemail.setError("Campo Requerido");
            etpais.setError("Campo Requerido");
            etciudad.setError("Campo Requerido");
            etpass.setError("Campo Requerido");
            ettienda.setError("Campo Requerido");

        } else if (!inputPass.matches(pattern)) {

            etpass.setError("Password no Válido");

    } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {

            etemail.setError("Correo no Válido");
    } else {

            mAuth.createUserWithEmailAndPassword(inputEmail, inputPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_LONG).show();
                                saveUserFirestore();
                                Intent inte = new Intent(getApplicationContext(), activity_login.class);
                                startActivity(inte);
                                limpiarDatos();

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

    public  void saveUserFirestore() {
        Map<String, Object> user = new HashMap<>();
        String rol = spinner.getSelectedItem().toString();
        String inputNombre = etnombre.getText().toString();
        String inputEmail = etemail.getText().toString();
        String inpuPais = etpais.getText().toString();
        String inputCiudad = etciudad.getText().toString();
        String inputPass = etpass.getText().toString();
        String inputTienda = ettienda.getText().toString();
        user.put("nombre", inputNombre);
        user.put("email", inputEmail );
        user.put("pais", inpuPais);
        user.put("ciudad", inputCiudad);
        user.put("rol", rol);
        user.put("tienda", inputTienda);
        db.collection("bduniversalstore")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Registro Completo", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Registro Fallido", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void  limpiarDatos(){
        etnombre.setText("");
        etemail.setText("");
        etpais.setText("");
        etciudad.setText("");
        etpass.setText("");
        ettienda.setText("");
    }
}


package com.moviles2.universalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.moviles2.universalstore.databinding.ActivityAddProductBinding;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

    private ActivityAddProductBinding addProductBinding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addProductBinding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = addProductBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
    }

    public void addProductFirestore(View view){
        Map<String, Object> addProduct = new HashMap<>();
        String name = addProductBinding.etName.getText().toString();
        String description = addProductBinding.etDescription.getText().toString();
        String stock = addProductBinding.etStock.getText().toString();
        String price = addProductBinding.etPrice.getText().toString();
        String category = addProductBinding.etCategory.getText().toString();
        int stock1 = Integer.parseInt(stock);
        double price1 = Double.parseDouble(price);
        addProduct.put("name", name);
        addProduct.put("description", description);
        addProduct.put("stock", stock1);
        addProduct.put("price", price1);
        addProduct.put("category", category);
        db.collection("products")
                .add(addProduct)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Registro Producto Correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Tienda_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Producto no Registrado", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
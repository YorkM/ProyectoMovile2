package com.moviles2.universalstore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moviles2.universalstore.databinding.ActivityAddProductBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

    private ActivityAddProductBinding addProductBinding;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private Uri imageUri, downloadUrl;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addProductBinding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = addProductBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
    }

    public void pickImage(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> galleryActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData(); // obtiene la data cuando selecciona la imagen
                        Uri uri = data.getData(); // obtiene la ruta de la imagen
                        if(uri != null){
                            imageUri = uri;
                            addProductBinding.ivProduct.setImageURI(uri);
                        }
                    }
                    else{
                        Toast.makeText(
                                getApplicationContext(),
                                "Imagen no Seleccionada",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
    );

    public void uploadImage(View view){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Agregando producto");
        progressDialog.show();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss",
                Locale.US
        );
        Date dateNow = new Date();
        String filenameImage = dateFormatter.format(dateNow);
        storageReference = FirebaseStorage.getInstance().getReference(
                "products/"+filenameImage);
        UploadTask uploadTask = storageReference.putFile(imageUri);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    downloadUrl = task.getResult(); // obtiene la url de descarga
                    addProductFirestore();
                }
            }
        });
    }


    public void addProductFirestore(){
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
        addProduct.put("imageUrl",downloadUrl.toString());
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
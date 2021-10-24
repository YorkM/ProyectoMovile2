package com.moviles2.universalstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.moviles2.universalstore.Adapters.ProductAdapter;
import com.moviles2.universalstore.Entities.Product;
import com.moviles2.universalstore.databinding.ActivityTiendaBinding;

import java.util.ArrayList;

public class Tienda_Activity extends AppCompatActivity {

    private ActivityTiendaBinding tiendaBinding;
    private FirebaseFirestore db;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tiendaBinding = ActivityTiendaBinding.inflate(getLayoutInflater());
        View view = tiendaBinding.getRoot();
        setContentView(view);
        db = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productArrayList);
        tiendaBinding.rvProducts.setHasFixedSize(true);
        tiendaBinding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        tiendaBinding.rvProducts.setAdapter(productAdapter);
        getProducts();
    }

    public void getProducts(){
        db.collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(getApplicationContext(), "Fallo al recuperar los datos", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                productArrayList.add(dc.getDocument().toObject(Product.class));
                            }
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                });
    }
}
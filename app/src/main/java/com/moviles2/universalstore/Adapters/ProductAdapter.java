package com.moviles2.universalstore.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.moviles2.universalstore.AddProduct;
import com.moviles2.universalstore.EditProductActivity;
import com.moviles2.universalstore.Entities.Product;
import com.moviles2.universalstore.R;
import com.moviles2.universalstore.Splash_Screen;
import com.moviles2.universalstore.activity_login;
import com.moviles2.universalstore.databinding.ProductItemBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private ProductItemBinding productItemBinding;
    private ArrayList<Product> productArrayList;
    private FirebaseFirestore db;
    String roles=null;
    public ProductAdapter(Context context, ArrayList<Product> productArrayList, FirebaseFirestore db) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.db = db;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productItemBinding = ProductItemBinding.inflate(LayoutInflater.from(context));
        return new ProductViewHolder(productItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {

        Product product = productArrayList.get(position);
        holder.itemBinding.tvName.setText(product.getName());
        holder.itemBinding.tvDescription.setText(product.getDescription());
        holder.itemBinding.tvStock.setText(String.valueOf(product.getStock()));
        holder.itemBinding.tvPrecio.setText(String.valueOf(product.getPrice()));
        holder.itemBinding.tvCategoria.setText(product.getCategory());
        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_carrito_de_compras)
                .error(R.drawable.ic_carrito_de_compras)
                //.circleCrop()
                .into(holder.itemBinding.ivProductImg);

        Splash_Screen r = new Splash_Screen();

        if(r.rol(context).equals("Vendedor")){
            holder.itemBinding.btnEliminar.setVisibility(View.INVISIBLE);
            holder.itemBinding.btnComprar.setVisibility(View.INVISIBLE);

        }
        else
            if (r.rol(context).equals("Usuario")){
                holder.itemBinding.btnEliminar.setVisibility(View.INVISIBLE);
                holder.itemBinding.btnAgregar.setVisibility(View.INVISIBLE);
                holder.itemBinding.btnEditar.setVisibility(View.INVISIBLE);
                holder.itemBinding.btnComprar.setVisibility(View.VISIBLE);
            }

        holder.itemBinding.btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(
                        context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();

                Intent i = new Intent(context, activity_login.class);
                context.startActivity(i);
            }
        });


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.collection("products").document(product.getId())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, r.rol(context),Toast.LENGTH_LONG).show();
                        productArrayList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Fallo al eliminar el producto", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        holder.itemBinding.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.setMessage("¿Està seguro que quiere eliminar el producto?");
                alert.create().show();
            }
        });
        holder.itemBinding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });

        holder.itemBinding.btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddProduct.class);
                //intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ProductItemBinding itemBinding;

        public ProductViewHolder(@NonNull ProductItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}

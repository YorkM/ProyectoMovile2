package com.moviles2.universalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moviles2.universalstore.databinding.ActivityGetstartBinding;

public class GetStart extends AppCompatActivity {
    private ActivityGetstartBinding binding;
    Button btncomenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetstartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        btncomenzar = findViewById(R.id.btncomenzar);
    }

    public void irlogin(View view){
        Intent comenzar = new Intent(this, activity_login.class);
        startActivity(comenzar);
    }
}
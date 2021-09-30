package com.moviles2.universalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btncomenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.SplashTheme);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btncomenzar = findViewById(R.id.btncomenzar);
    }

    public void irlogin(View view){
        Intent comenzar = new Intent(this, activity_login.class);
        startActivity(comenzar);
    }
}
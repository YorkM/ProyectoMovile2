package com.moviles2.universalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_Screen extends AppCompatActivity {

    Animation zoom;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readUserPreferences(getApplicationContext());
            }
        }, 3000);

        zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img = findViewById(R.id.imageSp);
        img.startAnimation(zoom);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        readUserPreferences(getApplicationContext());
                    }
                }, 3000);

                finish();
            }
        }, 3000);
    }

    public void readUserPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        boolean status = sharedPref.getBoolean("status",false);



        if(status){
            Intent intent = new Intent(getApplicationContext(), Tienda_Activity.class);
            startActivity(intent);
        }

        else{
            Intent intent = new Intent(getApplicationContext(), GetStart.class);
            startActivity(intent);
        }
    }

    public String rol (Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.user_preference_key), Context.MODE_PRIVATE);
        String rol = sharedPref.getString("rol","admin");
        return rol;
    }
}
package com.example.itraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Cover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
               startActivity(new Intent(Cover.this, MainActivity.class));
            }
        }, 2000);

    }
}





package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class Intro extends AppCompatActivity implements View.OnClickListener{

    ViewFlipper viewFlipper;
    Button Continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewFlipper = findViewById(R.id.viewFlipper);
        Continue = findViewById(R.id.Continue);
        Continue.setOnClickListener(this);

        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
    }
    @Override
    public void onClick(View v){

        if(v == Continue){
            Intent intent = new Intent(this, LogInSection.class);
            startActivity(intent);
            this.finish();
        }

    }
}
package com.example.foodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener{
    


    DBHelper DB;
    Button btnprofile,btnhome, btnres, btncart;
    int currentFragment = 2;
    Button lastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        

        DB = new DBHelper(this);
        String ID = LogInSection.ID;
        btnprofile = findViewById(R.id.btnprofile);
        btnprofile.setOnClickListener(this);
        btnhome = findViewById(R.id.btnhome);
        btnhome.setOnClickListener(this);
        btnres = findViewById(R.id.btnres);
        btnres.setOnClickListener(this);
        btncart = findViewById(R.id.btncart);
        btncart.setOnClickListener(this);

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
        currentFragment = 2;
        btnhome.setBackgroundColor(Color.parseColor("#fccf51"));
        lastButton = btnhome;
    }

    @Override
    public void onClick(View view) {
        if (view == btnprofile){
            if(currentFragment != 1){
                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.commit();
                currentFragment = 1;
                btnprofile.setBackgroundColor(Color.parseColor("#fccf51"));
                lastButton.setBackgroundColor(Color.parseColor("#fce9b3"));
                lastButton = btnprofile;

            }
            CheckoutFragment.chID.clear();
        }

        if (view == btnhome){
            if(currentFragment != 2){
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                if (currentFragment == 1){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_right_to_left, R.anim.exit_right_to_left);
                }else{
                    fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
                }
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.commit();
                currentFragment = 2;
                btnhome.setBackgroundColor(Color.parseColor("#fccf51"));
                lastButton.setBackgroundColor(Color.parseColor("#fce9b3"));
                lastButton = btnhome;

            }
            CheckoutFragment.chID.clear();
        }

        if (view == btnres){
            if(currentFragment != 3){
                Fragment fragment = new ResFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                if (currentFragment == 4){
                    fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
                }else{
                    fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_right_to_left, R.anim.exit_right_to_left);
                }
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.commit();
                currentFragment = 3;
                btnres.setBackgroundColor(Color.parseColor("#fccf51"));
                lastButton.setBackgroundColor(Color.parseColor("#fce9b3"));
                lastButton = btnres;
            }
            CheckoutFragment.chID.clear();
        }

        if (view == btncart){
            if(currentFragment != 4){
                Fragment fragment = new CartFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_right_to_left, R.anim.exit_right_to_left);
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.commit();
                currentFragment = 4;
                btncart.setBackgroundColor(Color.parseColor("#fccf51"));
                lastButton.setBackgroundColor(Color.parseColor("#fce9b3"));
                lastButton = btncart;
            }
        }
    }

    @Override
    public void onBackPressed(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finishAffinity();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit??").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }




}
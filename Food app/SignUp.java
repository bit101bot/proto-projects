package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    Button LogIn, SignUp, Back;
    DBHelper DB;
    EditText email, password, password2, firstName, lastName, mobileNumber, address, landmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        LogIn = findViewById(R.id.btnLogIn2);
        LogIn.setOnClickListener(this);
        Back = findViewById(R.id.btnBack2);
        Back.setOnClickListener(this);
        SignUp = findViewById(R.id.btnSignUp2);
        SignUp.setOnClickListener(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        mobileNumber = findViewById(R.id.mobileNumber);
        address = findViewById(R.id.address);
        landmark = findViewById(R.id.landmark);

        DB = new DBHelper(this);
    }

    @Override

    public void onClick(View view) {
        if(view == LogIn || view == Back){
            Intent intent = new Intent(this, LogInSection.class);
            startActivity(intent);
            this.finish();
        }
        if(view == SignUp){
            String Email = email.getText().toString();
            String Pass = password.getText().toString();
            String Pass2 = password2.getText().toString();
            String firstnametxt = firstName.getText().toString();
            String lastnametxt = lastName.getText().toString();
            String mobilenumbertxt = mobileNumber.getText().toString();
            String addresstxt = address.getText().toString();
            String landmarktxt = landmark.getText().toString();

            if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass) || TextUtils.isEmpty(Pass2) || TextUtils.isEmpty(firstnametxt) || TextUtils.isEmpty(lastnametxt) || TextUtils.isEmpty(mobilenumbertxt) || TextUtils.isEmpty(addresstxt) || TextUtils.isEmpty(landmarktxt)){
                Toast.makeText(SignUp.this, "All Fields Required!", Toast.LENGTH_SHORT).show();
            }else {
                if (Pass.equals(Pass2)){
                    Boolean checkEmail = DB.checkEmail(Email);
                    if (checkEmail == false){
                        Boolean insert = DB.insertData(Email, Pass, firstnametxt, lastnametxt, mobilenumbertxt, addresstxt, landmarktxt);
                        if (insert == true){
                            Toast.makeText(SignUp.this, "Sign Up Complete", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, LogInSection.class);
                            startActivity(intent);
                            this.finish();
                        }else{
                            Toast.makeText(SignUp.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignUp.this, "Email Already Exist", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignUp.this, "Password do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, LogInSection.class);
        startActivity(intent);
        this.finish();
    }
}
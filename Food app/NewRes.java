package com.example.foodapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class NewRes extends Fragment implements View.OnClickListener {

    View view;
    DBHelper DB;
    DBHelper2 DB2;
    EditText resDate, resTime, resGuestNum, resTableNum, resSpecial;
    Button btnResConfirm, btnResCancel;
    TextView resEmail, resPhone;
    String ID;
    String email, phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_new_res, container, false);
        DB2 = new DBHelper2(getContext());
        DB = new DBHelper(getContext());
        resDate = view.findViewById(R.id.resDate);
        resTime = view.findViewById(R.id.resTime);
        resGuestNum = view.findViewById(R.id.resGuestNum);
        resTableNum = view.findViewById(R.id.resTableNum);
        resSpecial = view.findViewById(R.id.resSpecial);
        btnResConfirm = view.findViewById(R.id.btnResConfirm);
        btnResConfirm.setOnClickListener(this);
        btnResCancel = view.findViewById(R.id.btnResCancel);
        btnResCancel.setOnClickListener(this);
        resEmail = view.findViewById(R.id.txtResEmail);
        resPhone = view.findViewById(R.id.txtResPhone);
        ID = LogInSection.ID;

        Cursor cursor = DB.getUserData(ID);
        cursor.moveToFirst();
        resEmail.setText(cursor.getString(0));
        resPhone.setText(cursor.getString(4));
        email = cursor.getString(0);
        phone = cursor.getString(4);

        if (!cursor.isClosed()){
            cursor.close();
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btnResConfirm){
            String txtDate = resDate.getText().toString();
            String txtTime = resTime.getText().toString();
            String txtGuest = resGuestNum.getText().toString();
            String txtTable = resTableNum.getText().toString();
            String txtSpecial = resSpecial.getText().toString();
            if (TextUtils.isEmpty(txtDate) || TextUtils.isEmpty(txtTime) || TextUtils.isEmpty(txtGuest) || TextUtils.isEmpty(txtTable) || TextUtils.isEmpty(txtSpecial)){
                Toast.makeText(getContext(), "All Fields Required!", Toast.LENGTH_SHORT).show();
            }else{
                Random rand = new Random();
                int rn = rand.nextInt(999999999);
                String id = Integer.toString(rn);
                Boolean bol = DB2.insertReservation(id,email,phone,txtDate,txtTime,txtGuest,txtTable,txtSpecial);
                if (bol == true){
                    Toast.makeText(getContext(), "Reserved Successfully", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new ResFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                    fragmentTransaction.replace(R.id.frameLayout,fragment);
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();

                }
            }




        }
        if (view == btnResCancel){
            Fragment fragment = new ResFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }
    }
}
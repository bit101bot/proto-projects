package com.example.foodapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    View view;
    DBHelper DB;
    TextView userText, userEmail;
    EditText userMobile, userAddress, userLandmark, userPassword;
    Button btnEdit, btnUpdate, btnCancel, btnShowpass, btnSignout, btnOrders;
    String ID;
    String mobiletxt, addresstxt, landmarktxt, passwordtxt;
    Boolean showpass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_profile, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        DB = new DBHelper(getContext());
        userText = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userMobile = view.findViewById(R.id.userMobile);
        userAddress = view.findViewById(R.id.userAddress);
        userLandmark = view.findViewById(R.id.userLandmark);
        userPassword = view.findViewById(R.id.userPassword);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnUpdate.setVisibility(view.INVISIBLE);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnCancel.setVisibility(view.INVISIBLE);
        btnShowpass = view.findViewById(R.id.btnShowpass);
        btnShowpass.setOnClickListener(this);
        showpass = false;
        btnSignout = view.findViewById(R.id.btnSignout);
        btnSignout.setOnClickListener(this);
        btnOrders = view.findViewById(R.id.btnOrders);
        btnOrders.setOnClickListener(this);

        ID = LogInSection.ID;

        Cursor cursor = DB.getUserData(ID);
        cursor.moveToFirst();
        userText.setText(cursor.getString(2).toString() + " "+ cursor.getString(3).toString());
        userEmail.setText(cursor.getString(0).toString());

        userMobile.setText(cursor.getString(4).toString());
        enableEdit(userMobile,false);
        userAddress.setText(cursor.getString(5).toString());
        enableEdit(userAddress, false);
        userLandmark.setText(cursor.getString(6).toString());
        enableEdit(userLandmark,false);
        userPassword.setText(cursor.getString(1).toString());
        enableEdit(userPassword,false);
        if(!cursor.isClosed()){
            cursor.close();
        }

        mobiletxt = userMobile.getText().toString();
        addresstxt = userAddress.getText().toString();
        landmarktxt = userLandmark.getText().toString();
        passwordtxt = userPassword.getText().toString();

    }

    @Override
    public void onClick(View view) {
        if (view == btnEdit){
            enableEdit(userMobile,true);
            enableEdit(userAddress,true);
            enableEdit(userLandmark,true);
            enableEdit(userPassword,true);

            userMobile.requestFocus();
            userMobile.setSelection(userMobile.getText().length());
            showKeyboard(userMobile);
            btnUpdate.setVisibility(view.VISIBLE);
            btnCancel.setVisibility(view.VISIBLE);
            btnEdit.setVisibility(view.INVISIBLE);

        }
        if (view == btnUpdate){
            String email = userEmail.getText().toString();
            String mobile = userMobile.getText().toString();
            String address = userAddress.getText().toString();
            String landmark = userLandmark.getText().toString();
            String password = userPassword.getText().toString();

            if(TextUtils.isEmpty(mobile) || TextUtils.isEmpty(address) || TextUtils.isEmpty(landmark) || TextUtils.isEmpty(password)){
                Toast.makeText(getContext(), "All Fields cannot be left Empty", Toast.LENGTH_SHORT).show();
            }else{
                Boolean update = DB.updateUserData(email,password,mobile,address,landmark);

                if (update == true){
                    Toast.makeText(getContext(), "User Data Updated", Toast.LENGTH_SHORT).show();
                    btnUpdate.setVisibility(view.INVISIBLE);
                    btnCancel.setVisibility(view.INVISIBLE);
                    btnEdit.setVisibility(view.VISIBLE);

                    enableEdit(userMobile,false);
                    enableEdit(userAddress,false);
                    enableEdit(userLandmark,false);
                    enableEdit(userPassword,false);

                    hideKeyboard(userMobile);
                    hideKeyboard(userAddress);
                    hideKeyboard(userLandmark);
                    hideKeyboard(userPassword);

                    mobiletxt = userMobile.getText().toString();
                    addresstxt = userAddress.getText().toString();
                    landmarktxt = userLandmark.getText().toString();
                    passwordtxt = userPassword.getText().toString();
                }else{
                    Toast.makeText(getContext(), "Failed to Update User Data", Toast.LENGTH_SHORT).show();
                    btnUpdate.setVisibility(view.INVISIBLE);
                    btnCancel.setVisibility(view.INVISIBLE);
                    btnEdit.setVisibility(view.VISIBLE);

                    enableEdit(userMobile,false);
                    enableEdit(userAddress,false);
                    enableEdit(userLandmark,false);
                    enableEdit(userPassword, false);

                    hideKeyboard(userMobile);
                    hideKeyboard(userAddress);
                    hideKeyboard(userLandmark);
                    hideKeyboard(userPassword);
                }
            }

        }

        if (view == btnCancel){
            userMobile.setText(mobiletxt);
            userAddress.setText(addresstxt);
            userLandmark.setText(landmarktxt);
            userPassword.setText(passwordtxt);

            btnUpdate.setVisibility(view.INVISIBLE);
            btnCancel.setVisibility(view.INVISIBLE);
            btnEdit.setVisibility(view.VISIBLE);

            enableEdit(userMobile,false);
            enableEdit(userAddress,false);
            enableEdit(userLandmark,false);
            enableEdit(userPassword,false);

            hideKeyboard(userMobile);
            hideKeyboard(userAddress);
            hideKeyboard(userLandmark);
            hideKeyboard(userPassword);

        }
        if (view == btnShowpass){
            if (showpass){
                userPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                userPassword.setTypeface(null, Typeface.BOLD);
                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_visibility_24);
                DrawableCompat.setTint(img, Color.rgb(102, 85, 60));
                btnShowpass.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                showpass = false;
            }else {
                userPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
                userPassword.setTypeface(null, Typeface.BOLD);
                Drawable img = getContext().getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24);
                DrawableCompat.setTint(img, Color.rgb(102, 85, 60));
                btnShowpass.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                showpass = true;
            }
            userPassword.setSelection(userPassword.getText().length());

        }

        if(view == btnSignout){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent intent = new Intent(getActivity(), LogInSection.class);
                            startActivity(intent);
                            getActivity().finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you want to Sign out??").setPositiveButton("Sign Out", dialogClickListener)
                    .setNegativeButton("Cancel", dialogClickListener).show();
        }

        if (view == btnOrders){
            Fragment fragment = new RecordFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }
    }

    public void enableEdit(EditText editText, Boolean isEnable){
        editText.setFocusable(isEnable);
        editText.setClickable(isEnable);
        editText.setFocusableInTouchMode(isEnable);
        editText.setLongClickable(isEnable);
        editText.setCursorVisible(isEnable);
    }

    public void showKeyboard(EditText editText){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public void hideKeyboard(EditText editText){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
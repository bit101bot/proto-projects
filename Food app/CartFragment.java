package com.example.foodapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class CartFragment extends Fragment implements View.OnClickListener{

    String []data = {"hello, hi, welcome"};
    int counter = 0;
    View view;
    DBHelper DB;
    String ID;
    TextView txtEmpty;
    Button btnCheckout;

    RecyclerView recyclerView;
    CartAdapter adapter;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cart, container, false);
        btnCheckout = view.findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(this);
        txtEmpty = view.findViewById(R.id.txtEmpty);
        ID = LogInSection.ID;
        DB = new DBHelper(getContext());
        List <String> items = new LinkedList<>();
        List <String> itemprize = new LinkedList<>();
        List <String> itemquantity = new LinkedList<>();
        List <String> itemID = new LinkedList<>();



        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(items,itemprize,itemquantity,itemID);
        recyclerView.setAdapter(adapter);

        Cursor cursor = DB.getUserItem(ID);
        if (cursor.getCount()>0){
            txtEmpty.setVisibility(txtEmpty.GONE);
        }
        int count = 1;
        cursor.moveToFirst();


        while (count <= cursor.getCount()){
            itemID.add(cursor.getString(0).toString());
            items.add(cursor.getString(2).toString());
            itemprize.add("₱"+ cursor.getString(3).toString());
            itemquantity.add(cursor.getString(4).toString() + "x");
            adapter.notifyItemInserted(items.size()-1);

            if(count < cursor.getCount()){
                cursor.moveToNext();
            }
            count++;
        }

        if (!cursor.isClosed()){
            cursor.close();
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btnCheckout){
            if (!CheckoutFragment.chID.isEmpty()){
                Fragment fragment = new CheckoutFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_right_to_left, R.anim.exit_right_to_left);
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.commit();
            }else{
                Toast.makeText(getContext(), "No Items Selected", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
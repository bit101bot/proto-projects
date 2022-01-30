package com.example.foodapp;

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


public class ResFragment extends Fragment implements View.OnClickListener {

    View view;
    DBHelper DB;
    DBHelper2 DB2;
    String ID;
    Button btnReservations;
    TextView txtEmptyRes;

    RecyclerView recyclerView;
    ResAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_res, container, false);
        ID = LogInSection.ID;
        DB = new DBHelper(getContext());
        DB2 = new DBHelper2(getContext());
        btnReservations = view.findViewById(R.id.btnReservations);
        btnReservations.setOnClickListener(this);

        txtEmptyRes = view.findViewById(R.id.txtEmptyRes);
        List<String> email = new LinkedList<>();
        List <String> mobile = new LinkedList<>();
        List <String> date = new LinkedList<>();
        List <String> time = new LinkedList<>();
        List <String> guest = new LinkedList<>();
        List <String> tableres = new LinkedList<>();
        List <String> request = new LinkedList<>();

        recyclerView = view.findViewById(R.id.recyclerViewRes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ResAdapter(email,mobile,date,time, guest, tableres, request);
        recyclerView.setAdapter(adapter);


        Cursor cursor = DB2.getReservations(ID);
        if (cursor.getCount()>0) {
            txtEmptyRes.setVisibility(txtEmptyRes.GONE);
        }
        int count = 1;
        cursor.moveToFirst();


        while (count <= cursor.getCount()){
            email.add(cursor.getString(1).toString());
            mobile.add(cursor.getString(2).toString());
            date.add("Date: " + cursor.getString(3).toString());
            time.add("Time: "+cursor.getString(4).toString());
            guest.add("Guest: "+cursor.getString(5).toString());
            tableres.add("Tables: "+cursor.getString(6).toString());
            request.add(cursor.getString(7).toString());
            adapter.notifyItemInserted(email.size()-1);

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
        if (view == btnReservations){
            Fragment fragment = new NewRes();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_right_to_left, R.anim.exit_right_to_left);
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }
    }
}
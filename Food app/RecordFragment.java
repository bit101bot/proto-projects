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

import java.util.LinkedList;
import java.util.List;

public class RecordFragment extends Fragment implements View.OnClickListener {
    View view;

    RecyclerView recyclerView;
    RecordAdapter adapter;
    DBHelper DB;
    String ID;
    Button btnRecordsBack;
    TextView recordEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_record, container, false);

        List<String> items = new LinkedList<>();
        List <String> itemprize = new LinkedList<>();
        List <String> itemdate = new LinkedList<>();
        List <String> itemID = new LinkedList<>();

        DB = new DBHelper(getContext());
        ID = LogInSection.ID;
        btnRecordsBack = view.findViewById(R.id.btnRecordsBack);
        btnRecordsBack.setOnClickListener(this);
        recordEmpty = view.findViewById(R.id.recordEmpty);


        recyclerView = view.findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecordAdapter(items,itemprize,itemdate,itemID);
        recyclerView.setAdapter(adapter);

        Cursor cursor = DB.getUserRecord(ID);
        if (cursor.getCount()>0){
            recordEmpty.setVisibility(recordEmpty.GONE);
        }
        int count = 1;
        cursor.moveToFirst();
        while (count <= cursor.getCount()){
            itemID.add(cursor.getString(0).toString());
            items.add(cursor.getString(2).toString());
            itemprize.add("₱"+ cursor.getString(3).toString());
            itemdate.add(cursor.getString(4).toString() + "x");
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
        if (view == btnRecordsBack){
            Fragment fragment = new ProfileFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,R.anim.enter_right_to_left,R.anim.exit_right_to_left);
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }
    }
}
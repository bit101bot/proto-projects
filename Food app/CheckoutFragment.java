package com.example.foodapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class CheckoutFragment extends Fragment implements View.OnClickListener {

    View view;
    DBHelper DB;
    RecyclerView recyclerView;
    CheckoutAdapter adapter;
    String ID;
    Button btnchBack, btnPlaceOrder;
    TextView chUserName, chUserAddress, chUserLandmark, chDate, chTime, chTotalTime, chTotalPrize;
    EditText chInstruction, chCookingInstruction;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView txtPlaced, txtContent, txtNote;
    private Button btnOkay;

    List<String>recorditems = new LinkedList<>();
    List<String>recordquantity = new LinkedList<>();
    int recordprize;
    String finalrecord = "";


    public static List<String> chID = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_checkout, container, false);


        btnchBack = view.findViewById(R.id.btnchBack);
        btnchBack.setOnClickListener(this);
        btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(this);
        chUserName = view.findViewById(R.id.chUserName);
        chUserAddress = view.findViewById(R.id.chUserAddress);
        chUserLandmark = view.findViewById(R.id.chUserLandmark);
        chDate = view.findViewById(R.id.chDate);
        chTime = view.findViewById(R.id.chTime);
        chTotalTime = view.findViewById(R.id.chTotalTime);
        chInstruction = view.findViewById(R.id.chInstruction);
        chCookingInstruction = view.findViewById(R.id.chCookingInstruction);
        chTotalPrize = view.findViewById(R.id.chTotalPrize);

        DB = new DBHelper(getContext());
        ID = LogInSection.ID;

        List<String> items = new LinkedList<>();
        List <String> itemprize = new LinkedList<>();
        List <String> itemquantity = new LinkedList<>();
        List <String> itemID = new LinkedList<>();



        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CheckoutAdapter(items,itemprize,itemquantity,itemID);
        recyclerView.setAdapter(adapter);

        int TotalPrize = 0;
        int count = 1;
        while (count <= chID.size()){
            Cursor cursor = DB.getUserItemCH(ID, chID.get(count-1));
            cursor.moveToFirst();
            itemID.add(cursor.getString(0).toString());
            items.add(cursor.getString(2).toString());
            itemprize.add("₱"+ cursor.getString(3).toString());
            itemquantity.add(cursor.getString(4).toString() + "x");
            adapter.notifyItemInserted(items.size()-1);
            TotalPrize += Integer.parseInt(cursor.getString(3).toString());

            recorditems.add(cursor.getString(2).toString() +" - "+cursor.getString(4).toString());

            if (!cursor.isClosed()){
                cursor.close();
            }
            if(count < cursor.getCount()){
                cursor.moveToNext();
            }
            count++;
        }


        recordprize = TotalPrize;



        Cursor cursor = DB.getUserData(ID);
        cursor.moveToFirst();

        chUserName.setText(cursor.getString(2).toString() + " "+ cursor.getString(3).toString());
        chUserAddress.setText(cursor.getString(5).toString());
        chUserLandmark.setText(cursor.getString(6).toString());
        chDate.setText(java.time.LocalDate.now().toString());
        LocalTime time = LocalTime.now();
        String t = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        chTime.setText(t);
        int Totaltime = chID.size() * 10;
        chTotalTime.setText(Integer.toString(Totaltime) + " min.");
        chTotalPrize.setText("Total : ₱"+ Integer.toString(TotalPrize));
        if (!cursor.isClosed()){
            cursor.close();
        }


       return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btnchBack){
            chID.clear();
            recorditems.clear();
            Fragment fragment = new CartFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }

        if (view == btnPlaceOrder){
            LocalTime time = LocalTime.now();
            String t = time.format(DateTimeFormatter.ofPattern("HH:mm"));
            Random rand = new Random();
            int code = rand.nextInt(999999999);
            Boolean ir = DB.insertRecord(Integer.toString(code),ID,recorditems.toString(),Integer.toString(recordprize),java.time.LocalDate.now().toString() + " "+t);

            int delct = 0;
            while (delct < chID.size()){
                DB.deleteItem(chID.get(delct).toString());
                delct++;
            }


            recorditems.clear();
            chID.clear();
            onCreateNoticePopupDialog();

        }
    }
    public void onCreateNoticePopupDialog(){

        dialogBuilder = new AlertDialog.Builder(getContext());
        final View noticepopupview = getLayoutInflater().inflate(R.layout.notice, null);
        txtPlaced = noticepopupview.findViewById(R.id.txtPlaced);
        txtContent = noticepopupview.findViewById(R.id.txtContent);
        txtNote = noticepopupview.findViewById(R.id.txtNote);
        btnOkay = noticepopupview.findViewById(R.id.btnOkay);



        dialogBuilder.setView(noticepopupview);

        dialog = dialogBuilder.create();
        dialog.setCancelable(false);
        dialog.show();



        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Fragment fragment = new CartFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.commit();
            }
        });



    }
}
package com.example.foodapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class HomeFragment extends Fragment implements View.OnClickListener{

    View view;
    TextView txtUserDel, txtItemName, txtItemPrice, txtItemName2, txtItemPrice2;
    Button btnAdd, btnAdd2;
    DBHelper DB;
    String ID;
    Item item1;
    Item item2;
    int ordercount;
    String ordername;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView txtQuantityItem, txtQuantityNum;
    private Button btnDecrease, btnIncrease, btnConfirmAdd;
    private  View lineQuantity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        txtItemName = view.findViewById(R.id.txtItemName);
        txtItemPrice = view.findViewById(R.id.txtItemPrice);
        txtUserDel = view.findViewById(R.id.txtUserDel);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        txtItemName2 = view.findViewById(R.id.txtItemName2);
        txtItemPrice2 = view.findViewById(R.id.txtItemPrice2);
        btnAdd2 = view.findViewById(R.id.btnAdd2);
        btnAdd2.setOnClickListener(this);

        DB = new DBHelper(getContext());
        ID = LogInSection.ID;

        item1 = new Item("Chicken Noodles", 100);
        txtItemName.setText(item1.getItemName());
        txtItemPrice.setText("₱ " + Integer.toString(item1.getPrice()));
        item2 = new Item("Fried Chicken", 160);
        txtItemName2.setText(item2.getItemName());
        txtItemPrice2.setText("₱ " + Integer.toString(item2.getPrice()));

        Cursor cursor = DB.getUserData(ID);
        cursor.moveToFirst();
        txtUserDel.setText(cursor.getString(2).toString() + " "+ cursor.getString(3).toString());

        if(!cursor.isClosed()){
            cursor.close();
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btnAdd){
            ordername = item1.itemName.toString();
            onCreateQuantityPopupDialog(item1);
        }

        if (view == btnAdd2){
            ordername = item2.itemName.toString();
            onCreateQuantityPopupDialog(item2);
        }
    }

    public void onCreateQuantityPopupDialog(Item item){

        dialogBuilder = new AlertDialog.Builder(getContext());
        final View quantityPopupView = getLayoutInflater().inflate(R.layout.quantity_popup, null);
        txtQuantityItem = quantityPopupView.findViewById(R.id.txtQuantityItem);
        txtQuantityNum = quantityPopupView.findViewById(R.id.txtQuantityNum);
        btnDecrease = quantityPopupView.findViewById(R.id.btnDecrease);
        btnIncrease = quantityPopupView.findViewById(R.id.btnIncrease);
        btnConfirmAdd = quantityPopupView.findViewById(R.id.btnConfirmAdd);
        lineQuantity = quantityPopupView.findViewById(R.id.lineQuantity);

        dialogBuilder.setView(quantityPopupView);
        dialog = dialogBuilder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(wlp);
        dialog.show();

        ordercount = 1;
        txtQuantityNum.setText(Integer.toString(ordercount));
        txtQuantityItem.setText(ordername);

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ordercount > 1){
                    ordercount -= 1;
                }
                txtQuantityNum.setText(Integer.toString(ordercount));
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ordercount < 9){
                    ordercount += 1;
                }
                txtQuantityNum.setText(Integer.toString(ordercount));
            }
        });

        btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                int idnum = rand.nextInt(999999999);
                String itemid = Integer.toString(idnum);
                String email = ID.toString();
                String itemname = item.getItemName().toString();
                String price = Integer.toString(item.getPrice() * ordercount);
                String quantity = Integer.toString(ordercount);
                Boolean check = DB.checkItem(itemid);
                if(check == false){
                    Boolean insert = DB.insertItem(itemid,email,itemname,price,quantity);
                    if (insert == true){
                        Toast.makeText(getContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Adding Failed, Please Retry", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Same ID", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
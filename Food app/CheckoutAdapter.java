package com.example.foodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutVH>{

    List<String> items, itemprize, itemquantity, itemID;


    public CheckoutAdapter(List<String> items, List<String> itemprize, List<String> itemquantity, List<String> itemID) {

        this.items = items;
        this.itemprize = itemprize;
        this.itemquantity = itemquantity;
        this.itemID = itemID;
    }

    @NonNull
    @Override
    public CheckoutVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);

        return new CheckoutVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutVH holder, int position) {
        holder.itemname.setText(items.get(position));
        holder.prize.setText(itemprize.get(position));
        holder.quantity.setText(itemquantity.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

class CheckoutVH extends RecyclerView.ViewHolder{
    TextView itemname, prize, quantity;
    DBHelper DB;

    private CheckoutAdapter adapter;


    public CheckoutVH(@NonNull View itemView) {
        super(itemView);

        DB = new DBHelper(itemView.getContext());
        itemname = itemView.findViewById(R.id.chItemName);
        prize = itemView.findViewById(R.id.chItemPrize);
        quantity = itemView.findViewById(R.id.chItemQuantity);

    }

    public CheckoutVH linkAdapter(CheckoutAdapter adapter){
        this.adapter = adapter;
        return this;
    }








}

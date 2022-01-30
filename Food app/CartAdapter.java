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

public class CartAdapter extends RecyclerView.Adapter<CartVH>{

    List<String> items, itemprize, itemquantity, itemID;


    public CartAdapter(List<String> items, List<String> itemprize, List<String> itemquantity, List<String> itemID) {

        this.items = items;
        this.itemprize = itemprize;
        this.itemquantity = itemquantity;
        this.itemID = itemID;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new CartVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        holder.itemname.setText(items.get(position));
        holder.prize.setText(itemprize.get(position));
        holder.quantity.setText(itemquantity.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

class CartVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView itemname, prize, quantity;
    CheckBox btnCartCheck;
    Button btnRemove;
    DBHelper DB;

    private CartAdapter adapter;


    public CartVH(@NonNull View itemView) {
        super(itemView);

        DB = new DBHelper(itemView.getContext());
        itemname = itemView.findViewById(R.id.cartname);
        prize = itemView.findViewById(R.id.cartprize);
        quantity = itemView.findViewById(R.id.cartquantity);
        btnCartCheck = itemView.findViewById(R.id.btnCartCheck);
        btnCartCheck.setOnClickListener(this);
        btnRemove = itemView.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(this);
    }

    public CartVH linkAdapter(CartAdapter adapter){
        this.adapter = adapter;
        return this;
    }



    @Override
    public void onClick(View view) {
        if (view == btnCartCheck){
            if (btnCartCheck.isChecked()){
                CheckoutFragment.chID.add(adapter.itemID.get(getAdapterPosition()));
            }else{
                CheckoutFragment.chID.remove(adapter.itemID.get(getAdapterPosition()));
            }

        }
        if (view == btnRemove){
            DB.deleteItem(adapter.itemID.get(getAdapterPosition()));
            adapter.items.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
            Toast.makeText(view.getContext(), "Item removed", Toast.LENGTH_SHORT).show();
        }

    }




}

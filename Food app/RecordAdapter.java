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

public class RecordAdapter extends RecyclerView.Adapter<RecordVH>{

    List<String> items, itemprize, itemdate, itemID;


    public RecordAdapter(List<String> items, List<String> itemprize, List<String> itemdate, List<String> itemID) {

        this.items = items;
        this.itemprize = itemprize;
        this.itemdate = itemdate;
        this.itemID = itemID;
    }

    @NonNull
    @Override
    public RecordVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item3, parent, false);

        return new RecordVH(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordVH holder, int position) {
        holder.itemname.setText(items.get(position));
        holder.prize.setText(itemprize.get(position));
        holder.date.setText(itemdate.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

class RecordVH extends RecyclerView.ViewHolder{
    TextView itemname, prize, date;
    DBHelper DB;

    private RecordAdapter adapter;


    public RecordVH(@NonNull View itemView) {
        super(itemView);

        DB = new DBHelper(itemView.getContext());
        itemname = itemView.findViewById(R.id.recordItemName);
        prize = itemView.findViewById(R.id.recordItemPrize);
        date = itemView.findViewById(R.id.recordDate);

    }

    public RecordVH linkAdapter(RecordAdapter adapter){
        this.adapter = adapter;
        return this;
    }








}

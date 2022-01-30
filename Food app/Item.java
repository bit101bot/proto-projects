package com.example.foodapp;

public class Item {


    String itemName;
    int price;



    public Item(String itemName, int price){
        this.itemName = itemName;
        this.price = price;


    }

    public int totalPrize(int price, int quantity){
        return price * quantity;
    }





    public String getItemName(){
        return this.itemName;
    }

    public int getPrice(){
        return this.price;
    }

}

package com.example.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(email TEXT primary key, password TEXT, firstname TEXT, lastname TEXT, mobile TEXT, address TEXT, landmark TEXT)");
        db.execSQL("create table items(orderid TEXT primary key, email TEXT, itemname TEXT, price TEXT, quantity TEXT)");
        db.execSQL("create table records(recordid TEXT primary key, email TEXT, items TEXT, itemprice TEXT, date TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists items");
        db.execSQL("drop table if exists records");



    }

    public boolean insertData(String email, String password, String firstname, String lastname, String mobile, String address, String landmark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", email);
        values.put("password", password);
        values.put("firstname", firstname);
        values.put("lastname", lastname);
        values.put("mobile", mobile);
        values.put("address", address);
        values.put("landmark", landmark);

        long result = db.insert("users", null, values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email=?", new String[] {email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email=? and password=?", new String[] {email,password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getUserData(String email){

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from users where email=?",new String[] {email});
        return cursor;
    }

    public Boolean updateUserData(String email, String password, String mobile, String address, String landmark){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        contentValues.put("mobile", mobile);
        contentValues.put("address", address);
        contentValues.put("landmark", landmark);
        Cursor cursor = DB.rawQuery("select * from users where email=?",new String[] {email});

        if (cursor.getCount() > 0){
            long result = DB.update("users", contentValues, "email=?", new String[] {email});

            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public boolean insertItem(String orderid, String email, String itemname, String price, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("orderid",orderid);
        values.put("email", email);
        values.put("itemname", itemname);
        values.put("price", price);
        values.put("quantity", quantity);

        long result = db.insert("items", null, values);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getUserItem(String email){

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from items where email=?",new String[] {email});
        return cursor;

    }
    public Cursor getUserItemCH(String email,String orderid){

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from items where email = ? and orderid = ?",new String[] {email,orderid});
        return cursor;

    }

    public boolean deleteItem(String orderid){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from items where orderid =?", new String[]{orderid});
        if(cursor.getCount() > 0){
            long result = DB.delete("items", "orderid =?", new String[]{orderid});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean checkItem(String orderid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from items where orderid=?", new String[] {orderid});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean insertRecord(String recordid, String email, String items, String itemprice, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("recordid",recordid);
        values.put("email", email);
        values.put("items", items);
        values.put("itemprice", itemprice);
        values.put("date", date);

        long result = db.insert("records", null, values);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getUserRecord(String email){

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from records where email=?",new String[] {email});
        return cursor;

    }

    public boolean deleteRecord(String recorid){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from records where recordid =?", new String[]{recorid});
        if(cursor.getCount() > 0){
            long result = DB.delete("records", "recordid =?", new String[]{recorid});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }





}

package com.example.foodapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper2 extends SQLiteOpenHelper{

    public static final String DBNAME = "UserRes.db";
    public DBHelper2(Context context) {
        super(context, "UserRes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table reservations(res TEXT primary key, email TEXT, phone TEXT, date TEXT, time TEXT, guest TEXT, restable TEXT, request TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists reservations");


    }
    public  boolean insertReservation(String res, String email, String phone, String date, String time, String guest, String restable, String request){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("res", res);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        contentValues.put("date",date);
        contentValues.put("time",time);
        contentValues.put("guest",guest);
        contentValues.put("restable",restable);
        contentValues.put("request",request);
        long result = DB.insert("reservations",null,contentValues);
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getReservations(String email){

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from reservations where email=?",new String[] {email});
        return cursor;

    }
}

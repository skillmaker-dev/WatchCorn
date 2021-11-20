package com.watchcorn.watchcorn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, "Userdata.dp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table UserDetails(Email TEXT primary key, Password TEXT, FN TEXT , FirstTime TEXT default '0' )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists UserDetails");
    }

    public Boolean InsertUserData(String email, String password, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        contentValues.put("FN", name);
        long result = db.insert("UserDetails", null, contentValues);
        return result != -1;
    }


    public Boolean UpdateFirstTime(String email,String i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstTime",i);

        Cursor cursor = db.rawQuery("Select * from UserDetails where Email = ?", new String[] {email});
        if(cursor.getCount() >0) {
            long result = db.update("UserDetails", contentValues, "Email=?", new String[]{email});
            return result != -1;
        }
        else
            return false;
    }

    /*
    public Boolean DeleteUserData(String email){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from UserDetails where Email = ?", new String[] {email});
        if(cursor.getCount() >0) {
            long result = db.delete("UserDetails", "Email=?", new String[]{email});
            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }
     */

    public Cursor GetData() {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("Select * from UserDetails", null);
    }

}

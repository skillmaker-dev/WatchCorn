package com.watchcorn.watchcorn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, "Userdata.dp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table UserDetails(ID INTEGER primary key AUTOINCREMENT default 0,Email TEXT UNIQUE, Password TEXT, FN TEXT, FirstTime TEXT default '0',Status TEXT default 'offline')");
        db.execSQL("create table Favorites(MovieID TEXT primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists UserDetails");
        db.execSQL("drop Table if exists Favorites");
    }

    // Insert user into UserDetails table
    public Boolean InsertUserData(String email, String password, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        contentValues.put("FN", name);

        long result = db.insert("UserDetails", null, contentValues);
        return result != -1;
    }

    // Update FirstTime column in UserDetails table
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

    // Update Status column in UserDetails
    public Boolean UpdateStatus(String email,String i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Status",i);

        Cursor cursor = db.rawQuery("Select * from UserDetails where Email = ?", new String[] {email});
        if(cursor.getCount() >0) {
            long result = db.update("UserDetails", contentValues, "Email=?", new String[]{email});
            return result != -1;
        }
        else
            return false;
    }

    // Update user details
    public Boolean UpdateUserinfos(String email,String new_email,String new_pass,String new_Fn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",new_email);
        contentValues.put("FN",new_Fn);
        contentValues.put("Password",new_pass);

        Cursor cursor = db.rawQuery("Select * from UserDetails where Email = ?", new String[] {email});
        if(cursor.getCount() >0) {
            long result = db.update("UserDetails", contentValues, "Email=?", new String[]{email});
            return result != -1;
        }
        else
            return false;
    }

    // Get all data from UserDetails
    public Cursor GetData() {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("Select * from UserDetails", null);
    }

    //Utilisation pour résoudre le problème  de login à chaque fois qu'onrentre à l'application
    /*
    public Boolean UpdateLoggedIn(String email,String i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("LoggedIn",i);


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

    // Insert data into favorites table
    public void insertIntoFavorites(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MovieID", id);
        db.insert("Favorites", null, cv);
    }

    // Read one data from favorites table
    public Cursor getDataFromFavorites(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from Favorites where MovieID = '" + id + "'";
        return db.rawQuery(sql, null, null);
    }

    // Remove one data from favorites table
    public void removeFromFavorites(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM Favorites WHERE MovieID = '" + id + "'";
        db.execSQL(sql);
    }

    // Read all data from favorites
    public Cursor getAllDataFromFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from Favorites";
        return db.rawQuery(sql, null, null);
    }

    // Check if movie is in the favorites table
    public boolean checkMovieInFavorites(String id) {
        Cursor cursor = getAllDataFromFavorites();
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(id)) {
                return true;
            }
        }
        return false;
    }
}

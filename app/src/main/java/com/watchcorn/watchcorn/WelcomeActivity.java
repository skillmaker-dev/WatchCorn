package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends AppCompatActivity {

    private DB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new DB(this);
        Cursor res = database.GetData();

        String C = "";

        while(res.moveToNext()){
            if(res.getString(5).equals("online"))
                C = res.getString(5);
        }

        res.close();
        database.close();

        if(C.equals("online")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent start = new Intent(WelcomeActivity.this,MainPageActivity.class);
                    startActivity(start);
                    finish();
                }
            }, 2000);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent start = new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(start);
                    finish();
                }
            }, 2000);
        }

        setContentView(R.layout.activity_welcome);
    }




}
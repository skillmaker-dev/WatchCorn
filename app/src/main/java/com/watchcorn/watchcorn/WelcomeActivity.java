package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(start);
                finish();
            }
        }, 2000);

        setContentView(R.layout.activity_welcome);
    }




}
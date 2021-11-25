package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button[] buttons = new Button[17];
        boolean[] buttonIsClicked = new boolean[17];
        List<String> genres = new ArrayList<String>();

        //get ids of all buttons and set corresponding click event to false
        for(int i = 1;i<18;i++)
        {
            int id = getResources().getIdentifier("choice_"+i, "id", getPackageName());
            buttons[i-1] = (Button) findViewById(id);
            buttonIsClicked[i-1] = false;
        }



        //setting click listener to every button
        //changing button color and inserting selected genre to arraylist
        for(int i = 0;i<17;i++) {
            int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (buttonIsClicked[finalI] == true) {
                        buttons[finalI].setBackgroundColor(Color.parseColor("#1b212f"));
                        buttonIsClicked[finalI] = false;
                        genres.remove(buttons[finalI].getText().toString());
                        //logging arraylist
                        for(String genre : genres)
                        {
                            Log.d("ArrayList",genre);
                        }


                    } else {
                        buttons[finalI].setBackgroundColor(Color.parseColor("#3d4457"));
                        buttonIsClicked[finalI] = true;
                        genres.add(buttons[finalI].getText().toString());
                        //logging arraylist
                        for(String genre : genres)
                        {
                            Log.d("ArrayList",genre);
                        }
                    }


                }
            });
        }

        }



    }

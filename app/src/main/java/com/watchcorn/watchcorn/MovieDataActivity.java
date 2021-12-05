package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MovieDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_data);
        String imdbId;
        Bundle extras = getIntent().getExtras();

            imdbId= extras.getString("ImdbId");


        TextView filmTitle;
        filmTitle = findViewById(R.id.film_name);
        filmTitle.setText(imdbId);
    }

}
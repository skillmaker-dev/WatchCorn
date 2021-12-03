package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUpcomingMovies, recyclerViewMovies;
    private Context MainPageActivityActivity;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        recyclerViewUpcomingMovies = findViewById(R.id.recylcerViewUpcomingMovies);
        recyclerViewUpcomingMovies.setHasFixedSize(true);
        recyclerViewUpcomingMovies.setItemViewCacheSize(20);
        recyclerViewUpcomingMovies.setDrawingCacheEnabled(true);
        recyclerViewUpcomingMovies.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        recyclerViewMovies = findViewById(R.id.recylcerViewMovies);
        recyclerViewMovies.setHasFixedSize(true);
        recyclerViewMovies.setItemViewCacheSize(20);
        recyclerViewMovies.setDrawingCacheEnabled(true);
        recyclerViewMovies.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        MainPageActivityActivity = this;

        ArrayList<Movie> upcomingMovies = new ArrayList<>();
        ArrayList<Movie> movies = new ArrayList<>();

        searchBtn = findViewById(R.id.searchButton);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPageActivity.this, Search_List_Activity.class);
                startActivity(i);
                finish();
            }
        });

        try {
            RecyclerViewMoviesAdapter moviesAdapter = new RecyclerViewMoviesAdapter(MainPageActivityActivity);
            moviesAdapter.setMovies(movies);
            recyclerViewMovies.setAdapter(moviesAdapter);


            Movie.getBestMovies(new BestMovies(){
                @Override
                public void getBestMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            movies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl()));
                            moviesAdapter.notifyDataSetChanged();

                        }
                    });
                }

            });


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }



        try {
            RecyclerViewUpcomingMoviesAdapter upcomingMoviesAdapter = new RecyclerViewUpcomingMoviesAdapter(MainPageActivityActivity);
            upcomingMoviesAdapter.setUpcomingMovies(upcomingMovies);
            recyclerViewUpcomingMovies.setAdapter(upcomingMoviesAdapter);
            Movie.getUpcomingMovies(new UpcomingMovies(){
                @Override
                public void igetUpcomingMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            upcomingMovies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl()));
                            upcomingMoviesAdapter.notifyDataSetChanged();


                        }
                    });
                }

            });


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        recyclerViewUpcomingMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
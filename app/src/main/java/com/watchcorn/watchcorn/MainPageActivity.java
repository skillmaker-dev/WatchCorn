package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUpcomingMovies, recyclerViewMovies;
    private Context MainPageActivityActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        recyclerViewUpcomingMovies = findViewById(R.id.recylcerViewUpcomingMovies);
        recyclerViewMovies = findViewById(R.id.recylcerViewMovies);
        MainPageActivityActivity = this;

        ArrayList<Movie> upcomingMovies = new ArrayList<>();
        ArrayList<Movie> movies = new ArrayList<>();


        try {

            Movie.getBestMovies(new BestMovies(){
                @Override
                public void getBestMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            movies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl()));
                            RecyclerViewMoviesAdapter moviesAdapter = new RecyclerViewMoviesAdapter(MainPageActivityActivity);
                            moviesAdapter.setMovies(movies);
                            recyclerViewMovies.setAdapter(moviesAdapter);
                            //Log.d("Image : ", movie.getSmallImageUrl());
                        }
                    });
                }

            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }



        try {

            Movie.getUpcomingMovies(new BestMovies(){
                @Override
                public void getBestMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            upcomingMovies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl()));
                            RecyclerViewUpcomingMoviesAdapter upcomingMoviesAdapter = new RecyclerViewUpcomingMoviesAdapter(MainPageActivityActivity);
                            upcomingMoviesAdapter.setUpcomingMovies(upcomingMovies);
                            recyclerViewUpcomingMovies.setAdapter(upcomingMoviesAdapter);
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
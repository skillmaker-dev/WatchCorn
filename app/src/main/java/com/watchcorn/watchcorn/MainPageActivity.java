package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewArtists, recyclerViewMovies;
    private Context MainPageActivityActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        recyclerViewArtists = findViewById(R.id.recylcerViewArtists);
        recyclerViewMovies = findViewById(R.id.recylcerViewMovies);
        MainPageActivityActivity = this;

        ArrayList<Artist> artists = new ArrayList<>();
        ArrayList<Movie> movies = new ArrayList<>();


        try {

            Movie.getBestMovies(new BestMovies(){
                @Override
                public void getBestMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            artists.add(new Artist(movie.getTitle(), "https://images.sftcdn.net/images/t_app-cover-l,f_auto/p/1ecfc7c3-8b2b-43d8-94f7-947c1bdb4a95/3545844269/masa-cool-wallpapers-wallpaper-hd-background-screenshot.jpg"));
                            RecyclerViewArtistsAdapter adapter = new RecyclerViewArtistsAdapter(MainPageActivityActivity);
                            adapter.setArtists(artists);
                            recyclerViewArtists.setAdapter(adapter);

                            movies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl()));
                            RecyclerViewMoviesAdapter moviesAdapter = new RecyclerViewMoviesAdapter(MainPageActivityActivity);
                            moviesAdapter.setMovies(movies);
                            recyclerViewMovies.setAdapter(moviesAdapter);
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

                            // Get upcoming movies

                        }
                    });
                }

            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        recyclerViewArtists.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
package com.watchcorn.watchcorn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.watchcorn.watchcorn.Internet.Utility.NetworkChangeListner;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUpcomingMovies, recyclerViewMovies;
    private Context MainPageActivityActivity;
    private BottomNavigationView bottomNavigationView;

    NetworkChangeListner networkChangeListner = new NetworkChangeListner();

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListner);
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // initialize the nav
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        // set home as default selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        // the listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search_List_Activity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.watchList:
                        startActivity(new Intent(getApplicationContext(), WatchListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favorits:
                        startActivity(new Intent(getApplicationContext(), FavoritsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

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

                            movies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl(),movie.getImdbID()));
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

                            upcomingMovies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl(),movie.getImdbID()));
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
package com.watchcorn.watchcorn;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    Button[] buttons = new Button[17];
    int count = 0;

    @Override
    public void onBackPressed() {

        count++;

        if (count == 1)
            Toast.makeText(getApplicationContext(), "Press Back Again To Exit", Toast.LENGTH_SHORT).show();

        if (count == 2)
            super.onBackPressed();
    }

    NetworkChangeListner networkChangeListner = new NetworkChangeListner();

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner, filter);
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


        for (int i = 1; i < 18; i++) {
            int id = getResources().getIdentifier("btnGenre" + i, "id", getPackageName());
            buttons[i - 1] = (Button) findViewById(id);
        }


        for (int i = 0; i < 17; i++) {
            int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //send id to the new intent
                    switch (buttons[finalI].getText().toString()) {
                        case "Action":
                            Log.d("genre id", "28");
                            break;
                        case "Adventure":
                            Log.d("genre id", "12");
                            break;
                        case "Animation":
                            Log.d("genre id", "16");
                            break;
                        case "Musical":
                            Log.d("genre id", "10402");
                            break;
                        case "Comedy":
                            Log.d("genre id", "35");
                            break;
                        case "Horror":
                            Log.d("genre id", "27");
                            break;
                        case "Documentary":
                            Log.d("genre id", "99");
                            break;
                        case "Mystery":
                            Log.d("genre id", "9648");
                            break;
                        case "Crime":
                            Log.d("genre id", "80");
                            break;
                        case "Western":
                            Log.d("genre id", "37");
                            break;
                        case "History":
                            Log.d("genre id", "36");
                            break;
                        case "Thriller":
                            Log.d("genre id", "53");
                            break;
                        case "Drama":
                            Log.d("genre id", "18");
                            break;
                        case "Family":
                            Log.d("genre id", "10751");
                            break;
                        case "Fantasy":
                            Log.d("genre id", "14");
                            break;
                        case "Science Fiction":
                            Log.d("genre id", "878");
                            break;
                        case "Romance":
                            Log.d("genre id", "10749");
                            break;
                    }


                    String[] id = new String[1];
                    id[0] = "28";
                    try {

                        Movie.getMoviesByGenres(id, new MoviesByGenre() {
                            @Override
                            public void igetMoviesByGenre(Movie movie) throws JSONException, IOException {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        Log.d("movie", movie.getTitle());

                                    }
                                });
                            }

                        });

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

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
                        finish();
                        return true;
                    case R.id.watchList:
                        startActivity(new Intent(getApplicationContext(), WatchListActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.favorits:
                        startActivity(new Intent(getApplicationContext(), FavoritsActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
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

            Movie.getBestMovies(new BestMovies() {
                @Override
                public void getBestMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            movies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl(), movie.getImdbID(), null));
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
            Movie.getUpcomingMovies(new UpcomingMovies() {
                @Override
                public void igetUpcomingMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            upcomingMovies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl(), movie.getImdbID(), null));
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
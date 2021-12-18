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

    Button[] buttons = new Button[17];
    int count = 0;
    NetworkChangeListner networkChangeListner = new NetworkChangeListner();
    private RecyclerView recyclerViewUpcomingMovies, recyclerViewMovies;
    private Context MainPageActivityActivity;
    private BottomNavigationView bottomNavigationView;

    @Override
    public void onBackPressed() {

        count++;

        if (count == 1)
            Toast.makeText(getApplicationContext(), "Press Back Again To Exit", Toast.LENGTH_SHORT).show();

        if (count == 2)
            super.onBackPressed();
    }

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

        Intent intent = new Intent(getApplicationContext(),GetMovieByGenreActivity.class);

        for (int i = 0; i < 17; i++) {
            int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //send id to the new intent
                    switch (buttons[finalI].getText().toString()) {
                        case "Action":
                            intent.putExtra("id","28");
                            startActivity(intent);
                            break;
                        case "Adventure":
                            intent.putExtra("id","12");
                            startActivity(intent);
                            break;
                        case "Animation":
                            intent.putExtra("id","16");
                            startActivity(intent);
                            break;
                        case "Musical":
                            intent.putExtra("id","10402");
                            startActivity(intent);
                            break;
                        case "Comedy":
                            intent.putExtra("id","35");
                            startActivity(intent);
                            break;
                        case "Horror":
                            intent.putExtra("id","27");
                            startActivity(intent);
                            break;
                        case "Documentary":
                            intent.putExtra("id","99");
                            startActivity(intent);
                            break;
                        case "Mystery":
                            intent.putExtra("id","9648");
                            startActivity(intent);
                            break;
                        case "Crime":
                            intent.putExtra("id","80");
                            startActivity(intent);
                            break;
                        case "Western":
                            intent.putExtra("id","37");
                            startActivity(intent);
                            break;
                        case "History":
                            intent.putExtra("id","36");
                            startActivity(intent);
                            break;
                        case "Thriller":
                            intent.putExtra("id","53");
                            startActivity(intent);
                            break;
                        case "Drama":
                            intent.putExtra("id","18");
                            startActivity(intent);
                            break;
                        case "Family":
                            intent.putExtra("id","10751");
                            startActivity(intent);
                            break;
                        case "Fantasy":
                            intent.putExtra("id","14");
                            startActivity(intent);
                            break;
                        case "Science Fiction":
                            intent.putExtra("id","878");
                            startActivity(intent);
                            break;
                        case "Romance":
                            intent.putExtra("id","10749");
                            startActivity(intent);
                            break;
                    }


//                    String[] id = new String[1];
//                    id[0] = "28";
//                    try {
//
//                        Movie.getMoviesByGenres(id, new MoviesByGenre() {
//                            @Override
//                            public void igetMoviesByGenre(Movie movie) throws JSONException, IOException {
//
//                                runOnUiThread(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//
//                                        Log.d("movie", movie.getTitle());
//
//                                    }
//                                });
//                            }
//
//                        });
//
//                    } catch (IOException | JSONException e) {
//                        e.printStackTrace();
//                    }
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
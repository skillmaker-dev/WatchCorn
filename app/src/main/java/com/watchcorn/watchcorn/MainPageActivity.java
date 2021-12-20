package com.watchcorn.watchcorn;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
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
import java.util.Arrays;

public class MainPageActivity extends AppCompatActivity {

    int count1 = 0;
    int count2 = 0;

    Button[] buttons = new Button[17];
    int count = 0;
    NetworkChangeListner networkChangeListner = new NetworkChangeListner();
    private RecyclerView recyclerViewUpcomingMovies, recyclerViewMovies, recyclerViewRecommended;
    private Context MainPageActivityActivity;
    private BottomNavigationView bottomNavigationView;
    private DB database;

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

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

        database = new DB(this);

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
                            intent.putExtra("Name","Action : ");
                            startActivity(intent);
                            break;
                        case "Adventure":
                            intent.putExtra("id","12");
                            intent.putExtra("Name","Adventure : ");
                            startActivity(intent);
                            break;
                        case "Animation":
                            intent.putExtra("id","16");
                            intent.putExtra("Name","Animation : ");
                            startActivity(intent);
                            break;
                        case "Musical":
                            intent.putExtra("id","10402");
                            intent.putExtra("Name","Musical : ");
                            startActivity(intent);
                            break;
                        case "Comedy":
                            intent.putExtra("id","35");
                            intent.putExtra("Name","Comedy : ");
                            startActivity(intent);
                            break;
                        case "Horror":
                            intent.putExtra("id","27");
                            intent.putExtra("Name","Horror : ");
                            startActivity(intent);
                            break;
                        case "Documentary":
                            intent.putExtra("id","99");
                            intent.putExtra("Name","Documentary : ");
                            startActivity(intent);
                            break;
                        case "Mystery":
                            intent.putExtra("id","9648");
                            intent.putExtra("Name","Mystery : ");
                            startActivity(intent);
                            break;
                        case "Crime":
                            intent.putExtra("id","80");
                            intent.putExtra("Name","Crime : ");
                            startActivity(intent);
                            break;
                        case "Western":
                            intent.putExtra("id","37");
                            intent.putExtra("Name","Western : ");
                            startActivity(intent);
                            break;
                        case "History":
                            intent.putExtra("id","36");
                            intent.putExtra("Name","History : ");
                            startActivity(intent);
                            break;
                        case "Thriller":
                            intent.putExtra("id","53");
                            intent.putExtra("Name","Thriller : ");
                            startActivity(intent);
                            break;
                        case "Drama":
                            intent.putExtra("id","18");
                            intent.putExtra("Name","Drama : ");
                            startActivity(intent);
                            break;
                        case "Family":
                            intent.putExtra("id","10751");
                            intent.putExtra("Name","Family : ");
                            startActivity(intent);
                            break;
                        case "Fantasy":
                            intent.putExtra("id","14");
                            intent.putExtra("Name","Fantasy : ");
                            startActivity(intent);
                            break;
                        case "Science Fiction":
                            intent.putExtra("id","878");
                            intent.putExtra("Name","Science Fiction : ");
                            startActivity(intent);
                            break;
                        case "Romance":
                            intent.putExtra("id","10749");
                            intent.putExtra("Name","Romance : ");
                            startActivity(intent);
                            break;
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

        recyclerViewRecommended = findViewById(R.id.recylcerViewRecommendedMovies);
        recyclerViewRecommended.setHasFixedSize(true);
        recyclerViewRecommended.setItemViewCacheSize(20);
        recyclerViewRecommended.setDrawingCacheEnabled(true);
        recyclerViewRecommended.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        MainPageActivityActivity = this;

        ArrayList<Movie> upcomingMovies = new ArrayList<>();
        ArrayList<Movie> recommendedMovies = new ArrayList<>();
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

        String[] ids = new String[1];

        Cursor res = database.getOrderedDataFromGenre();
        while(res.moveToNext()){

            ids[0] = res.getString(2);

            try {
                RecyclerViewUpcomingMoviesAdapter recommendedMoviesAdapter = new RecyclerViewUpcomingMoviesAdapter(MainPageActivityActivity);
                recommendedMoviesAdapter.setUpcomingMovies(recommendedMovies);
                recyclerViewRecommended.setAdapter(recommendedMoviesAdapter);
                Movie.getMoviesByGenres(ids, new MoviesByGenre() {
                    @Override
                    public void igetMoviesByGenre(Movie movie) throws JSONException, IOException {

                        System.out.println("externe" + count1++);

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                recommendedMovies.add(new Movie(movie.getTitle(), movie.getReleaseYear(), movie.getSmallImageUrl(), movie.getImdbID(), movie.getRating()));
                                recommendedMoviesAdapter.notifyDataSetChanged();
                                System.out.println("interne" + count2++);
                            }
                        });
                    }
                });


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }



        recyclerViewUpcomingMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRecommended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
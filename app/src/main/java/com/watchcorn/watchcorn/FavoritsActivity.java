package com.watchcorn.watchcorn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.watchcorn.watchcorn.Internet.Utility.NetworkChangeListner;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class FavoritsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private Context context;
    private BottomNavigationView bottomNavigationView;
    private DB db;

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
        setContentView(R.layout.activity_favorits);

        // initialize the nav
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        // set home as default selected
        bottomNavigationView.setSelectedItemId(R.id.favorits);
        // the listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        recyclerViewFavorites = findViewById(R.id.favoritesRecyclerView);
        recyclerViewFavorites.setHasFixedSize(true);
        recyclerViewFavorites.setItemViewCacheSize(20);
        recyclerViewFavorites.setDrawingCacheEnabled(true);
        recyclerViewFavorites.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        context = this;
        db = new DB(context);

        ArrayList<Movie> favoritesMovies = new ArrayList<>();

        FavoritesRecyclerViewAdapter favoritesAdapter = new FavoritesRecyclerViewAdapter(context);
        favoritesAdapter.setMovies(favoritesMovies);
        recyclerViewFavorites.setAdapter(favoritesAdapter);

        Cursor cursor = db.getAllDataFromFavorites();

        while (cursor.moveToNext()) {

            try {

                Movie.getMovieById(cursor.getString(0),new MovieById(){
                    @Override
                    public void getMovieById(Movie movie) throws JSONException, IOException {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                favoritesMovies.add(new Movie(movie.getTitle(), null, movie.getSmallImageUrl(), null, null));
                                favoritesAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        recyclerViewFavorites.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
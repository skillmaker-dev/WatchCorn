package com.watchcorn.watchcorn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.watchcorn.watchcorn.Internet.Utility.NetworkChangeListner;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class WatchListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewWatchList;
    private Context context;
    private BottomNavigationView bottomNavigationView;
    private SearchView searchView;
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
        setContentView(R.layout.activity_watch_list);

        searchView = findViewById(R.id.search_bar_watch);

        // initialize the nav
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        // set home as default selected
        bottomNavigationView.setSelectedItemId(R.id.watchList);
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

        recyclerViewWatchList = findViewById(R.id.watchListRecyclerView);
        recyclerViewWatchList.setHasFixedSize(true);
        recyclerViewWatchList.setItemViewCacheSize(20);
        recyclerViewWatchList.setDrawingCacheEnabled(true);
        recyclerViewWatchList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        context = this;
        db = new DB(context);

        ArrayList<Movie> watchListMovies = new ArrayList<>();

        WatchListRecyclerViewAdapter watchListAdapter = new WatchListRecyclerViewAdapter(context);
        watchListAdapter.setMovies(watchListMovies);
        recyclerViewWatchList.setAdapter(watchListAdapter);

        Cursor cursor = db.getAllDataFromWatchList();

        while (cursor.moveToNext()) {

            try {

                Movie.getMovieById(cursor.getString(0),new MovieById(){
                    @Override
                    public void getMovieById(Movie movie) throws JSONException, IOException {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                watchListMovies.add(new Movie(movie.getTitle(), movie.getReleaseYear(), movie.getSmallImageUrl(), movie.getImdbID(), movie.getRating()));
                                watchListAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Movie> result = new ArrayList<>();
                for(Movie x : watchListMovies){

                    if(x.getTitle().toLowerCase().contains(newText.toLowerCase()))
                        result.add(x);

                    WatchListRecyclerViewAdapter watchListAdapter = new WatchListRecyclerViewAdapter(context);
                    watchListAdapter.setMovies(result);
                    recyclerViewWatchList.setAdapter(watchListAdapter);                  }

                return false;
            }
        });

        recyclerViewWatchList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
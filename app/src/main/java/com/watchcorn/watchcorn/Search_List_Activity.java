package com.watchcorn.watchcorn;

import static com.watchcorn.watchcorn.R.layout.activity_search_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.watchcorn.watchcorn.Internet.Utility.NetworkChangeListner;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Search_List_Activity extends AppCompatActivity {

    protected RecyclerView myRecyclerView;
    private SearchView mySearchView;
    private Context Search_List_Activity;
    private BottomNavigationView bottomNavigationView;

    NetworkChangeListner networkChangeListner = new NetworkChangeListner();

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
        finish();
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
        setContentView(activity_search_list);

        // initialize the nav
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        // set home as default selected
        bottomNavigationView.setSelectedItemId(R.id.search);
        // the listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
                        finish();
                        return true;
                    case R.id.search:
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

        myRecyclerView = findViewById(R.id.searchRecyclerView);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemViewCacheSize(20);
        myRecyclerView.setDrawingCacheEnabled(true);
        myRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

        mySearchView = findViewById(R.id.search_bar);
        Search_List_Activity = this;

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    ArrayList<Movie> results = new ArrayList<>();

                    MoviesAdapterForRecyclerView adapter = new MoviesAdapterForRecyclerView(Search_List_Activity);
                    adapter.setResMovie(results);
                    myRecyclerView.setAdapter(adapter);
                    Movie.getMoviesByTitle(query.toLowerCase(), new MoviesByTitle() {
                        @Override
                        public void getMovieByTitle(Movie movie) throws JSONException, IOException {

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    results.add(new Movie(movie.getTitle(), movie.getReleaseYear(), movie.getSmallImageUrl(), movie.getImdbID(), movie.getRating()));
                                    adapter.notifyItemInserted(results.size() - 1);

                                }
                            });
                        }
                    });


                    results.clear();

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
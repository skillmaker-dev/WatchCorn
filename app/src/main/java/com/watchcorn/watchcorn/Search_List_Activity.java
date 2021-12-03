package com.watchcorn.watchcorn;

import static com.watchcorn.watchcorn.R.layout.activity_search_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Search_List_Activity extends AppCompatActivity {

    protected RecyclerView myRecyclerView;
    private SearchView mySearchView;
    private Context Search_List_Activity;
    private BottomNavigationView bottomNavigationView;

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
                        startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.watchList:
                        // same as the others, just start the activity
                    case R.id.favorits:
                        // same as the others, just start the activity
                }
                return false;
            }
        });

        myRecyclerView = findViewById(R.id.oussama);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemViewCacheSize(20);
        myRecyclerView.setDrawingCacheEnabled(true);
        myRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);


        mySearchView = findViewById(R.id.search_bar);
        Search_List_Activity = this;

        ArrayList<Movie>results = new ArrayList<>();
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    MoviesAdapterForRecyclerView adapter = new MoviesAdapterForRecyclerView(Search_List_Activity);
                    adapter.setResMovie(results);
                    myRecyclerView.setAdapter(adapter);
                    Movie.getMoviesByTitle(query,new MoviesByTitle(){
                        @Override
                        public void getMovieByTitle(Movie movie) throws JSONException, IOException {

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    /*if(movie.getTitle().toLowerCase(Locale.ROOT).contains(query)){
                                        results.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl()));
                                        MoviesAdapterForRecyclerView adapter = new MoviesAdapterForRecyclerView(Search_List_Activity);
                                        adapter.setResMovie(results);
                                        myRecyclerView.setAdapter(adapter);
                                    }*/


                                        results.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl()));
                                        //adapter.notifyDataSetChanged();
                                    adapter.notifyItemInserted(results.size()-1);


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
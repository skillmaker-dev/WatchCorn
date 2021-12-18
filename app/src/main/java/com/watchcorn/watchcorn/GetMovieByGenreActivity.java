package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.watchcorn.watchcorn.Internet.Utility.NetworkChangeListner;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class GetMovieByGenreActivity extends AppCompatActivity {

    NetworkChangeListner networkChangeListner = new NetworkChangeListner();
    protected RecyclerView myRecyclerView;
    private SearchView mySearchView;
    private Context GetMovieByGenreActivity;

    @Override
    public void onBackPressed() {
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
        setContentView(R.layout.activity_get_movie_by_genre);

        myRecyclerView = findViewById(R.id.RecyclerViewForMovieByGenre);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemViewCacheSize(20);
        myRecyclerView.setDrawingCacheEnabled(true);
        myRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mySearchView = findViewById(R.id.search_bar_forMovieByGenre);
        GetMovieByGenreActivity = this;

        String idMovie = getIntent().getExtras().getString("id");

        String[] id = new String[1];
                    id[0] = idMovie;

        ArrayList<Movie> results = new ArrayList<>();
        MoviesAdapterForRecyclerView adapter = new MoviesAdapterForRecyclerView(GetMovieByGenreActivity);

        try {

            Movie.getMoviesByGenres(id, new MoviesByGenre() {
                @Override
                public void igetMoviesByGenre(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            results.add(new Movie(movie.getTitle(), movie.getReleaseYear(), movie.getSmallImageUrl(), movie.getImdbID(), movie.getRating()));
                            adapter.setResMovie(results);
                            myRecyclerView.setAdapter(adapter);
                            //adapter.notifyItemInserted(results.size() - 1);
                        }
                    });
                }
            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Movie> resultsFromSearch = new ArrayList<>();

                for(Movie x : results){
                    if(x.getTitle().toLowerCase().contains(newText.toLowerCase())){
                        resultsFromSearch.add(x);
                        adapter.setResMovie(resultsFromSearch);
                        myRecyclerView.setAdapter(adapter);
                        adapter.notifyItemInserted(resultsFromSearch.size() - 1);
                    }
                }
                return false;
            }
        });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
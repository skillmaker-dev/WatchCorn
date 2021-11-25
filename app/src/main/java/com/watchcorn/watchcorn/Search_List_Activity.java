package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Search_List_Activity extends AppCompatActivity {

    private ListView listView;
    private SearchView mySearchView;
    private Context Search_List_Activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        listView = findViewById(R.id.myListView);
        mySearchView = findViewById(R.id.search_bar);

        ArrayList<FilmTest> arrayList = new ArrayList<>();





        //Replace this whole try catch with getMoviesByTitle from "movies functions.txt".
        try {

            Movie.getBestMovies(new BestMovies(){
                @Override
                public void getBestMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            arrayList.add(new FilmTest(R.drawable.euro, movie.getTitle(), movie.getRating()));

                            FilmTestAdapter filmTestAdapter = new FilmTestAdapter(Search_List_Activity, R.layout.activity_item_for_listview, arrayList);
                            listView.setAdapter(filmTestAdapter);
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

                ArrayList<FilmTest> results = new ArrayList<>();
                for (FilmTest x : arrayList) {
                    if (x.getName().toLowerCase(Locale.ROOT).contains(newText))
                        results.add(x);
                }

                FilmTestAdapter filmTestAdapter = new FilmTestAdapter(Search_List_Activity.this, R.layout.activity_item_for_listview, results);

                listView.setAdapter(filmTestAdapter);

                //((FilmTestAdapter)listView.getAdapter()).update(results);

                return false;
            }
        });

    }
}
package com.watchcorn.watchcorn;

import static com.watchcorn.watchcorn.R.layout.activity_search_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Search_List_Activity extends AppCompatActivity {

    protected RecyclerView myRecyclerView;
    private SearchView mySearchView;
    private TextView numberOfMovies;
    private ImageView returnToMainPage;
    private Context Search_List_Activity;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_search_list);

        myRecyclerView = findViewById(R.id.oussama);
        mySearchView = findViewById(R.id.search_bar);
        returnToMainPage = findViewById(R.id.returnImage);
        numberOfMovies = findViewById(R.id.textView1);
        Search_List_Activity = this;

        numberOfMovies.setText("Result " + count +" records availabale");

        returnToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.watchcorn.watchcorn.Search_List_Activity.this,MainPageActivity.class);
                startActivity(i);
                finish();
            }
        });


        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    ArrayList<Movie>results = new ArrayList<>();
                    Movie.getMoviesByTitle(query,new MoviesByTitle(){
                        @Override
                        public void getMovieByTitle(Movie movie) throws JSONException, IOException {

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    if(movie.getTitle().toLowerCase(Locale.ROOT).contains(query)){
                                        results.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl()));
                                        MoviesAdapterForRecyclerView adapter = new MoviesAdapterForRecyclerView(Search_List_Activity);
                                        adapter.setResMovie(results);
                                        myRecyclerView.setAdapter(adapter);
                                    }
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
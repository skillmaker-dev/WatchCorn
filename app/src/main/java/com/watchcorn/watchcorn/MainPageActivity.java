package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewArtists;
    private Context MainPageActivityActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        recyclerViewArtists = findViewById(R.id.recylcerViewArtists);


        ArrayList<Artist> artists = new ArrayList<>();
        try {

            Movie.getTopMovies(new BestMovies(){
                @Override
                public void getBestMovies(Movie movie) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            artists.add(new Artist(movie.getTitle(), "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
                            RecyclerViewArtistsAdapter adapter = new RecyclerViewArtistsAdapter(MainPageActivityActivity);
                            adapter.setArtists(artists);
                            recyclerViewArtists.setAdapter(adapter);

                        }
                    });
                }

            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        recyclerViewArtists.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
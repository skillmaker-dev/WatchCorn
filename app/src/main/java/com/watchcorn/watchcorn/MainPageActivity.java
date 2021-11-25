package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        recyclerViewArtists = findViewById(R.id.recylcerViewArtists);

        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist("Artist 1", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 2", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 3", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 4", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 5", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 6", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 7", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 8", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 9", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));
        artists.add(new Artist("Artist 10", "src/main/res/drawable-v24/ic_launcher_foreground.xml"));

        RecyclerViewArtistsAdapter adapter = new RecyclerViewArtistsAdapter(this);
        adapter.setArtists(artists);
        recyclerViewArtists.setAdapter(adapter);
        recyclerViewArtists.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
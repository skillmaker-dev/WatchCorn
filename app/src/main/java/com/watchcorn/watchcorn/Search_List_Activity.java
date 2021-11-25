package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Locale;

public class Search_List_Activity extends AppCompatActivity {

    private ListView listView;
    private SearchView mySearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        listView = findViewById(R.id.myListView);
        mySearchView = findViewById(R.id.search_bar);


        ArrayList<FilmTest> arrayList = new ArrayList<>();

        arrayList.add(new FilmTest(R.drawable.euro, "Monday", "I Love this Film"));
        arrayList.add(new FilmTest(R.drawable.euro, "Tuesday", "I Love this Film"));
        arrayList.add(new FilmTest(R.drawable.euro, "Wednsday", "I Love this Film"));
        arrayList.add(new FilmTest(R.drawable.euro, "Thersday", "I Love this Film"));
        arrayList.add(new FilmTest(R.drawable.euro, "Friday", "I Love this Film"));
        arrayList.add(new FilmTest(R.drawable.euro, "Saturday", "I Love this Film"));
        arrayList.add(new FilmTest(R.drawable.euro, "Sunday", "I Love this Film"));

        FilmTestAdapter filmTestAdapter = new FilmTestAdapter(this, R.layout.activity_item_for_listview, arrayList);

        listView.setAdapter(filmTestAdapter);

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
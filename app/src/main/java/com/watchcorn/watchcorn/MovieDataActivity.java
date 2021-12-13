package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MovieDataActivity extends AppCompatActivity {
    public Context context = this;
    private RecyclerView recyclerViewSimilarMovies;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_data);

        String imdbId;
        Bundle extras = getIntent().getExtras();

        imdbId= extras.getString("ImdbId");


        TextView filmTitle=findViewById(R.id.film_name);
        TextView filmDirector = findViewById(R.id.film_director);
        TextView filmLength= findViewById(R.id.film_time);
        TextView filmYear = findViewById(R.id.film_year);
        TextView filmGenre = findViewById(R.id.film_genre);
        TextView filmPlot = findViewById(R.id.film_plot);
        TextView filmRating = findViewById(R.id.film_rating);
        ImageView trailerThumb = findViewById(R.id.film_trailer_thumb_iv);
        Context dataActivity = this;
        Button favBtn = findViewById(R.id.favButton);
        db = new DB(context);

        try {

            Movie.getMovieById(imdbId,new MovieById(){
                @Override
                public void getMovieById(Movie movie) throws JSONException, IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            trailerThumb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(movie.getTrailers().size() != 0)
                                    {
                                        Uri uri = Uri.parse("https://www.youtube.com/watch?v="+movie.getTrailers().get(0));
                                        startActivity(new Intent(Intent.ACTION_VIEW,uri));
                                    }
                                }
                            });
                            filmTitle.setText(movie.getTitle());
                            if(movie.getTrailers().size() != 0)
                            Glide.with(context).load("https://img.youtube.com/vi/"+ movie.getTrailers().get(0)+"/maxresdefault.jpg").error("https://img.youtube.com/vi/"+ movie.getTrailers().get(0)+"/mqdefault.jpg").into(trailerThumb);
                            filmDirector.setText(movie.getDirector());
                            filmLength.setText(movie.getMovieLength() + "min");
                            filmGenre.setText(movie.getGenres().get(0));
                            filmPlot.setText(movie.getDescription());
                            filmRating.setText(movie.getRating());
                            filmYear.setText(movie.getReleaseYear().substring(0,4));

                            if (db.checkMovieInFavorites(imdbId)) {
                                favBtn.setBackgroundResource(R.drawable.ic_red_favorite);
                            } else {
                                favBtn.setBackgroundResource(R.drawable.ic_grey_favorite);
                            }
                            favBtn.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Movie> similarMovies = new ArrayList<>();
        recyclerViewSimilarMovies = findViewById(R.id.similar_movies_rv);
        recyclerViewSimilarMovies.setHasFixedSize(true);
        recyclerViewSimilarMovies.setItemViewCacheSize(20);
        recyclerViewSimilarMovies.setDrawingCacheEnabled(true);
        recyclerViewSimilarMovies.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        try {
            RecyclerViewUpcomingMoviesAdapter upcomingMoviesAdapter = new RecyclerViewUpcomingMoviesAdapter(dataActivity);
            upcomingMoviesAdapter.setUpcomingMovies(similarMovies);
            recyclerViewSimilarMovies.setAdapter(upcomingMoviesAdapter);
            Movie.getSimilarMovies(imdbId,new SimilarMoviesI(){
                @Override
                public void IgetSimilarMovies(Movie movie) throws JSONException, IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            similarMovies.add(new Movie(movie.getTitle(), movie.getMovieLength(), movie.getSmallImageUrl(),movie.getImdbID(),null));
                            upcomingMoviesAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        recyclerViewSimilarMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkMovieInFavorites(imdbId)) {
                    favBtn.setBackgroundResource(R.drawable.ic_grey_favorite);
                    db.removeFromFavorites(imdbId);
                    Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    favBtn.setBackgroundResource(R.drawable.ic_red_favorite);
                    db.insertIntoFavorites(imdbId);
                    Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
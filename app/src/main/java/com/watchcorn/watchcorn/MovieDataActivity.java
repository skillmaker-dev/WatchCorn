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
    private DB db;
    private RecyclerView recyclerViewSimilarMovies,recyclerViewActors;

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_data);

        String imdbId;
        Bundle extras = getIntent().getExtras();

        imdbId= extras.getString("ImdbId");

        TextView watchlist = findViewById(R.id.watchList);
        TextView favorite = findViewById(R.id.favorite);
        TextView filmTitle=findViewById(R.id.film_name);
        TextView filmDirector = findViewById(R.id.film_director);
        TextView filmLength= findViewById(R.id.film_time);
        TextView filmYear = findViewById(R.id.film_year);
        TextView filmGenre = findViewById(R.id.film_genre);
        TextView filmPlot = findViewById(R.id.film_plot);
        TextView filmRating = findViewById(R.id.film_rating);
        ImageView trailerThumb = findViewById(R.id.film_trailer_thumb_iv);
        ImageView moviePoster = findViewById(R.id.filmPoster);
        Context dataActivity = this;
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
                            Glide.with(getApplicationContext()).load("https://img.youtube.com/vi/"+ movie.getTrailers().get(0)+"/maxresdefault.jpg").error("https://img.youtube.com/vi/"+ movie.getTrailers().get(0)+"/mqdefault.jpg").into(trailerThumb);
                            filmDirector.setText(movie.getDirector());
                            filmLength.setText(movie.getMovieLength() + "min");
                            filmGenre.setText(movie.getGenres().get(0));
                            filmPlot.setText(movie.getDescription());
                            filmRating.setText(movie.getRating());
                            filmYear.setText(movie.getReleaseYear().substring(0,4));
                            Glide.with(getApplicationContext()).asBitmap().load(movie.getSmallImageUrl()).error(R.drawable.coming_soon).fallback(R.drawable.coming_soon).into(moviePoster);

                            if (db.checkMovieInFavorites(imdbId)) {
                              favorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.favorite_checked, 0, 0);
                            } else {
                              favorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.favorite_unchecked, 0, 0);
                            }
                            favorite.setVisibility(View.VISIBLE);

                            if (db.checkMovieInWatchList(imdbId)) {
                                watchlist.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.watchlist_checked, 0, 0);
                            } else {
                                watchlist.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bookmark_unchecked, 0, 0);
                            }
                            watchlist.setVisibility(View.VISIBLE);
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

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkMovieInFavorites(imdbId)) {
                    favorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.favorite_unchecked, 0, 0);
                    db.removeFromFavorites(imdbId);
                    db.decrementGenreTotalSelected(filmGenre.getText().toString().toLowerCase());
                    Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    favorite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.favorite_checked, 0, 0);
                    db.insertIntoFavorites(imdbId);
                    db.incrementGenreTotalSelected(filmGenre.getText().toString().toLowerCase());
                    Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkMovieInWatchList(imdbId)) {
                    watchlist.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.bookmark_unchecked, 0, 0);
                    db.removeFromWatchList(imdbId);
                    Toast.makeText(getApplicationContext(), "Removed from watchList", Toast.LENGTH_SHORT).show();
                } else {
                    watchlist.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.watchlist_checked, 0, 0);
                    db.insertIntoWatchList(imdbId);
                    Toast.makeText(getApplicationContext(), "Added to watchList", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayList<Actor> cast = new ArrayList<>();
        recyclerViewActors = findViewById(R.id.cast_rv);
        recyclerViewActors.setHasFixedSize(true);
        recyclerViewActors.setItemViewCacheSize(20);
        recyclerViewActors.setDrawingCacheEnabled(true);
        recyclerViewActors.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        try {
            ActorsRecyclerViewAdapter actorsAdapter = new ActorsRecyclerViewAdapter(dataActivity);
            actorsAdapter.setActors(cast);
            recyclerViewActors.setAdapter(actorsAdapter);


            Actor.getCast(imdbId,new MovieCastI(){
                @Override
                public void IgetCast(Actor actor) throws JSONException, IOException {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            cast.add(new Actor(actor.getName(),actor.getPoster()));
                            actorsAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

}
package com.watchcorn.watchcorn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.IOException;

public class MovieDataActivity extends AppCompatActivity {
    public Context context = this;

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

                        }
                    });
                }

            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }

}
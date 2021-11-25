package com.watchcorn.watchcorn;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import org.json.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Movie {


    private String title;
    private String imdbID;
    private String releaseYear;
    private String rating;
    private String description;
    private String movieLength;
    private String popularity;
    private String contentRating;
    private String smallImageUrl;
    private String bigImageUrl;
    private String trailerUrl;
    private String plot;
    private ArrayList<String> genres = new ArrayList<String>();

    //Create actor class
    //private Actor actors;



    public static ArrayList<Movie> getMoviesByGenre(String genre) throws IOException {


        ArrayList<Movie> moviesList = new ArrayList<Movie>();

        return moviesList;
    }


    public static void getTopMovies(BestMovies bestMoviesResult) throws IOException, JSONException {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        String url = "https://data-imdb1.p.rapidapi.com/movie/order/byRating/?page_size=10";



        ImdbApi.callApi(url,(new Result(){
            @Override
            public void getResult(String jsonData) throws JSONException, IOException {



        String bestMoviesJson = jsonData; ; //assign your JSON String here
        JSONObject bestMoviesObj = new JSONObject(bestMoviesJson);
        String nextPage = bestMoviesObj.getString("next");

        JSONArray arr = bestMoviesObj.getJSONArray("results");


        for (int i = 0; i < arr.length(); i++)
        {
            String imdb_id = arr.getJSONObject(i).getString("imdb_id");

            String url2 = "https://data-imdb1.p.rapidapi.com/movie/id/" + imdb_id + "/";


            ImdbApi.callApi(url2,(new Result(){
                @Override
                public void getResult(String jsonData) throws JSONException, IOException {


            JSONObject MoviesObj = new JSONObject(jsonData);
            //JSONArray movieGenres = MoviesObj.getJSONArray("gen");

            Movie movie = new Movie();

             movie.title = MoviesObj.getJSONObject("results").getString("title");
             movie.imdbID = MoviesObj.getJSONObject("results").getString("imdb_id");
             movie.releaseYear = MoviesObj.getJSONObject("results").getString("year");
             movie.rating = MoviesObj.getJSONObject("results").getString("rating");
             movie.description = MoviesObj.getJSONObject("results").getString("description");
             movie.movieLength = MoviesObj.getJSONObject("results").getString("movie_length");
             movie.popularity = MoviesObj.getJSONObject("results").getString("popularity");
             movie.contentRating = MoviesObj.getJSONObject("results").getString("content_rating");
             movie.smallImageUrl = MoviesObj.getJSONObject("results").getString("image_url");
             movie.bigImageUrl = MoviesObj.getJSONObject("results").getString("banner");
             movie.trailerUrl = MoviesObj.getJSONObject("results").getString("trailer");
             movie.plot = MoviesObj.getJSONObject("results").getString("plot");
                    /*for (int j = 0; j < movieGenres.length(); j++)
                    {
                        String genre = movieGenres.getJSONObject(j).getString("genre");
                        movie.genres.add(genre);
                    }*/
             moviesList.add(movie);
                    bestMoviesResult.getBestMovies(movie);
                                                                                        }

                                            }));
        }




            }
        }));



    }










    public String getTitle() {
        return title;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getMovieLength() {
        return movieLength;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getContentRating() {
        return contentRating;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getPlot() {
        return plot;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }


}

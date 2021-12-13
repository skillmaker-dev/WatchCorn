package com.watchcorn.watchcorn;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import org.json.*;

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
    private String director;
    public ArrayList<String> genres = new ArrayList<String>();
    public ArrayList<String> trailers = new ArrayList<String>();


    //Create actor class
    //private Actor actors;


    public Movie(String title, String movieYear, String smallImageUrl,String imdbId,String fimlRating) {
        this.title = title;
        this.releaseYear = movieYear;
        this.imdbID = imdbId;
        this.smallImageUrl = smallImageUrl;
        this.rating = fimlRating;
    }

    public Movie()
    {

    }



    public static void getMoviesByTitle(String movieTitle, MoviesByTitle moviesByTitleResult) throws IOException,JSONException
    {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=c886594305fd254a4e477c9042b4d584&language=en-US&query="+ movieTitle +"&page=1&include_adult=false";

        ImdbApi.callApi(url,(new Result(){
            @Override
            public void getResult(String jsonData) throws JSONException, IOException {



                String moviesByTitle = jsonData; ; //assign your JSON String here
                JSONObject MoviesObj = new JSONObject(moviesByTitle);
                //String nextPage = bestMoviesObj.getString("next");

                JSONArray arr = MoviesObj.getJSONArray("results");


                for (int i = 0; i < arr.length(); i++)
                {

                    if(arr.getJSONObject(i).getString("video").equals("false") && !arr.getJSONObject(i).getString("vote_average").equals("0") &&
                            Integer.parseInt(arr.getJSONObject(i).getString("vote_count")) > 50) {
                        Movie movie = new Movie();
                        movie.title = arr.getJSONObject(i).getString("title");
                        movie.smallImageUrl = "https://image.tmdb.org/t/p/w300/" + arr.getJSONObject(i).getString("poster_path");
                        movie.releaseYear = arr.getJSONObject(i).getString("release_date");
                        movie.rating = arr.getJSONObject(i).getString("vote_average");


                        String id = arr.getJSONObject(i).getString("id");
                        String idsUrl = "https://api.themoviedb.org/3/movie/" + id + "/external_ids?api_key=c886594305fd254a4e477c9042b4d584";

                        ImdbApi.callApi(idsUrl, (new Result() {
                            @Override
                            public void getResult(String jsonData) throws JSONException, IOException {
                                String externalIds = jsonData;
                                ; //assign your JSON String here
                                JSONObject idsObject = new JSONObject(externalIds);
                                String imdbId = idsObject.getString("imdb_id");
                                movie.imdbID = imdbId;
                                moviesByTitleResult.getMovieByTitle(movie);

                            }
                        }));

                    }
                }




            }
        }));


    }



    public static void getSimilarMovies(String imdbID,SimilarMoviesI similarMoviesResult) throws  IOException,JSONException
    {
        String similarMoviesUrl = "https://api.themoviedb.org/3/movie/"+imdbID +"/similar?api_key=c886594305fd254a4e477c9042b4d584&language=en-US&page=1&include_adult=false";


        ImdbApi.callApi(similarMoviesUrl,(new Result(){
            @Override
            public void getResult(String jsonData) throws JSONException, IOException {



                String bestMoviesJson = jsonData; ; //assign your JSON String here
                JSONObject bestMoviesObj = new JSONObject(bestMoviesJson);
                //String nextPage = bestMoviesObj.getString("next");

                JSONArray arr = bestMoviesObj.getJSONArray("results");


                for (int i = 0; i < arr.length(); i++)
                {
                    Movie movie = new Movie();
                    movie.title = arr.getJSONObject(i).getString("title");
                    movie.smallImageUrl = "https://image.tmdb.org/t/p/original/" + arr.getJSONObject(i).getString("poster_path");
                    String id = arr.getJSONObject(i).getString("id");
                    String idsUrl = "https://api.themoviedb.org/3/movie/"+id +"/external_ids?api_key=c886594305fd254a4e477c9042b4d584";
                    ImdbApi.callApi(idsUrl,(new Result(){
                        @Override
                        public void getResult(String jsonData) throws JSONException, IOException {
                            String externalIds = jsonData; ; //assign your JSON String here
                            JSONObject idsObject = new JSONObject(externalIds);
                            String imdbId = idsObject.getString("imdb_id");
                            movie.imdbID = imdbId;
                            similarMoviesResult.IgetSimilarMovies(movie);

                        }
                    }));
                }




            }
        }));
    }


    public static void getMovieById(String imdbId,MovieById movieByIdResult) throws IOException,JSONException {
        String generalDataUrl = "https://api.themoviedb.org/3/movie/"+ imdbId +"?api_key=c886594305fd254a4e477c9042b4d584";


        ImdbApi.callTmdbApi(generalDataUrl,(new Result(){
            @Override
            public void getResult(String jsonData) throws JSONException, IOException {


                JSONObject MoviesObj = new JSONObject(jsonData);
                JSONArray movieGenres = MoviesObj.getJSONArray("genres");

                Movie movie = new Movie();

                movie.title = MoviesObj.getString("title");
                movie.imdbID = MoviesObj.getString("imdb_id");
                movie.releaseYear = MoviesObj.getString("release_date");
                movie.rating = MoviesObj.getString("vote_average");
                movie.description = MoviesObj.getString("overview");
                movie.movieLength = MoviesObj.getString("runtime");
                movie.popularity = MoviesObj.getString("popularity");
                movie.smallImageUrl = "https://image.tmdb.org/t/p/original/" + MoviesObj.getString("poster_path");
                for (int j = 0; j < movieGenres.length(); j++)
                {
                    String genre = movieGenres.getJSONObject(j).getString("name");
                    movie.genres.add(genre);
                }


                String trailerUrl = "https://api.themoviedb.org/3/movie/"+MoviesObj.getString("imdb_id")+"/videos?api_key=c886594305fd254a4e477c9042b4d584&language=en-US";
                String castUrl = "https://api.themoviedb.org/3/movie/"+MoviesObj.getString("imdb_id")+"/credits?api_key=c886594305fd254a4e477c9042b4d584&language=en-US";


                ImdbApi.callTmdbApi(trailerUrl,(new Result(){
                    @Override
                    public void getResult(String jsonData) throws JSONException, IOException {


                        JSONObject MoviesObj = new JSONObject(jsonData);
                        JSONArray movieVideos = MoviesObj.getJSONArray("results");

                        for (int j = 0; j < movieVideos.length(); j++)
                        {
                            if(movieVideos.getJSONObject(j).getString("official").equals("true") && movieVideos.getJSONObject(j).getString("type").equals("Trailer"))
                            {
                                String videoUrl = movieVideos.getJSONObject(j).getString("key");

                                movie.trailers.add(videoUrl);
                            }
                            else if(movieVideos.getJSONObject(j).getString("type").equals("Trailer"))
                            {
                                String videoUrl = movieVideos.getJSONObject(j).getString("key");

                                movie.trailers.add(videoUrl);
                            }

                        }




                        ImdbApi.callTmdbApi(castUrl,(new Result(){
                            @Override
                            public void getResult(String jsonData) throws JSONException, IOException {


                                JSONObject MoviesObj = new JSONObject(jsonData);
                                JSONArray movieCast = MoviesObj.getJSONArray("crew");

                                for (int j = 0; j < movieCast.length(); j++)
                                {
                                    if(movieCast.getJSONObject(j).has("department"))
                                    {
                                        if(movieCast.getJSONObject(j).getString("known_for_department").equals("Directing") && movieCast.getJSONObject(j).getString("department").equals("Directing") && movieCast.getJSONObject(j).getString("job").equals("Director"))
                                        {

                                            movie.director = movieCast.getJSONObject(j).getString("name");
                                        }
                                    }


                                }
                                movieByIdResult.getMovieById(movie);
                            }

                        }));
                    }

                }));










            }

        }));




    }







    public static void getBestMovies (BestMovies bestMoviesResult) throws IOException, JSONException {
        String urlTmdb = "https://api.themoviedb.org/3/movie/popular?api_key=c886594305fd254a4e477c9042b4d584&language=en-US&page=1&include_adult=false";


        ImdbApi.callApi(urlTmdb,(new Result(){
            @Override
            public void getResult(String jsonData) throws JSONException, IOException {



                String bestMoviesJson = jsonData; ; //assign your JSON String here
                JSONObject bestMoviesObj = new JSONObject(bestMoviesJson);
                //String nextPage = bestMoviesObj.getString("next");

                JSONArray arr = bestMoviesObj.getJSONArray("results");


                for (int i = 0; i < arr.length(); i++)
                {
                    Movie movie = new Movie();
                    movie.title = arr.getJSONObject(i).getString("title");
                    movie.smallImageUrl = "https://image.tmdb.org/t/p/original/" + arr.getJSONObject(i).getString("poster_path");
                    String id = arr.getJSONObject(i).getString("id");
                    String idsUrl = "https://api.themoviedb.org/3/movie/"+id +"/external_ids?api_key=c886594305fd254a4e477c9042b4d584";

                    ImdbApi.callApi(idsUrl,(new Result(){
                        @Override
                        public void getResult(String jsonData) throws JSONException, IOException {
                            String externalIds = jsonData; ; //assign your JSON String here
                            JSONObject idsObject = new JSONObject(externalIds);
                            String imdbId = idsObject.getString("imdb_id");
                            movie.imdbID = imdbId;
                            bestMoviesResult.getBestMovies(movie);

                        }
                    }));
                }




            }
        }));



    }


    public static void getMoviesByGenre (String genre,MoviesByGenre moviesByGenreResult) throws IOException, JSONException {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        String url = "https://data-imdb1.p.rapidapi.com/movie/byGen/" + genre + "/?page_size=100";



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
                            JSONArray movieGenres = MoviesObj.getJSONObject("results").getJSONArray("gen");

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
                            for (int j = 0; j < movieGenres.length(); j++)
                            {
                                String genre = movieGenres.getJSONObject(j).getString("genre");
                                movie.genres.add(genre);
                            }
                            moviesList.add(movie);
                            moviesByGenreResult.igetMoviesByGenre(movie);
                        }

                    }));
                }




            }
        }));



    }



    public static void getMoviesByGenres (String[] genre,BestMovies bestMoviesResult) throws IOException, JSONException {
        ArrayList<Movie> moviesList = new ArrayList<Movie>();
        String url = "https://data-imdb1.p.rapidapi.com/movie/byGen/" + genre + "/?page_size=100";



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
                            JSONArray movieGenres = MoviesObj.getJSONObject("results").getJSONArray("gen");

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
                            for (int j = 0; j < movieGenres.length(); j++)
                            {
                                String genre = movieGenres.getJSONObject(j).getString("genre");
                                movie.genres.add(genre);
                            }
                            moviesList.add(movie);
                            bestMoviesResult.getBestMovies(movie);
                        }

                    }));
                }




            }
        }));



    }


    public static void getUpcomingMovies (UpcomingMovies upcomingMoviesResult) throws IOException, JSONException {
        String urlTmdb = "https://api.themoviedb.org/3/movie/upcoming?api_key=c886594305fd254a4e477c9042b4d584&language=en-US&page=1&include_adult=false";


        ImdbApi.callApi(urlTmdb,(new Result(){
            @Override
            public void getResult(String jsonData) throws JSONException, IOException {



                String bestMoviesJson = jsonData; ; //assign your JSON String here
                JSONObject bestMoviesObj = new JSONObject(bestMoviesJson);

                JSONArray arr = bestMoviesObj.getJSONArray("results");


                for (int i = 0; i < arr.length(); i++)
                {
                    String id = arr.getJSONObject(i).getString("id");
                    String idsUrl = "https://api.themoviedb.org/3/movie/"+id +"/external_ids?api_key=c886594305fd254a4e477c9042b4d584";
                    Movie movie = new Movie();
                    movie.title = arr.getJSONObject(i).getString("title");
                    movie.smallImageUrl = "https://image.tmdb.org/t/p/original/" + arr.getJSONObject(i).getString("poster_path");

                    ImdbApi.callApi(idsUrl,(new Result(){
                        @Override
                        public void getResult(String jsonData) throws JSONException, IOException {
                            String externalIds = jsonData; ; //assign your JSON String here
                            JSONObject idsObject = new JSONObject(externalIds);
                            String imdbId = idsObject.getString("imdb_id");
                            movie.imdbID = imdbId;

                            upcomingMoviesResult.igetUpcomingMovies(movie);
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

    public ArrayList<String> getTrailers() {
        return trailers;
    }


    public String getDirector() {
        return director;
    }
}

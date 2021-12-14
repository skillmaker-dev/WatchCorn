package com.watchcorn.watchcorn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Actor {

    public Actor()
    {

    }

    public static void getCast(String imdbID,MovieCastI movieCastResult) throws  IOException,JSONException
    {
        String movieCastUrl = "https://api.themoviedb.org/3/movie/"+imdbID+"/credits?api_key=c886594305fd254a4e477c9042b4d584&language=en-US";


        ImdbApi.callApi(movieCastUrl,(new Result(){
            @Override
            public void getResult(String jsonData) throws JSONException, IOException {



                String castJson = jsonData; ; //assign your JSON String here
                JSONObject castObj = new JSONObject(castJson);
                //String nextPage = bestMoviesObj.getString("next");

                JSONArray arr = castObj.getJSONArray("cast");


                for (int i = 0; i < arr.length(); i++)
                {
                    Actor actor = new Actor();
                    actor.name = arr.getJSONObject(i).getString("name");
                    actor.poster = "https://image.tmdb.org/t/p/original/" + arr.getJSONObject(i).getString("profile_path");

                    movieCastResult.IgetCast(actor);
                }


            }
        }));
    }

    public Actor(String name,String poster)
    {
        this.name = name;
        this.poster = poster;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    private String name;
    private String poster;
}

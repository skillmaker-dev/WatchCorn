package com.watchcorn.watchcorn;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImdbApi {

    public static void callApi(String api_url,final Result result) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(api_url)
                .get()
                .addHeader("x-rapidapi-host", "data-imdb1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "2f2c08fb2dmshceab1376d3663a9p1740a4jsnfef5f13c551d")
                .build();


        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {



            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData =  response.body().string();
                    try {
                        result.getResult(jsonData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        });

    }



    public static String callBestMovies() throws IOException {
        String url = "https://data-imdb1.p.rapidapi.com/movie/order/byRating/?page_size=10";
        final String[] data = new String[1];

        callApi(url,(new Result(){
            @Override
            public void getResult(String jsonData){

            }
        }));

        String lastData = data[0];

        if(!lastData.equals("error"))
        {
            return lastData;
        }
        return null;
    }

    public static String callMoviesByImdbId(String id) throws IOException
    {
        String url = "https://data-imdb1.p.rapidapi.com/movie/id/" + id + "/";
        final String[] data = new String[1];

        callApi(url,(new Result(){
            @Override
            public void getResult(String jsonData){
                data[0] = jsonData;
            }
        }));
        String lastData = data[0];
        if(!lastData.equals("error"))
        {
            return lastData;
        }
        return null;
    }


}

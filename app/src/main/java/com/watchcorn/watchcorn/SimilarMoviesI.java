package com.watchcorn.watchcorn;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface SimilarMoviesI {
    public void IgetSimilarMovies(Movie movie) throws JSONException, IOException;

}
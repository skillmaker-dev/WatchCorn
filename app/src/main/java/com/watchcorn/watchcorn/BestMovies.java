package com.watchcorn.watchcorn;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface BestMovies{
    public void getBestMovies(Movie movie) throws JSONException, IOException;
}
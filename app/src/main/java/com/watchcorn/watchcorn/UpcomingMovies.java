package com.watchcorn.watchcorn;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface UpcomingMovies {
    public void igetUpcomingMovies(Movie movie) throws JSONException, IOException;

}
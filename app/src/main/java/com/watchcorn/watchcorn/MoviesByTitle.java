package com.watchcorn.watchcorn;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public interface MoviesByTitle {
    public void getMovieByTitle(Movie movie) throws JSONException, IOException;
}
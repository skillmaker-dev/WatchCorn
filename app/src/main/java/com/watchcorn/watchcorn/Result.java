package com.watchcorn.watchcorn;

import org.json.JSONException;

import java.io.IOException;

public interface Result{
    public void getResult(String id) throws JSONException, IOException;
}
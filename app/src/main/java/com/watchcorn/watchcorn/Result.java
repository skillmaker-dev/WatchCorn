package com.watchcorn.watchcorn;

import org.json.JSONException;

import java.io.IOException;

public interface Result{
    public void getResult(String data) throws JSONException, IOException;
}
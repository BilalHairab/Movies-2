package com.bilal.movies.utils;

import com.bilal.movies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilal on 22/02/2018.
 */

public class JsonUtils {
    private final static String RESULTS_KEY = "results";
    private final static String POSTER_KEY = "poster_path";
    private final static String ID_KEY = "id";

    public static List<Movie> jsonString2MoviesList(String string) throws JSONException {
        List<Movie> list = new ArrayList<>();
        JSONObject totalJsonResult = new JSONObject(string);
        JSONArray moviesJsonArray = totalJsonResult.getJSONArray(RESULTS_KEY);
        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject jsonMovie = moviesJsonArray.getJSONObject(i);
            list.add(new Movie(jsonMovie.getString(ID_KEY), jsonMovie.getString(POSTER_KEY)));
        }
        return list;
    }
}

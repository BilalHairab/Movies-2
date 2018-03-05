package com.bilal.movies.models;

import java.util.ArrayList;

/**
 * Created by Bilal on 02/03/2018.
 */

public class MovieResult {
    private ArrayList<Movie> results;

    public MovieResult(ArrayList<Movie> results) {
        this.results = results;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }
}

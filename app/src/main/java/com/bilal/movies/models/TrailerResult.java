package com.bilal.movies.models;

import java.util.ArrayList;

/**
 * Created by Bilal on 02/03/2018.
 */

public class TrailerResult {
    private ArrayList<Trailer> results;

    public TrailerResult(ArrayList<Trailer> results) {
        this.results = results;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }
}

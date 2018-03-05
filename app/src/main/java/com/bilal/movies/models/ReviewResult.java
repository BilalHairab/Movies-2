package com.bilal.movies.models;

import java.util.ArrayList;

/**
 * Created by Bilal on 02/03/2018.
 */

public class ReviewResult {
    private ArrayList<Review> results;

    public ReviewResult(ArrayList<Review> results) {
        this.results = results;
    }

    public ArrayList<Review> getResults() {
        return results;
    }
}

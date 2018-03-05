package com.bilal.movies.models;

/**
 * Created by Bilal on 05/03/2018.
 */

public class Trailer {
    private String key;
    private final static String BASE_YOUTUBE = "https://www.youtube.com/watch?v=";

    public Trailer(String key) {
        this.key = key;
    }

    public String getYouTubeKey() {
        return BASE_YOUTUBE + key;
    }

}

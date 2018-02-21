package com.bilal.movies.models;

/**
 * Created by Bilal on 21/02/2018.
 */

public class Movie {
    private String title, thumbUrl, overView, releaseDate;
    private float voteAvg;

    public Movie(String title, String thumbUrl, String overView, String releaseDate, float voteAvg) {
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.overView = overView;
        this.releaseDate = releaseDate;
        this.voteAvg = voteAvg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(float voteAvg) {
        this.voteAvg = voteAvg;
    }
}

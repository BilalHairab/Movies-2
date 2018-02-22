package com.bilal.movies.models;

/**
 * Created by Bilal on 21/02/2018.
 */

public class Movie {
    private String id, title, thumbUrl, overView, releaseDate;
    private float voteAvg;

    public Movie(String id, String thumbUrl) {
        this.id = id;
        this.thumbUrl = thumbUrl;
    }

    public Movie(String id, String title, String thumbUrl, String overView, String releaseDate, float voteAvg) {
        this.id = id;
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.overView = overView;
        this.releaseDate = releaseDate;
        this.voteAvg = voteAvg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

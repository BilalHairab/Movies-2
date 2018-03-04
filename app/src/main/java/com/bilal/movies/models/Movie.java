package com.bilal.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bilal on 21/02/2018.
 */

public class Movie implements Parcelable {
    private int vote_count, id;
    private boolean video, adult;
    private String title, poster_path, overview, original_language, original_title, release_date, backdrop_path;
    private double vote_average, popularity;
    private int[] genre_ids;

    public Movie() {

    }

    private Movie(Parcel parcel) {
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.poster_path = parcel.readString();
        this.overview = parcel.readString();
        this.vote_average = parcel.readDouble();
        this.release_date = parcel.readString();
    }

    public Movie(int vote_count, int id, boolean video, boolean adult, String title, String poster_path, String overview, String original_language, String original_title, String release_date, String backdrop_path, double vote_average, double popularity, int[] genre_ids) {
        this.vote_count = vote_count;
        this.id = id;
        this.video = video;
        this.adult = adult;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.original_language = original_language;
        this.original_title = original_title;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.genre_ids = genre_ids;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
        dest.writeString(release_date);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };
}

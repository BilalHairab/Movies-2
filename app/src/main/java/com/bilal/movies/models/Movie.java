package com.bilal.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bilal on 21/02/2018.
 */

public class Movie implements Parcelable {
    private String id, title, thumbUrl, overView, releaseDate;
    private double voteAvg;

    private Movie(Parcel parcel) {
        this.id = parcel.readString();
        this.title = parcel.readString();
        this.thumbUrl = parcel.readString();
        this.overView = parcel.readString();
        this.voteAvg = parcel.readDouble();
        this.releaseDate = parcel.readString();
    }

    public Movie(String id, String title, String thumbUrl, String overView, String releaseDate, double voteAvg) {
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

    public String getOverView() {
        return overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAvg() {
        return voteAvg;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(thumbUrl);
        dest.writeString(overView);
        dest.writeDouble(voteAvg);
        dest.writeString(releaseDate);
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

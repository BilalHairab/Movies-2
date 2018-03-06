package com.bilal.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bilal on 05/03/2018.
 */

public class Trailer implements Parcelable {
    private String key;
    private final static String BASE_YOUTUBE = "https://www.youtube.com/watch?v=";

    private Trailer(Parcel parcel) {
        this.key = parcel.readString();
    }

    public Trailer(String key) {
        this.key = key;
    }

    public String getYouTubeKey() {
        return BASE_YOUTUBE + key;
    }
    public static Creator<Trailer> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int i) {
            return new Trailer[i];
        }

    };

}

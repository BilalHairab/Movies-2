package com.bilal.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Bilal on 04/03/2018.
 */

public class FavoriteContract {
    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_MOVIE_POSTER = "movie_poster";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String AUTHORITY = "com.bilal.movies";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://com.bilal.movies/" + TABLE_NAME);

    }
}

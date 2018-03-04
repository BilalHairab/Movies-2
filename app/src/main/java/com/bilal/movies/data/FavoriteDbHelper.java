package com.bilal.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bilal on 04/03/2018.
 */

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "movies.db";
    private final static int DATABASE_VERSION = 1;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " ("
                + FavoriteContract.FavoriteEntry.COLUMN_ID + " INTEGER PRIMARY KEY,"
                + FavoriteContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + FavoriteContract.FavoriteEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL, "
                + FavoriteContract.FavoriteEntry.COLUMN_USER_RATING + " FLOAT NOT NULL, "
                + FavoriteContract.FavoriteEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL, "
                + FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL"
                + ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }

}

package com.bilal.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.AUTHORITY;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.BASE_CONTENT_URI;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.TABLE_NAME;

@SuppressWarnings("ALL")
public class FavoritesProvider extends ContentProvider {

    static final int FAVORITES_CODE = 100;
    static final int SINGLE_FAVORITE_CODE = 10;
    private final static UriMatcher matcher = buildMatcher();
    private static FavoriteDbHelper favoriteDbHelper;

    public FavoritesProvider() {
    }

    @Override
    public boolean onCreate() {
        favoriteDbHelper = new FavoriteDbHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase sqLiteDatabase = favoriteDbHelper.getWritableDatabase();
        int match = matcher.match(uri);
        switch (match) {
            case FAVORITES_CODE:
                long id = sqLiteDatabase.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    //noinspection ConstantConditions
                    getContext().getContentResolver().notifyChange(uri, null);
                    return ContentUris.withAppendedId(BASE_CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            default:
                throw new UnsupportedOperationException("unknown uri" + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = favoriteDbHelper.getWritableDatabase();
        int match = matcher.match(uri);
        switch (match) {
            case FAVORITES_CODE:
                long id = sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);
                if (id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return (int) id;
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            default:
                throw new UnsupportedOperationException("unknown uri" + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase sqLiteDatabase = favoriteDbHelper.getReadableDatabase();
        int match = matcher.match(uri);
        Cursor result;
        switch (match) {
            case FAVORITES_CODE:
                result = sqLiteDatabase.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                result.setNotificationUri(getContext().getContentResolver(), uri);
                return result;
            case SINGLE_FAVORITE_CODE:
                String id = uri.getPathSegments().get(1);
                String mSelection = "id=?";
                String[] mSelectionArgs = new String[]{id};
                result = sqLiteDatabase.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                result.setNotificationUri(getContext().getContentResolver(), uri);
                return result;
            default:
                throw new UnsupportedOperationException("unknown uri" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = favoriteDbHelper.getWritableDatabase();
        int match = matcher.match(uri);
        switch (match) {
            case FAVORITES_CODE:
                long id = sqLiteDatabase.update(TABLE_NAME, values, selection, selectionArgs);
                if (id > 0) {
                    //noinspection ConstantConditions
                    getContext().getContentResolver().notifyChange(uri, null);
                    return (int) id;
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            default:
                throw new UnsupportedOperationException("unknown uri" + uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static UriMatcher buildMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, TABLE_NAME, FAVORITES_CODE);
        matcher.addURI(AUTHORITY, TABLE_NAME +"/#", SINGLE_FAVORITE_CODE);
        return matcher;
    }
}

package com.bilal.movies.activities;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bilal.movies.R;
import com.bilal.movies.adapters.MainMoviesAdapter;
import com.bilal.movies.data.FavoriteContract;
import com.bilal.movies.interfaces.MoviesCalls;
import com.bilal.movies.models.Movie;
import com.bilal.movies.models.Result;
import com.bilal.movies.utils.MoviesAPIContract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;

    final static String SORT = "SORT_TYPE";
    static final int LOADER_ID = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(R.string.sort_popular);
        loadMovies(MoviesAPIContract.SORT_TYPES.popular);
    }

    void loadMovies(MoviesAPIContract.SORT_TYPES sortType) {
        LoaderManager loaderManager = getSupportLoaderManager();
        Bundle args = new Bundle();
        args.putSerializable(SORT, sortType);
        Loader<String> loader = loaderManager.getLoader(LOADER_ID);
        if (loader == null)
            loaderManager.initLoader(LOADER_ID, args, this).forceLoad();
        else
            loaderManager.restartLoader(LOADER_ID, args, this).forceLoad();
    }

    @NonNull
    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(this) {
            Retrofit retrofit;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }

            @Nullable
            @Override
            public List<Movie> loadInBackground() {
                if (args.get(SORT) != MoviesAPIContract.SORT_TYPES.favorites) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(MoviesAPIContract.MOVIES_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    MoviesCalls calls = retrofit.create(MoviesCalls.class);
                    Call<Result> call = calls.fetchMovies((MoviesAPIContract.SORT_TYPES) args.get(SORT), MoviesAPIContract.API_KEY_VALUE);
                    try {
                        Result result = call.execute().body();
                        if (result != null)
                            return result.getResults();
                        else
                            return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    List<Movie> movies = new ArrayList<>();
                    Cursor cursor = getContentResolver().query(FavoriteContract.FavoriteEntry.BASE_CONTENT_URI,
                            null, null, null, null);
                    if (cursor != null)
                        while (cursor.moveToNext()) {
                            Movie movie = new Movie();
                            movie.setId(cursor.getInt(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_ID)));
                            movie.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE)));
                            movie.setPoster_path(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIE_POSTER)));
                            movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_USER_RATING)));
                            movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_SYNOPSIS)));
                            movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_SYNOPSIS)));
                            movie.setRelease_date(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE)));
                            movies.add(movie);
                        }
                    return movies;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        if (data == null || data.size() == 0) {
            Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show();
            return;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        MainMoviesAdapter adapter = new MainMoviesAdapter(data);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popular:
                setTitle(R.string.sort_popular);
                loadMovies(MoviesAPIContract.SORT_TYPES.popular);
                break;
            case R.id.action_sort_rated:
                setTitle(R.string.sort_rated);
                loadMovies(MoviesAPIContract.SORT_TYPES.top_rated);
                break;
            case R.id.action_sort_favorite:
                setTitle(R.string.sort_favorite);
                loadMovies(MoviesAPIContract.SORT_TYPES.favorites);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

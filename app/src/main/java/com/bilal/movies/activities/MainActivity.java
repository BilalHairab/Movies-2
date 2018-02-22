package com.bilal.movies.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bilal.movies.R;
import com.bilal.movies.adapters.MainMoviesAdapter;
import com.bilal.movies.models.Movie;
import com.bilal.movies.utils.JsonUtils;
import com.bilal.movies.utils.MoviesAPIContract;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;

    @BindView(R.id.sr_movies)
    SwipeRefreshLayout swipeRefreshLayout;

    static final int LOADER_ID = 7;
    String sortType = MoviesAPIContract.POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(R.string.sort_popular);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMovies();
            }
        });
        loadMovies();
    }

    void loadMovies() {
        Log.e("Movies", "dfdfd");
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(LOADER_ID);
        if (loader == null)
            loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
        else
            loaderManager.restartLoader(LOADER_ID, null, this).forceLoad();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.e("MoviesStart", "start");
            }

            @Nullable
            @Override
            public String loadInBackground() {
                Log.e("MoviesStart", "back");
                Uri uri = Uri.parse(MoviesAPIContract.MOVIES_BASE_URL + sortType).buildUpon()
                        .appendQueryParameter(MoviesAPIContract.API_KEY, MoviesAPIContract.API_KEY_VALUE).build();
                try {
                    URL url = new URL(uri.toString());
                    Log.e("MoviesUrl", url.toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    String result = "";
                    while (scanner.hasNext())
                        result += scanner.next();
                    return result;
                } catch (IOException e) {
                    Log.e("MoviesError", e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };
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
                sortType = MoviesAPIContract.POPULAR;
                loadMovies();
                break;
            case R.id.action_sort_rated:
                setTitle(R.string.sort_rated);
                sortType = MoviesAPIContract.TOP_RATED;
                loadMovies();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        if (data == null || data.contentEquals("")) {
            Toast.makeText(this, R.string.error_loading, Toast.LENGTH_LONG).show();
            return;
        }
        try {
            List<Movie> movieList = JsonUtils.jsonString2MoviesList(data);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            MainMoviesAdapter adapter = new MainMoviesAdapter(movieList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}

package com.bilal.movies.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilal.movies.R;
import com.bilal.movies.adapters.ReviewsAdapter;
import com.bilal.movies.adapters.TrailersAdapter;
import com.bilal.movies.data.FavoriteContract;
import com.bilal.movies.interfaces.MoviesCalls;
import com.bilal.movies.models.Movie;
import com.bilal.movies.models.Review;
import com.bilal.movies.models.ReviewResult;
import com.bilal.movies.models.Trailer;
import com.bilal.movies.models.TrailerResult;
import com.bilal.movies.utils.MoviesAPIContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.BASE_CONTENT_URI;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_ID;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_MOVIE_POSTER;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_SYNOPSIS;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_TITLE;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_USER_RATING;

public class MovieDetailActivity extends AppCompatActivity {
    Movie movie;
    String firstTrailerUri;

    @BindView(R.id.tv_released)
    TextView tvReleased;

    @BindView(R.id.tv_rating_avg)
    TextView tvRating;

    @BindView(R.id.tv_overview)
    TextView tvOverview;

    @BindView(R.id.im_movie_poster)
    ImageView poster;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    boolean isFavorite = false;

    @BindView(R.id.rv_reviews)
    RecyclerView recyclerReviews;

    @BindView(R.id.rv_trailers)
    RecyclerView recyclerTrailers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(MoviesAPIContract.MOVIE)) {
            //noinspection ConstantConditions
            movie = getIntent().getExtras().getParcelable(MoviesAPIContract.MOVIE);
        } else if (savedInstanceState.containsKey(MoviesAPIContract.MOVIE))
            movie = savedInstanceState.getParcelable(MoviesAPIContract.MOVIE);
        else finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (movie != null) {
            setTitle(movie.getTitle());
            tvReleased.append(movie.getRelease_date());
            tvRating.setText(String.valueOf(movie.getVote_average()));
            tvOverview.setText(movie.getOverview());
            Picasso.with(this).load(MoviesAPIContract.POSTERS_BASE_URL + movie.getPoster_path())
                    .placeholder(R.drawable.movie_placeholder).into(poster);
            Cursor cursor = getContentResolver().query(ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.BASE_CONTENT_URI,
                    (long) movie.getId()), null, null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                isFavorite = true;
                fab.setImageResource(R.drawable.ic_unfavorite);
            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isFavorite) {
                        unFavorite();
                    } else {
                        markAsFavorite();
                    }
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesAPIContract.MOVIES_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MoviesCalls calls = retrofit.create(MoviesCalls.class);
            Call<TrailerResult> trailerCall = calls.fetchTrailers(movie.getId(), MoviesAPIContract.API_KEY_VALUE);
            trailerCall.enqueue(new Callback<TrailerResult>() {
                @Override
                public void onResponse(@NonNull Call<TrailerResult> call, @NonNull Response<TrailerResult> response) {
                    TrailerResult body = response.body();
                    if (body != null) {
                        ArrayList<Trailer> trailers = body.getResults();
                        firstTrailerUri = trailers.get(0).getYouTubeKey();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetailActivity.this,
                                LinearLayoutManager.VERTICAL, false);
                        TrailersAdapter adapter = new TrailersAdapter(trailers);
                        recyclerTrailers.setLayoutManager(layoutManager);
                        recyclerTrailers.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TrailerResult> call, @NonNull Throwable t) {

                }
            });
            Call<ReviewResult> reviewCall = calls.fetchReviews(movie.getId(), MoviesAPIContract.API_KEY_VALUE);
            reviewCall.enqueue(new Callback<ReviewResult>() {
                @Override
                public void onResponse(@NonNull Call<ReviewResult> call, @NonNull Response<ReviewResult> response) {
                    ReviewResult body = response.body();
                    if (body != null) {
                        ArrayList<Review> reviews = body.getResults();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetailActivity.this,
                                LinearLayoutManager.VERTICAL, false);
                        ReviewsAdapter adapter = new ReviewsAdapter(reviews);
                        recyclerReviews.setLayoutManager(layoutManager);
                        recyclerReviews.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReviewResult> call, @NonNull Throwable t) {

                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MoviesAPIContract.MOVIE, movie);
        super.onSaveInstanceState(outState);
    }

    private void markAsFavorite() {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, movie.getId());
        cv.put(COLUMN_TITLE, movie.getTitle());
        cv.put(COLUMN_MOVIE_POSTER, movie.getPoster_path());
        cv.put(COLUMN_USER_RATING, movie.getVote_average());
        cv.put(COLUMN_SYNOPSIS, movie.getOverview());
        cv.put(COLUMN_RELEASE_DATE, movie.getRelease_date());
        try {
            getContentResolver().insert(BASE_CONTENT_URI, cv);
            Toast.makeText(MovieDetailActivity.this, getString(R.string.insert_success), Toast.LENGTH_LONG).show();
            isFavorite = true;
            fab.setImageResource(R.drawable.ic_unfavorite);
        } catch (SQLException ex) {
            Toast.makeText(MovieDetailActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void unFavorite() {
        try {
            getContentResolver().delete(ContentUris.withAppendedId(BASE_CONTENT_URI, (long) movie.getId()), null, null);
            Toast.makeText(MovieDetailActivity.this, getString(R.string.delete_success), Toast.LENGTH_LONG).show();
            isFavorite = false;
            fab.setImageResource(R.drawable.ic_favorite);
        } catch (SQLException ex) {
            Toast.makeText(MovieDetailActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (firstTrailerUri != null)
                    ShareCompat.IntentBuilder.from(this)
                            .setChooserTitle(getString(R.string.share_title))
                            .setType("text/plain")
                            .setText(getString(R.string.share_text) + firstTrailerUri)
                            .startChooser();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

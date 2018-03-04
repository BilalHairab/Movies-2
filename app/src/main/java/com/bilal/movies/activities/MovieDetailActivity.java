package com.bilal.movies.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilal.movies.R;
import com.bilal.movies.data.FavoriteContract;
import com.bilal.movies.models.Movie;
import com.bilal.movies.utils.MoviesAPIContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.BASE_CONTENT_URI;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_ID;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_MOVIE_POSTER;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_SYNOPSIS;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_TITLE;
import static com.bilal.movies.data.FavoriteContract.FavoriteEntry.COLUMN_USER_RATING;

public class MovieDetailActivity extends AppCompatActivity {
    Movie movie;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(MoviesAPIContract.MOVIE)) {
            //noinspection ConstantConditions
            movie = getIntent().getExtras().getParcelable(MoviesAPIContract.MOVIE);
        }
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
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_ID, movie.getId());
                    cv.put(COLUMN_TITLE, movie.getTitle());
                    cv.put(COLUMN_MOVIE_POSTER, movie.getPoster_path());
                    cv.put(COLUMN_USER_RATING, movie.getVote_average());
                    cv.put(COLUMN_SYNOPSIS, movie.getOverview());
                    cv.put(COLUMN_RELEASE_DATE, movie.getRelease_date());
                    try {
                        Toast.makeText(MovieDetailActivity.this, getContentResolver().insert(BASE_CONTENT_URI, cv).toString(), Toast.LENGTH_LONG).show();

                    } catch (SQLException ex) {
                        Toast.makeText(MovieDetailActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}

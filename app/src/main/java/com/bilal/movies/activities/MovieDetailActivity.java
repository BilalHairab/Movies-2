package com.bilal.movies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bilal.movies.R;
import com.bilal.movies.models.Movie;
import com.bilal.movies.utils.MoviesAPIContract;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Movie currentMovie = getIntent().getExtras().getParcelable(MoviesAPIContract.ID);
        setTitle(currentMovie.getTitle());
        Log.e("Movies", currentMovie.getOverView());
        Log.e("Movies", currentMovie.getVoteAvg() + "");
        Log.e("Movies", currentMovie.getId());
        Log.e("Movies", currentMovie.getReleaseDate());
    }
}

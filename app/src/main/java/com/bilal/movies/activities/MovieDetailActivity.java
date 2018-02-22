package com.bilal.movies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilal.movies.R;
import com.bilal.movies.models.Movie;
import com.bilal.movies.utils.MoviesAPIContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        if (getIntent().hasExtra(MoviesAPIContract.MOVIE)) {
            movie = getIntent().getExtras().getParcelable(MoviesAPIContract.MOVIE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (movie != null) {
            setTitle(movie.getTitle());
            tvReleased.append(movie.getReleaseDate());
            tvRating.setText(movie.getVoteAvg() + "");
            tvOverview.setText(movie.getOverView());
            Picasso.with(this).load(MoviesAPIContract.POSTERS_BASE_URL + movie.getThumbUrl())
                    .placeholder(R.drawable.movie_placeholder).into(poster);
        }
    }
}

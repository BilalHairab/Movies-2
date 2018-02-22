package com.bilal.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bilal.movies.activities.MovieDetailActivity;
import com.bilal.movies.R;
import com.bilal.movies.models.Movie;
import com.bilal.movies.utils.MoviesAPIContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bilal on 21/02/2018.
 */

public class MainMoviesAdapter extends RecyclerView.Adapter<MainMoviesAdapter.MovieHolder> {
    private Context context;
    private List<Movie> movies = new ArrayList<>();
    private OnMovieClickListener onMovieClickListener = new OnMovieClickListener() {
        @Override
        public void onMovieClick(int position) {
            Intent toDetailActivity = new Intent(context, MovieDetailActivity.class);
            toDetailActivity.putExtra(MoviesAPIContract.ID, movies.get(position).getId());
            context.startActivity(toDetailActivity);
        }
    };

    public MainMoviesAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Picasso.with(context).load(MoviesAPIContract.POSTERS_BASE_URL + movies.get(position).getThumbUrl())
                .placeholder(R.drawable.movie_placeholder).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.im_movie_poster)
        ImageView imageView;

        MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onMovieClickListener.onMovieClick(position);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(int position);
    }
}

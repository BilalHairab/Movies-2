package com.bilal.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilal.movies.adapters.MainMoviesAdapter;
import com.bilal.movies.models.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;
    List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        MainMoviesAdapter adapter = new MainMoviesAdapter(movieList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

package com.bilal.movies.interfaces;

import com.bilal.movies.models.Result;
import com.bilal.movies.utils.MoviesAPIContract;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bilal-Laptop on 02/03/2018.
 */

public interface MoviesCalls {
    @GET("{sortType}/")
    Call<Result> fetchMovies(@Path("sortType") MoviesAPIContract.SORT_TYPES sortType,
                             @Query(MoviesAPIContract.API_KEY) String api);

}

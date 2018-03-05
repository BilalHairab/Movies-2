package com.bilal.movies.interfaces;

import com.bilal.movies.models.MovieResult;
import com.bilal.movies.models.ReviewResult;
import com.bilal.movies.models.TrailerResult;
import com.bilal.movies.utils.MoviesAPIContract;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bilal on 02/03/2018.
 */

public interface MoviesCalls {
    @GET("{sortType}/")
    Call<MovieResult> fetchMovies(@Path("sortType") MoviesAPIContract.SORT_TYPES sortType,
                                  @Query(MoviesAPIContract.API_KEY) String api);

    @GET("{id}/videos")
    Call<TrailerResult> fetchTrailers(@Path("id") int id,
                                      @Query(MoviesAPIContract.API_KEY) String api);

    @GET("{id}/reviews")
    Call<ReviewResult> fetchReviews(@Path("id") int id,
                                    @Query(MoviesAPIContract.API_KEY) String api);

}

package com.dpelluzi.moviedbexample.interfaces;

import com.dpelluzi.moviedbexample.models.MovieListResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbApi {

    @GET("/{version}/movie/now_playing")
    Single<MovieListResult> getNowPlayingMovies(@Path("version") int version, @Query("api_key") String apiKey);

    @GET("/{version}/search/movie")
    Single<MovieListResult> searchMovie(@Path("version") int version, @Query("api_key") String apiKey,
                                        @Query("query") String query);
}

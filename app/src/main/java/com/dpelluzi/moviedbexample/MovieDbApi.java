package com.dpelluzi.moviedbexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDbApi {

    @GET("/{version}/movie/now_playing")
    Call<MovieListResult> getNowPlayingMovies(@Path("version") int version, @Query("api_key") String apiKey);
}

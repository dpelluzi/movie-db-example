package com.dpelluzi.moviedbexample;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    public interface GetMoviesCallback {
        void onSuccess(List<Movie> movies);
        void onError();
    }

    private static final String URL_MOVIE_API = "https://api.themoviedb.org";
    private static MovieRepository sRepository;

    private MovieDbApi mMovieDbApi;

    public static MovieRepository getInstance() {
        if (sRepository == null) {
            sRepository = new MovieRepository();
        }

        return sRepository;
    }

    private MovieRepository() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_MOVIE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMovieDbApi = retrofit.create(MovieDbApi.class);
    }

    public void getNowPlayingMovies(final GetMoviesCallback callback) {
        mMovieDbApi.getNowPlayingMovies(BuildConfig.MOVIE_DB_API_VER, BuildConfig.MOVIE_DB_API)
                .enqueue(new Callback<MovieListResult>() {
            @Override
            public void onResponse(Call<MovieListResult> call, Response<MovieListResult> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().results);
                } else {
                    callback.onError();
                }

            }

            @Override
            public void onFailure(Call<MovieListResult> call, Throwable t) {
                callback.onError();
            }
        });
    }

}

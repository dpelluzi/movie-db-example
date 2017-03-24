package com.dpelluzi.moviedbexample;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    private static final String URL_MOVIE_API = "https://api.themoviedb.org";
    private static MovieRepository sRepository;
    private MovieDbApi mMovieDbApi;

    private MovieRepository() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL_MOVIE_API)
                .build();

        mMovieDbApi = retrofit.create(MovieDbApi.class);
    }

    public static MovieRepository getInstance() {
        if (sRepository == null) {
            sRepository = new MovieRepository();
        }

        return sRepository;
    }

    public void getNowPlayingMovies(final GetMoviesCallback callback, final float minRating) {
        mMovieDbApi.getNowPlayingMovies(BuildConfig.MOVIE_DB_API_VER, BuildConfig.MOVIE_DB_API)
                .enqueue(new Callback<MovieListResult>() {

            @Override
            public void onResponse(Call<MovieListResult> call, Response<MovieListResult> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = new MovieFilter().filterByRating(response.body().results, minRating);
                    callback.onSuccess(movies);
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

    public interface GetMoviesCallback {
        void onSuccess(List<Movie> movies);

        void onError();
    }

}

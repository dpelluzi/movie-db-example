package com.dpelluzi.moviedbexample.models;


import com.dpelluzi.moviedbexample.BuildConfig;
import com.dpelluzi.moviedbexample.interfaces.MovieDbApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    private static final String URL_MOVIE_API = "https://api.themoviedb.org";
    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/";
    private static final String POSTER_IMAGE_SIZE = "w500";

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

    public void searchMovie(final GetMoviesCallback callback, final String query) {
        mMovieDbApi.searchMovie(BuildConfig.MOVIE_DB_API_VER, BuildConfig.MOVIE_DB_API, query)
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

    public String getImageUrl(String imagePath) {
        return BASE_URL_IMAGE + POSTER_IMAGE_SIZE + imagePath;
    }

    public interface GetMoviesCallback {
        void onSuccess(List<Movie> movies);

        void onError();
    }

}

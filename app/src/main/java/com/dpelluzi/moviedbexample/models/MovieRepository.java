package com.dpelluzi.moviedbexample.models;


import com.dpelluzi.moviedbexample.BuildConfig;
import com.dpelluzi.moviedbexample.interfaces.MovieDbApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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

    public void getNowPlayingMovies(final GetMoviesCallback callback, final float minRating, int page) {
        mMovieDbApi.getNowPlayingMovies(BuildConfig.MOVIE_DB_API_VER, BuildConfig.MOVIE_DB_API, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieListResult>() {
                    @Override
                    public void accept(MovieListResult movieListResult) throws Exception {
                        if (movieListResult != null) {
                            movieListResult.movies = new MovieFilter().filterByRating(movieListResult.movies, minRating);
                            callback.onSuccess(movieListResult);
                        } else {
                            callback.onError();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onError();
                    }
                });
    }

    public void searchMovie(final GetMoviesCallback callback, final String query) {
        mMovieDbApi.searchMovie(BuildConfig.MOVIE_DB_API_VER, BuildConfig.MOVIE_DB_API, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieListResult>() {
                    @Override
                    public void accept(MovieListResult movieListResult) throws Exception {
                        if (movieListResult != null) {
                            callback.onSuccess(movieListResult);
                        } else {
                            callback.onError();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onError();
                    }
                });
    }

    public String getImageUrl(String imagePath) {
        return BASE_URL_IMAGE + POSTER_IMAGE_SIZE + imagePath;
    }

    public interface GetMoviesCallback {
        void onSuccess(MovieListResult result);

        void onError();
    }

}

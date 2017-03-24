package com.dpelluzi.moviedbexample.interfaces;

import com.dpelluzi.moviedbexample.models.Movie;

import java.util.List;

public interface MovieListContract {

    interface Presenter {

        void onViewCreated();

        void onMovieItemClicked(Movie movie);

        void onSearchClicked();

        void loadMoreData();
    }

    interface View {

        void setupViews();

        void showProgressBar();

        void showError();

        void dismissProgressBar();

        void addMovies(List<Movie> movies);

        void showList();

        void startMovieDetail(Movie movie);

        void startSearchActivity();
    }

}

package com.dpelluzi.moviedbexample.interfaces;


import com.dpelluzi.moviedbexample.models.Movie;

import java.util.List;

public interface SearchMovieContract {

    interface Presenter {

        void onViewCreated();

        void onSearchTextChanged(String searchText);
    }

    interface View {

        void setupViews();

        void showError();

        void clearList();

        void addMovies(List<Movie> movies);

        void showLoading();

        void dismissLoading();
    }
}

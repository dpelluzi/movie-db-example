package com.dpelluzi.moviedbexample;

import java.util.List;

public interface MovieListContract {

    interface Presenter {

        void onViewCreated();

        void onMovieItemClicked(Movie movie);
    }

    interface View {

        void setupViews();

        void showProgressBar();

        void showError();

        void dismissProgressBar();

        void addMovies(List<Movie> movies);

        void showList();

        void startMovieDetail(int id);
    }

}

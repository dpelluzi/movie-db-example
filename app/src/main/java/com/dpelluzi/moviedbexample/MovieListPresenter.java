package com.dpelluzi.moviedbexample;

import java.util.List;

public class MovieListPresenter implements MovieListContract.Presenter {

    private static final float MIN_RATING = 5.0f;

    private MovieListContract.View mView;

    public MovieListPresenter(MovieListContract.View view) {
        mView = view;
    }

    @Override
    public void onViewCreated() {
        mView.setupViews();
        mView.showProgressBar();

        getMovies();
    }

    private void getMovies() {
        MovieRepository.getInstance().getNowPlayingMovies(new MovieRepository.GetMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mView.dismissProgressBar();
                mView.showList();
                mView.addMovies(movies);
            }

            @Override
            public void onError() {
                mView.dismissProgressBar();
                mView.showError();
            }
        }, MIN_RATING);
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        mView.startMovieDetail(movie.id);
    }
}

package com.dpelluzi.moviedbexample.presenters;

import com.dpelluzi.moviedbexample.interfaces.MovieListContract;
import com.dpelluzi.moviedbexample.models.Movie;
import com.dpelluzi.moviedbexample.models.MovieListResult;
import com.dpelluzi.moviedbexample.models.MovieRepository;

public class MovieListPresenter implements MovieListContract.Presenter {

    private static final float MIN_RATING = 5.0f;

    private MovieListContract.View mView;
    private int mNextPage = 1;
    private int mTotalPages;

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
            public void onSuccess(MovieListResult result) {
                mNextPage = result.page + 1;
                mTotalPages = result.totalPages;
                mView.dismissProgressBar();
                mView.showList();
                mView.addMovies(result.movies);
            }

            @Override
            public void onError() {
                mView.dismissProgressBar();
                mView.showError();
            }
        }, MIN_RATING, mNextPage);
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        mView.startMovieDetail(movie);
    }

    @Override
    public void onSearchClicked() {
        mView.startSearchActivity();
    }

    @Override
    public void loadMoreData() {
        if (mNextPage < mTotalPages) {
            getMovies();
        }
    }
}

package com.dpelluzi.moviedbexample.presenters;

import com.dpelluzi.moviedbexample.interfaces.SearchMovieContract;
import com.dpelluzi.moviedbexample.models.MovieListResult;
import com.dpelluzi.moviedbexample.models.MovieRepository;

public class SearchMoviePresenter implements SearchMovieContract.Presenter {

    private static final int MIN_SEARCH_STRING = 3;

    private SearchMovieContract.View mView;
    private MovieRepository mRepository;

    public SearchMoviePresenter(SearchMovieContract.View view, MovieRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void onViewCreated() {
        mView.setupViews();

    }

    @Override
    public void onSearchTextChanged(String searchText) {
        if (searchText.length() >= MIN_SEARCH_STRING) {
            mView.showLoading();
            searchMovie(searchText);
        } else {
            mView.dismissLoading();
            mView.clearList();
        }
    }

    private void searchMovie(String movieTitle) {
        mRepository.searchMovie(new MovieRepository.GetMoviesCallback() {
            @Override
            public void onSuccess(MovieListResult result) {
                mView.dismissLoading();
                mView.clearList();
                mView.addMovies(result.movies);
            }

            @Override
            public void onError() {
                mView.dismissLoading();
                mView.clearList();
                mView.showError();
            }
        }, movieTitle);
    }
}

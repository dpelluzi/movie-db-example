package com.dpelluzi.moviedbexample;

import com.dpelluzi.moviedbexample.interfaces.SearchMovieContract;
import com.dpelluzi.moviedbexample.models.MovieListResult;
import com.dpelluzi.moviedbexample.models.MovieRepository;
import com.dpelluzi.moviedbexample.presenters.SearchMoviePresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovieSearchPresenterTest {

    @Mock
    private SearchMovieContract.View mView;

    @Mock
    private MovieRepository mRepository;

    @Captor
    private ArgumentCaptor<MovieRepository.GetMoviesCallback> mCallbackCaptor;

    private SearchMovieContract.Presenter mPresenter;

    private MovieListResult mMovieListResult;

    private String mQuery;

    @Before
    public void setup() {
        mPresenter = new SearchMoviePresenter(mView, mRepository);
        mMovieListResult = new MovieListResult();
        mQuery = "Logan";
    }

    @Test
    public void testSearchMovies_onSuccess() {
        mPresenter.onSearchTextChanged(mQuery);

        verify(mView).showLoading();

        verify(mRepository).searchMovie(mCallbackCaptor.capture(), eq(mQuery));
        mCallbackCaptor.getValue().onSuccess(mMovieListResult);

        verify(mView).dismissLoading();
        verify(mView).clearList();
        verify(mView).addMovies(eq(mMovieListResult.movies));
    }

    @Test
    public void testSearchMovies_onError() {
        mPresenter.onSearchTextChanged(mQuery);

        verify(mView).showLoading();

        verify(mRepository).searchMovie(mCallbackCaptor.capture(), eq(mQuery));
        mCallbackCaptor.getValue().onError();

        verify(mView).dismissLoading();
        verify(mView).clearList();
        verify(mView).showError();
    }

    @Test
    public void testSearchMovies_shortQuery() {
        mPresenter.onSearchTextChanged("ab");

        verify(mView).dismissLoading();
        verify(mView).clearList();
    }

}

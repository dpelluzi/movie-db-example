package com.dpelluzi.moviedbexample;

import com.dpelluzi.moviedbexample.interfaces.MovieListContract;
import com.dpelluzi.moviedbexample.models.MovieListResult;
import com.dpelluzi.moviedbexample.models.MovieRepository;
import com.dpelluzi.moviedbexample.presenters.MovieListPresenter;

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
public class MovieListPresenterTest {

    @Mock
    private MovieListContract.View mView;

    @Mock
    private MovieRepository mRepository;

    @Captor
    private ArgumentCaptor<MovieRepository.GetMoviesCallback> mCallbackCaptor;

    private MovieListContract.Presenter mPresenter;

    private MovieListResult mMovieListResult;

    @Before
    public void setup() {
        mPresenter = new MovieListPresenter(mView, mRepository);
        mMovieListResult = new MovieListResult();
    }

    @Test
    public void testGetMovies_onSuccess() {
        mPresenter.onViewCreated();

        verify(mView).setupViews();
        verify(mView).showProgressBar();

        verify(mRepository).getNowPlayingMovies(mCallbackCaptor.capture(), eq(5.0f), eq(1));
        mCallbackCaptor.getValue().onSuccess(mMovieListResult);

        verify(mView).dismissProgressBar();
        verify(mView).showList();
        verify(mView).addMovies(eq(mMovieListResult.movies));
    }

    @Test
    public void testGetMovies_onError() {
        mPresenter.onViewCreated();

        verify(mView).setupViews();
        verify(mView).showProgressBar();

        verify(mRepository).getNowPlayingMovies(mCallbackCaptor.capture(), eq(5.0f), eq(1));
        mCallbackCaptor.getValue().onError();

        verify(mView).dismissProgressBar();
        verify(mView).showError();
    }

}

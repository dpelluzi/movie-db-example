package com.dpelluzi.moviedbexample.presenters;

import com.dpelluzi.moviedbexample.interfaces.MovieDetailContract;
import com.dpelluzi.moviedbexample.models.Movie;
import com.dpelluzi.moviedbexample.models.MovieRepository;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View mView;
    private Movie mMovie;

    public MovieDetailPresenter(MovieDetailContract.View view, Movie movie) {
        this.mView = view;
        mMovie = movie;
    }

    @Override
    public void onViewCreated() {

        mView.setMovieTitle(mMovie.title);
        mView.setOverview(mMovie.overview);
        mView.setRating(mMovie.voteAverage);
        mView.setPoster(MovieRepository.getInstance().getImageUrl(mMovie.posterPath));
    }
}

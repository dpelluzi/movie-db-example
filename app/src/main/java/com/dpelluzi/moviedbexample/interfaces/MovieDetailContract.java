package com.dpelluzi.moviedbexample.interfaces;


public interface MovieDetailContract {

    interface Presenter {

        void onViewCreated();

    }

    interface View {

        void setMovieTitle(String title);

        void setOverview(String overview);

        void setRating(float voteAverage);

        void setPoster(String posterPath);

    }
}

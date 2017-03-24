package com.dpelluzi.moviedbexample.models;

import java.util.ArrayList;
import java.util.List;

public class MovieFilter {

    public List<Movie> filterByRating(List<Movie> movies, float minRating) {
        List<Movie> filteredList = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.voteAverage >= minRating) {
                filteredList.add(movie);
            }
        }
        return filteredList;
    }
}

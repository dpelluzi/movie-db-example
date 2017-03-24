package com.dpelluzi.moviedbexample;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MovieFilterTest {

    @Test
    public void filterByRating_isCorrect() throws Exception {

        Movie m1 = new Movie();
        m1.voteAverage = 7.0f;

        Movie m2 = new Movie();
        m2.voteAverage = 8.0f;

        Movie m3 = new Movie();
        m3.voteAverage = 9.0f;

        List<Movie> allMovies = Arrays.asList(m1, m2, m3);

        List<Movie> filteredMovies = new MovieFilter().filterByRating(allMovies, 8.0f);

        assertEquals(2, filteredMovies.size());
    }
}
package com.dpelluzi.moviedbexample.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieListResult {

    @SerializedName("page")
    public int page;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResult;

    @SerializedName("results")
    public List<Movie> results;

    public MovieListResult() {
        results = new ArrayList<>();
    }
}

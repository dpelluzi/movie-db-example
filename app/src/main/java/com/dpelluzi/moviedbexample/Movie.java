package com.dpelluzi.moviedbexample;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    public int id;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("title")
    public String title;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("overview")
    public String overview;

}

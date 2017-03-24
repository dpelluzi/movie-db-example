package com.dpelluzi.moviedbexample.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
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

    protected Movie(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        title = in.readString();
        voteAverage = in.readFloat();
        overview = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeFloat(voteAverage);
        dest.writeString(overview);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

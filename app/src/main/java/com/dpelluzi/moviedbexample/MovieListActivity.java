package com.dpelluzi.moviedbexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        MovieRepository.getInstance().getNowPlayingMovies(new MovieRepository.GetMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                Log.d("TMDB", "sucess");
            }

            @Override
            public void onError() {
                Toast.makeText(MovieListActivity.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.dpelluzi.moviedbexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity {

    @BindView(R.id.list)
    RecyclerView mMovieList;

    private MovieListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        mMovieList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MovieListAdapter();
        mAdapter.setOnItemClickListener(new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Movie movie) {
                Toast.makeText(MovieListActivity.this, movie.title, Toast.LENGTH_SHORT).show();
            }
        });
        mMovieList.setAdapter(mAdapter);

        MovieRepository.getInstance().getNowPlayingMovies(new MovieRepository.GetMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                Log.d("TMDB", "sucess");
                mAdapter.addItems(movies);
            }

            @Override
            public void onError() {
                Toast.makeText(MovieListActivity.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

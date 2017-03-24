package com.dpelluzi.moviedbexample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dpelluzi.moviedbexample.R;
import com.dpelluzi.moviedbexample.interfaces.MovieListContract;
import com.dpelluzi.moviedbexample.models.Movie;
import com.dpelluzi.moviedbexample.presenters.MovieListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.View {

    @BindView(R.id.list)
    RecyclerView mMovieList;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private MovieListContract.Presenter mPresenter;
    private MovieListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        mPresenter = new MovieListPresenter(this);
        mPresenter.onViewCreated();
    }

    @Override
    public void setupViews() {
        mMovieList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MovieListAdapter();
        mAdapter.setOnItemClickListener(new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Movie movie) {
                mPresenter.onMovieItemClicked(movie);
            }
        });
        mMovieList.setAdapter(mAdapter);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void addMovies(List<Movie> movies) {
        mAdapter.addItems(movies);
    }

    @Override
    public void showList() {
        mMovieList.setVisibility(View.VISIBLE);
    }

    @Override
    public void startMovieDetail(Movie movie) {
        final Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

}

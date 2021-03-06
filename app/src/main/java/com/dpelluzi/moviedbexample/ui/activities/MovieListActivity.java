package com.dpelluzi.moviedbexample.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dpelluzi.moviedbexample.R;
import com.dpelluzi.moviedbexample.interfaces.MovieListContract;
import com.dpelluzi.moviedbexample.models.Movie;
import com.dpelluzi.moviedbexample.models.MovieRepository;
import com.dpelluzi.moviedbexample.presenters.MovieListPresenter;
import com.dpelluzi.moviedbexample.ui.EndlessRecyclerViewScrollListener;
import com.dpelluzi.moviedbexample.ui.MovieListAdapter;

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

        mPresenter = new MovieListPresenter(this, MovieRepository.getInstance());
        mPresenter.onViewCreated();
    }

    @Override
    public void setupViews() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMovieList.setLayoutManager(layoutManager);
        mAdapter = new MovieListAdapter();
        mAdapter.setOnItemClickListener(new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Movie movie) {
                mPresenter.onMovieItemClicked(movie);
            }
        });
        mMovieList.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.loadMoreData();
            }
        });
        mMovieList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            mPresenter.onSearchClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void startSearchActivity() {
        startActivity(new Intent(this, SearchMovieActivity.class));
    }

}

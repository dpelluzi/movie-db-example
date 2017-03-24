package com.dpelluzi.moviedbexample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dpelluzi.moviedbexample.R;
import com.dpelluzi.moviedbexample.interfaces.SearchMovieContract;
import com.dpelluzi.moviedbexample.models.Movie;
import com.dpelluzi.moviedbexample.presenters.SearchMoviePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class SearchMovieActivity extends AppCompatActivity implements SearchMovieContract.View {

    @BindView(R.id.list)
    RecyclerView mMovieList;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private SearchMovieContract.Presenter mPresenter;
    private MovieListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        ButterKnife.bind(this);

        mPresenter = new SearchMoviePresenter(this);
        mPresenter.onViewCreated();
    }

    @OnTextChanged(value = R.id.input_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onSearchInput(Editable editable) {
        mPresenter.onSearchTextChanged(editable.toString());
    }

    @Override
    public void setupViews() {
        mMovieList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MovieListAdapter();
        mMovieList.setAdapter(mAdapter);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearList() {
        mAdapter.clear();
    }

    @Override
    public void addMovies(List<Movie> movies) {
        mAdapter.addItems(movies);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

}

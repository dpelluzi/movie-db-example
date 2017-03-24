package com.dpelluzi.moviedbexample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpelluzi.moviedbexample.R;
import com.dpelluzi.moviedbexample.interfaces.MovieDetailContract;
import com.dpelluzi.moviedbexample.models.Movie;
import com.dpelluzi.moviedbexample.presenters.MovieDetailPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

    public static final String EXTRA_MOVIE = "extra.MOVIE";

    @BindView(R.id.img_poster)
    ImageView posterImageView;

    @BindView(R.id.text_overview)
    TextView overviewTextView;

    @BindView(R.id.text_rating)
    TextView ratingView;

    private MovieDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        mPresenter = new MovieDetailPresenter(this, movie);
        mPresenter.onViewCreated();
    }

    @Override
    public void setMovieTitle(String title) {
        setTitle(title);
    }

    @Override
    public void setOverview(String overview) {
        overviewTextView.setText(overview);
    }

    @Override
    public void setRating(float voteAverage) {
        ratingView.setText(getString(R.string.text_rating, voteAverage));
    }

    @Override
    public void setPoster(String posterUrl) {
        Picasso.with(this).load(posterUrl).resizeDimen(R.dimen.image_width, R.dimen.image_height)
                .centerCrop().into(posterImageView);
    }
}

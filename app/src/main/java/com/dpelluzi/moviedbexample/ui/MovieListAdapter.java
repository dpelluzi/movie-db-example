package com.dpelluzi.moviedbexample.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dpelluzi.moviedbexample.R;
import com.dpelluzi.moviedbexample.models.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> mData;
    private OnItemClickListener mOnItemClickListener;
    public MovieListAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItems(List<Movie> items) {
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClicked(Movie movie);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_title)
        TextView titleView;

        @BindView(R.id.text_overview)
        TextView overviewTextView;

        @BindView(R.id.text_rating)
        TextView ratingView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(@NonNull Movie item) {
            titleView.setText(item.title);
            overviewTextView.setText(item.overview);
            ratingView.setText(ratingView.getResources().getString(R.string.text_rating, item.voteAverage));
        }

        @OnClick(R.id.list_item)
        public void onItemClicked() {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClicked(mData.get(getAdapterPosition()));
            }
        }
    }
}

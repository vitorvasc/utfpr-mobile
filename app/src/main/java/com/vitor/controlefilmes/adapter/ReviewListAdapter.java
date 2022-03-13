package com.vitor.controlefilmes.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitor.controlefilmes.R;
import com.vitor.controlefilmes.entity.Review;

import java.util.List;

public final class ReviewListAdapter extends BaseAdapter {

    Context context;
    List<Pair<Review, Drawable>> reviewList;

    private static class ReviewListHolder {
        public ImageView imageViewMovieImage;
        public TextView textViewMovieName;
        public TextView textViewReviewGenre;
        public TextView textViewReviewRating;
        public TextView textViewReviewRecommends;
    }

    public ReviewListAdapter(Context context, List<Pair<Review, Drawable>> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ReviewListAdapter.ReviewListHolder holder;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_reviews, viewGroup, false);

            holder = new ReviewListAdapter.ReviewListHolder();

            holder.imageViewMovieImage = view.findViewById(R.id.imageViewMovieImage);
            holder.textViewMovieName = view.findViewById(R.id.textViewMovieName);
            holder.textViewReviewGenre = view.findViewById(R.id.textViewReviewGenre);
            holder.textViewReviewRating = view.findViewById(R.id.textViewReviewRating);
            holder.textViewReviewRecommends = view.findViewById(R.id.textViewReviewRecommends);

            view.setTag(holder);
        } else {
            holder = (ReviewListAdapter.ReviewListHolder) view.getTag();
        }

        holder.imageViewMovieImage.setImageDrawable(reviewList.get(i).second);
        holder.textViewMovieName.setText(reviewList.get(i).first.getTitle());
        holder.textViewReviewGenre.setText(String.valueOf(reviewList.get(i).first.getGenre()));
        holder.textViewReviewRating.setText(String.valueOf(reviewList.get(i).first.getRating()));
        holder.textViewReviewRecommends.setText(reviewList.get(i).first.getRecommends() == 0 ? context.getString(R.string.no) : context.getString(R.string.yes));

        return view;
    }
}

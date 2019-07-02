package com.example.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.databinding.ItemReviewBinding;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.ui.ReviewActivity;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private final Activity mActivity;
    private List<Review> reviews;

    public ReviewAdapter(Activity activity) {
        this.mActivity = activity;

    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        ItemReviewBinding reviewBinding = ItemReviewBinding.inflate(layoutInflater, parent, false);
        return new ReviewViewHolder(reviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, final int position) {
        final Review review = reviews.get(position);
        holder.bind(review);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ReviewActivity.class);
                intent.putExtra(ReviewActivity.REVIEW_INTENT_KEY, review);
                context.startActivity(intent);
            }
        });
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    class ReviewViewHolder extends RecyclerView.ViewHolder {
        ItemReviewBinding reviewBinding;

        ReviewViewHolder(ItemReviewBinding reviewBinding) {
            super(reviewBinding.getRoot());
            this.reviewBinding = reviewBinding;

        }

        void bind(final Review review){
            reviewBinding.setViewModel(review);
        }

    }
}

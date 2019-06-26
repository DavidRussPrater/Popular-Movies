package com.example.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> reviews;
    private int reviewLayout;
    private Context context;


    public ReviewAdapter(List<Review> reviews, int reviewLayout, Context context) {
        this.reviews = reviews;
        this.reviewLayout = reviewLayout;
        this.context = context;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, final int position) {
        holder.reviewAuthor.setText(reviews.get(position).getAuthorName());
        holder.reviewContent.setText(reviews.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        CardView reviewLayout;
        TextView reviewAuthor;
        TextView reviewContent;

        public ReviewViewHolder(View view) {
            super(view);
            mView = view;

            reviewLayout = (CardView) view.findViewById(R.id.review_layout);
            reviewAuthor = (TextView) view.findViewById(R.id.reviewer_text_view);
            reviewContent = (TextView) view.findViewById(R.id.review_text_view);
        }

    }
}

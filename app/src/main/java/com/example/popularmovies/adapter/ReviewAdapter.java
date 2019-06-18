package com.example.popularmovies.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends ArrayAdapter<Review> {

    /**
     * Create a new {@link MovieAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param reviews  is the list of {@link Movie}s to be displayed.
     */
    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        super(context, 0, reviews);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View reviewView = convertView;
        if (reviewView == null) {
            reviewView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_review, parent, false);
        }


        return reviewView;
    }

}

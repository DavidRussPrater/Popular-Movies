package com.example.popularmovies.utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.example.popularmovies.model.Review;

import java.util.List;

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    // Query the URL
    private final String mUrl;

    // Construct a new {@Link MovieLoader}
    public ReviewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //This is performed on a background thread
    @Override
    public List<Review> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return ReviewQueryUtils.fetchReviewData(mUrl);

    }
}

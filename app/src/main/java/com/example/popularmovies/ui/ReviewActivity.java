package com.example.popularmovies.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.databinding.ActivityDetailBinding;
import com.example.popularmovies.databinding.ActivityReviewBinding;
import com.example.popularmovies.model.Review;

import org.w3c.dom.Text;

import java.util.Objects;

public class ReviewActivity extends AppCompatActivity {

    public static final String REVIEW_INTENT_KEY = "com.ahmetroid.popularmovies.ui.review";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_review);

        Intent intent = getIntent();
        Review review = intent.getParcelableExtra(REVIEW_INTENT_KEY);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.setViewModel(review);

    }

}

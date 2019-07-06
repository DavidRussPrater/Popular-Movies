package com.example.popularmovies.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.popularmovies.FavoriteExecutor;
import com.example.popularmovies.data.FavoriteDatabase;
import com.example.popularmovies.model.MiniMovie;
import com.example.popularmovies.rest.ApiService;
import com.example.popularmovies.R;
import com.example.popularmovies.adapter.ReviewAdapter;
import com.example.popularmovies.adapter.VideoAdapter;
import com.example.popularmovies.databinding.ActivityDetailBinding;
import com.example.popularmovies.model.ApiResponse;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Video;
import com.example.popularmovies.rest.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_INTENT_KEY = "com.example.prate.popularmovies.ui.detail";
    public static final String MOVIE_NUMBER_KEY = "com.example.prate.popularmovies.ui.movie_number";

    private int movieNumber;

    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter;
    private FavoriteDatabase mDatabase;
    private boolean isFavorite;
    private Executor executor;
    private ApiService mApiService;
    private ActivityDetailBinding mBinding;
    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mApiService = ServiceGenerator.createService(ApiService.class);
        executor = new FavoriteExecutor();

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(DETAIL_INTENT_KEY);
        mBinding.setViewModel(movie);

        mDatabase = FavoriteDatabase.getDatabase(getApplicationContext());

        setSupportActionBar(mBinding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        populateUI();
        populateReviews();
        populateVideos();


        FloatingActionButton favoritesFab = findViewById(R.id.favorite_fab);

        favoritesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                String snackBarText;

                if (isFavorite) {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            mDatabase.movieDao().delete(movie);
                        }
                    });
                    isFavorite = false;
                    mBinding.favoriteFab.setImageResource(R.drawable.ic_favorite_outline_white_24px);
                    snackBarText = getString(R.string.removed_from_favorites);
                } else {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            mDatabase.movieDao().insert(movie);
                        }
                    });
                    isFavorite = true;
                    mBinding.favoriteFab.setImageResource(R.drawable.ic_favorite_solid_white_24px);
                    snackBarText = getString(R.string.added_favorite);
                }
                Snackbar.make(mBinding.coordinatorLayout, snackBarText, Snackbar.LENGTH_SHORT).show();


            }
        });

    }

    protected void populateUI(){

        String picassoBackdropImage = "http://image.tmdb.org/t/p/w1280/" + movie.getBackdropPath();
        Picasso.get().load(picassoBackdropImage)
                .error(R.drawable.posterimageplaceholder)
                .into(mBinding.backdrop);

        Picasso.get().load("http://image.tmdb.org/t/p/w342" + movie.getPosterPath())
                .error(R.drawable.posterimageplaceholder)
                .into(mBinding.moviesDetail.moviePoster);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                MiniMovie miniMovie = mDatabase.movieDao().getMovieById(movie.getMovieId());

                if (miniMovie != null) {
                    isFavorite = true;
                    mBinding.favoriteFab.setImageResource(R.drawable.ic_favorite_solid_white_24px);
                } else {
                    isFavorite = false;
                    mBinding.favoriteFab.setImageResource(R.drawable.ic_favorite_outline_white_24px);
                }
            }
        });
    }


    private void populateReviews() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mBinding.reviewDetails.reviewRecyclerView.setLayoutManager(layoutManager);
        mBinding.reviewDetails.reviewRecyclerView.setHasFixedSize(true);
        mBinding.reviewDetails.reviewRecyclerView.setNestedScrollingEnabled(true);

        mReviewAdapter = new ReviewAdapter(this);
        mBinding.reviewDetails.reviewRecyclerView.setAdapter(mReviewAdapter);

        Call<ApiResponse<Review>> call = mApiService.getReviews(movie.getMovieId());

        call.enqueue(new Callback<ApiResponse<Review>>() {
            @Override
            public void onResponse(Call<ApiResponse<Review>> call,
                                   Response<ApiResponse<Review>> response) {
                List<Review> results = response.body().results;
                if (!results.isEmpty() && results != null) {
                    mReviewAdapter.setReviews(results);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Review>> call, Throwable t) {
                mReviewAdapter = null;
            }
        });

    }

    private void populateVideos(){
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mBinding.videoDetail.videoRecyclerView.setLayoutManager(layoutManager);
        mBinding.videoDetail.videoRecyclerView.setHasFixedSize(true);
        mBinding.videoDetail.videoRecyclerView.setNestedScrollingEnabled(true);

        mVideoAdapter = new VideoAdapter(this);
        mBinding.videoDetail.videoRecyclerView.setAdapter(mVideoAdapter);

        Call<ApiResponse<Video>> call = mApiService.getVideos(movie.getMovieId());

        call.enqueue(new Callback<ApiResponse<Video>>() {
            @Override
            public void onResponse(Call<ApiResponse<Video>> call,
                                   Response<ApiResponse<Video>> response) {
                List<Video> results = response.body().results;
                if (!results.isEmpty() && results != null) {
                    mVideoAdapter.setVideos(results);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Video>> call, Throwable t) {
                mVideoAdapter = null;
            }
        });

    }


    public class UndoFavoritesFabAdd implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final FloatingActionButton fab = findViewById(R.id.favorite_fab);

            Snackbar.make(view, R.string.removed_from_favorites, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            fab.setImageResource(R.drawable.ic_favorite_outline_white_24px);
        }
    }



}

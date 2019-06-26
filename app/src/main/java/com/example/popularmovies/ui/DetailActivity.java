package com.example.popularmovies.ui;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.ApiService;
import com.example.popularmovies.R;
import com.example.popularmovies.adapter.ReviewAdapter;
import com.example.popularmovies.adapter.VideoAdapter;
import com.example.popularmovies.model.ApiResponse;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String TAG = MainActivity.class.getSimpleName();
    // insert your themoviedb.org API KEY here
    private final static String API_KEY = BuildConfig.API_KEY;
    public static AppBarLayout mAppBarLayout;
    private static Retrofit retrofit = null;

    private RecyclerView reviewRecyclerView = null;
    private RecyclerView videoRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the image and text views in the detail_activity and set them to their corresponding
        // variables


        reviewRecyclerView = (RecyclerView) findViewById(R.id.review_recycler_view);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        videoRecyclerView = (RecyclerView) findViewById(R.id.video_recycler_view);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        ImageView moviePosterImageView = findViewById(R.id.movie_poster);
        TextView movieTitleTextView = findViewById(R.id.movie_title);
        TextView movieReleaseDateTextView = findViewById(R.id.release_date);
        TextView movieVoteAverageTextView = findViewById(R.id.vote_average);
        TextView moviePlotSynopsisTextView = findViewById(R.id.plot_synopsis);

        // Pass in the array of strings using the intent from tha MainActivity and set its values to
        // the corresponding text and image view variables
        Bundle extras = getIntent().getExtras();

        String[] detailsArray = Objects.requireNonNull(extras).getStringArray("details");

        if (detailsArray != null) {
            movieTitleTextView.setText(detailsArray[1]);
        }

        mAppBarLayout = findViewById(R.id.app_bar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(detailsArray[1]);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView toolbarImage = findViewById(R.id.backdrop);


        // This if statement checks if there is a poster image for the current movie. If the
        // response returns null set it to the posterimageplaceholder.png else set it to the
        // correct image provided.

        String picassoPosterImage = Objects.requireNonNull(detailsArray)[0];
        Picasso.get().load(picassoPosterImage)
                .error(R.drawable.posterimageplaceholder)
                .into(moviePosterImageView);

        moviePlotSynopsisTextView.setText(detailsArray[2]);
        movieReleaseDateTextView.setText(detailsArray[3]);

        String picassoBackdropImage = "http://image.tmdb.org/t/p/w1280/" + Objects.requireNonNull(detailsArray)[4];
        Picasso.get().load(picassoBackdropImage)
                .error(R.drawable.posterimageplaceholder)
                .into(toolbarImage);

        movieVoteAverageTextView.setText(detailsArray[5]);


        final FloatingActionButton fab = findViewById(R.id.favorite_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.added_favorite, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new UndoFavoritesFabAdd()).show();
                fab.setImageResource(R.drawable.ic_favorite_solid_white_24px);
            }
        });

        connectAndGetReviewData();
        connectAndGetVideoData();

    }

    // This method create an instance of Retrofit set the base url
    public void connectAndGetReviewData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiResponse<Review>> call = apiService.getReviews("157336", API_KEY);
        call.enqueue(new Callback<ApiResponse<Review>>() {
            @Override
            public void onResponse(Call<ApiResponse<Review>> call, Response<ApiResponse<Review>> response) {
                List<Review> reviews = response.body().getResults();
                reviewRecyclerView.setAdapter(new ReviewAdapter(reviews, R.layout.item_review, getApplicationContext()));
                Log.d(TAG, "Number of reviews received: " + reviews.size());
            }

            @Override
            public void onFailure(Call<ApiResponse<Review>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }


    // This method create an instance of Retrofit set the base url
    public void connectAndGetVideoData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiResponse<Video>> call = apiService.getVideos("157336", API_KEY);
        call.enqueue(new Callback<ApiResponse<Video>>() {
            @Override
            public void onResponse(Call<ApiResponse<Video>> call, Response<ApiResponse<Video>> response) {
                List<Video> videos = response.body().getResults();
                videoRecyclerView.setAdapter(new VideoAdapter(videos, R.layout.item_video, getApplicationContext()));
                Log.d(TAG, "Number of videos received: " + videos.size());
            }

            @Override
            public void onFailure(Call<ApiResponse<Video>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
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

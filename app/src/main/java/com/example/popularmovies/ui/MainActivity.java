package com.example.popularmovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.ApiService;
import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MovieAdapter;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = BuildConfig.API_KEY;
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        connectAndGetApiData();
    }

    // This method create an instance of Retrofit
    // set the base url
    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiResponse<Movie>> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<ApiResponse<Movie>>() {
            @Override
            public void onResponse(Call<ApiResponse<Movie>> call, Response<ApiResponse<Movie>> response) {
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies, R.layout.item_movie, getApplicationContext()));
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<ApiResponse<Movie>> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
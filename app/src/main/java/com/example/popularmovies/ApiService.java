package com.example.popularmovies;

import com.example.popularmovies.model.ApiResponse;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/top_rated")
    Call<ApiResponse<Movie>> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ApiResponse<Review>> getReviews(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<ApiResponse<Video>> getVideos(@Path("id") String id, @Query("api_key") String apiKey);
}

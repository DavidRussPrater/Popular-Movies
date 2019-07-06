package com.example.popularmovies.rest;

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
    Call<ApiResponse<Movie>> getTopRatedMovies();

    @GET("movie/popular")
    Call<ApiResponse<Movie>> getPopularMovies();

    @GET("movie/{movieId}/reviews")
    Call<ApiResponse<Review>> getReviews(@Path("movieId") String id);

    @GET("movie/{movieId}/videos")
    Call<ApiResponse<Video>> getVideos(@Path("movieId") String id);

}

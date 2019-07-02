package com.example.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.popularmovies.data.FavoriteDatabase;
import com.example.popularmovies.model.Movie;

public class AddFavoriteViewModel extends ViewModel {

    private LiveData<Movie> movie;

//    public AddFavoriteViewModel(FavoriteDatabase database, String movieId) {
//        movie = database.movieDao().getMovieById(movieId);
//    }

    // COMPLETED (7) Create a getter for the task variable
//    public LiveData<Movie> getMovie() {
//        return movie;
//    }

}

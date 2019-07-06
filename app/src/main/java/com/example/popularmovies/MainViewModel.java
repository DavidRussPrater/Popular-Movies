package com.example.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.popularmovies.data.FavoriteDatabase;
import com.example.popularmovies.model.ApiResponse;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.rest.ApiService;
import com.example.popularmovies.rest.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<List<Movie>> popularMovies;
    private MutableLiveData<List<Movie>> topRatedMovies;
    private LiveData<List<Movie>> favoriteMovies;
    private FavoriteDatabase favoriteDatabase;
    private ApiService apiService;

    public MainViewModel(Application application){
        super(application);
        favoriteDatabase = FavoriteDatabase.getDatabase(getApplication());
        apiService = ServiceGenerator.createService(ApiService.class);

    }

    public LiveData<List<Movie>> getPopularMovies() {
        if (popularMovies == null) {
            popularMovies = new MutableLiveData<>();
            loadMovies(0);
        }
        return popularMovies;
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        if (topRatedMovies == null) {
            topRatedMovies = new MutableLiveData<>();
            loadMovies(1);
        }
        return topRatedMovies;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        if (favoriteMovies == null) {
            favoriteMovies = new MutableLiveData<>();
            getFavoritesFromDatabase();
        }
        return favoriteMovies;
    }

    private void loadMovies(int sorting){

        if(sorting == 0) {
            Call<ApiResponse<Movie>> call = apiService.getPopularMovies();

            call.enqueue(new Callback<ApiResponse<Movie>>() {
                @Override
                public void onResponse(Call<ApiResponse<Movie>> call,
                                       Response<ApiResponse<Movie>> response) {
                    if (response.isSuccessful()) {
                        List<Movie> results = response.body().results;
                        if (!results.isEmpty() && results != null) {
                            popularMovies.postValue(results);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Movie>> call, Throwable t) {
                    popularMovies = null;
                }
            });
        } else if (sorting == 1) {
            Call<ApiResponse<Movie>> call = apiService.getTopRatedMovies();

            call.enqueue(new Callback<ApiResponse<Movie>>() {
                @Override
                public void onResponse(Call<ApiResponse<Movie>> call,
                                       Response<ApiResponse<Movie>> response) {
                    if (response.isSuccessful()) {
                        List<Movie> results = response.body().results;
                        if (!results.isEmpty() && results != null) {
                            topRatedMovies.setValue(results);
                        }

                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Movie>> call, Throwable t) {
                    topRatedMovies = null;
                }
            });

        }

    }

    private void getFavoritesFromDatabase() {
        favoriteMovies = favoriteDatabase.movieDao().getAll();
    }

}

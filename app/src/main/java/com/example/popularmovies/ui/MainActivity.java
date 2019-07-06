package com.example.popularmovies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.popularmovies.MainViewModel;
import com.example.popularmovies.R;
import com.example.popularmovies.adapter.MovieAdapter;
import com.example.popularmovies.data.SettingsActivity;
import com.example.popularmovies.databinding.ActivityMainBinding;
import com.example.popularmovies.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private GridLayoutManager mGridLayoutManager;
    private ActivityMainBinding mBinding;
    private MainViewModel mainViewModel;
    private MovieAdapter mMovieAdapter;

    public static final int FAVORITES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Context context;
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mMovieAdapter = new MovieAdapter(this);

        mBinding.moviesRecyclerView.setLayoutManager(mGridLayoutManager);
        mBinding.moviesRecyclerView.setAdapter(mMovieAdapter);


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        populateUI();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        populateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMovieAdapter.refreshFavorite();
    }

    protected void populateUI() {
        mainViewModel.getPopularMovies().removeObservers(MainActivity.this);
        mainViewModel.getTopRatedMovies().removeObservers(MainActivity.this);
        mainViewModel.getFavoriteMovies().removeObservers(MainActivity.this);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

        switch (orderBy) {
            case "popular":
                mainViewModel.getPopularMovies().observe(MainActivity.this,
                        new Observer<List<Movie>>() {
                            @Override
                            public void onChanged(@Nullable List<Movie> movies) {
                                mMovieAdapter.setMoviesList(movies);
                            }
                        });
                break;
            case "top_rated":
                mainViewModel.getTopRatedMovies().observe(MainActivity.this,
                        new Observer<List<Movie>>() {
                            @Override
                            public void onChanged(@Nullable List<Movie> movies) {
                                mMovieAdapter.setMoviesList(movies);
                            }
                        });
                break;
            default:
                mainViewModel.getFavoriteMovies().observe(MainActivity.this,
                        new Observer<List<Movie>>() {
                            @Override
                            public void onChanged(@Nullable List<Movie> movies) {
                                if (mMovieAdapter.getItemCount() < movies.size()) {
                                    mMovieAdapter.setMoviesList(movies);
                                } else if (movies.size() == 0) {

                                }
                            }
                        });
        }

    }


}
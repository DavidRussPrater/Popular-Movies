package com.example.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.FavoriteExecutor;
import com.example.popularmovies.R;
import com.example.popularmovies.data.FavoriteDatabase;
import com.example.popularmovies.data.SettingsActivity;
import com.example.popularmovies.databinding.ItemMovieBinding;

import com.example.popularmovies.model.MiniMovie;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Activity mActivity;
    private List<Movie> movies;
    private FavoriteDatabase mDatabase;
    private Executor executor;


    public MovieAdapter(Activity activity) {
        this.mActivity = activity;
        this.mDatabase = FavoriteDatabase.getDatabase(activity);
        this.executor = new FavoriteExecutor();
    }

    @Override
    @NonNull
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        ItemMovieBinding movieBinding = ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new MovieViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        holder.bind(movie);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.DETAIL_INTENT_KEY, movie);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }

        return movies.size();
    }

    public void clearList() {
        if (movies == null) {
            movies = new ArrayList<>();
        } else {
            int itemCount = movies.size();
            movies.clear();
            notifyItemRangeRemoved(0, itemCount);
        }
    }

    public void setMoviesList(List<Movie> movies) {
        int positionStart = movies.size();
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void refreshFavorite() {
        int movieNumber = SettingsActivity.getChangedMovie(mActivity);
        if (movieNumber != -1) {
            notifyItemChanged(movieNumber);
            SettingsActivity.setChangedMovie(mActivity, -1);
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding movieBinding;
        boolean isFavorite;


        MovieViewHolder(ItemMovieBinding movieBinding) {
            super(movieBinding.getRoot());
            this.movieBinding = movieBinding;
        }

        void bind(final Movie movie) {
            movieBinding.setViewModel(movie);
            movieBinding.setPresenter(this);

            Picasso.get()
                    .load("http://image.tmdb.org/t/p/w342" + movie.getPosterPath())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.drawable.trailer_place_holder)
                    .into(movieBinding.moviePoster);

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    MiniMovie miniMovie = mDatabase.movieDao().getMovieById(movie.getMovieId());

                    if (miniMovie != null) {
                        isFavorite = true;
                        movieBinding.favoriteImageView.setImageResource(R.drawable.ic_favorite_solid_white_24px);
                    } else {
                        isFavorite = false;
                        movieBinding.favoriteImageView.setImageResource(R.drawable.ic_favorite_outline_white_24px);
                    }
                }
            });
        }

        public void onClickFavorite(View view) {
            String snackBarText;
            int position = getAdapterPosition();
            final Movie movie = movies.get(position);

            if (isFavorite) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.movieDao().delete(movie);
                    }
                });
                movies.remove(position);
                notifyItemRemoved(position);
                isFavorite = false;
                movieBinding.favoriteImageView.setImageResource(R.drawable.ic_favorite_outline_white_24px);
                snackBarText = mActivity.getString(R.string.removed_from_favorites);
                Snackbar.make(view, snackBarText, Snackbar.LENGTH_SHORT).show();


            } else {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.movieDao().insert(movie);
                    }
                });
                isFavorite = true;
                movieBinding.favoriteImageView.setImageResource(R.drawable.ic_favorite_solid_white_24px);
                snackBarText = mActivity.getString(R.string.added_favorite);
                Snackbar.make(view, snackBarText, Snackbar.LENGTH_SHORT).show();
            }

        }

    }

 }




package com.example.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";
    private List<Movie> movies;
    private int gridLayout;
    private Context context;


    public MovieAdapter(List<Movie> movies, int gridLayout, Context context) {
        this.movies = movies;
        this.gridLayout = gridLayout;
        this.context = context;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(gridLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        String imageUrl = IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
        String movieTitle = movies.get(position).getTitle();
        String movieOverview = movies.get(position).getOverview();
        String movieReleaseDate = movies.get(position).getReleaseDate();
        String movieBackdropPath = movies.get(position).getBackdropPath();
        String movieVoteAverage = movies.get(position).getVoteAverage();
        String movieId = movies.get(position).getId();

        final String[] movieDetailsArray = {imageUrl, movieTitle, movieOverview, movieReleaseDate,
                movieBackdropPath, movieVoteAverage, movieId};


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("details", movieDetailsArray);

                context.startActivity(intent);
            }
        });


        Picasso.get()
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);
        holder.movieTitle.setText(movies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        ConstraintLayout moviesLayout;
        TextView movieTitle;
        ImageView movieImage;

        public MovieViewHolder(View view) {
            super(view);
            mView = view;

            moviesLayout = (ConstraintLayout) view.findViewById(R.id.movies_layout);
            movieImage = (ImageView) view.findViewById(R.id.movie_poster);
            movieTitle = (TextView) view.findViewById(R.id.movie_title);
        }

    }

}



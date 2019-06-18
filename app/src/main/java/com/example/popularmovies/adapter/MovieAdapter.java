package com.example.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class MovieAdapter extends ArrayAdapter<Movie> {


    /**
     * Create a new {@link MovieAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param movies  is the list of {@link Movie}s to be displayed.
     */
    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View gridView = convertView;
        if (gridView == null) {
            gridView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_movie, parent, false);
        }
        // Get the current position in the MovieAdapter
        Movie currentMovie = getItem(position);

        // Find the ImageView for the movie poster in the grid view and set it to the posterImageView
        // variable
        ImageView posterImageView = gridView.findViewById(R.id.movie_poster);
        String posterMovieString = Objects.requireNonNull(currentMovie).getPosterImage();

        // This if statement checks if there is a poster image for the current movie. If the
        // response returns null set it to the posterimageplaceholder.png else set it to the
        // correct image provided.
        String picassoPosterImage = "http://image.tmdb.org/t/p/w342/" + posterMovieString;
        Picasso.get().load(picassoPosterImage)
                .error(R.drawable.posterimageplaceholder)
                .into(posterImageView);

        // Find the TextView for the movie title and set it to the current movies title
        TextView titleTextView = gridView.findViewById(R.id.movie_title);
        titleTextView.setText(currentMovie.getMovieTitle());

        final ImageView imageButton = gridView.findViewById(R.id.favorite_image_view);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.added_favorite, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new UndoFavoritesFabAdd()).show();
                imageButton.setImageResource(R.drawable.ic_favorite_solid_white_24px);
            }
        });


        return gridView;
    }


    public class UndoFavoritesFabAdd implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            Snackbar.make(view, R.string.removed_from_favorites, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}

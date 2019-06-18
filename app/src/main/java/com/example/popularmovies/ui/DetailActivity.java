package com.example.popularmovies.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Find the image and text views in the detail_activity and set them to their corresponding
        // variables

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
            movieTitleTextView.setText(detailsArray[0]);
        }

        // This if statement checks if there is a poster image for the current movie. If the
        // response returns null set it to the posterimageplaceholder.png else set it to the
        // correct image provided.

        String picassoPosterImage = "http://image.tmdb.org/t/p/w342/" + Objects.requireNonNull(detailsArray)[1];
        Picasso.get().load(picassoPosterImage)
                .error(R.drawable.posterimageplaceholder)
                .into(moviePosterImageView);

        movieReleaseDateTextView.setText(detailsArray[2]);
        movieVoteAverageTextView.setText(detailsArray[3]);
        moviePlotSynopsisTextView.setText(detailsArray[4]);

        final FloatingActionButton fab = findViewById(R.id.favorite_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.added_favorite, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new UndoFavoritesFabAdd()).show();
                fab.setImageResource(R.drawable.ic_favorite_solid_white_24px);
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

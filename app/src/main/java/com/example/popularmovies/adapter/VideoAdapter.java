package com.example.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Video;

import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<Video> {

    /**
     * Create a new {@link MovieAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param videos  is the list of {@link Movie}s to be displayed.
     */
    public VideoAdapter(Context context, ArrayList<Video> videos) {
        super(context, 0, videos);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View videoView = convertView;
        if (videoView == null) {
            videoView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_video, parent, false);
        }


        return videoView;
    }
}
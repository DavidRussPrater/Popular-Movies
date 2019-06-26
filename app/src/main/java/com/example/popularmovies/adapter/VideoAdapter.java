package com.example.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<Video> videos;
    private int videoLayout;
    private Context context;


    public VideoAdapter(List<Video> videos, int videoLayout, Context context) {
        this.videos = videos;
        this.videoLayout = videoLayout;
        this.context = context;
    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, final int position) {
        holder.videoTitle.setText(videos.get(position).getVideoTitle());

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        LinearLayout videoLayout;
        TextView videoTitle;

        public VideoViewHolder(View view) {
            super(view);
            mView = view;

            videoLayout = (LinearLayout) view.findViewById(R.id.video_layout);
            videoTitle = (TextView) view.findViewById(R.id.video_name_text_view);
        }

    }
}
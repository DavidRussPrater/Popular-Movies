package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("name")
    private String videoTitle;

    @SerializedName("key")
    private String videoUrl;


    public Video(String videoTitle, String videoUrl){
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoUrl(){
        return videoUrl;
    }

}

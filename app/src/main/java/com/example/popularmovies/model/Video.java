package com.example.popularmovies.model;

public class Video {

    private final String mVideoTitle;

    private final String mVideoKey;

    public Video(String videoTitle, String videoKey){
        mVideoTitle = videoTitle;
        mVideoKey = videoKey;
    }

    public String getVideoTitle(){
        return mVideoTitle;
    }
    public String getVideoKey(){
        return mVideoKey;
    }
}

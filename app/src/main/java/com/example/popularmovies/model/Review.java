package com.example.popularmovies.model;

public class Review {


    private final String mAuthor;

    private final String mContent;

    private final String mReviewUrl;

    public Review(String author, String content, String reviewUrl){
        mAuthor = author;
        mContent = content;
        mReviewUrl = reviewUrl;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getContent(){
        return mContent;
    }

    public String getReviewUrl(){
        return mReviewUrl;
    }

}

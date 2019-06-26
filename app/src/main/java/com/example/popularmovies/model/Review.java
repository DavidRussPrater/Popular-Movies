package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Review {


    @SerializedName("author")
    private String authorName;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String reviewUrl;


    public Review(String authorName, String content, String reviewUrl){
        this.authorName = authorName;
        this.content = content;
        this.reviewUrl = reviewUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName() {
         this.authorName = authorName;
    }

    public String getContent(){
        return content;
    }

    public void setContent() {
        this.content = content;
    }

    public String getReviewUrl(){
        return reviewUrl;
    }

    public void setReviewUrl() {
        this.reviewUrl = reviewUrl;
    }

}

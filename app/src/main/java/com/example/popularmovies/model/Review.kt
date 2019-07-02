package com.example.popularmovies.model

import android.os.Parcelable
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(@NonNull @SerializedName ("author") val authorName: String,
                  val content: String,
                  @SerializedName ("url") val reviewUrl: String) : Parcelable
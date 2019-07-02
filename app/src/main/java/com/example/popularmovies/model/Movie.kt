package com.example.popularmovies.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Movie(@PrimaryKey @NonNull @SerializedName ("id") val movieId: String,
                 @SerializedName("poster_path") val posterPath: String?,
                 val overview: String?,
                 @SerializedName ("release_date") val releaseDate: String?,
                 val title: String?,
                 @SerializedName("backdrop_path") val backdropPath: String?,
                 @SerializedName("vote_average") val voteAverage: String?) : Parcelable


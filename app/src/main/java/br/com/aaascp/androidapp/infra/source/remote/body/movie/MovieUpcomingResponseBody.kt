package br.com.aaascp.androidapp.infra.source.remote.body.movie

import com.google.gson.annotations.SerializedName

data class MovieUpcomingResponseBody(
        val id: Int,
        @SerializedName("poster_path")
        val posterPath: String,
        val title: String,
        @SerializedName("release_date")
        val releaseDate: String
)
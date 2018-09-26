package br.com.aaascp.androidapp.infra.source.local.entity

import com.google.gson.annotations.SerializedName

data class MovieUpcoming(
        val id: Int,
        @SerializedName("original_title")
        val title: String,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("release_date")
        val releaseDate: String
)
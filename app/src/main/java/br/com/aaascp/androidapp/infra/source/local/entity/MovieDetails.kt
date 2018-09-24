package br.com.aaascp.androidapp.infra.source.local.entity

import com.google.gson.annotations.SerializedName

data class MovieDetails(
        val id: Int,
        val title: String,
        val overview: String,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("release_date")
        val releaseDate: String
)

data class Genre(
        val id: Int,
        val name: String,
        val movieId: Int
)

data class MovieDetailsWithGenre(
        val genres: List<Genre> = mutableListOf(),
        val movie: MovieDetails? = null
)

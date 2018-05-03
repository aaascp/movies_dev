package br.com.aaascp.androidapp.infra.source.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import br.com.aaascp.androidapp.infra.source.remote.body.movie.Genre
import com.google.gson.annotations.SerializedName

@Entity
data class MovieDetails(
        @PrimaryKey val id: String,
        val genres: List<Genre>,
        val title: String,
        val overview: String,
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("release_date")
        val releaseDate: String
)
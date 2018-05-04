package br.com.aaascp.androidapp.infra.source.local.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import com.google.gson.annotations.SerializedName

@Entity
data class MovieDetails(
        @PrimaryKey val id: Int,
        val title: String,
        val overview: String,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("release_date")
        val releaseDate: String
)

@Entity
data class Genre(
        @PrimaryKey
        val id: Int,
        val name: String,
        val movieId: Int
)

class MovieDetailsWithGenre {
    @Embedded
    var movie: MovieDetails? = null

    @Relation(
            parentColumn = "id",
            entityColumn = "movieId")
    var genres: List<Genre> = mutableListOf()
}

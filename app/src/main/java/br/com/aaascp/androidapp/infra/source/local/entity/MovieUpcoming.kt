package br.com.aaascp.androidapp.infra.source.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieUpcoming(
        @PrimaryKey val id: Int,
        @SerializedName("original_title")
        val title: String,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("release_date")
        val releaseDate: String
)
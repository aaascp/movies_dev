package br.com.aaascp.androidapp.infra.source.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.annotation.Nullable

@Entity
data class MovieUpcoming(
        @PrimaryKey val id: Int,
        @SerializedName("original_title")
        val title: String,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("poster_path")
        @Nullable
        val posterPath: String
)
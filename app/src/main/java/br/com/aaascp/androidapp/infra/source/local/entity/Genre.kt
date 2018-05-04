package br.com.aaascp.androidapp.infra.source.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity
@ForeignKey(
        entity = MovieDetails::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = CASCADE)
data class Genre(
        @PrimaryKey
        val id: Int,
        val name: String,
        val movieId: Int
)
package br.com.aaascp.androidapp.infra.source.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Movie(
        @PrimaryKey val id: String,
        val title: String,
        val subject: String
) {
    var indexInResponse: Int = -1
}
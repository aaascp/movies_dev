package br.com.aaascp.androidapp.infra.source.local.entity

import android.arch.persistence.room.PrimaryKey

data class Genre(
        @PrimaryKey
        val id: Int,
        val name: String
)
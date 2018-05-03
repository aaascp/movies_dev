package br.com.aaascp.androidapp.infra.source.remote.body.movie

data class Subject(val title: String)

data class MovieResponseBody(
        val id: String,
        val title: String,
        val subject: Subject
)
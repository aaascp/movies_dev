package br.com.aaascp.androidapp.infra.adapter

import br.com.aaascp.androidapp.infra.source.local.entity.Movie
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieResponseBody

class MovieAdapter {
    companion object {
        fun adapt(lessons: List<MovieResponseBody>): List<Movie> {
            return lessons.map {
                Movie(it.id, it.title, it.subject.title)
            }
        }
    }
}

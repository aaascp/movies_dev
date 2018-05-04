package br.com.aaascp.androidapp.infra.adapter

import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieUpcomingResponseBody

class MovieAdapter {
    companion object {
        fun adapt(movie: List<MovieUpcomingResponseBody>): List<MovieUpcoming> {
            return movie.map {
                MovieUpcoming(it.id, it.title, it.posterPath, it.releaseDate)
            }
        }
    }
}

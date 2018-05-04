package br.com.aaascp.androidapp.infra.adapter

import br.com.aaascp.androidapp.infra.source.local.entity.Genre
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.infra.source.remote.body.movie.GenreResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieDetailsResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieUpcomingResponseBody

class MovieAdapter {
    companion object {
        fun adaptUpcoming(movies: List<MovieUpcomingResponseBody>): List<MovieUpcoming> {
            return movies.map {
                MovieUpcoming(
                        id = it.id,
                        title = it.title,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate)
            }
        }

        fun adaptDetails(movie: MovieDetailsResponseBody): MovieDetails {
            return MovieDetails(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    posterPath = movie.posterPath,
                    releaseDate = movie.releaseDate
            )

        }

        fun adaptGenre(
                genres: List<GenreResponseBody>,
                movieId: Int
        ): List<Genre> {
            return genres.map {
                Genre(
                        id = it.id,
                        name = it.name,
                        movieId = movieId
                )
            }
        }
    }
}

package br.com.aaascp.androidapp.infra.source.local.dao.movie

import br.com.aaascp.androidapp.infra.source.local.entity.Genre
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import io.reactivex.Flowable

interface MovieLocalDataSource {

    fun getUpcoming(): Flowable<List<MovieUpcoming>>

    fun saveUpcoming(upcomingMoviesList: MovieUpcoming)

    fun removeAllUpcoming()

    fun getDetails(): Flowable<MovieDetails>

    fun saveDetails(movieDetails: MovieDetails)

    fun saveDetailsGenre(movieGenre: Genre)

}
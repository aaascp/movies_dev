package br.com.aaascp.androidapp.infra.source.local.dao.movie

import br.com.aaascp.androidapp.infra.source.local.entity.*
import io.reactivex.Flowable

interface MovieLocalDataSource {

    fun getUpcoming(): Flowable<List<MovieUpcoming>>

    fun saveUpcoming(upcomingMoviesList: List<MovieUpcoming>)

    fun removeAllUpcoming()

    fun getDetails(id: Int): Flowable<MovieDetailsWithGenre>

    fun saveDetails(movieDetails: MovieDetails)

    fun saveDetailsGenre(movieGenre: List<Genre>)

}
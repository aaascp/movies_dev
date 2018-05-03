package br.com.aaascp.androidapp.infra.source.local.dao.movie

import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import io.reactivex.Maybe

interface MovieLocalDataSource {

    fun getUpcoming(): Maybe<List<MovieUpcoming>>

    fun saveUpcoming(upcomingMoviesList: MovieUpcoming)

    fun removeAllUpcoming()

}
package br.com.aaascp.androidapp.infra.repository.movie

import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import io.reactivex.Maybe

interface MovieRepository {
    fun getUpcoming(): Maybe<List<MovieUpcoming>>
}
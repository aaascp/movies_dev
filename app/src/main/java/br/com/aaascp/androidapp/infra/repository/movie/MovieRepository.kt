package br.com.aaascp.androidapp.infra.repository.movie

import br.com.aaascp.androidapp.infra.repository.Resource
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming

interface MovieRepository {
    fun getUpcomingList(): Resource<List<MovieUpcoming>>
}
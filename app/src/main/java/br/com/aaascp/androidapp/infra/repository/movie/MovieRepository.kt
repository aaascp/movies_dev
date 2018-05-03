package br.com.aaascp.androidapp.infra.repository.movie

import br.com.aaascp.androidapp.infra.repository.Listing
import br.com.aaascp.androidapp.infra.source.local.entity.Movie

interface MovieRepository {
    fun getAll(): Listing<Movie>
}
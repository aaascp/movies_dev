package br.com.aaascp.androidapp.infra.source.local.dao.movie

import android.arch.paging.DataSource
import br.com.aaascp.androidapp.infra.source.local.entity.Movie

interface MovieLocalDataSource {

    fun getAll(): DataSource.Factory<Int, Movie>

    fun save(areas: List<Movie>)

    fun removeAll()

    fun getNextIndex(): Int

}
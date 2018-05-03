package br.com.aaascp.androidapp.infra.source.local

import br.com.aaascp.androidapp.infra.source.local.dao.movie.MovieLocalDataSource

interface AppDatabase {
    fun runInTransaction(body: () -> Unit)

    fun movieDao(): MovieLocalDataSource
}
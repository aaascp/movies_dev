package br.com.aaascp.androidapp.infra.source.local

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import br.com.aaascp.androidapp.infra.source.local.entity.Movie
import br.com.aaascp.androidapp.infra.source.local.dao.movie.MovieRoomDao
import javax.inject.Singleton


@Singleton
@Database(
        entities = arrayOf(
                Movie::class),
        version = 1)
abstract class RoomDatabase : RoomDatabase(), AppDatabase {

    override fun runInTransaction(body: () -> Unit) {
        super.runInTransaction(body)
    }

    abstract override fun movieDao(): MovieRoomDao
}
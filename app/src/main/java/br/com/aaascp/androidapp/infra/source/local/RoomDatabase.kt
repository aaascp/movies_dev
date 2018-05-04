package br.com.aaascp.androidapp.infra.source.local

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.infra.source.local.dao.movie.MovieRoomDao
import javax.inject.Singleton


@Singleton
@Database(
        entities = arrayOf(
                MovieUpcoming::class),
        version = 2)
abstract class RoomDatabase : RoomDatabase(), AppDatabase {

    override fun runInTransaction(body: () -> Unit) {
        super.runInTransaction(body)
    }

    abstract override fun movieDao(): MovieRoomDao
}
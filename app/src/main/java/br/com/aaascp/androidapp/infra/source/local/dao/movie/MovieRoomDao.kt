package br.com.aaascp.androidapp.infra.source.local.dao.movie

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import br.com.aaascp.androidapp.infra.source.local.entity.Movie

@Dao
interface MovieRoomDao : MovieLocalDataSource {

    @Query("SELECT * from Movie")
    override fun getAll(): DataSource.Factory<Int, Movie>

    @Insert(onConflict = REPLACE)
    override fun save(areas: List<Movie>)

    @Query("DELETE FROM Movie")
    override fun removeAll()

    @Query("SELECT MAX(indexInResponse) + 1 FROM Movie")
    override fun getNextIndex() : Int
}

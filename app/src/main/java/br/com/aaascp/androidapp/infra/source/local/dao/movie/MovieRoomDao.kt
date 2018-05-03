package br.com.aaascp.androidapp.infra.source.local.dao.movie

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import io.reactivex.Maybe

@Dao
interface MovieRoomDao : MovieLocalDataSource {

    @Query("SELECT * from MovieUpcoming")
    override fun getUpcoming(): Maybe<List<MovieUpcoming>>

    @Insert(onConflict = REPLACE)
    override fun saveUpcoming(upcomingMoviesList: MovieUpcoming)

    @Query("DELETE FROM MovieUpcoming")
    override fun removeAllUpcoming()

}

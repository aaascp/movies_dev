package br.com.aaascp.androidapp.infra.source.local.dao.movie

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import br.com.aaascp.androidapp.infra.source.local.entity.*
import io.reactivex.Flowable

@Dao
interface MovieRoomDao : MovieLocalDataSource {

    @Query("SELECT * from MovieUpcoming")
    override fun getUpcoming(): Flowable<List<MovieUpcoming>>

    @Insert(onConflict = REPLACE)
    override fun saveUpcoming(upcomingMoviesList: MovieUpcoming)

    @Query("DELETE FROM MovieUpcoming")
    override fun removeAllUpcoming()

    @Transaction
    @Query("SELECT * from MovieDetails WHERE id=:id")
    override fun getDetails(id: Int): Flowable<MovieDetailsWithGenre>

    @Insert(onConflict = REPLACE)
    override fun saveDetails(movieDetails: MovieDetails)

    @Insert(onConflict = REPLACE)
    override fun saveDetailsGenre(movieGenre: Genre)
}

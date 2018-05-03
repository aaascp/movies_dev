package br.com.aaascp.androidapp.infra.repository.movie

import android.util.Log
import br.com.aaascp.androidapp.infra.adapter.MovieAdapter
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import br.com.aaascp.androidapp.util.TableUtils
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieWithLocalDataRepository @Inject constructor(
        private val db: AppDatabase,
        private val endpoint: MovieEndpoint
) : MovieRepository {

    override fun getUpcomingList(): Maybe<List<MovieUpcoming>> {
        endpoint.getUpcoming()
                .map { MovieAdapter.adapt(it.results) }
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { upcomingMoviesList -> insertUpcomingMoviesListIntoDb(upcomingMoviesList) },
                        { error ->
                            run {
                                Log.d("Andre", error.message)
                            }
                        })

        return db.movieDao()
                .getUpcoming()
                .subscribeOn(Schedulers.io())

    }

    private fun insertUpcomingMoviesListIntoDb(
            upcomingMoviesList: List<MovieUpcoming>
    ) {
        db.runInTransaction({
            db.movieDao().removeAllUpcoming()
            upcomingMoviesList.map {
                db.movieDao().saveUpcoming(it)
            }
        })

        TableUtils().setAreaTableLastUpdate()
    }
}



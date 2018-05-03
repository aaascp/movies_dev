package br.com.aaascp.androidapp.infra.repository.movie

import br.com.aaascp.androidapp.infra.adapter.MovieAdapter
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import br.com.aaascp.androidapp.util.TableUtils
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieWithLocalDataRepository @Inject constructor(
        private val db: AppDatabase,
        private val endpoint: MovieEndpoint
) : MovieRepository {

    override fun getUpcoming(): Maybe<List<MovieUpcoming>> {
        endpoint.getUpcoming()
                .map { MovieAdapter.adapt(it.result) }
                .subscribe(
                        { upcomingMoviesList -> insertUpcomingMoviesListIntoDb(upcomingMoviesList) },
                        { error -> /* TODO */ })

        return db.movieDao().getUpcoming();

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



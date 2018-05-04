package br.com.aaascp.androidapp.infra.repository.movie

import android.util.Log
import br.com.aaascp.androidapp.infra.adapter.MovieAdapter
import br.com.aaascp.androidapp.infra.repository.Resource
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import br.com.aaascp.androidapp.util.TableUtils
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieWithLocalDataRepository @Inject constructor(
        private val db: AppDatabase,
        private val endpoint: MovieEndpoint
) : MovieRepository {

    override fun getUpcomingList(): Resource<List<MovieUpcoming>> {
        val networkState: Subject<NetworkState> = PublishSubject.create()
        networkState.onNext(NetworkState.LOADING)

        endpoint.getUpcoming()
                .map { MovieAdapter.adapt(it.results) }
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    networkState.onNext(NetworkState.LOADED)
                    insertUpcomingMoviesListIntoDb(it)
                }.doOnError {
                    networkState.onNext(NetworkState.error(it.message))
                }.subscribe()

        val upcomingMovies =
                db.movieDao()
                        .getUpcoming()
                        .subscribeOn(Schedulers.io())

        return Resource(
                upcomingMovies,
                networkState)

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



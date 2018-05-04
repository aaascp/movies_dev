package br.com.aaascp.androidapp.infra.repository.movie

import android.util.Log
import br.com.aaascp.androidapp.infra.adapter.MovieAdapter
import br.com.aaascp.androidapp.infra.repository.Resource
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.entity.Genre
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
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
                .map { MovieAdapter.adaptUpcoming(it.results) }
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

    override fun getDetails(id: Int): Resource<MovieDetailsWithGenre> {
        val networkState: Subject<NetworkState> = PublishSubject.create()
        networkState.onNext(NetworkState.LOADING)

        endpoint.getDetails(id)
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    Log.d("Andre", it.toString())
                    networkState.onNext(NetworkState.LOADED)
                    insertMoviesDetailsIntoDb(
                            MovieAdapter.adaptDetails(it),
                            MovieAdapter.adaptGenre(
                                    it.genres,
                                    it.id))
                }.doOnError {
                    networkState.onNext(NetworkState.error(it.message))
                }.subscribe()

        val movieDetails =
                db.movieDao()
                        .getDetails(id)
                        .subscribeOn(Schedulers.io())

        return Resource(
                movieDetails,
                networkState)

    }

    private fun insertUpcomingMoviesListIntoDb(
            upcomingMoviesList: List<MovieUpcoming>
    ) {
        db.runInTransaction({
            db.movieDao().removeAllUpcoming()
            db.movieDao().saveUpcoming(upcomingMoviesList)
        })
    }

    private fun insertMoviesDetailsIntoDb(
            movieDetails: MovieDetails,
            genres: List<Genre>
    ) {
        db.movieDao().saveDetails(movieDetails)
        insertMovieGenresIntoDb(genres)
    }

    private fun insertMovieGenresIntoDb(genres: List<Genre>) {
        db.movieDao().saveDetailsGenre(genres)

    }
}



package br.com.aaascp.androidapp.infra.repository.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import br.com.aaascp.androidapp.infra.adapter.MovieAdapter
import br.com.aaascp.androidapp.infra.repository.Listing
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.entity.Movie
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import br.com.aaascp.androidapp.util.TableUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieWithLocalDataRepository @Inject constructor(
        private val db: AppDatabase,
        private val endpoint: MovieEndpoint
) : MovieRepository {

    override fun getAll(): Listing<Movie> {

        val boundaryCallback =
                MovieBoundaryCallback(
                        endpoint,
                        this::insertResultIntoDb)

        val dataSourceFactory = db.movieDao().getAll()
        val builder =
                LivePagedListBuilder(
                        dataSourceFactory,
                        20)
                        .setBoundaryCallback(boundaryCallback)

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger, {
            refresh()
        })

        return Listing(
                pagedList = builder.build(),
                networkState = boundaryCallback.networkState,
                retry = {
                    boundaryCallback.helper.retryAllFailed()
                },
                refresh = {
                    refreshTrigger.value = null
                },
                refreshState = refreshState
        )
    }

    private fun insertResultIntoDb(areas: List<Movie>?) {
        areas?.let {
            db.runInTransaction {
                val start = db.movieDao().getNextIndex()
                val items = it.mapIndexed { index, area ->
                    area.indexInResponse = start + index
                    area
                }
                db.movieDao().save(items)
            }
        }

        TableUtils().setAreaTableLastUpdate()
    }

    private fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING

        endpoint.getAll().enqueue(
                RepositoryCallbackBase({
                    db.runInTransaction {
                        db.movieDao().removeAll()
                        insertResultIntoDb(MovieAdapter.adapt(it.data))
                    }
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    networkState.value = NetworkState.error(it.message)
                }))
        return networkState
    }
}


package br.com.aaascp.androidapp.infra.repository.movie

import android.arch.paging.PagedList
import android.arch.paging.PagingRequestHelper
import android.support.annotation.MainThread
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.adapter.MovieAdapter
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.entity.Movie
import br.com.aaascp.androidapp.infra.source.remote.body.MovieResponse
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import br.com.aaascp.androidapp.util.createStatusLiveData

class MovieBoundaryCallback(
        private val endpoint: MovieEndpoint,
        private val handleResponse: (List<Movie>?) -> Unit)
    : PagedList.BoundaryCallback<Movie>() {

    val helper = MainApplication.component.getPagingRequestHelper()
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            endpoint.getAll()
                    .enqueue(createWebserviceCallback(it))
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            endpoint.getAll(
                    itemAtEnd.indexInResponse,
                    20)
                    .enqueue(createWebserviceCallback(it))
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: Movie) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun createWebserviceCallback(
            pagingRequestHelper: PagingRequestHelper.Request.Callback
    ): RepositoryCallbackBase<MovieResponse> {

        return RepositoryCallbackBase({
            handleResponse(MovieAdapter.adapt(it.data))
            pagingRequestHelper.recordSuccess()
        }, {
            pagingRequestHelper.recordFailure(it)
        })
    }
}
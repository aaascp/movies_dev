package br.com.aaascp.androidapp.presentation.movie.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.ResourceState
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetails
import br.com.aaascp.androidapp.infra.source.local.entity.MovieDetailsWithGenre
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.Subject


class MovieDetailsViewModel : ViewModel() {

    val repository: MovieRepository = MainApplication.component.getMovieRepository()

    val movieDetails = MutableLiveData<MovieDetailsWithGenre>()
    val state = MutableLiveData<ResourceState>()
    val networkState = MutableLiveData<NetworkState>()

    fun getDetails(id: Int) {
        val result = repository.getDetails(id)
        observeResource(result.value)
        observeState(result.state)
        observeNetworkState(result.networkState)
    }

    private fun observeNetworkState(networkState: Subject<NetworkState>) {
        networkState
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { this.networkState.value = it }
                .subscribe()
    }

    private fun observeState(state: Observable<ResourceState>) {
        state.observeOn(AndroidSchedulers.mainThread())
                .doOnNext { this.state.value = it }
                .subscribe()
    }

    private fun observeResource(resource: Flowable<MovieDetailsWithGenre>) {
        resource.observeOn(AndroidSchedulers.mainThread())
                .doOnNext { this.movieDetails.value = it }
                .subscribe()
    }
}


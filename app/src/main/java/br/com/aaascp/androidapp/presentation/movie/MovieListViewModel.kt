package br.com.aaascp.androidapp.presentation.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository
import javax.inject.Inject


class MovieListViewModel : ViewModel() {

    @Inject
    lateinit var repository: MovieRepository

    private var start = MutableLiveData<Boolean>()

    init {
        MainApplication.component.inject(this)
        this.start.value = true
    }

    private val repositoryResult = map(start, {
        repository.getAll()
    })

    val areas = switchMap(repositoryResult, { it.pagedList })!!
    val listState = switchMap(repositoryResult, { it.listState })!!
    val refreshState = switchMap(repositoryResult, { it.refreshState })!!

    fun refresh() {
        repositoryResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repositoryResult?.value
        listing?.retry?.invoke()
    }
}


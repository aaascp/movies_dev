package br.com.aaascp.androidapp.presentation.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.util.Log
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository
import br.com.aaascp.androidapp.infra.source.local.entity.MovieUpcoming
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UpcomingMoviesListViewModel : ViewModel() {

    @Inject
    lateinit var repository: MovieRepository

    val upcomingMoviesList = MutableLiveData<List<MovieUpcoming>>()

    init {
        MainApplication.component.inject(this)
        repository.getUpcomingList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    upcomingMoviesList.value = it})
    }


}


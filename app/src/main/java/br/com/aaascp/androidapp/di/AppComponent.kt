package br.com.aaascp.androidapp.di

import android.content.Context
import br.com.aaascp.androidapp.di.module.ApplicationModule
import br.com.aaascp.androidapp.di.module.infra.DatabaseModule
import br.com.aaascp.androidapp.di.module.infra.EndpointModule
import br.com.aaascp.androidapp.infra.repository.movie.MovieRepository
import br.com.aaascp.androidapp.presentation.movie.upcoming.UpcomingMoviesListViewModel
import dagger.Component
import java.util.concurrent.Executor
import javax.inject.Singleton

@Singleton
@Component(
        modules = arrayOf(
                ApplicationModule::class,
                DatabaseModule::class,
                EndpointModule::class))
interface AppComponent {

    fun getMovieRepository(): MovieRepository

    fun inject(upcomingMoviesListViewModel: UpcomingMoviesListViewModel)

    fun getExecutor(): Executor

    fun getApplicationContext(): Context
}
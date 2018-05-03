package br.com.aaascp.androidapp.di

import android.arch.paging.PagingRequestHelper
import android.content.Context
import br.com.aaascp.androidapp.di.module.ApplicationModule
import br.com.aaascp.androidapp.di.module.infra.DatabaseModule
import br.com.aaascp.androidapp.di.module.infra.EndpointModule
import br.com.aaascp.androidapp.presentation.movie.MovieListViewModel
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

    fun inject(movieListViewModel: MovieListViewModel)

    fun getExecutor(): Executor

    fun getPagingRequestHelper(): PagingRequestHelper

    fun getApplicationContext(): Context
}
package br.com.aaascp.androidapp.di.module.infra

import android.arch.paging.PagingRequestHelper
import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Singleton


@Module
open class EndpointModule {

    @Singleton
    @Provides
    fun providesPagingRequestHelper(executor: Executor): PagingRequestHelper {
        return PagingRequestHelper(executor)
    }

    @Singleton
    @Provides
    open fun providesAreaEndpoint(): MovieEndpoint {
        return ServiceGenerator.createService(MovieEndpoint::class.java)
    }
}
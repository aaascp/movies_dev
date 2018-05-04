package br.com.aaascp.androidapp.di.module.infra

import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
open class EndpointModule {

    @Singleton
    @Provides
    open fun providesMovieEndpoint(): MovieEndpoint {
        return ServiceGenerator.createService(MovieEndpoint::class.java)
    }
}
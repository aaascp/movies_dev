package br.com.aaascp.androidapp.di.module.infra

import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.MovieMockEndpoint
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

class EndpointMockModule : EndpointModule() {

    override fun providesAreaEndpoint(): MovieEndpoint {
        mockRetrofit.create(MovieEndpoint::class.java)
        return MovieMockEndpoint(mockRetrofit.create(MovieEndpoint::class.java))    }

    private val mockRetrofit =
            MockRetrofit.Builder(ServiceGenerator.retrofit)
                    .networkBehavior(NetworkBehavior.create())
                    .build()

}
package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.MovieResponse
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate

class MovieMockEndpoint(
        private val delegate: BehaviorDelegate<MovieEndpoint>
) : MovieEndpoint {

    override fun getAll(
            start: Int,
            end: Int
    ): Call<MovieResponse> {

        val data = FakeData().getAreas(start, end)

        return delegate
                .returningResponse(data)
                .getAll()
    }
}
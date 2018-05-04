package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.MovieDetailsResponse
import br.com.aaascp.androidapp.infra.source.remote.body.MovieUpcomingResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieEndpoint {

    @GET("/3/movie/upcoming")
    fun getUpcoming(
            @Query("page") page: Int  = 1,
            @Query("region") region: String = "BR"
    ): Single<MovieUpcomingResponse>

    @GET("/3/movie/{id}")
    fun getDetails(
            @Path("id") id: Int,
            @Query("region") language: String = "pt-BR"
    ): Single<MovieDetailsResponse>
}
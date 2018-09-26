package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.ResultsResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieDetailsResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.movie.MovieUpcomingResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieEndpoint {

    @GET("/3/movie/upcoming")
    fun getUpcoming(
            @Query("page") page: Int  = 1,
            @Query("region") region: String = "BR"
    ): Call<ResultsResponseBody<List<MovieUpcomingResponseBody>>>

    @GET("/3/movie/{id}")
    fun getDetails(
            @Path("id") id: Int,
            @Query("region") language: String = "pt-BR"
    ): Call<MovieDetailsResponseBody>
}
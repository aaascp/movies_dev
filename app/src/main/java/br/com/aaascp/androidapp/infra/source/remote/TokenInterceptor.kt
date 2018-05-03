package br.com.aaascp.androidapp.infra.source.remote

import br.com.aaascp.androidapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val urlWithToken = request.url()
                .newBuilder()
                .addQueryParameter(
                        BuildConfig.API_KEY_NAME,
                        BuildConfig.API_KEY)
                .build()

        val requestWithToken =
                request.newBuilder()
                        .url(urlWithToken)
                        .build()

        return chain.proceed(requestWithToken)
    }
}
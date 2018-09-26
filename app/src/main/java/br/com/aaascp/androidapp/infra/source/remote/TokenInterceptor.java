package br.com.aaascp.androidapp.infra.source.remote;

import android.util.Log;

import java.io.IOException;

import br.com.aaascp.androidapp.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl urlWithToken = request.url()
                .newBuilder()
                .addQueryParameter(
                        BuildConfig.API_KEY_NAME,
                        BuildConfig.API_KEY)
                .build();

        Request requestWithToken = request.newBuilder()
                .url(urlWithToken)
                .build();

        return  chain.proceed(requestWithToken);
    }
}

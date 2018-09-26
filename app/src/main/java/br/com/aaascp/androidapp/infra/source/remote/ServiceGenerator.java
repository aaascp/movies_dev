package br.com.aaascp.androidapp.infra.source.remote;

import br.com.aaascp.androidapp.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String BASE_URL = BuildConfig.API_URL;

    private final Retrofit retrofit;

    public ServiceGenerator() {
        final HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY);

        final TokenInterceptor tokenInterceptor = new TokenInterceptor();
        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .addInterceptor(loggingInterceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        this.retrofit = retrofitBuilder.build();
    }

    public <S> S createService(Class<S> serviceClass) {
        return this.retrofit.create(serviceClass);
    }

}

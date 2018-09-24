package br.com.aaascp.androidapp.infra.source.remote;

import br.com.aaascp.androidapp.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String BASE_URL = BuildConfig.API_URL;

    private final HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private final TokenInterceptor tokenInterceptor = new TokenInterceptor();

    private final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(this.loggingInterceptor)
            .addInterceptor(this.tokenInterceptor);

    private final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(this.httpClientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private final Retrofit retrofit = this.retrofitBuilder.build();

    public <S> S createService(Class<S> serviceClass) {
        return this.retrofit.create(serviceClass);
    }

}

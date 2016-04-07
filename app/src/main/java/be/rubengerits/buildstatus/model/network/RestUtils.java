package be.rubengerits.buildstatus.model.network;

import android.support.annotation.NonNull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestUtils {
    @NonNull
    public static Retrofit createRetrofit(String url, Interceptor... interceptors) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.interceptors().add(interceptor);

        if (interceptors != null) {
            for (Interceptor inter : interceptors) {
                clientBuilder.interceptors().add(inter);
            }
        }
        builder.client(clientBuilder.build());

        return builder.build();
    }
}


package be.rubengerits.buildstatus.model.network;

import android.support.annotation.NonNull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
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

        OkHttpClient client = new OkHttpClient();
//        HttpLoggingInterceptor interceptor = new Inter();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        client.interceptors().add(interceptor);

        if (interceptors != null) {
            for (Interceptor inteceptor : interceptors) {
                client.interceptors().add(inteceptor);
            }
        }
        builder.client(client);

        return builder.build();
    }
}

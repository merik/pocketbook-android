package com.dmc.pocketbook.dagger;

import android.content.Context;

import com.dmc.pocketbook.data.AppRepository;
import com.dmc.pocketbook.data.remote.AppDataRemoteStore;
import com.dmc.pocketbook.mock.OfflineMockInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chi on 16/1/18.
 */
@Module
public class DataModule {
    String baseUrl;
    Context context;

    public DataModule(String baseUrl, Context context) {
        this.baseUrl = baseUrl;
        this.context = context;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new OfflineMockInterceptor(context));

        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();

    }

    @Provides
    @Singleton
    AppDataRemoteStore provideAppDataRemoteStore() {
        return new AppDataRemoteStore();
    }

    @Provides
    @Singleton
    AppRepository provideAppRepository() {
        return new AppRepository();
    }


}

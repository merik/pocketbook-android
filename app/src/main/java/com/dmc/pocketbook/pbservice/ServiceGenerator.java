package com.dmc.pocketbook.pbservice;

import android.content.Context;

import com.dmc.pocketbook.mock.OfflineMockInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by chi on 13/1/18.
 */

public class ServiceGenerator {

    private static String EndPoint = "http://api.pocketbook.com.au";
    private static OkHttpClient.Builder mHttpClient;
    private static Retrofit.Builder mBuilder;

    public static PBAPI createService(Context context) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new OfflineMockInterceptor(context));

        //mHttpClient = new OkHttpClient.Builder();
        mBuilder = new Retrofit.Builder()
                .baseUrl(EndPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        OkHttpClient client = builder.build(); // mHttpClient.build();
        Retrofit retrofit = mBuilder.client(client).build();
        return retrofit.create(PBAPI.class);
    }
}

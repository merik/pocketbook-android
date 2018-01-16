package com.dmc.pocketbook.data.remote;

import android.content.Context;

import com.dmc.pocketbook.PocketBookApp;
import com.dmc.pocketbook.data.AppDataStore;
import com.dmc.pocketbook.models.Transaction;
import com.dmc.pocketbook.pbservice.PBAPI;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by chi on 16/1/18.
 */
@Singleton
public class AppDataRemoteStore implements AppDataStore{
    @Inject
    Retrofit retrofit;

    public AppDataRemoteStore() {
        PocketBookApp.getAppComponent().inject(this);
    }

    public Observable<List<Transaction>> fetchTransactions(String userId) {

        return retrofit.create(PBAPI.class).fetchTransactions("1");

    }
}

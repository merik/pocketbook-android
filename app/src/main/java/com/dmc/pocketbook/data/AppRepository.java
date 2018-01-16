package com.dmc.pocketbook.data;

import com.dmc.pocketbook.PocketBookApp;
import com.dmc.pocketbook.data.remote.AppDataRemoteStore;
import com.dmc.pocketbook.models.Transaction;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by chi on 16/1/18.
 */
@Singleton
public class AppRepository implements AppDataStore {

    @Inject
    AppDataRemoteStore appDataRemoteStore;


    public AppRepository() {
        PocketBookApp.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<Transaction>> fetchTransactions(String userId) {
        return appDataRemoteStore.fetchTransactions(userId);
    }
}

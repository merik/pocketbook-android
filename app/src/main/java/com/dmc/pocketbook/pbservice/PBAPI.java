package com.dmc.pocketbook.pbservice;

import com.dmc.pocketbook.models.Transaction;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chi on 13/1/18.
 */

public interface PBAPI {

    @GET("/transactions/{userId}")
    Observable<List<Transaction>> fetchTransactions(@Path("userId") String userId);

}

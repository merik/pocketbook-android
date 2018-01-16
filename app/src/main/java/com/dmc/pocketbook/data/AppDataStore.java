package com.dmc.pocketbook.data;

import com.dmc.pocketbook.models.Transaction;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by chi on 16/1/18.
 */

public interface AppDataStore {
    Observable<List<Transaction>> fetchTransactions(String userId);
}

package com.dmc.pocketbook.models;

import com.dmc.pocketbook.pbservice.PBService;
import com.dmc.pocketbook.pbservice.ServiceGenerator;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by chi on 16/1/18.
 */

public class TransactionRepository {
    public Observable<List<Transaction>> fetchTransactions() {
        return PBService.getInstance().fetchTransactions("1");
    }
}

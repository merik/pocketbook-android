package com.dmc.pocketbook.pbservice;

import android.content.Context;

import com.dmc.pocketbook.models.Transaction;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by chi on 13/1/18.
 */

public class PBService {

    Context mContext;
    private static final PBService ourInstance = new PBService();

    public static PBService getInstance() {
        return ourInstance;
    }

    public void initContext(Context context) {
        this.mContext  = context;
    }
    private PBService() {
    }

    //private PBAPI pbAPI;

    public Observable<List<Transaction>> fetchTransactions(String userId) {

        PBAPI pbAPI = ServiceGenerator.createService(mContext);

        return pbAPI.fetchTransactions(userId);

    }
}

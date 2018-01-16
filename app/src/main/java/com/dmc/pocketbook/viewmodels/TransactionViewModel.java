package com.dmc.pocketbook.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.dmc.pocketbook.models.Transaction;

/**
 * Created by chi on 16/1/18.
 */

public class TransactionViewModel extends ViewModel {
    private final MutableLiveData<Transaction> selected = new MutableLiveData<>();
    public void select(Transaction transaction) {
        selected.setValue(transaction);
    }
    public LiveData<Transaction> getSelected() {
        return selected;
    }
}

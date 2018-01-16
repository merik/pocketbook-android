package com.dmc.pocketbook.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.dmc.pocketbook.models.Transaction;
import com.dmc.pocketbook.models.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chi on 16/1/18.
 */

public class RecentTransactionViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<Transaction>> transactions = new MutableLiveData<>();
    private final TransactionRepository transactionRepository;

    RecentTransactionViewModel(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
    public MutableLiveData<List<Transaction>> transactions() {
        return transactions;
    }

    public void loadTransactions() {
        disposables.add(transactionRepository.fetchTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Transaction>>() {
                               @Override
                               public void accept(List<Transaction> recentTransactions) throws Exception {
                                   //handleTransactions(transactions);
                                   transactions.setValue(recentTransactions);
                               }

                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                //Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show();
                                // just ignore for now and returns empty list
                                transactions.setValue(new ArrayList<Transaction>());
                            }
                        })
        );
    }
}

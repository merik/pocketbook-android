package com.dmc.pocketbook.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.dmc.pocketbook.models.TransactionRepository;

/**
 * Created by chi on 16/1/18.
 */

public class RecentTransactionViewModelFactory implements ViewModelProvider.Factory {

    private final TransactionRepository transactionRepository;

    public RecentTransactionViewModelFactory(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecentTransactionViewModel.class)) {
            return (T) new RecentTransactionViewModel(transactionRepository);
        }
        return null;
    }
}

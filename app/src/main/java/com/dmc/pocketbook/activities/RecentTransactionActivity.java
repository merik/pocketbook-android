package com.dmc.pocketbook.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dmc.pocketbook.R;
import com.dmc.pocketbook.fragments.RecentTransactionFragment;
import com.dmc.pocketbook.fragments.TransactionFragment;
import com.dmc.pocketbook.models.Transaction;
import com.dmc.pocketbook.viewmodels.TransactionViewModel;


public class RecentTransactionActivity extends AppCompatActivity implements RecentTransactionFragment.OnRecentTransactionFragmentListener, TransactionFragment.OnTransactionFragmentListener {

    TransactionViewModel selectedTransaction;

    RecentTransactionFragment recentTransactionFragment;
    TransactionFragment transactionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_transaction);
        recentTransactionFragment = RecentTransactionFragment.newInstance();
        selectedTransaction = ViewModelProviders.of(this).get(TransactionViewModel.class);
        transactionFragment = new TransactionFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_placeholder, recentTransactionFragment).commit();
        }


    }

    public void showTransactionDetails(Transaction transaction) {
        selectedTransaction.select(transaction);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_placeholder, transactionFragment).commit();

    }

    @Override
    public void onRecentTransactionBackPressed() {
        finish();
    }

    @Override
    public void onTransactionClicked(Transaction transaction) {
        showTransactionDetails(transaction);
    }

    @Override
    public void onTransactionFragmentClosePressed() {
        getSupportFragmentManager().beginTransaction().remove(transactionFragment).commit();
    }
}

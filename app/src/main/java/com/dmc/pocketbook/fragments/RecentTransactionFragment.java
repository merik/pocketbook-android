package com.dmc.pocketbook.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dmc.pocketbook.R;

import com.dmc.pocketbook.helpers.BottomNavigationViewHelper;
import com.dmc.pocketbook.models.Transaction;
import com.dmc.pocketbook.models.TransactionRepository;
import com.dmc.pocketbook.transactionrecyclerview.SectionHeader;
import com.dmc.pocketbook.transactionrecyclerview.SimpleDividerItemDecoration;
import com.dmc.pocketbook.transactionrecyclerview.TransactionAdapter;
import com.dmc.pocketbook.viewmodels.RecentTransactionViewModel;
import com.dmc.pocketbook.viewmodels.RecentTransactionViewModelFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class RecentTransactionFragment extends Fragment implements TransactionAdapter.OnTransactionClickListener {
    @BindView(R.id.table_transactions)
    RecyclerView recyclerView;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    TransactionAdapter adapter;
    List<SectionHeader> sections;


    private OnRecentTransactionFragmentListener mListener;
    private Unbinder unbinder;
    private RecentTransactionViewModel transactionViewModel;
    private RecentTransactionViewModelFactory transactionViewModelFactory;


    Context mContext;


    public RecentTransactionFragment() {
        // Required empty public constructor
    }


    public static RecentTransactionFragment newInstance() {
        RecentTransactionFragment fragment = new RecentTransactionFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContext = getActivity();

        View rootView = inflater.inflate(R.layout.fragment_recent_transactions, container, false);
        unbinder = ButterKnife.bind(this, rootView);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));

        sections = new ArrayList<>();
        adapter = new TransactionAdapter(mContext, sections, this);
        recyclerView.setAdapter(adapter);

        transactionViewModelFactory = new RecentTransactionViewModelFactory(new TransactionRepository());

        transactionViewModel = ViewModelProviders.of(this, transactionViewModelFactory).get(RecentTransactionViewModel.class);
        transactionViewModel.transactions().observe(this, transactions -> handleTransactions(transactions));

        transactionViewModel.loadTransactions();


        return rootView;
    }


    private void handleTransactions(List<Transaction> transactions) {
        Collections.sort(transactions);
        // create sections
        sections.clear();

        ArrayList<Transaction> items = new ArrayList<>();

        for (Transaction t: transactions) {
            if (items.size() == 0) {
                items.add(t);
                continue;
            }
            //
            if (items.get(0).getDisplayDate().equalsIgnoreCase(t.getDisplayDate())) {
                items.add(t);
                continue;
            }


            sections.add(new SectionHeader(items, items.get(0).getDisplayDate().toUpperCase()));
            items = new ArrayList<>();
            items.add(t);

        }
        if (items.size() > 0) {
            sections.add(new SectionHeader(items, items.get(0).getDisplayDate().toUpperCase()));

        }
        adapter.notifyDataChanged(sections);
    }

    @Override
    public void onItemClick(Transaction transaction) {
        if (mListener != null) {
            mListener.onTransactionClicked(transaction);
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_track:
                    return true;
                case R.id.navigation_safely_spend:
                    return true;
                case R.id.navigation_notifications:
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };

    @OnClick(R.id.image_back)
    public void onBackPressed() {
        if (mListener != null) {
            mListener.onRecentTransactionBackPressed();
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecentTransactionFragmentListener) {
            mListener = (OnRecentTransactionFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecentTransactionFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    
    public interface OnRecentTransactionFragmentListener {
        void onRecentTransactionBackPressed();
        void onTransactionClicked(Transaction transaction);
    }
}

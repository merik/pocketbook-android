package com.dmc.pocketbook.fragments;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.dmc.pocketbook.R;

import com.dmc.pocketbook.helpers.BottomNavigationViewHelper;
import com.dmc.pocketbook.models.Transaction;
import com.dmc.pocketbook.pbservice.PBService;
import com.dmc.pocketbook.transactionrecyclerview.SectionHeader;
import com.dmc.pocketbook.transactionrecyclerview.SimpleDividerItemDecoration;
import com.dmc.pocketbook.transactionrecyclerview.TransactionAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecentTransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecentTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentTransactionFragment extends Fragment implements TransactionAdapter.OnTransactionClickListener {
    RecyclerView recyclerView;
    TransactionAdapter adapter;

    List<SectionHeader> sections;


    private OnRecentTransactionFragmentListener mListener;

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
        BottomNavigationView navigation = rootView.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationViewHelper.disableShiftMode(navigation);

        ImageView imgBack = rootView.findViewById(R.id.image_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = rootView.findViewById(R.id.table_transactions);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));

        sections = new ArrayList<>();
        adapter = new TransactionAdapter(mContext, sections, this);
        recyclerView.setAdapter(adapter);


        fetchTransactions();

        return rootView;
    }

    private void fetchTransactions() {

        PBService.getInstance().fetchTransactions("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Transaction>>() {
                               @Override
                               public void accept(List<Transaction> transactions) throws Exception {
                                   handleTransactions(transactions);
                               }

                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


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
    private void onBackPressed() {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecentTransactionFragmentListener {
        void onRecentTransactionBackPressed();
        void onTransactionClicked(Transaction transaction);
    }
}

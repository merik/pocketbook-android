package com.dmc.pocketbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dmc.pocketbook.helpers.BottomNavigationViewHelper;
import com.dmc.pocketbook.models.Transaction;
import com.dmc.pocketbook.pbservice.PBService;
import com.dmc.pocketbook.transactionrecyclerview.SectionHeader;
import com.dmc.pocketbook.transactionrecyclerview.SimpleDividerItemDecoration;
import com.dmc.pocketbook.transactionrecyclerview.TransactionAdapter;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RecentTransactionActivity extends AppCompatActivity implements TransactionAdapter.OnTransactionClickListener {


    RecyclerView recyclerView;
    TransactionAdapter adapter;

    List<SectionHeader> sections;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_transaction);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationViewHelper.disableShiftMode(navigation);

        ImageView imgBack = findViewById(R.id.image_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.table_transactions);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        sections = new ArrayList<>();
        adapter = new TransactionAdapter(this, sections, this);
        recyclerView.setAdapter(adapter);


        fetchTransactions();
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
                                Toast.makeText(RecentTransactionActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, TransactionActivity.class);
        intent.putExtra("transaction", transaction);
        startActivity(intent);
    }
}

package com.dmc.pocketbook.transactionrecyclerview;

/**
 * Created by chi on 13/1/18.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;




import com.dmc.pocketbook.R;
import com.dmc.pocketbook.models.Transaction;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;


public class TransactionAdapter extends SectionRecyclerViewAdapter<SectionHeader, Transaction, SectionViewHolder, TransactionViewHolder> implements TransactionViewHolder.OnTransactionViewHolderClickListener {

    @Override
    public void onTransactionClick(Transaction transaction) {
        listener.onItemClick(transaction);
    }

    public interface OnTransactionClickListener {
        void onItemClick(Transaction transaction);
    }

    private final OnTransactionClickListener listener;

    Context context;

    public TransactionAdapter(Context context, List<SectionHeader> sectionItemList, OnTransactionClickListener listener) {
        super(context, sectionItemList);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_header, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public TransactionViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item, childViewGroup, false);
        return new TransactionViewHolder(view, this);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader section) {
        sectionViewHolder.title.setText(section.sectionTitle);
    }

    @Override
    public void onBindChildViewHolder(TransactionViewHolder childViewHolder, int sectionPosition, int childPosition, Transaction child) {
        childViewHolder.dayLabel.setText(child.getDay());
        childViewHolder.monthLabel.setText(child.getMonth());
        childViewHolder.nameLabel.setText(child.name);
        childViewHolder.amountLabel.setText(child.getAmountAsMoney());
        if (child.hasCategory()) {
            childViewHolder.categoryLabel.setText(child.main_category);
            int gray = ContextCompat.getColor(context, R.color.colorGray);
            childViewHolder.categoryLabel.setTextColor(gray);
            childViewHolder.infoIcon.setVisibility(View.GONE);
        } else {
            childViewHolder.categoryLabel.setText("Uncategorised");
            int amber = ContextCompat.getColor(context, R.color.colorAmber);
            childViewHolder.categoryLabel.setTextColor(amber);
            childViewHolder.infoIcon.setVisibility(View.VISIBLE);
        }
        if (childPosition == 0) {
            int green = ContextCompat.getColor(context, R.color.colorGreen);
            childViewHolder.amountLabel.setTextColor(green);

        } else {
            int black = ContextCompat.getColor(context, R.color.textColorBlack);
            childViewHolder.amountLabel.setTextColor(black);

        }
        childViewHolder.setTransaction(child);
    }



}
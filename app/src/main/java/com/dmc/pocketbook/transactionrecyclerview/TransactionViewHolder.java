package com.dmc.pocketbook.transactionrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmc.pocketbook.R;
import com.dmc.pocketbook.models.Transaction;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chi on 13/1/18.
 */

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    public interface OnTransactionViewHolderClickListener {
        void onTransactionClick(Transaction transaction);
    }

    private final OnTransactionViewHolderClickListener listener;
    @BindView(R.id.text_day_rvitem)
    TextView dayLabel;

    @BindView(R.id.text_month_rvitem)
    TextView monthLabel;

    @BindView(R.id.text_name_rvitem)
    TextView nameLabel;

    @BindView(R.id.text_category_rvitem)
    TextView categoryLabel;

    @BindView(R.id.text_amount_rvitem)
    TextView amountLabel;

    @BindView(R.id.image_info_rvitem)
    ImageView infoIcon;

    private Transaction transaction;

    public TransactionViewHolder(View itemView, final TransactionViewHolder.OnTransactionViewHolderClickListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTransactionClick(transaction);
            }
        });
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}

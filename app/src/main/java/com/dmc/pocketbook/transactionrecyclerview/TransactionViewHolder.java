package com.dmc.pocketbook.transactionrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmc.pocketbook.R;
import com.dmc.pocketbook.models.Transaction;

/**
 * Created by chi on 13/1/18.
 */

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    public interface OnTransactionViewHolderClickListener {
        void onTransactionClick(Transaction transaction);
    }

    private final OnTransactionViewHolderClickListener listener;

    TextView dayLabel;
    TextView monthLabel;
    TextView nameLabel;
    TextView categoryLabel;
    TextView amountLabel;
    ImageView infoIcon;

    private Transaction transaction;

    public TransactionViewHolder(View itemView, final TransactionViewHolder.OnTransactionViewHolderClickListener listener) {
        super(itemView);

        this.listener = listener;

        dayLabel = (TextView) itemView.findViewById(R.id.text_day_rvitem);
        monthLabel = itemView.findViewById(R.id.text_month_rvitem);
        nameLabel = itemView.findViewById(R.id.text_name_rvitem);
        categoryLabel = itemView.findViewById(R.id.text_category_rvitem);
        amountLabel = itemView.findViewById(R.id.text_amount_rvitem);
        infoIcon = itemView.findViewById(R.id.image_info_rvitem);

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

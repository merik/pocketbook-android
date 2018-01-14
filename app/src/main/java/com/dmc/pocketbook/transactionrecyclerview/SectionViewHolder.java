package com.dmc.pocketbook.transactionrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.dmc.pocketbook.R;

/**
 * Created by chi on 13/1/18.
 */

public class SectionViewHolder extends ViewHolder {
    TextView title;

    public SectionViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.text_title_header);
    }
}

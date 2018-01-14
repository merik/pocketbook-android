package com.dmc.pocketbook.transactionrecyclerview;

import com.dmc.pocketbook.models.Transaction;
import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

/**
 * Created by chi on 13/1/18.
 */

public class SectionHeader implements Section<Transaction> {
    List<Transaction> transactions;
    String sectionTitle;

    public SectionHeader(List<Transaction> transactions, String title) {
        this.transactions = transactions;
        this.sectionTitle = title;
    }
    @Override
    public List<Transaction> getChildItems() {
        return transactions;
    }

}

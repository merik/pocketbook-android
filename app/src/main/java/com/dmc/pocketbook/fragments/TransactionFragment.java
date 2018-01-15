package com.dmc.pocketbook.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmc.pocketbook.R;
import com.dmc.pocketbook.models.Transaction;


public class TransactionFragment extends DialogFragment {


    private Transaction transaction;

    TextView txtAmount;
    TextView txtMainCategory;
    TextView txtSubCategory;
    TextView txtDay;
    TextView txtPayment;


    private OnTransactionFragmentListener mListener;

    public TransactionFragment() {
        // Required empty public constructor
    }


    public static TransactionFragment newInstance(Transaction transaction) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putParcelable("transaction", transaction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaction = getArguments().getParcelable("transaction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);

        txtSubCategory = rootView.findViewById(R.id.text_subcategory);
        txtMainCategory = rootView.findViewById(R.id.text_main_category);
        txtDay = rootView.findViewById(R.id.text_day);
        txtPayment = rootView.findViewById(R.id.text_payment);
        txtAmount = rootView.findViewById(R.id.text_amount);


        txtSubCategory.setBackground(roundedCornerBG(30));      // FIXME
        txtMainCategory.setBackground(roundedCornerBG(30));

        TextView txtX = rootView.findViewById(R.id.text_x);
        txtX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClosedButtonPressed();
            }
        });

        showTransaction();

        return rootView;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    private void showTransaction() {
        if (transaction != null) {
            txtPayment.setText(transaction.payment_type);
            if (transaction.hasCategory()) {
                txtMainCategory.setText(transaction.main_category);
                txtSubCategory.setText(transaction.sub_category);
            } else {
                txtMainCategory.setText(R.string.uncategorised);
                txtSubCategory.setText(R.string.uncategorised);
            }
            txtAmount.setText(transaction.getAmountAsMoney());
            txtDay.setText(transaction.getDay() + " " + transaction.getMonth());
        }
    }

    private GradientDrawable roundedCornerBG(Integer radius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(Color.TRANSPARENT);
        gd.setStroke(1, Color.GRAY);
        gd.setCornerRadius(radius);
        return gd;
    }


    public void onClosedButtonPressed() {
        //getActivity().getFragmentManager().popBackStack();
        if (mListener != null) {
            mListener.onTransactionFragmentClosePressed();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTransactionFragmentListener) {
            mListener = (OnTransactionFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTransactionFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnTransactionFragmentListener {
        // TODO: Update argument type and name
        void onTransactionFragmentClosePressed();
    }
}

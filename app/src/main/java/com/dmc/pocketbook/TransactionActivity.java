package com.dmc.pocketbook;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.dmc.pocketbook.models.Transaction;

public class TransactionActivity extends AppCompatActivity {

    TextView txtAmount;
    TextView txtMainCategory;
    TextView txtSubCategory;
    TextView txtDay;
    TextView txtPayment;

    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.activity_transaction);

        transaction = getIntent().getParcelableExtra("transaction");


        txtSubCategory = findViewById(R.id.text_subcategory);
        txtMainCategory = findViewById(R.id.text_main_category);
        txtDay = findViewById(R.id.text_day);
        txtPayment = findViewById(R.id.text_payment);
        txtAmount = findViewById(R.id.text_amount);


        txtSubCategory.setBackground(roundedCornerBG(30));      // FIXME
        txtMainCategory.setBackground(roundedCornerBG(30));

        TextView txtX = findViewById(R.id.text_x);
        txtX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        showTransaction();


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

}

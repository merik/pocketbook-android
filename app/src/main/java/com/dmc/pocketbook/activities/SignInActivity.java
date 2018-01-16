package com.dmc.pocketbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dmc.pocketbook.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.button_sign_in)
    public void onSignIn() {
        Intent intent = new Intent(this, RecentTransactionActivity.class);
        startActivity(intent);
    }
}

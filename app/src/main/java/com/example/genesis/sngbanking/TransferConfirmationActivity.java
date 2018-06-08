package com.example.genesis.sngbanking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;


public class TransferConfirmationActivity extends AppCompatActivity {

    double amount;
    private TextView tvName, tvAcc, tvDestName, tvDestAcc, tvAmount;
    BankAccount loginAcc, destAcc;
    Database.MyDbHelper mHelper = new Database.MyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_confirmation);

        loginAcc = (BankAccount) getIntent().getSerializableExtra("loginAcc");
        destAcc = (BankAccount) getIntent().getSerializableExtra("destAcc");
        amount = (Double) getIntent().getSerializableExtra("amount");

        tvName = (TextView) findViewById(R.id.tvName);
        tvAcc = (TextView) findViewById(R.id.tvAcc);
        tvDestName = (TextView) findViewById(R.id.tvDestName);
        tvDestAcc = (TextView) findViewById(R.id.tvDestAcc);
        tvAmount = (TextView) findViewById(R.id.tvAmount);

        tvName.setText(loginAcc.getFullName());
        tvAcc.setText(loginAcc.getAccoutNumber());
        tvDestName.setText(destAcc.getFullName());
        tvDestAcc.setText(destAcc.getAccoutNumber());
        tvAmount.setText(Double.toString(amount));

        Button btConfirm = (Button) findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickConfirm();
            }
        });

        Button btCancle = (Button) findViewById(R.id.btCancle);

        btCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickCancle();
            }
        });


    }


    public void onClickConfirm() {
        mHelper = new Database.MyDbHelper(this);

        loginAcc.transfer(destAcc,amount);

        mHelper.updateAcc(loginAcc);
        mHelper.updateAcc(destAcc);

        Log.i("sng","transfer: "+amount+" to: "+destAcc.getAccoutNumber()+", newBalanc="+loginAcc.getBalance());

        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("loginAcc",loginAcc);
        startActivity(intent);
    }

    public void onClickCancle() {
        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("loginAcc",loginAcc);
        startActivity(intent);
    }
}

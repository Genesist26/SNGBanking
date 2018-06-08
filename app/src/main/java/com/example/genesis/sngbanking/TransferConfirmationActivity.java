package com.example.genesis.sngbanking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import org.w3c.dom.Text;

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

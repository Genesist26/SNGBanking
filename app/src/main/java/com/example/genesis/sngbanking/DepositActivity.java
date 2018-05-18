package com.example.genesis.sngbanking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DepositActivity extends AppCompatActivity {

    private EditText etAmount;
    private TextView tvfname, tvlname, tvAccNumber;
    BankAccount loginAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        loginAcc = (BankAccount) getIntent().getSerializableExtra("loginAcc");

        tvfname = (TextView) findViewById(R.id.tvfname);
        tvlname = (TextView) findViewById(R.id.tvlname);
        tvAccNumber = (TextView) findViewById(R.id.tvAccNumber);
        etAmount = (EditText) findViewById(R.id.etAmount);

        tvfname.setText(loginAcc.getfName());
        tvlname.setText(loginAcc.getlname());
        tvAccNumber.setText(loginAcc.getAccoutNumber());


    }

    public void onClickSubmit(View v) {
        double amount = Double.parseDouble(etAmount.getText().toString());
        loginAcc.deposit(amount);
        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("loginAcc",loginAcc);
        startActivity(intent);
    }
}

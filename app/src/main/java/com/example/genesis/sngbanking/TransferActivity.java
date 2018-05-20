package com.example.genesis.sngbanking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TransferActivity extends AppCompatActivity {

    BankAccount loginAcc;
    private EditText etToAcc, etAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        loginAcc = (BankAccount) getIntent().getSerializableExtra("loginAcc");

        etToAcc = (EditText) findViewById(R.id.etToAcc);
        etAmount = (EditText) findViewById(R.id.etAmount);
    }

    public void onClickSubmit(View v) {
        String toAcc = etToAcc.toString();
        Double amount = Double.parseDouble(etAmount.toString());



        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("loginAcc",loginAcc);
        startActivity(intent);
    }
}

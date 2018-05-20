package com.example.genesis.sngbanking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class WithdrawActivity extends AppCompatActivity {

    BankAccount loginAcc;
    private EditText etAmount;
    private TextView tvfname, tvlname, tvAccNum;
    private Database.MyDbHelper mHelper = new Database.MyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        loginAcc = (BankAccount) getIntent().getSerializableExtra("loginAcc");

        etAmount = (EditText) findViewById(R.id.etAmount);
        tvfname  = (TextView) findViewById(R.id.tvfname);
        tvlname  = (TextView) findViewById(R.id.tvlname);
        tvAccNum = (TextView) findViewById(R.id.tvAccNum);

        tvfname.setText(loginAcc.getfName());
        tvlname.setText(loginAcc.getlName());
        tvAccNum.setText(loginAcc.getAccoutNumber());
    }

    public void onClickSubmit(View v) {
        double amount = Double.parseDouble(etAmount.getText().toString());
        boolean res = loginAcc.withdraw(amount);
        if(res == true){
            mHelper.updateAcc(loginAcc);
            Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
            // direct to transaction resualt
            finish();
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("loginAcc",loginAcc);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Insufficient Funds!!!", Toast.LENGTH_SHORT).show();
        }
    }

}

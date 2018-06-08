package com.example.genesis.sngbanking;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TransferActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    BankAccount loginAcc;
    private EditText etToAcc, etAmount;
    Database.MyDbHelper mHelper = new Database.MyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        loginAcc = (BankAccount) getIntent().getSerializableExtra("loginAcc");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        etToAcc = (EditText) findViewById(R.id.etToAcc);
        etAmount = (EditText) findViewById(R.id.etAmount);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_transfer) {
            finish();
            intent = new Intent(this, TransferActivity.class);
            intent.putExtra("loginAcc",loginAcc);
            startActivity(intent);
        } else if (id == R.id.nav_deposit) {
            finish();
            intent = new Intent(this, DepositActivity.class);
            intent.putExtra("loginAcc",loginAcc);
            startActivity(intent);
        } else if (id == R.id.nav_withdraw) {
            finish();
            intent = new Intent(this, WithdrawActivity.class);
            intent.putExtra("loginAcc",loginAcc);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("sng","Transfer Activity");
    }

    public void onClickSubmit(View v) {
        BankAccount destAcc;
        String destAccNumber = etToAcc.getText().toString();
        String getAmount = etAmount.getText().toString();
        if(destAccNumber.isEmpty() || getAmount.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please fill in all information", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Double amount = Double.parseDouble(etAmount.getText().toString());

            mHelper = new Database.MyDbHelper(this);
            destAcc = mHelper.getBankAcc(destAccNumber);

            if(destAcc == null || destAccNumber.equals(Long.toString(loginAcc.getAccNumber()))){
                Toast.makeText(this, "Eror: Destination account invalid!",Toast.LENGTH_SHORT).show();
            }
            else {
                if(loginAcc.getBalance() < amount){
                    Toast.makeText(this, "Eror: Insufficient funds ",Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                    Intent intent = new Intent(this, TransferConfirmationActivity.class);
                    intent.putExtra("loginAcc",loginAcc);
                    intent.putExtra("destAcc",destAcc);
                    intent.putExtra("amount",amount);
                    startActivity(intent);

                    Log.i("sng","need confirm transfering: "+amount+" to: "+destAccNumber);
                }
            }
        }

    }
    public void onClickCancle(View v) {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("loginAcc",loginAcc);
        startActivity(intent);
    }
}
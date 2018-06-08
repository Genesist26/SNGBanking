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
import android.widget.TextView;
import android.widget.Toast;

public class DepositActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private EditText etAmount;
    private TextView tvName, tvAccNumber;
    BankAccount loginAcc;
    private Database.MyDbHelper mHelper = new Database.MyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

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

        tvName = (TextView) findViewById(R.id.tvName);
        tvAccNumber = (TextView) findViewById(R.id.tvAccNumber);
        etAmount = (EditText) findViewById(R.id.etAmount);

        tvName.setText(loginAcc.getFullName());
        tvAccNumber.setText(loginAcc.getAccoutNumber());


    }

    public void onClickSubmit(View v) {
        String getAmount = etAmount.getText().toString();
        if(!getAmount.isEmpty()){
            double amount = Double.parseDouble(getAmount);
            loginAcc.deposit(amount);
            mHelper.updateAcc(loginAcc);
            Toast.makeText(getApplicationContext(), "Transaction Completed", Toast.LENGTH_SHORT).show();
            Log.i("sng","Deposit: "+amount+", newBalanc="+loginAcc.getBalance());
            finish();
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("loginAcc",loginAcc);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
        }

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
        Log.i("sng","Deposit Activity");
    }


    public void onClickCancle(View v) {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("loginAcc",loginAcc);
        startActivity(intent);
    }

}

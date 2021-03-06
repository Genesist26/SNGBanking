package com.example.genesis.sngbanking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvCustName;
    private TextView tvCustAccNum;
    private TextView tvCustBalance;
    private BankAccount loginAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MenuActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.month));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tvCustName = (TextView) findViewById(R.id.tvCustName);
        tvCustAccNum = (TextView) findViewById(R.id.tvCustAccNum);
        tvCustBalance = (TextView) findViewById(R.id.tvCustBalance);

        tvCustName.setText(loginAcc.getFullName());
        tvCustAccNum.setText(loginAcc.getAccountNumber());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Log.i("sng", "Logout success user=["+loginAcc.getFullName()+"]");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            Log.i("sng", "Logout success, user=["+loginAcc.getFullName()+"]");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Double re = loginAcc.getBalance();
        tvCustBalance.setText(""+re);
        //Toast.makeText(this,"Refresh balance = ",Toast.LENGTH_SHORT);

    }

    @Override
    protected void onPause(){
        super.onPause();
    }
}

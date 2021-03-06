package com.example.genesis.sngbanking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.audiofx.AcousticEchoCanceler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button btLogin, btSignup;
    private EditText etEmail, etPass;
    SQLiteDatabase mDb;
    Cursor mCursor;
    Authen auth;
    SharedPreferences prefs = null;
    Database.MyDbHelper mHelper = new Database.MyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.example.genesis.sngbanking", MODE_PRIVATE);

        btLogin = (Button) findViewById(R.id.btLogin);
        btSignup = (Button) findViewById(R.id.btSignup);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass  = (EditText) findViewById(R.id.etPass);

        firstRun();

    }

    public void onClickLogin(View v){

        String loginMail = etEmail.getText().toString();
        String loginPass = etPass.getText().toString();
        String pin;

        if(loginMail.isEmpty() || loginPass.isEmpty()){
            Toast.makeText(this, "Empty email or password", Toast.LENGTH_SHORT).show();
        }
        else if(!isEmailValid(loginMail)) {
            Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show();
        }
        else if(loginPass.length() < 8){
            Toast.makeText(this, "Passwords must be at least 8 characters", Toast.LENGTH_SHORT).show();
        }
        else {
            mHelper = new Database.MyDbHelper(this);
            auth = mHelper.getAuthen(loginMail, loginPass);

            // is record exists
            if (auth == null)
                Toast.makeText(this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
            else {
                Log.i("sng", "Login success user=["+auth.getEmail()+"], acc=["+auth.getAccountNumber()+"]");
                BankAccount loginAcc = mHelper.getBankAcc(auth.getAccountNumber());

                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("loginAcc", loginAcc);
                startActivity(intent);
            }
        }
    }

    public void setOnClickSignup(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void firstRun() {
        // check first use
        if (prefs.getBoolean("firstrun", true)) {

            Log.i("sng","detected firstRun");
            Log.i("sng","----------------------------------");
            Log.i("sng","created sample account");
            Log.i("sng","user=test1     pass=testtest");
            Log.i("sng","user=test2     pass=testtest");
            Log.i("sng","user=test3     pass=testtest");
            Log.i("sng","----------------------------------");

            BankAccount t1 = new BankAccount("test1","test1",0);
            Authen tt1 = new Authen(t1.getAccountNumber(), "test1@test.com","testtest");
            BankAccount t2 = new BankAccount("test2","test2",0);
            Authen tt2 = new Authen(t2.getAccountNumber(), "test2@test.com","testtest");
            BankAccount t3 = new BankAccount("test3","test3",0);
            Authen tt3 = new Authen(t3.getAccountNumber(), "test3@test.com","testtest");

            //add sample accout
            mHelper.addAcc(t1);
            mHelper.addAuthen(tt1);
            mHelper.addAcc(t2);
            mHelper.addAuthen(tt2);
            mHelper.addAcc(t3);
            mHelper.addAuthen(tt3);


            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}


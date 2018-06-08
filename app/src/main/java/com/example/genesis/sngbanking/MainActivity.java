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

public class MainActivity extends AppCompatActivity {

    private Button btLogin, btSignup;
    private EditText etEmail, etPass;
    SQLiteDatabase mDb;
    Cursor mCursor;
    BankAccount loginAcc;
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
        else {
            mHelper = new Database.MyDbHelper(this);
            loginAcc = mHelper.getBankAcc(loginMail, loginPass);

            // is record exists
            if (loginAcc == null)
                Toast.makeText(this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
            else {
                Log.i("sng", "Login success user=["+loginAcc.getEmail()+"], acc=["+loginAcc.getAccoutNumber()+"]");
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


        long lastAccNumber = 4823521350L;
        // check first use
        if (prefs.getBoolean("firstrun", true)) {

            Log.i("sng","detected firstRun");
            Log.i("sng","----------------------------------");
            Log.i("sng","created sample account");
            Log.i("sng","user=test      pass=test");
            Log.i("sng","user=test2     pass=test");
            Log.i("sng","user=Somsak    pass=Binarwaeloh");
            Log.i("sng","user=Chantapat pass=Sopontanasiri");
            Log.i("sng","user=Nalina    pass=Witee");
            Log.i("sng","----------------------------------");

            BankAccount t1 = new BankAccount("test","test","test","test",0);
            BankAccount t2 = new BankAccount("test2","test2","test2","test",0);
            BankAccount t3 = new BankAccount("Somsak","Binarwaeloh","somsakwp8@gmail.com","1234",0);
            BankAccount t4 = new BankAccount("Chantapat","Sopontanasiri","sunsun@gmail.com","1234",0);
            BankAccount t5 = new BankAccount("Nalina","Witee","nalina@gmail.com","1234",0);

            //add sample accout
            mHelper.addAcc(t1);
            mHelper.addAcc(t2);
            mHelper.addAcc(t3);
            mHelper.addAcc(t4);
            mHelper.addAcc(t5);

            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}


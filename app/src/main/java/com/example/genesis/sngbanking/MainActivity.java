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
            Toast errorToast = Toast.makeText(this, "Empty email or password", Toast.LENGTH_SHORT);
            errorToast.show();
        } else {
            mHelper = new Database.MyDbHelper(this);
            mDb = mHelper.getWritableDatabase();
/*
            mCursor = mDb.rawQuery("SELECT " + Database.MyDbHelper.COL_FIRSTNAME + ", "
                    + Database.MyDbHelper.COL_LASTNAME + ", "  + Database.MyDbHelper.COL_ACCNUMBER
                    + ", "  + Database.MyDbHelper.COL_EMAIL + ", "  + Database.MyDbHelper.COL_PASS
                    + ", "  + Database.MyDbHelper.COL_BALANCE + " FROM " + Database.MyDbHelper.TABLE_NAME
                    + " WHERE " + Database.MyDbHelper.COL_EMAIL + " = ? AND "
                    + Database.MyDbHelper.COL_PASS + " = ?", new String[] {loginMail, loginPass});
*/

            mCursor = mDb.rawQuery("SELECT *"+
                    " FROM " + Database.MyDbHelper.TABLE_NAME +
                    " WHERE " + Database.MyDbHelper.COL_EMAIL + " = ? AND "
                    + Database.MyDbHelper.COL_PASS + " = ?", new String[] {loginMail, loginPass});

            /*
            String query = "Select * from " + Database.MyDbHelper.TABLE_NAME +
                    " where " + Database.MyDbHelper.COL_EMAIL + " = ?" + loginMail +
                    " AND "   + Database.MyDbHelper.COL_PASS +  " = ?" + loginPass;

            mCursor = mDb.rawQuery(query, null);
            */
            if (mCursor.moveToFirst()) {
                loginAcc = new BankAccount(mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_FIRSTNAME)),
                        mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_LASTNAME)),
                        mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_EMAIL)),
                        mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_PASS)),
                        mCursor.getDouble(mCursor.getColumnIndex(Database.MyDbHelper.COL_BALANCE)));
            }

            if(loginAcc == null) {
                Toast errorToast = Toast.makeText(this, "Email or Password incorrect", Toast.LENGTH_SHORT);
                errorToast.show();
            }
            else {
                Log.i("Info", "Login success");
                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("loginAcc",loginAcc);
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

            BankAccount a = new BankAccount("test","test","test","test",0);
            BankAccount b = new BankAccount("Somsak","Binarwaeloh","somsakwp8@gmail.com","1234",0);
            BankAccount c = new BankAccount("Chantapat","Sopontanasiri","sunsun@gmail.com","1234",0);
            BankAccount d = new BankAccount("Nalina","Witee","nalina@gmail.com","1234",0);
            //add sample accout
            mHelper.addAcc(a);
            mHelper.addAcc(b);
            mHelper.addAcc(c);
            mHelper.addAcc(d);


            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}


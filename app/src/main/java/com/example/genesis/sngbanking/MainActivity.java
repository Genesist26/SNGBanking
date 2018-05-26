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
    public static Account aList;
    SQLiteDatabase mDb;
    Database.MyDbHelper mHelper;
    Cursor mCursor;
    BankAccount loginAcc;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.example.genesis.sngbanking", MODE_PRIVATE);

        btLogin = (Button) findViewById(R.id.btLogin);
        btSignup = (Button) findViewById(R.id.btSignup);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass  = (EditText) findViewById(R.id.etPass);

        firstUse();

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
            mCursor = mDb.rawQuery("SELECT " + Database.MyDbHelper.COL_FIRSTNAME + ", "
                    + Database.MyDbHelper.COL_LASTNAME + ", "  + Database.MyDbHelper.COL_ACCNUMBER
                    + ", "  + Database.MyDbHelper.COL_EMAIL + ", "  + Database.MyDbHelper.COL_PASS
                    + ", "  + Database.MyDbHelper.COL_BALANCE + " FROM " + Database.MyDbHelper.TABLE_NAME
                    + " WHERE " + Database.MyDbHelper.COL_EMAIL + " = ? AND "
                    + Database.MyDbHelper.COL_PASS + " = ?", new String[] {loginMail, loginPass});
            Log.i("sng","LINE:58");
            if (mCursor.moveToFirst()) {
                loginAcc = new BankAccount(mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_FIRSTNAME)),
                        mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_LASTNAME)),
                        mCursor.getInt(mCursor.getColumnIndex(Database.MyDbHelper.COL_ACCNUMBER)),
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

    public void firstUse() {
        Database.MyDbHelper mHelper = new Database.MyDbHelper(this);

        // check first use
        if (prefs.getBoolean("firstrun", true)) {

            //add sample accout
            mHelper.addAcc("test","test","test","test");
            mHelper.addAcc("Somsak","Binarwaeloh","somsakwp8@gmail.com","1234");
            mHelper.addAcc("Chantapat","Sopontanasiri","sunsun@gmail.com","1234");
            mHelper.addAcc("Nalina","Witee","nalina@gmail.com","1234");

            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}


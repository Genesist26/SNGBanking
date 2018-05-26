package com.example.genesis.sngbanking;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aList = new Account();
        aList.addAccount("TestUser","TestUser",1,"test","1234",9999);
        aList.addAccount("Genesis","G",2,"gene","1234",9999);
        aList.addAccount("nalina","N",3,"nalina","1234",9999);
        aList.addAccount("chantapat","C",4,"chantapat","1234",9999);

        btLogin = (Button) findViewById(R.id.btLogin);
        btSignup = (Button) findViewById(R.id.btSignup);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass  = (EditText) findViewById(R.id.etPass);


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

            if (mCursor.moveToFirst()) {
                loginAcc = new BankAccount(mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_FIRSTNAME)),
                        mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_LASTNAME)),
                        mCursor.getInt(mCursor.getColumnIndex(Database.MyDbHelper.COL_ACCNUMBER)),
                        mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_EMAIL)),
                        mCursor.getString(mCursor.getColumnIndex(Database.MyDbHelper.COL_PASS)),
                        mCursor.getDouble(mCursor.getColumnIndex(Database.MyDbHelper.COL_BALANCE)));
            }

//            loginAcc = aList.search(loginMail, loginPass);
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

}

